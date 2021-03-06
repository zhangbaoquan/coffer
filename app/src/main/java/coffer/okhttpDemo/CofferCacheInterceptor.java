package coffer.okhttpDemo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author：张宝全
 * @date：2019-10-27
 * @Description： 自定义缓存拦截器
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CofferCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Response response1 = response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                //cache for 30 days
                .header("Cache-Control", "max-age=" + 3600 * 24 * 30)
                .build();
        return response1;
    }
}
