package networkDemo.okhttpDemo;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;
import okhttp3.Handshake;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author：张宝全
 * @date：2020/5/31
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class OkHttpEventListener extends EventListener {

    public static final Factory FACTORY = new Factory() {
        @Override
        public EventListener create(Call call) {
            return new OkHttpEventListener();
        }
    };

    OkhttpEvent okhttpEvent;

    public OkHttpEventListener() {
        super();
        okhttpEvent = new OkhttpEvent();
    }

    @Override
    public void callStart(Call call) {
        super.callStart(call);
    }

    @Override
    public void dnsStart(Call call, String domainName) {
        super.dnsStart(call, domainName);
        okhttpEvent.dnsStartTime = System.currentTimeMillis();
    }

    @Override
    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        super.dnsEnd(call, domainName, inetAddressList);
        okhttpEvent.dnsEndTime = System.currentTimeMillis();
    }

    @Override
    public void connectStart(Call call, InetSocketAddress inetSocketAddress, Proxy proxy) {
        super.connectStart(call, inetSocketAddress, proxy);
    }

    @Override
    public void secureConnectStart(Call call) {
        super.secureConnectStart(call);
    }

    @Override
    public void secureConnectEnd(Call call, @Nullable Handshake handshake) {
        super.secureConnectEnd(call, handshake);
    }

    @Override
    public void connectEnd(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol);
    }

    @Override
    public void connectFailed(Call call, InetSocketAddress inetSocketAddress, Proxy proxy, @Nullable Protocol protocol, IOException ioe) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
    }

    @Override
    public void connectionAcquired(Call call, Connection connection) {
        super.connectionAcquired(call, connection);
    }

    @Override
    public void connectionReleased(Call call, Connection connection) {
        super.connectionReleased(call, connection);
    }

    @Override
    public void requestHeadersStart(Call call) {
        super.requestHeadersStart(call);
    }

    @Override
    public void requestHeadersEnd(Call call, Request request) {
        super.requestHeadersEnd(call, request);
    }

    @Override
    public void requestBodyStart(Call call) {
        super.requestBodyStart(call);
    }

    @Override
    public void requestBodyEnd(Call call, long byteCount) {
        super.requestBodyEnd(call, byteCount);
        // byteCount 网络请求返回值的大小，掌阅的OOM中经常出现返回的数据过大而OOM，这里可以监控
        // 也可统计流量消耗
        okhttpEvent.byteCount = byteCount;
    }

    @Override
    public void responseHeadersStart(Call call) {
        super.responseHeadersStart(call);
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        super.responseHeadersEnd(call, response);
    }

    @Override
    public void responseBodyStart(Call call) {
        super.responseBodyStart(call);
    }

    @Override
    public void responseBodyEnd(Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
    }

    @Override
    public void callEnd(Call call) {
        super.callEnd(call);
    }

    @Override
    public void callFailed(Call call, IOException ioe) {
        super.callFailed(call, ioe);
    }
}

