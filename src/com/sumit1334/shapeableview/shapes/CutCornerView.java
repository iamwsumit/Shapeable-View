package com.sumit1334.shapeableview.shapes;

import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;


import com.sumit1334.shapeableview.ShapeOfView;
import com.sumit1334.shapeableview.manager.ClipPathManager;



public class CutCornerView extends ShapeOfView {

    private final RectF rectF = new RectF();

    private float topLeftCutSizePx = 0f;
    private float topRightCutSizePx = 0f;
    private float bottomRightCutSizePx = 0f;
    private float bottomLeftCutSizePx = 0f;

    public CutCornerView( Context context) {
        super(context);
        init(context, null);
    }

    public CutCornerView( Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CutCornerView( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                rectF.set(0, 0, width, height);
                return generatePath(rectF, topLeftCutSizePx, topRightCutSizePx, bottomRightCutSizePx, bottomLeftCutSizePx);
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }

    private Path generatePath(RectF rect, float topLeftDiameter, float topRightDiameter, float bottomRightDiameter, float bottomLeftDiameter) {
        final Path path = new Path();

        topLeftDiameter = topLeftDiameter < 0 ? 0 : topLeftDiameter;
        topRightDiameter = topRightDiameter < 0 ? 0 : topRightDiameter;
        bottomLeftDiameter = bottomLeftDiameter < 0 ? 0 : bottomLeftDiameter;
        bottomRightDiameter = bottomRightDiameter < 0 ? 0 : bottomRightDiameter;

        path.moveTo(rect.left + topLeftDiameter, rect.top);
        path.lineTo(rect.right - topRightDiameter, rect.top);
        path.lineTo(rect.right, rect.top + topRightDiameter);
        path.lineTo(rect.right, rect.bottom - bottomRightDiameter);
        path.lineTo(rect.right - bottomRightDiameter, rect.bottom);
        path.lineTo(rect.left + bottomLeftDiameter, rect.bottom);
        path.lineTo(rect.left, rect.bottom - bottomLeftDiameter);
        path.lineTo(rect.left, rect.top + topLeftDiameter);
        path.lineTo(rect.left + topLeftDiameter, rect.top);
        path.close();

        return path;
    }

    public float getTopLeftCutSize() {
        return topLeftCutSizePx;
    }

    public void setTopLeftCutSize(float topLeftCutSize) {
        this.topLeftCutSizePx = topLeftCutSize;
        requiresShapeUpdate();
    }

    public float getTopLeftCutSizeDp() {
        return pxToDp(getTopLeftCutSize());
    }

    public void setTopLeftCutSizeDp(float topLeftCutSize) {
        setTopLeftCutSize(dpToPx(topLeftCutSize));
    }

    public float getTopRightCutSize() {
        return topRightCutSizePx;
    }

    public void setTopRightCutSize(float topRightCutSize) {
        this.topRightCutSizePx = topRightCutSize;
        requiresShapeUpdate();
    }

    public float getTopRightCutSizeDp() {
        return pxToDp(getTopRightCutSize());
    }

    public void setTopRightCutSizeDp(float topRightCutSize) {
        setTopRightCutSize(dpToPx(topRightCutSize));
    }

    public float getBottomRightCutSize() {
        return bottomRightCutSizePx;
    }

    public void setBottomRightCutSize(float bottomRightCutSize) {
        this.bottomRightCutSizePx = bottomRightCutSize;
        requiresShapeUpdate();
    }

    public float getBottomRightCutSizeDp() {
        return pxToDp(getBottomRightCutSize());
    }

    public void setBottomRightCutSizeDp(float bottomRightCutSize) {
        setBottomRightCutSize(dpToPx(bottomRightCutSize));
    }

    public float getBottomLeftCutSize() {
        return bottomLeftCutSizePx;
    }

    public void setBottomLeftCutSize(float bottomLeftCutSize) {
        this.bottomLeftCutSizePx = bottomLeftCutSize;
        requiresShapeUpdate();
    }

    public float getBottomLeftCutSizeDp() {
        return pxToDp(getBottomLeftCutSize());
    }

    public void setBottomLeftCutSizeDp(float bottomLeftCutSize) {
        setBottomLeftCutSize(dpToPx(bottomLeftCutSize));
    }
}
