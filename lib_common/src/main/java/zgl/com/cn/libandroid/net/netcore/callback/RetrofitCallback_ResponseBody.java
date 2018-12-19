package zgl.com.cn.libandroid.net.netcore.callback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/11/30
 */

public class RetrofitCallback_ResponseBody implements Callback<ResponseBody>{

    private final IListener LISTENER;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private IResBack mListener;


    public RetrofitCallback_ResponseBody(IListener listener, ISuccess success, IFailure failure, IError error, IResBack resBack){
        LISTENER = listener;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        mListener = resBack;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

        if(response.isSuccessful()){
            if(call.isExecuted()){
                if(SUCCESS!=null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        }else{
            if(ERROR!=null){
                ERROR.onError(response.code(),response.message());
            }
        }

        if(LISTENER!=null){
            LISTENER.onRequestEnd();
        }

        if (mListener!=null) {
            mListener.onResBack();
        }

    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {

        if (mListener!=null) {
            mListener.onResBack();
        }

        if(FAILURE!=null){
            FAILURE.onFailure();
        }

        if(LISTENER!=null){
            LISTENER.onRequestEnd();
        }

        if (mListener!=null) {
            mListener.onResBack();
        }

    }


    /**
     * 监听请求回来的状态，用于删除请求Call
     */
    public interface IResBack{
        void onResBack();
    }

}
