package networkDemo;

import android.view.View;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.util.CofferLog;
import networkDemo.retrofit.BookService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author：张宝全
 * @date：2020/5/31
 * @Description： Retrofit 学习
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class RetrofitActivity extends BaseDefaultActivity {

    @Override
    public void initView() {
        setContentView(R.layout.activity_retrofit_main);
    }

    @Override
    public void initData() {
        findViewById(R.id.use).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNet();
            }
        });
    }

    private void requestNet() {
        // 1、创建Retrofit 的实例。（里面默认的数据解析工具是Gson）
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .build();

        // 2、创建一个请求接口BookService 里面声明一个请求类型的方法。
        // 3、创建一个Call实例，retrofit使用动态代理创建一个刚刚声明的接口实现
        BookService bookService = retrofit.create(BookService.class);
        Call<ResponseBody> call = bookService.getBook(1220562);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                CofferLog.D("coffer_retrofit",response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                CofferLog.D("coffer_retrofit",t.toString());
            }
        });

    }
}
