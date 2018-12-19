package zgl.com.cn.libandroid.net.netcore.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import zgl.com.cn.libandroid.net.netcore.callback.IProgress;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/12/4
 */

public class ProgressInterceptor implements Interceptor {

    private IProgress listener ;

    public ProgressInterceptor(IProgress listener){
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body(), listener))
                .build();
    }

}
