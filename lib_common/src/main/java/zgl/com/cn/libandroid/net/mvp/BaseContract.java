package zgl.com.cn.libandroid.net.mvp;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2019/1/9
 */

public interface BaseContract {


    interface IPresenter<T>{
        void attachView(T view);
        void detachView();
    }

    interface IView{

    }


}
