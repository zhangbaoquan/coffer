package networkDemo.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author：张宝全
 * @date：2020/11/28
 * @Description：声明Book相关的网络请求
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public interface BookService {

    /**
     * 定义了一个getBook方法，其返回类型为retrofit的Call类型，尖括号里是okhttp的ResponseBody类型
     * GET注解的作用是声明采用GET的方法进行网络请求
     * Path注解的作用是声明其之后的 int id 参数，将以路径的形式被替换到@GET注解括号里的{id}处，
     * 比如后面调用getBook方法时传入参数1111，注解Get就会将这里转化成book/111，最后还需拼接到之前在创建
     * retrofit 对象时设置的BaseUrl 后面，例如 ： https://api.douban.com/v2/book/1111
     *
     * @param id
     * @return
     */
    @GET("book/{id}")
    Call<ResponseBody> getBook(@Path("id") int id);
}
