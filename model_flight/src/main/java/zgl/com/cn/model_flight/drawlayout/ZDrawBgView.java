package zgl.com.cn.model_flight.drawlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 *  描述：贝塞尔曲线背景，P点可调整
 *
 * @author : jsj_android
 * @date : 2018/12/29
 */

public class ZDrawBgView extends View {

    private Paint mPaint;
    private Path mPath;

    public ZDrawBgView(Context context) {
        this(context,null);
    }

    public ZDrawBgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZDrawBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPath = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {



        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath,mPaint);
    }

    /**
     * 根据手指的位置动态绘制背景
     * @param y  手指位置
     * @param percent  手指横向滑动百分比
     */
    public void setTouchY(float y,float percent){

        /*mPath.reset();
        float width = getWidth()*percent;
        float height = getHeight();
        float pointX = width/2;
        //补偿的Y
        float offsetY = height/8;
        //设置第一个Y的位置
        mPath.lineTo(pointX,-offsetY);
        mPath.quadTo(width*3/2,y,pointX,height+offsetY);
        mPath.lineTo();*/
    }



}
