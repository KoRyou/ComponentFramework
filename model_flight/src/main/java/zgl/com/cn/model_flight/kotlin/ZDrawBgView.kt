package zgl.com.cn.model_flight.kotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

/**
 * 描述：贝塞尔曲线背景，P点可调整
 *
 * @author : jsj_android
 * @date : 2018/12/29
 *
 * path的各种方法的综合运用
 * https://www.jianshu.com/p/db01b37b6231
 */

class ZDrawBgView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mPaint: Paint? = null
    private var mPath: Path? = null
    private var mDrawable: BitmapDrawable? = null

    init {
        init()
    }

    private fun init() {
        mPaint = Paint()
        //抗锯齿
        mPaint!!.isAntiAlias = true
        mPath = Path()

    }

    override fun onDraw(canvas: Canvas) {

        //如果存在图片背景
        if (mDrawable != null) {
            val bitmap = mDrawable!!.bitmap
            val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            mPaint!!.shader = shader
        }

        mPaint!!.style = Paint.Style.FILL
        canvas.drawPath(mPath!!, mPaint!!)
    }

    /**
     * 根据手指的位置动态绘制背景
     * @param y  手指位置
     * @param percent  手指横向滑动百分比
     */
    fun setTouchY(y: Float, percent: Float) {

        mPath!!.reset()
        val width = width * percent
        val height = height.toFloat()
        val pointX = width / 2
        //补偿的Y
        val offsetY = height / 8
        //设置第一个Y的位置
        mPath!!.lineTo(pointX, -offsetY)
        mPath!!.quadTo(width * 3 / 2, y, pointX, height + offsetY)
        mPath!!.lineTo(0f, height)
        mPath!!.close()
        //X轴平移,去掉背景的空白
        mPath!!.offset(getWidth() - width, 0f)
        //刷新布局
        invalidate()
    }


    fun setColor(color: Drawable) {
        //传入的颜色可能为图，也可能为色值
        if (color is ColorDrawable) {
            mPaint!!.color = color.color
        } else if (color is BitmapDrawable) {
            this.mDrawable = color
        }
    }


}
