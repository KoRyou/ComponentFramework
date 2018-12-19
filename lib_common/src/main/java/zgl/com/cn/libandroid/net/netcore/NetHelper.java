package zgl.com.cn.libandroid.net.netcore;

import zgl.com.cn.libandroid.net.netcore.bean.NetClient;
import zgl.com.cn.libandroid.net.netcore.bean.NetClientBuilder;
import zgl.com.cn.libandroid.net.netcore.callback.IRequest;

/**
 *  描述：全链式调用，网络请求
 *
 * @author : jsj_android
 * @date : 2018/11/29
 */

public class NetHelper implements IRequest {

    private static NetHelper mHelper;
    private static IRequest mWorker;

    private NetHelper(IRequest iRequest){
        mWorker = iRequest;
    }

    /**
     * 单例
     */
    private static final class Holder {
        private static final NetHelper init(IRequest iRequest){
            return new NetHelper(iRequest);
        }
    }

    public static void init(IRequest iRequest){
        mHelper = Holder.init(iRequest);
    }

    public static NetHelper getInstance(){
        return mHelper;
    }


    /**
     * 仅用于创建参数对象
     * @param baseUrl
     * @param url
     * @param tag
     * @return
     */
    public static NetClientBuilder createBuilder(String baseUrl,String url,String tag){
        return new NetClientBuilder(baseUrl, url, tag);
    }


    @Override
    public void request(NetClient client) {
        mWorker.request(client);
    }

    @Override
    public void cancel(String tag) {
        mWorker.cancel(tag);
    }
}
