package com.dribbb.sun.dribbblapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

/**
 * Created by sunbinqiang on 9/27/16.
 */

public class ScrollScaleImageView extends NetworkImageView {

    private Paint scrimPaint;
    private int imageOffset;
    private int minOffset;
    private Rect clipBounds = new Rect();
    private float parallaxFactor = -0.5f;

    public ScrollScaleImageView(Context context) {
        this(context, null);
    }

    public ScrollScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        scrimPaint = new Paint();
        scrimPaint.setColor(Color.TRANSPARENT);
    }

    public int getOffset() {
        return (int) getTranslationY();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void setOffset(int offset) {
        offset = Math.max(minOffset, offset);
        if (offset != getTranslationY()) {
            setTranslationY(offset);
            imageOffset = (int) (offset * parallaxFactor);
            clipBounds.set(0, -offset, getWidth(), getHeight());
            setClipBounds(clipBounds);
            postInvalidateOnAnimation();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h > getMinimumHeight()) {
            minOffset = getMinimumHeight() - h;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (imageOffset != 0) {
            final int saveCount = canvas.save();
            canvas.translate(0f, imageOffset);
            super.onDraw(canvas);
            //canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), scrimPaint);
            canvas.restoreToCount(saveCount);
        } else {
            super.onDraw(canvas);
            //canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), scrimPaint);
        }
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace + 1);
    }
}
