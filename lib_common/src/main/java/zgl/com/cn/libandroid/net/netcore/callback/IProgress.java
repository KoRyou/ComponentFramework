package zgl.com.cn.libandroid.net.netcore.callback;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/12/4
 */

public interface IProgress {

    /**
     * 进度监听
     * @param progress 当前进度
     * @param loaded 已经下载的length
     */
    void onProgress(int progress,long loaded);


}
