package zgl.com.cn.model_hotel;

import android.app.Application;

import zgl.com.cn.libandroid.net.network.HttpNetHelper;
import zgl.com.cn.libandroid.net.network.retrofit.RetrofitHttpRequest;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/11/21
 */

public class HotelApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //网络框架的初始化
        HttpNetHelper.init(new RetrofitHttpRequest());

    }
}
