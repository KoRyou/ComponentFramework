package zgl.com.cn.libandroid.net.mvp;

/**
 * todo 描述：模块名_具体页面描述
 *
 * @author : jsj_android
 * @date : 2019/1/9
 */

public class BasePresenter<T extends BaseContract.IView> implements BaseContract.IPresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }


}
