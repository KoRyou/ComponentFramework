package zgl.com.cn.model_flight.drawlayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;

/**
 *  描述：自定义左侧滑动控件
 *
 * @author : jsj_android
 * @date : 2018/12/29
 */

public class ZDrawerLayout extends DrawerLayout {

    public ZDrawerLayout(@NonNull Context context) {
        this(context ,null);
    }

    public ZDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //初始化的时候，布局还没有inflate
    }




}
