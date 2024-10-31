package com.sumit1334.shapeableview.shapes;

import android.content.Context;
import android.graphics.Path;

import android.util.AttributeSet;

import com.sumit1334.shapeableview.ShapeOfView;

import com.sumit1334.shapeableview.manager.ClipPathManager;


public class TriangleView extends ShapeOfView {
    private float percentBottom = 0.5f;
    private float percentLeft = 0f;
    private float percentRight = 0f;

    public TriangleView( Context context) {
        super(context);
        init(context, null);
    }

    public TriangleView( Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TriangleView( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final Path path = new Path();

                path.moveTo(0, percentLeft * height);
                path.lineTo(percentBottom * width, height);
                path.lineTo(width, percentRight * height);
                path.close();

                return path;
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }

    public float getPercentBottom() {
        return percentBottom;
    }

    public void setPercentBottom(float percentBottom) {
        this.percentBottom = percentBottom;
        requiresShapeUpdate();
    }

    public float getPercentLeft() {
        return percentLeft;
    }

    public void setPercentLeft(float percentLeft) {
        this.percentLeft = percentLeft;
        requiresShapeUpdate();
    }

    public float getPercentRight() {
        return percentRight;
    }

    public void setPercentRight(float percentRight) {
        this.percentRight = percentRight;
        requiresShapeUpdate();
    }
}
