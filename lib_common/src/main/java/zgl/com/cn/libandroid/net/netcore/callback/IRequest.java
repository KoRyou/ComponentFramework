package zgl.com.cn.libandroid.net.netcore.callback;

import zgl.com.cn.libandroid.net.netcore.bean.NetClient;

/**
 *  描述：用于发起请求和取消请求的接口
 *
 * @author : jsj_android
 * @date : 2018/11/29
 */

public interface IRequest {

    void request(NetClient client);

    void cancel(String tag);

}
