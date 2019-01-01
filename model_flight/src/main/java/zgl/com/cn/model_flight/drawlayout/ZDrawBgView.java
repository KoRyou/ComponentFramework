package zgl.com.cn.model_flight.drawlayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 *  描述：贝塞尔曲线背景，P点可调整
 *
 * @author : jsj_android
 * @date : 2018/12/29
 *
 * path的各种方法的综合运用
 * https://www.jianshu.com/p/db01b37b6231
 */

public class ZDrawBgView extends View {

    private Paint mPaint;
    private Path mPath;
    private BitmapDrawable mDrawable;

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

        //如果存在图片背景
        if (mDrawable!=null) {
            Bitmap bitmap = mDrawable.getBitmap();
            Shader shader = new BitmapShader(bitmap,Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mPaint.setShader(shader);
        }

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath,mPaint);
    }

    /**
     * 根据手指的位置动态绘制背景
     * @param y  手指位置
     * @param percent  手指横向滑动百分比
     */
    public void setTouchY(float y,float percent){

        mPath.reset();
        float width = getWidth()*percent;
        float height = getHeight();
        float pointX = width/2;
        //补偿的Y
        float offsetY = height/8;
        //设置第一个Y的位置
        mPath.lineTo(pointX,-offsetY);
        mPath.quadTo(width*3/2,  y,  pointX,  height+offsetY);
        mPath.lineTo(0,height);
        mPath.close();
        //X轴平移,去掉背景的空白
        mPath.offset(getWidth()-width,0);
        //刷新布局
        invalidate();
    }


    public void setColor(Drawable color){
        //传入的颜色可能为图，也可能为色值
        if(color instanceof ColorDrawable){
            ColorDrawable colorDrawable = (ColorDrawable)color;
            mPaint.setColor(colorDrawable.getColor());
        }else if(color instanceof BitmapDrawable){
            this.mDrawable = (BitmapDrawable) color;
        }
    }


}
