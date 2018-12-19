package zgl.com.cn.libandroid.net.netcore.retrofit;

import android.support.annotation.Nullable;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import zgl.com.cn.libandroid.net.network.bean.ZRequest;
import zgl.com.cn.libandroid.net.network.bean.ZResponse;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/11/30
 */

public interface ApiService {

    @GET
    Call<ResponseBody> get(@Url String url,@HeaderMap Map<String, String> headers, @QueryMap Map<String,Object> params);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> post(@Url String url,@HeaderMap Map<String, String> headers, @FieldMap Map<String,Object> params);

    @POST
    Call<ResponseBody> postRaw(@Url String url,@HeaderMap Map<String, String> headers, @Body RequestBody body);

    @POST
    Call<ZResponse> postProto(@Url String url, @HeaderMap Map<String, String> headers, @Body ZRequest baseReq);



    @FormUrlEncoded
    @PUT
    Call<ResponseBody> put(@Url String url,@HeaderMap Map<String, String> headers, @FieldMap Map<String,Object> params);

    @DELETE
    Call<ResponseBody> delete(@Url String url, @QueryMap Map<String,Object> params);



    /**
     * 下载是直接到内存,所以需要 @Streaming
     * @param url
     * @param headers  用于断点续传的请求头  range:bytes=300-
     * @param params
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url,@HeaderMap Map<String, String> headers);

    /**
     * 上传文件
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST
    Call<ResponseBody> upload(@Url String url, @Part MultipartBody.Part file);




}
