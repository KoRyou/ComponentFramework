package zgl.com.cn.model_flight.drawlayout;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 *  描述：用于存放自己的布局的容器
 *
 * @author : jsj_android
 * @date : 2018/12/29
 */

public class ZDrawContainerLayout extends FrameLayout {

    private ZDrawSlideLayout mSlideLayout;
    private ZDrawBgView mBgView;

    public ZDrawContainerLayout(ZDrawSlideLayout slideLayout) {
        super(slideLayout.getContext());
        init(slideLayout);
    }

    private void init(ZDrawSlideLayout slideLayout) {
        //把布局里定义的参数转移给这个容器--背景
        //给容器添加自定义的贝塞尔背景
        //把布局添加到容器中
        mSlideLayout = slideLayout;
        setLayoutParams(slideLayout.getLayoutParams());

        mBgView = new ZDrawBgView(getContext());
        mBgView.setColor(slideLayout.getBackground());
        addView(mBgView,0,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        slideLayout.setBackgroundColor(Color.TRANSPARENT);
        addView(slideLayout,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }

    /**
     * 把上层的触摸移动操作传给底部控件，做动画
     * @param y
     * @param offset
     */
    public void setTouch(float y,float offset){
        mBgView.setTouchY(y,offset);
        mSlideLayout.setTouchY(y,offset);
    }

    /**
     * 手指离开屏幕
     */
    public void onMotionEventUp(){
        mSlideLayout.onMotionEventUp();
    }





}
