package zgl.com.cn.libandroid.net.network;

/**
 *  描述：用于网络请求状态的回调
 * @author : ZGL
 * @date : 2018/11/6
 */
public interface ICallBack<T> {

    /**
     * 请求成功的回调
     * @param result
     */
    void onSuccess(T result);

    /**
     * 响应成功，但是出错的回调
     * @param errorCode
     * @param
     */
    void onError(int errorCode,String msg);

    /**
     * 请求失败的回调
     * @param msg
     */
    void onFailure(String msg);

}
