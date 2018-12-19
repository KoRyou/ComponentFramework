package zgl.com.cn.libandroid.net.netcore.callback;

import zgl.com.cn.libandroid.net.netcore.bean.NetClient;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/11/29
 */

public interface IRequest {

    void request(NetClient client);

    void cancel(String tag);

}
