package com.sumit1334.shapeableview.shapes;

import android.content.Context;
import android.graphics.Path;

import android.util.AttributeSet;


import com.sumit1334.shapeableview.ShapeOfView;
import com.sumit1334.shapeableview.manager.ClipPathManager;

public class StarView extends ShapeOfView {

    private int noOfPoints = 5;

    public StarView( Context context) {
        super(context);
        init(context, null);
    }

    public StarView( Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StarView( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final int points = noOfPoints;
        noOfPoints = points > 2? points : noOfPoints;

        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {

                final int vertices = noOfPoints * 2;
                final float alpha = (float)(2 * Math.PI) / vertices;
                final int radius = (height <= width? height : width) / 2;
                final float centerX = width / 2;
                final float centerY = height / 2;

                final Path path = new Path();
                for (int i = vertices + 1; i != 0; i--) {
                    float r = radius * (i % 2 + 1) / 2;
                    double omega = alpha * i;
                    path.lineTo((float)(r * Math.sin(omega)) + centerX, (float)(r * Math.cos(omega)) + centerY);
                }
                path.close();
                return path;
            }

            @Override
            public boolean requiresBitmap() {
                return true;
            }
        });
    }

    public void setNoOfPoints(int noOfPoints) {
        this.noOfPoints = noOfPoints;
        requiresShapeUpdate();
    }

    public int getNoOfPoints() { return noOfPoints; }
}
