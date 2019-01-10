package zgl.com.cn.model_flight.drawlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import zgl.com.cn.model_flight.R;
/**
 *  描述：展示具体布局的控件
 *
 * @author : jsj_android
 * @date : 2018/12/29
 */

public class ZDrawSlideLayout extends LinearLayout {


    public ZDrawSlideLayout(Context context) {
        this(context,null);
    }

    public ZDrawSlideLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZDrawSlideLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 用于获得自定义属性
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray type = getContext().obtainStyledAttributes(attrs,R.styleable.SlideLayout);
            maxTranslation = type.getDimension(R.styleable.SlideLayout_maxTranslationX,0);
            type.recycle();
        }
    }


    private boolean opened;
    private float maxTranslation;

    public void setTouchY(float y, float percent) {
        //TODO 随着手指滑动，进行条目横向移动动画
        //滑动比例为1，则完全打开
        opened = percent==1;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.setPressed(false);
            boolean inHover = opened && child.getTop()<y && child.getBottom()>y;
            if (inHover) {
                child.setPressed(true);
            }
            apply((ViewGroup)getParent(),child,y,percent);

        }


    }

    private void apply(ViewGroup slideLayout, View child, float touchY, float percent) {

        //比例
        int centerY = child.getTop() + child.getHeight()/2;
        float distance = Math.abs(touchY-centerY);
        float scale = distance/slideLayout.getHeight()*3;  // 距离中心点距离与 SlideLayout 的 1/3 对比
        //滑动距离
        float translationX=0;
        translationX = maxTranslation-scale*maxTranslation;
        child.setTranslationX(translationX * percent);
    }

    public void onMotionEventUp() {
        // 手指松开，锁定的条目就被选中
        for (int i = 0; opened && i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.isPressed()) {
                child.performClick();
                return;
            }
        }

    }
}
