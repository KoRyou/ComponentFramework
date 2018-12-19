package zgl.com.cn.libandroid.net.network.retrofit;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import zgl.com.cn.libandroid.net.network.bean.ZRequest;
import zgl.com.cn.libandroid.net.network.bean.ZResponse;


/**
 *  描述：网络架构--retrofit请求的service接口
 *
 * @author : jsj_android
 * @date : 2018/11/8
 */

public interface ApiService {

    /**
     * 注意：如果方法的泛型指定的类不是ResponseBody,retrofit会将返回的string用json转换器自动转换该类的一个对象,转换不成功就报错
     * 如果不需要Gson转换,那么就指定泛型为ResponseBody,只能是ResponseBody,子类都不行.
     */
    @GET("/")
    Call<ResponseBody> executeGet(@HeaderMap Map<String, String> headers, @Url String url);

    /**
     * POST方式将以表单的方式传递键值对作为请求体发送到服务器
     * 其中@FormUrlEncoded 以表单的方式传递键值对
     * 其中 @Path：所有在网址中的参数（URL的问号前面）
     * 另外@FieldMap 用于POST请求，提交多个表单数据，@Field：用于POST请求，提交单个数据
     * 使用@url是为了防止URL被转义为https://10.33.31.200:8890/msp%2Fmobile%2Flogin%3
     */
    @FormUrlEncoded
    @POST("/{url}")
    Call<ResponseBody> executePost(@Path("url") String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, String> map);


    /**
     * 为Protobuffer文件提供的（兼容旧请求）
     * @param headers
     * @param baseReq
     * @return
     */
    @POST("/{url}")
    Call<ZResponse> executeProtoPost(@Path("url") String url, @HeaderMap Map<String, String> headers, @Body ZRequest baseReq);

   /* @POST("/")
    Call<ResponseBody> executeProtoPost( @HeaderMap Map<String, String> headers, @Body RequestBody baseReq);
*/

}
