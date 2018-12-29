package zgl.com.cn.libandroid.net.netcore.bean;

/**
 * 描述：所有支持的请求类型
 *
 * @author : jsj_android
 * @date : 2018/11/29
 */

public enum HttpMethod {

    GET,
    //入参为Map
    POST,
    //入参为普通实体
    POST_RAW,
    //入参为proto
    POST_PROTO,
    PUT,
    DELETE,
    //上传
    UPLOAD,
    //下载
    DOWNLOAD

}
