package zgl.com.cn.libandroid.net.netcore.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zgl.com.cn.libandroid.net.network.bean.ZResponse;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/11/30
 */

public class RetrofitCallback_ZResponse implements Callback<ZResponse>{

    private final IListener LISTENER;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private IResBack mListener;


    public RetrofitCallback_ZResponse(IListener listener, ISuccess success, IFailure failure, IError error, IResBack resBack){
        LISTENER = listener;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        mListener = resBack;
    }

    @Override
    public void onResponse(Call<ZResponse> call, Response<ZResponse> response) {

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
    public void onFailure(Call<ZResponse> call, Throwable t) {

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
