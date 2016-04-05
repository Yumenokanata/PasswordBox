package indi.yume.app.passwordbox.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.IntRange;
import android.support.annotation.Size;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.view.View;

import indi.yume.app.passwordbox.R;
import indi.yume.app.passwordbox.util.CheckPwdUtil;

/**
 * Created by bush2 on 2016/4/5.
 */
public class PwdCheckView extends View {
    private static final int MAX_LEVEL = 5;
    private static final int[] RECT_COLOR_LIST = {
            0xffe11c1b,
            0xffe0791b,
            0xffe1ce1b,
            0xff83e01b,
            0xff1ce11a
    };
    private static final int BACK_RECT_COLOR = 0xffeeeeee;

    @Size(5)
    private Paint[] rectPaintList = {
            new Paint(),
            new Paint(),
            new Paint(),
            new Paint(),
            new Paint()
    };
    private Paint backRectPaint = new Paint();

    @Size(5)
    private Rect[] rectList = {
            new Rect(),
            new Rect(),
            new Rect(),
            new Rect(),
            new Rect()
    };
    private int level = 0;

    public PwdCheckView(Context context) {
        this(context, null);
    }

    public PwdCheckView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if(attrs != null)
            init(context.obtainStyledAttributes(attrs, R.styleable.PwdCheckView));

        init();
    }

    private void init(TypedArray tArray) {
        int count = tArray.getIndexCount();
        for (int i = 0; i < count; i++)
            setAttr(tArray.getIndex(i), tArray);
        tArray.recycle();
    }

    private void setAttr(@StyleableRes int attrIndex, TypedArray tArray) {
        switch (attrIndex) {
            case R.styleable.PwdCheckView_pcv_password:
                String pwd = tArray.getString(attrIndex);
                checkLevel(pwd);
                break;
        }
    }

    private void init() {
        for (int i = 0; i < 5; i++) {
            rectPaintList[i].setAntiAlias(true);
            rectPaintList[i].setColor(RECT_COLOR_LIST[i]);
        }

        backRectPaint.setAntiAlias(true);
        backRectPaint.setColor(BACK_RECT_COLOR);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int cellWithSpace = w / MAX_LEVEL;
        int space = cellWithSpace / 6;

        int cellWidth = (w - (MAX_LEVEL - 1) * space) / MAX_LEVEL;

        int xOffset = 0;
        for(int i = 0; i < MAX_LEVEL; i++){
            Rect rect = rectList[i];
            rect.set(xOffset, 0, xOffset + cellWidth, h);
            xOffset += cellWidth + space;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint;
        for(int i = 0; i < MAX_LEVEL; i++) {
            if(i > level)
                paint = backRectPaint;
            else
                paint = rectPaintList[i];

            canvas.drawRect(rectList[i], paint);
        }
    }

    public void setLevel(@IntRange(from = 0, to = MAX_LEVEL) int level) {
        this.level = level;
        postInvalidate();
    }

    public void checkLevel(String pwd) {
        this.level = CheckPwdUtil.checkPwdStrong(pwd, 8, 8);
        postInvalidate();
    }
}
