package coffer.androidDemo.messageDemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2019-12-07
 * @Description： 延时发消息
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class MessageTestActivity extends AppCompatActivity {

    private static final String TAG = "MessageTestActivity_TAG";
    private static final int M1 = 0x01;

    private long mBaseTime = 1000L;
    private EditText mEditText;
    private TextView mTv1;
    private TextView mTv2;
    private TimeHandler mHandler;

    private static long mStartTime;

    // Java提供的定时器任务
    private Timer mTimer = new Timer();
    private TimerTask mTimerTask = null;

    // android 推荐的定时器任务
    ScheduledExecutorService mService = Executors.newScheduledThreadPool(5);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_main);
        mHandler = new TimeHandler(this);

        mEditText = findViewById(R.id.edit);
        mTv1 = findViewById(R.id.tv1);
        mTv2 = findViewById(R.id.tv2);

        // 使用Handler 发送延时消息
        findViewById(R.id.send1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendHandlerDelayMessage();
            }
        });

        // 使用Java定时器
        findViewById(R.id.send2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTimerTaskDelayMessage();
            }
        });

        // 使用Android定时器
        findViewById(R.id.send3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartTime = System.currentTimeMillis();
                Log.d(TAG, "mStartTime : " + mStartTime);
                mService.schedule(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "话费3 : " +(System.currentTimeMillis() - mStartTime));
                    }
                },5, TimeUnit.SECONDS);
            }
        });

    }

    /**
     * 使用Handler发送延时消息
     */
    private void sendHandlerDelayMessage() {
        setTime();
        mStartTime = System.currentTimeMillis();
        Log.d(TAG, "mStartTime : " + mStartTime);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mHandler.sendEmptyMessageDelayed(M1, mBaseTime);
                    Thread.sleep(10*mBaseTime);
                    Log.d(TAG, "睡眠结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static class TimeHandler extends Handler {

        private WeakReference<MessageTestActivity> mMessageTestActivity;

        TimeHandler(MessageTestActivity activity) {
            this.mMessageTestActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case M1:
                    Log.d(TAG, "话费1 : " +(System.currentTimeMillis() - mStartTime));
                    mMessageTestActivity.get().mTv1.setText("消息1：收到");
                default:
                    break;

            }
        }
    }

    /**
     * 使用Java 的Timer 和 TimerTask 处理延时消息
     */
    private void sendTimerTaskDelayMessage() {
        mStartTime = System.currentTimeMillis();
        Log.d(TAG, "mStartTime : " + mStartTime);
        setTime();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "话费2 : " +(System.currentTimeMillis() - mStartTime));
                Log.i(TAG,"当前线程的状态: "+Thread.currentThread().getName());
            }
        };
        mTimer.schedule(mTimerTask,mBaseTime);
    }

    private void setTime(){
        String time = mEditText.getText().toString();
        if (TextUtils.isEmpty(time)) {
            Toast.makeText(MessageTestActivity.this, "时间空了", Toast.LENGTH_SHORT).show();
            time = "3";
        }
        mBaseTime = Long.parseLong(time) * mBaseTime;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler = null;
        }
    }
}
