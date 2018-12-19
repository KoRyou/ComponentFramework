package zgl.com.cn.componentframework;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import zgl.com.cn.libandroid.net.netcore.NetHelper;
import zgl.com.cn.libandroid.net.netcore.retrofit.RetrofitWorker;
import zgl.com.cn.libandroid.net.network.HttpNetHelper;
import zgl.com.cn.libandroid.net.network.retrofit.RetrofitHttpRequest;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2018/11/20
 */

public class MainApplication extends Application {


    public static boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();


        //路由框架的初始化
        if (isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        //网络框架的初始化
        HttpNetHelper.init(new RetrofitHttpRequest());

        NetHelper.init(new RetrofitWorker());
    }
}
