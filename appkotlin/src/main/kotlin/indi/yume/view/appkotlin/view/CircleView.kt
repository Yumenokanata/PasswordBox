package indi.yume.view.appkotlin.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.support.annotation.ColorInt
import android.support.annotation.StyleableRes
import android.util.AttributeSet
import android.view.View

import indi.yume.view.appkotlin.R

/**
 * Created by bush2 on 2016/4/4.
 */
class CircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var backColor = Color.BLUE
    private val textColor = Color.WHITE
    private var text = "H"

    private val circlePoint = Point()
    private var radius: Int = 0
    private val textPoint = Point()

    private val circlePaint = Paint()
    private val textPaint = Paint()

    init {

        if (attrs != null)
            init(context.obtainStyledAttributes(attrs, R.styleable.CircleView))

        init()
    }

    private fun init() {
        circlePaint.isAntiAlias = true
        circlePaint.color = backColor

        textPaint.isAntiAlias = true
        textPaint.color = textColor
    }

    private fun init(tArray: TypedArray) {
        val count = tArray.indexCount
        for (i in 0..count - 1)
            setAttr(tArray.getIndex(i), tArray)
        tArray.recycle()
    }

    private fun setAttr(@StyleableRes attrIndex: Int, tArray: TypedArray) {
        if (attrIndex == R.styleable.CircleView_cv_backColor) {
            backColor = tArray.getColor(attrIndex, Color.BLUE)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        radius = if (width < height) width / 2 else height / 2
        circlePoint.set(width / 2, height / 2)

        textPaint.textSize = (radius * 4 / 3).toFloat()

        reCalculateText()
    }

    private fun reCalculateText() {
        val fm = textPaint.fontMetrics
        val textHeight = fm.bottom - fm.ascent
        val textWidth = textPaint.measureText(text)

        textPoint.set((circlePoint.x - textWidth / 2).toInt(),
                (circlePoint.y.toFloat() - textHeight / 2 - fm.ascent).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(circlePoint.x.toFloat(), circlePoint.y.toFloat(), radius.toFloat(), circlePaint)
        canvas.drawText(text, textPoint.x.toFloat(), textPoint.y.toFloat(), textPaint)
    }

    fun setColor(@ColorInt color: Int) {
        backColor = color
        circlePaint.color = backColor
        postInvalidate()
    }

    fun setText(text: String?) {
        var text = text
        text = if (text == null) "" else text

        if (text.length > 1)
            text = text.substring(0, 1)

        this.text = text
        reCalculateText()
        postInvalidate()
    }
}
