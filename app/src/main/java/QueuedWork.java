///*
// * Copyright (C) 2010 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.os.Looper;
//import android.os.Message;
//import android.os.Process;
//import android.os.StrictMode;
//import android.util.Log;
//
//import com.android.internal.annotations.GuardedBy;
//import com.android.internal.util.ExponentiallyBucketedHistogram;
//
//import java.util.LinkedList;
//
///**
// * Internal utility class to keep track of process-global work that's outstanding and hasn't been
// * finished yet.
// *
// * New work will be {@link #queue queued}.
// *
// * It is possible to add 'finisher'-runnables that are {@link #waitToFinish guaranteed to be run}.
// * This is used to make sure the work has been finished.
// *
// * This was created for writing SharedPreference edits out asynchronously so we'd have a mechanism
// * to wait for the writes in Activity.onPause and similar places, but we may use this mechanism for
// * other things in the future.
// *
// * The queued asynchronous work is performed on a separate, dedicated thread.
// *
// *
// *
// * @hide
// */
//
///**
// * 这是个内部 工具 类，用于跟踪那些未完成的或尚未结束的全局任务，
// * 新任务通过方法 queue 加入。添加 finisher 的runnables，
// * 由 waitToFinish 方法保证执行，用于保证任务已被处理完成。
// *
// * 这个类用于 SharedPreference 编辑后修改异步写入到磁盘，
// * 所以设计一个在 Activity.onPause 或类似地方等待写入操作机制，
// * 而这个机制也能用于其他功能。所有排队的异步任务都在一个独立、专用的线程上处理。
//
// */
//public class QueuedWork {
//    private static final String LOG_TAG = QueuedWork.class.getSimpleName();
//    private static final boolean DEBUG = false;
//
//    /** Delay for delayed runnables, as big as possible but low enough to be barely perceivable */
//    private static final long DELAY = 100;
//
//    /** If a {@link #waitToFinish()} takes more than {@value #MAX_WAIT_TIME_MILLIS} ms, warn */
//    private static final long MAX_WAIT_TIME_MILLIS = 512;
//
//    /** Lock for this class */
//    private static final Object sLock = new Object();
//
//    /**
//     * Used to make sure that only one thread is processing work items at a time. This means that
//     * they are processed in the order added.
//     *
//     * This is separate from {@link #sLock} as this is held the whole time while work is processed
//     * and we do not want to stall the whole class.
//     */
//    private static Object sProcessingWork = new Object();
//
//    /** Finishers {@link #addFinisher added} and not yet {@link #removeFinisher removed} */
//    @GuardedBy("sLock")
//    private static final LinkedList<Runnable> sFinishers = new LinkedList<>();
//
//    /** {@link #getHandler() Lazily} created handler */
//    @GuardedBy("sLock")
//    private static Handler sHandler = null;
//
//    /** Work queued via {@link #queue} */
//    @GuardedBy("sLock")
//    private static final LinkedList<Runnable> sWork = new LinkedList<>();
//
//    /** If new work can be delayed or not */
//    @GuardedBy("sLock")
//    private static boolean sCanDelay = true;
//
//    /** Time (and number of instances) waited for work to get processed */
//    @GuardedBy("sLock")
//    private final static ExponentiallyBucketedHistogram
//            mWaitTimes = new ExponentiallyBucketedHistogram(
//            16);
//    private static int mNumWaits = 0;
//
//    /**
//     * Lazily create a handler on a separate thread.
//     *
//     * @return the handler
//     */
//    private static Handler getHandler() {
//        synchronized (sLock) {
//            if (sHandler == null) {
//                HandlerThread handlerThread = new HandlerThread("queued-work-looper",
//                        Process.THREAD_PRIORITY_FOREGROUND);
//                handlerThread.start();
//
//                sHandler = new QueuedWorkHandler(handlerThread.getLooper());
//            }
//            return sHandler;
//        }
//    }
//
//    /**
//     * Add a finisher-runnable to wait for {@link #queue asynchronously processed work}.
//     *
//     * Used by SharedPreferences$Editor#startCommit().
//     *
//     * Note that this doesn't actually start it running.  This is just a scratch set for callers
//     * doing async work to keep updated with what's in-flight. In the common case, caller code
//     * (e.g. SharedPreferences) will pretty quickly call remove() after an add(). The only time
//     * these Runnables are run is from {@link #waitToFinish}.
//     *
//     * @param finisher The runnable to add as finisher
//     */
//    public static void addFinisher(Runnable finisher) {
//        synchronized (sLock) {
//            sFinishers.add(finisher);
//        }
//    }
//
//    /**
//     * Remove a previously {@link #addFinisher added} finisher-runnable.
//     *
//     * @param finisher The runnable to remove.
//     */
//    public static void removeFinisher(Runnable finisher) {
//        synchronized (sLock) {
//            sFinishers.remove(finisher);
//        }
//    }
//
//    /**
//     * Trigger queued work to be processed immediately. The queued work is processed on a separate
//     * thread asynchronous. While doing that run and process all finishers on this thread. The
//     * finishers can be implemented in a way to check weather the queued work is finished.
//     *
//     * Is called from the Activity base class's onPause(), after BroadcastReceiver's onReceive,
//     * after Service command handling, etc. (so async work is never lost)
//     */
//    // waitToFinish() 方法就做了一件事，会一直等待写入任务执行完毕，其它什么都不做，
//    // 当有很多写入任务，会依次执行，当文件很大时，效率很低，造成 ANR 就不奇怪了
//    public static void waitToFinish() {
//        long startTime = System.currentTimeMillis();
//        boolean hadMessages = false;
//        // 这里使用的是HandlerThread执行任务
//        Handler handler = getHandler();
//
//        synchronized (sLock) {
//            if (handler.hasMessages(QueuedWorkHandler.MSG_RUN)) {
//                // Delayed work will be processed at processPendingWork() below
//                handler.removeMessages(QueuedWorkHandler.MSG_RUN);
//
//                if (DEBUG) {
//                    hadMessages = true;
//                    Log.d(LOG_TAG, "waiting");
//                }
//            }
//
//            // We should not delay any work as this might delay the finishers
//            sCanDelay = false;
//        }
//
//        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
//        try {
//            processPendingWork();
//        } finally {
//            StrictMode.setThreadPolicy(oldPolicy);
//        }
//
//        try {
//            while (true) {
//                Runnable finisher;
//
//                synchronized (sLock) {
//                    // finisher 里存的是apply时提交的任务，取任务执行
//                    // 相当于调用 mcr.writtenToDiskLatch.await() 方法，会一直等待。
//                    finisher = sFinishers.poll();
//                }
//
//                if (finisher == null) {
//                    break;
//                }
//
//                finisher.run();
//            }
//        } finally {
//            sCanDelay = true;
//        }
//
//        synchronized (sLock) {
//            long waitTime = System.currentTimeMillis() - startTime;
//
//            if (waitTime > 0 || hadMessages) {
//                mWaitTimes.add(Long.valueOf(waitTime).intValue());
//                mNumWaits++;
//
//                if (DEBUG || mNumWaits % 1024 == 0 || waitTime > MAX_WAIT_TIME_MILLIS) {
//                    mWaitTimes.log(LOG_TAG, "waited: ");
//                }
//            }
//        }
//    }
//
//    /**
//     * Queue a work-runnable for processing asynchronously.
//     *
//     * @param work The new runnable to process
//     * @param shouldDelay If the message should be delayed
//     */
//    public static void queue(Runnable work, boolean shouldDelay) {
//        Handler handler = getHandler();
//
//        synchronized (sLock) {
//            // 将任务塞到队列中
//            sWork.add(work);
//            // 通过handler发送消息
//            if (shouldDelay && sCanDelay) {
//                handler.sendEmptyMessageDelayed(QueuedWorkHandler.MSG_RUN, DELAY);
//            } else {
//                handler.sendEmptyMessage(QueuedWorkHandler.MSG_RUN);
//            }
//        }
//    }
//
//    /**
//     * @return True iff there is any {@link #queue async work queued}.
//     */
//    public static boolean hasPendingWork() {
//        synchronized (sLock) {
//            return !sWork.isEmpty();
//        }
//    }
//
//    private static void processPendingWork() {
//        long startTime = 0;
//
//        if (DEBUG) {
//            startTime = System.currentTimeMillis();
//        }
//
//        synchronized (sProcessingWork) {
//            LinkedList<Runnable> work;
//
//            synchronized (sLock) {
//                work = (LinkedList<Runnable>) sWork.clone();
//                sWork.clear();
//
//                // Remove all msg-s as all work will be processed now
//                getHandler().removeMessages(QueuedWorkHandler.MSG_RUN);
//            }
//
//            if (work.size() > 0) {
//                for (Runnable w : work) {
//                    //执行清理任务，也就是QueuedWork.queue(writeToDiskRunnable, !isFromSyncCommit);
//                    w.run();
//                }
//
//                if (DEBUG) {
//                    Log.d(LOG_TAG, "processing " + work.size() + " items took " +
//                            +(System.currentTimeMillis() - startTime) + " ms");
//                }
//            }
//        }
//    }
//
//    private static class QueuedWorkHandler extends Handler {
//        static final int MSG_RUN = 1;
//
//        QueuedWorkHandler(Looper looper) {
//            super(looper);
//        }
//
//        public void handleMessage(Message msg) {
//            if (msg.what == MSG_RUN) {
//                processPendingWork();
//            }
//        }
//    }
//}
