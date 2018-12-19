package zgl.com.cn.libandroid.net.network;


import zgl.com.cn.libandroid.net.netcore.bean.NetClient;
import zgl.com.cn.libandroid.net.network.bean.NetParams;

/**
 *  描述：网络框架的代理类  第一代（废弃）
 *
 * @author : jsj_android
 * @date : 2018/11/6
 */

public class HttpNetHelper implements IHttpRequest {

    private static HttpNetHelper netHelper;
    private IHttpRequest httpRequest;

    /**
     * 单例模式，保证只有一个对象
     * @param httpRequest
     */
    private HttpNetHelper(IHttpRequest httpRequest){
        this.httpRequest = httpRequest;
    }

    public static void init(IHttpRequest httpRequest){
        if (null==netHelper) {
            synchronized (HttpNetHelper.class){
                if(null==netHelper){
                    netHelper = new HttpNetHelper(httpRequest);
                }
            }
        }
    }

    public static HttpNetHelper getInstance(){
        return netHelper;
    }


    @Override
    public void get(NetParams params, ICallBack callBack) {
        httpRequest.get(params, callBack);
    }

    @Override
    public void post(NetParams params, ICallBack callBack) {
        httpRequest.post(params, callBack);
    }

    @Override
    public void cancel(String tag) {
        httpRequest.cancel(tag);
    }
}
