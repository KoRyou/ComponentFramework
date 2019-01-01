package zgl.com.cn.model_flight.drawlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 *  描述：自定义左侧滑动控件
 *
 * @author : jsj_android
 * @date : 2018/12/29
 */

public class ZDrawerLayout extends DrawerLayout {

    /**
     * 滑动区域的布局
     */
    private ZDrawSlideLayout mSlideLayout;
    /**
     * 内容布局
     */
    private View mContentView;
    private ZDrawContainerLayout mContainerLayout;
    /**
     * 记录Y轴坐标，用于动画
     */
    private float mY;
    private float mSlideOffset;

    public ZDrawerLayout(@NonNull Context context) {
        this(context ,null);
    }

    public ZDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //初始化的时候，布局还没有inflate，所以在onFinishInflate方法中执行
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initChild();
    }

    private void initChild() {
        //取出所有子控件中的ZDrawSlideLayout布局
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof ZDrawSlideLayout) {
                mSlideLayout = (ZDrawSlideLayout) view;
                break;
            }else{
                mContentView = view;
            }
        }

        //移除子控件，植入自定义的布局
        removeView(mSlideLayout);
        mContainerLayout = new ZDrawContainerLayout(mSlideLayout);
        addView(mContainerLayout);




        //添加监听
        addDrawerListener(new DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                //手指拉动的监听
                mSlideOffset = slideOffset;
                mContainerLayout.setTouch(mY,slideOffset);
                //内容布局移动动画
                float contentViewOffset = drawerView.getWidth()* mSlideOffset /2;
                mContentView.setTranslationX(contentViewOffset);
            }
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {}
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {}
            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //手指抬起，关闭侧滑布局
            closeDrawers();
            mContainerLayout.onMotionEventUp();
            return super.dispatchTouchEvent(ev);
        }

        mY = ev.getY();

        if(mSlideOffset<1){
            super.dispatchTouchEvent(ev);
        }else if(ev.getAction() == MotionEvent.ACTION_MOVE){
            //当滑动比例>=1,北背景做贝塞尔曲线变化。拦截后  DrawerLayout.DrawerListener 不再起作用
            mContainerLayout.setTouch(mY,mSlideOffset);
        }
        return true;
    }
}
