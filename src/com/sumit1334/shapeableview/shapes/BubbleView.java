package com.sumit1334.shapeableview.shapes;

import android.content.Context;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;


import com.sumit1334.shapeableview.ShapeOfView;
import com.sumit1334.shapeableview.manager.ClipPathManager;

import androidx.annotation.IntDef;


public class BubbleView extends ShapeOfView {

    public static final int POSITION_BOTTOM = 1;
    public static final int POSITION_TOP = 2;
    public static final int POSITION_LEFT = 3;
    public static final int POSITION_RIGHT = 4;

    @Position
    private int position = POSITION_BOTTOM;

    private float borderRadiusPx;
    private float arrowHeightPx;
    private float arrowWidthPx;

    private float defPositionPer=0.5f;
    private float positionPer;

    public BubbleView( Context context) {
        super(context);
        init(context, null);
    }

    public BubbleView( Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BubbleView( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        borderRadiusPx =  (int) dpToPx(10);
        position = position;
        arrowHeightPx = (int) dpToPx(10);
        arrowWidthPx = (int) dpToPx(10);
        positionPer=defPositionPer;
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            @Override
            public Path createClipPath(int width, int height) {
                final RectF myRect = new RectF(0, 0, width, height);
                return drawBubble(myRect, borderRadiusPx, borderRadiusPx, borderRadiusPx, borderRadiusPx);
            }

            @Override
            public boolean requiresBitmap() {
                return false;
            }
        });
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(@Position int position) {
        this.position = position;
        requiresShapeUpdate();
    }

    public float getBorderRadius() {
        return borderRadiusPx;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadiusPx = borderRadius;
        requiresShapeUpdate();
    }

    public float getArrowHeight() {
        return arrowHeightPx;
    }

    public void setArrowHeight(float arrowHeight) {
        this.arrowHeightPx = arrowHeight;
        requiresShapeUpdate();
    }

    public float getArrowWidth() {
        return arrowWidthPx;
    }

    public void setArrowWidth(float arrowWidth) {
        this.arrowWidthPx = arrowWidth;
        requiresShapeUpdate();
    }
    public void setPositionPer(float positionPer) {
        this.positionPer = positionPer;
        requiresShapeUpdate();
    }
    public float getPositionPer(){
        return this.positionPer;
    }

    private Path drawBubble(RectF myRect, float topLeftDiameter, float topRightDiameter, float bottomRightDiameter, float bottomLeftDiameter) {
        final Path path = new Path();

        topLeftDiameter = topLeftDiameter < 0 ? 0 : topLeftDiameter;
        topRightDiameter = topRightDiameter < 0 ? 0 : topRightDiameter;
        bottomLeftDiameter = bottomLeftDiameter < 0 ? 0 : bottomLeftDiameter;
        bottomRightDiameter = bottomRightDiameter < 0 ? 0 : bottomRightDiameter;

        final float spacingLeft = this.position == POSITION_LEFT ? arrowHeightPx : 0;
        final float spacingTop = this.position == POSITION_TOP ? arrowHeightPx : 0;
        final float spacingRight = this.position == POSITION_RIGHT ? arrowHeightPx : 0;
        final float spacingBottom = this.position == POSITION_BOTTOM ? arrowHeightPx : 0;

        final float left = spacingLeft + myRect.left;
        final float top = spacingTop + myRect.top;
        final float right = myRect.right - spacingRight;
        final float bottom = myRect.bottom - spacingBottom;

        final float centerX = (myRect.left + myRect.right) * positionPer;

        path.moveTo(left + topLeftDiameter / 2f, top);
        //LEFT, TOP

        if (position == POSITION_TOP) {
            path.lineTo(centerX - arrowWidthPx, top);
            path.lineTo(centerX, myRect.top);
            path.lineTo(centerX + arrowWidthPx, top);
        }
        path.lineTo(right - topRightDiameter / 2f, top);

        path.quadTo(right, top, right, top + topRightDiameter / 2);
        //RIGHT, TOP

        if (position == POSITION_RIGHT) {
            path.lineTo(right, bottom-(bottom*(1-positionPer))- arrowWidthPx);
            path.lineTo(myRect.right, bottom-(bottom*(1-positionPer)));
            path.lineTo(right, bottom-(bottom*(1-positionPer)) + arrowWidthPx);
        }
        path.lineTo(right, bottom - bottomRightDiameter / 2);

        path.quadTo(right, bottom, right - bottomRightDiameter / 2, bottom);
        //RIGHT, BOTTOM

        if (position == POSITION_BOTTOM) {
            path.lineTo(centerX + arrowWidthPx, bottom);
            path.lineTo(centerX, myRect.bottom);
            path.lineTo(centerX - arrowWidthPx, bottom);
        }
        path.lineTo(left + bottomLeftDiameter / 2, bottom);

        path.quadTo(left, bottom, left, bottom - bottomLeftDiameter / 2);
        //LEFT, BOTTOM

        if (position == POSITION_LEFT) {
            path.lineTo(left, bottom-(bottom*(1-positionPer))+ arrowWidthPx);
            path.lineTo(myRect.left, bottom-(bottom*(1-positionPer)));
            path.lineTo(left, bottom-(bottom*(1-positionPer)) - arrowWidthPx);
        }
        path.lineTo(left, top + topLeftDiameter / 2);

        path.quadTo(left, top, left + topLeftDiameter / 2, top);

        path.close();

        return path;
    }

    @IntDef({POSITION_LEFT, POSITION_RIGHT, POSITION_TOP, POSITION_BOTTOM})
    public @interface Position {
    }
}
