package indi.yume.app.passwordbox.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.ColorInt;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.View;

import indi.yume.app.passwordbox.R;

/**
 * Created by bush2 on 2016/4/4.
 */
public class CircleView extends View {
    private int backColor = Color.BLUE;
    private int textColor = Color.WHITE;
    private String text = "H";

    private int width;
    private int height;

    private Point circlePoint = new Point();
    private int radius;
    private Point textPoint = new Point();

    private Paint circlePaint = new Paint();
    private Paint textPaint = new Paint();

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if(attrs != null)
            init(context.obtainStyledAttributes(attrs, R.styleable.CircleView));

        init();
    }

    private void init() {
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(backColor);

        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
    }

    private void init(TypedArray tArray) {
        int count = tArray.getIndexCount();
        for (int i = 0; i < count; i++)
            setAttr(tArray.getIndex(i), tArray);
        tArray.recycle();
    }

    private void setAttr(@StyleableRes int attrIndex, TypedArray tArray) {
        if(attrIndex == R.styleable.CircleView_cv_backColor) {
            backColor = tArray.getColor(attrIndex, Color.BLUE);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        radius = width < height ? width / 2 : height / 2;
        circlePoint.set(width / 2, height / 2);

        textPaint.setTextSize(radius * 4 / 3);

        reCalculateText();
    }

    private void reCalculateText() {
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        float textHeight = fm.bottom - fm.ascent;
        float textWidth = textPaint.measureText(text);

        textPoint.set((int)(circlePoint.x - textWidth / 2),
                (int)(circlePoint.y - textHeight / 2 - fm.ascent));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(circlePoint.x, circlePoint.y, radius, circlePaint);
        canvas.drawText(text, textPoint.x, textPoint.y, textPaint);
    }

    public void setColor(@ColorInt int color) {
        backColor = color;
        circlePaint.setColor(backColor);
        postInvalidate();
    }

    public void setText(String text) {
        text = text == null ? "" : text;

        if(text.length() > 1)
            text = text.substring(0, 1);

        this.text = text;
        reCalculateText();
        postInvalidate();
    }
}
