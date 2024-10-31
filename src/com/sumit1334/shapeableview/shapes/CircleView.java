package com.sumit1334.shapeableview.shapes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;


import android.util.AttributeSet;

import com.sumit1334.shapeableview.ShapeOfView;

import com.sumit1334.shapeableview.manager.ClipPathManager;


public class CircleView extends ShapeOfView {
    private float borderWidthPx = 0f;


    private int borderColor = Color.WHITE;

    private final Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView( Context context) {
        super(context);
        init(context, null);
    }

    public CircleView( Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleView( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final Path path = new Path();

                path.addCircle(width/2f, height/2f, Math.min(width/2f, height/2f), Path.Direction.CW);

                return path;
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if(borderWidthPx > 0){
            borderPaint.setStrokeWidth(borderWidthPx);
            borderPaint.setColor(borderColor);
            canvas.drawCircle(getWidth()/2f, getHeight()/2f, Math.min((getWidth() - borderWidthPx) /2f, (getHeight() - borderWidthPx) /2f), borderPaint);
        }
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidthPx = borderWidth;
        requiresShapeUpdate();
    }

    public void setBorderColor( int borderColor) {
        this.borderColor = borderColor;
        requiresShapeUpdate();
    }

    public void setBorderWidthDp(float borderWidth) {
        setBorderWidth(dpToPx(borderWidth));
    }

    public float getBorderWidth() {
        return borderWidthPx;
    }

    public float getBorderWidthDp() {
        return pxToDp(getBorderWidth());
    }

    public int getBorderColor() {
        return borderColor;
    }
}
