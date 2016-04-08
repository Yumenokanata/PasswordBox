package indi.yume.view.appkotlin.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.annotation.IntRange
import android.support.annotation.Size
import android.support.annotation.StyleableRes
import android.util.AttributeSet
import android.view.View

import indi.yume.view.appkotlin.R
import indi.yume.view.appkotlin.util.CheckPwdUtil

/**
 * Created by bush2 on 2016/4/5.
 */
class PwdCheckView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    @Size(5)
    private val rectPaintList = arrayOf(Paint(), Paint(), Paint(), Paint(), Paint())
    private val backRectPaint = Paint()

    @Size(5)
    private val rectList = arrayOf(Rect(), Rect(), Rect(), Rect(), Rect())
    private var level = 0

    init {

        if (attrs != null)
            init(context.obtainStyledAttributes(attrs, R.styleable.PwdCheckView))

        init()
    }

    private fun init(tArray: TypedArray) {
        val count = tArray.indexCount
        for (i in 0..count - 1)
            setAttr(tArray.getIndex(i), tArray)
        tArray.recycle()
    }

    private fun setAttr(@StyleableRes attrIndex: Int, tArray: TypedArray) {
        when (attrIndex) {
            R.styleable.PwdCheckView_pcv_password -> {
                val pwd = tArray.getString(attrIndex)
                checkLevel(pwd)
            }
        }
    }

    private fun init() {
        for (i in 0..4) {
            rectPaintList[i].isAntiAlias = true
            rectPaintList[i].color = RECT_COLOR_LIST[i]
        }

        backRectPaint.isAntiAlias = true
        backRectPaint.color = BACK_RECT_COLOR
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val cellWithSpace = w / MAX_LEVEL
        val space = cellWithSpace / 6

        val cellWidth = (w - (MAX_LEVEL - 1) * space) / MAX_LEVEL

        var xOffset = 0
        for (i in 0..MAX_LEVEL - 1) {
            val rect = rectList[i]
            rect.set(xOffset, 0, xOffset + cellWidth, h)
            xOffset += cellWidth + space
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var paint: Paint
        for (i in 0..MAX_LEVEL - 1) {
            if (i > level)
                paint = backRectPaint
            else
                paint = rectPaintList[i]

            canvas.drawRect(rectList[i], paint)
        }
    }

    fun setLevel(@IntRange(from = 0, to = MAX_LEVEL.toLong()) level: Int) {
        this.level = level
        postInvalidate()
    }

    fun checkLevel(pwd: String) {
        this.level = CheckPwdUtil.checkPwdStrong(pwd, 8, 8)
        postInvalidate()
    }

    companion object {
        private const val MAX_LEVEL = 5
        private val RECT_COLOR_LIST = intArrayOf(0xffe11c1b.toInt(), 0xffe0791b.toInt(), 0xffe1ce1b.toInt(), 0xff83e01b.toInt(), 0xff1ce11a.toInt())
        private const val BACK_RECT_COLOR = 0xffeeeeee.toInt()
    }
}
