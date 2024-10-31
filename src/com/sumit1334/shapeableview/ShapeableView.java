package com.sumit1334.shapeableview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.*;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import com.google.appinventor.components.runtime.util.YailList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.sumit1334.shapeableview.shapes.CircleView;
import com.sumit1334.shapeableview.shapes.RoundRectView;
import com.sumit1334.shapeableview.shapes.TriangleView;
import com.sumit1334.shapeableview.shapes.PolygonView;
import com.sumit1334.shapeableview.shapes.BubbleView;
import com.sumit1334.shapeableview.shapes.StarView;
import com.sumit1334.shapeableview.shapes.DiagonalView;
import com.sumit1334.shapeableview.shapes.ArcView;
import com.sumit1334.shapeableview.shapes.CutCornerView;

public class ShapeableView extends AndroidNonvisibleComponent implements Component{
    private final HashMap<String, ShapeOfView> dictionary = new HashMap<>();
    private final Context activity;
    private final String TAG = "Shapeable View";
    private final HashMap<String, String> instance = new HashMap<>();
    private final HashMap<String,AndroidViewComponent> layouts=new HashMap<>();
    private final ArrayList<String> style = new ArrayList<>(
            Arrays.asList("Circle View", "Arc View", "Bubble View", "Cut Corner View", "Diagonal View", "Polygon View", "Rect View", "Star View", "Triangle View"
            ));

    public ShapeableView(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
    }

    @SimpleEvent
    public void AnimationStart(String id, String radius, int duration, float from, float to, int repeatCount, boolean repeatReverse) {
        EventDispatcher.dispatchEvent(this, "AnimationStart", id, radius, duration, from, to, repeatCount, repeatReverse);
    }

    @SimpleEvent
    public void AnimationEnd(String id, String radius, int duration, float from, float to, int repeatCount, boolean repeatReverse) {
        EventDispatcher.dispatchEvent(this, "AnimationEnd", id, radius, duration, from, to, repeatCount, repeatReverse);
    }

    @SimpleEvent
    public void Click(String id) {
        EventDispatcher.dispatchEvent(this, "Click", id);
    }

    @SimpleEvent
    public void LongClick(String id) {
        EventDispatcher.dispatchEvent(this, "LongClick", id);
    }

    @SimpleFunction
    public void Create(final String id,final  AndroidViewComponent layout,final String shape) {
        if (isIdExist(id)) {
            throw new YailRuntimeError("id need to be unique", "Duplicate Id");
        } else {
            if (!this.style.contains(shape))
                throw new YailRuntimeError("Please enter a valid style", "Invalid Style");
            else {
                ShapeOfView shapeOfView = null;
                if (shape.equals(Circle())) {
                    CircleView circleView = new CircleView(this.activity);
                    shapeOfView = makeLayout(layout, circleView);
                    Log.d(TAG, "Create: Circle View is created");
                } else if (shape.equals(Rectangle())) {
                    RoundRectView rectView = new RoundRectView(this.activity);
                    shapeOfView = this.makeLayout(layout, rectView);
                    Log.d(TAG, "Create: Rectangle View is created");
                } else if (shape.equals(CutCorner())) {
                    CutCornerView cutCornerView = new CutCornerView(this.activity);
                    shapeOfView = this.makeLayout(layout, cutCornerView);
                    Log.d(TAG, "Create: Cut Corner view is created");
                } else if (shape.equals(Arc())) {
                    ArcView arcView = new ArcView(this.activity);
                    shapeOfView = this.makeLayout(layout, arcView);
                    Log.d(TAG, "Create: Arc View is created");
                } else if (shape.equals(Diagonal())) {
                    DiagonalView diagonalView = new DiagonalView(this.activity);
                    shapeOfView = this.makeLayout(layout, diagonalView);
                    Log.d(TAG, "Create: Diagonal View is created");
                } else if (shape.equals(Bubble())) {
                    BubbleView bubbleView = new BubbleView(this.activity);
                    shapeOfView = this.makeLayout(layout, bubbleView);
                    Log.d(TAG, "Create: Bubble View is created");
                } else if (shape.equals(Polygon())) {
                    PolygonView polygonView = new PolygonView(this.activity);
                    shapeOfView = this.makeLayout(layout, polygonView);
                    Log.d(TAG, "Create: Polygon View is created");
                } else if (shape.equals(Star())) {
                    StarView starView = new StarView(this.activity);
                    shapeOfView = this.makeLayout(layout, starView);
                    Log.d(TAG, "Create: Start View is created");
                } else if (shape.equals(Triangle())) {
                    TriangleView triangleView = new TriangleView(this.activity);
                    shapeOfView = this.makeLayout(layout, triangleView);
                    Log.d(TAG, "Create: Triangle View is created");
                }
                this.layouts.put(id,layout);
                int bgColor=this.setLayoutBG(layout);
                shapeOfView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2, 17));
                shapeOfView.setPadding(0, 0, 0, 0);
                shapeOfView.id = id;
                this.dictionary.put(id, shapeOfView);
                this.instance.put(id, shape);
                this.SetBackgroundColor(id, bgColor);
                if (shape.equals(Rectangle()))
                    this.SetCornerRadius(id, 5, 5, 5, 5);
                else if (shape.equals(Arc()))
                    this.SetArc(id, 15, Bottom());
                else if (shape.equals(Diagonal()))
                    this.SetDiagonal(id, 10, Bottom());
                else if (shape.equals(Triangle()))
                    this.SetTrianglePercent(id, 0.5f, 0, 0);
                else if (shape.equals(CutCorner()))
                    this.SetCornerCutSize(id, 0, 0, 0, 15);
                else if (shape.equals(Bubble()))
                    this.SetBubbleArrow(id, 8, 8, Bottom(), 0.5f, 10);
                else if (shape.equals(Star()))
                    this.SetStarPoints(id, 5);
                else if (shape.equals(Polygon()))
                    this.SetPolygonSides(id, 9);
                layout.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Click(id);
                    }
                });
                layout.getView().setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        LongClick(id);
                        return false;
                    }
                });
                shapeOfView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Click(id);
                    }
                });
                shapeOfView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        LongClick(id);
                        return false;
                    }
                });
            }
        }
    }

    @SimpleFunction
    public int GetPolygonSides(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Polygon()))
                return ((PolygonView) this.dictionary.get(id)).getNoOfSides();
            else
                return 0;
        } else
            return 0;
    }

    @SimpleFunction
    public void SetPolygonSides(String id, int sides) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Polygon()))
                ((PolygonView) this.dictionary.get(id)).setNoOfSides(sides);
        }
    }

    @SimpleFunction
    public void SetHeight(String id, int height) {
        if (isIdExist(id)) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.dictionary.get(id).getLayoutParams();
            params.height = height == -1 || height == -2 ? (height == -1 ? -2 : -1) : this.dp2px(height);
            this.dictionary.get(id).setLayoutParams(params);
            this.dictionary.get(id).requiresShapeUpdate();
        }
    }

    @SimpleFunction
    public void SetWidth(String id, int width) {
        if (isIdExist(id)) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.dictionary.get(id).getLayoutParams();
            params.width = width == -1 || width == -2 ? (width == -1 ? -2 : -1) : this.dp2px(width);
            this.dictionary.get(id).setLayoutParams(params);
            this.dictionary.get(id).requiresShapeUpdate();
        }
    }

    @SimpleFunction
    public int GetStarPoints(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Star())) {
                return ((StarView) this.dictionary.get(id)).getNoOfPoints();
            } else
                return 0;
        } else
            return 0;
    }

    @SimpleFunction
    public void SetStarPoints(String id, int points) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Star()))
                ((StarView) this.dictionary.get(id)).setNoOfPoints(points);
        }
    }

    @SimpleFunction
    public int GetBubbleArrowWidth(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Bubble())) {
                return px2dp((Float) this.getProperty(id, "getArrowWidth"));
            } else
                return 0;
        } else
            return 0;
    }

    @SimpleFunction
    public int GetBubbleArrowHeight(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Bubble())) {
                return px2dp((Float) this.getProperty(id, "getArrowHeight"));
            } else
                return 0;
        } else
            return 0;
    }

    @SimpleFunction
    public float GetBubbleArrowPosition(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Bubble())) {
                return ((BubbleView) this.dictionary.get(id)).getPositionPer();
            } else
                return 0;
        } else
            return 0;
    }

    @SimpleFunction
    public int GetBubbleCornerRadius(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Bubble())) {
                return px2dp((float) this.getProperty(id, "getBorderRadius"));
            } else
                return 0;
        } else
            return 0;
    }

    @SimpleFunction
    public String GetBubbleArrowDirection(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Bubble())) {
                String[] list = new String[]{"Bottom", "Top", "Left", "Right"};
                return list[((BubbleView) this.dictionary.get(id)).getPosition()];
            } else
                return null;
        } else
            return null;
    }

    @SimpleFunction
    public void SetBubbleArrow(String id, int arrowHeight, int arrowWidth, String arrowDirection, float arrowPosition, int cornerRadius) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Bubble())) {
                BubbleView bubbleView = (BubbleView) this.dictionary.get(id);
                bubbleView.setArrowHeight(dp2px(arrowHeight));
                bubbleView.setArrowWidth(dp2px(arrowWidth));
                if (arrowDirection.equals(Right()))
                    bubbleView.setPosition(4);
                else if (arrowDirection.equals(Left()))
                    bubbleView.setPosition(3);
                else if (arrowDirection.equals(Bottom()))
                    bubbleView.setPosition(1);
                else if (arrowDirection.equals(Top()))
                    bubbleView.setPosition(2);
                bubbleView.setPositionPer(arrowPosition);
                bubbleView.setBorderRadius(dp2px(cornerRadius));
            }
        }
    }

    @SimpleFunction
    public void SetTrianglePercent(String id, float bottom, float left, float right) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Triangle())) {
                TriangleView triangleView = (TriangleView) this.dictionary.get(id);
                triangleView.setPercentBottom(bottom);
                triangleView.setPercentLeft(left);
                triangleView.setPercentRight(right);
            }
        }
    }

    @SimpleFunction
    public Object GetBottomPercent(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Triangle()))
                return ((TriangleView) this.dictionary.get(id)).getPercentBottom();
            else
                return null;
        } else
            return null;
    }

    @SimpleFunction
    public Object GetLeftPercent(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Triangle())) {
                return ((TriangleView) this.dictionary.get(id)).getPercentLeft();
            } else
                return null;
        } else
            return null;
    }

    @SimpleFunction
    public Object GetRightPercent(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Triangle())) {
                return ((TriangleView) this.dictionary.get(id)).getPercentRight();
            } else
                return null;
        } else
            return null;
    }

    @SimpleFunction
    public void SetDiagonal(String id, int angle, String diagonalPosition) {
        if (isIdExist(id)) {
            if (this.dictionary.get(id) instanceof DiagonalView) {
                ((DiagonalView) this.dictionary.get(id)).setDiagonalAngle((angle));
                this.setDiagonalPosition(id, diagonalPosition);
            }
        }
    }

    @SimpleFunction
    public float GetDiagonalAngle(String id) {
        if (isIdExist(id)) {
            if (this.dictionary.get(id) instanceof DiagonalView)
                return px2dp(((DiagonalView) this.dictionary.get(id)).getDiagonalAngle());
            else
                return 0;
        } else
            return 0;
    }

    @SimpleFunction
    public String GetDiagonalPosition(String id) {
        if (isIdExist(id)) {
            if (this.dictionary.get(id) instanceof DiagonalView) {
                String[] list = new String[]{"Bottom", "Top", "Left", "Right"};
                return list[((DiagonalView) this.dictionary.get(id)).getDiagonalPosition() - 1];
            } else
                return null;
        } else
            return null;
    }

    @SimpleFunction
    public String GetArcPosition(String id) {
        if (isIdExist(id)) {
            if (this.dictionary.get(id) instanceof ArcView) {
                String[] list = new String[]{"Bottom", "Top", "Left", "Right"};
                return list[((ArcView) this.dictionary.get(id)).getArcPosition() - 1];
            } else
                return null;
        } else
            return null;
    }

    @SimpleFunction
    public void SetArc(String id, int height, String arcPosition) {
        if (isIdExist(id)) {
            if (this.dictionary.get(id) instanceof ArcView) {
                ArcView arcView = (ArcView) this.dictionary.get(id);
                arcView.setArcHeight(dp2px(height));
                this.setArcPosition(id, arcPosition);
            }

        }
    }

    @SimpleFunction
    public float GetArcHeight(String id) {
        if (isIdExist(id)) {
            if (this.dictionary.get(id) instanceof ArcView)
                return px2dp(((ArcView) this.dictionary.get(id)).getArcHeight());
            else
                return 0;
        } else
            return 0;
    }

    @SimpleFunction
    public void SetBackgroundColor(String id, int color) {
        if (isIdExist(id)) {
            int col=this.setLayoutBG(this.layouts.get(id));
            this.setProperty(id, "setTheBackground", color);
        }
    }

    @SimpleFunction
    public int GetBackgroundColor(String id) {
        if (isIdExist(id))
            return (int) this.getProperty(id, "getTheBackground");
        else
            return 0;
    }

    @SimpleFunction
    public void SetCornerCutSize(String id, int topLeft, int topRight, int bottomLeft, int bottomRight) {
        if (isIdExist(id)) {
            this.setProperty(id, "setBottomLeftCutSize", dp2px(bottomLeft));
            this.setProperty(id, "setBottomRightCutSize", dp2px(bottomRight));
            this.setProperty(id, "setTopLeftCutSize", dp2px(topLeft));
            this.setProperty(id, "setTopRightCutSize", dp2px(topRight));
        }
    }

    @SimpleFunction
    public Object GetTopLeftCutSize(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(CutCorner())) {
                return px2dp((float) this.getProperty(id, "getTopLeftCutSize"));
            } else
                return null;
        } else
            return null;
    }

    @SimpleFunction
    public Object GetBottomRightCutSize(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(CutCorner())) {
                return px2dp((float) this.getProperty(id, "getBottomRightCutSize"));
            } else
                return null;
        } else
            return null;
    }

    @SimpleFunction
    public Object GetBottomLeftCutSize(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(CutCorner())) {
                return px2dp((float) this.getProperty(id, "getBottomLeftCutSize"));
            } else
                return null;
        } else
            return null;
    }

    @SimpleFunction
    public Object GetTopRightCutSize(String id) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(CutCorner())) {
                return px2dp((float) this.getProperty(id, "getTopRightCutSize"));
            } else
                return null;
        } else
            return null;
    }

    @SimpleFunction
    public void SetStrokeColor(String id, int color) {
        if (isIdExist(id))
            this.setProperty(id, "setBorderColor", color);
    }

    @SimpleFunction
    public Object GetStrokeColor(String id) {
        if (isIdExist(id))
            return this.getProperty(id, "getBorderColor");
        else
            return null;
    }

    @SimpleFunction
    public Object GetStrokeWidth(String id) {
        if (isIdExist(id))
            return px2dp((int) this.getProperty(id, "getBorderWidth"));
        else
            return null;
    }

    @SimpleFunction
    public void SetStrokeWidth(String id, int width) {
        if (isIdExist(id))
            this.setProperty(id, "setBorderWidth", dp2px(width));

    }

    @SimpleFunction
    public void SetCornerRadius(String id, float topLeft, float topRight, float bottomLeft, float bottomRight) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Rectangle())) {
                RoundRectView rectView = (RoundRectView) this.dictionary.get(id);
                rectView.setBottomRightRadius(dp2px(bottomRight));
                rectView.setBottomLeftRadius(dp2px(bottomLeft));
                rectView.setTopRightRadius(dp2px(topRight));
                rectView.setTopLeftRadius(dp2px(topLeft));
            }
        }
    }

    @SimpleFunction
    public float GetCornerRadius(String id, String radius) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Rectangle())) {
                String radiusName = "getTopLeftRadius";
                if (radius.equals(TopLeft()))
                    radiusName = "getTopLeftRadius";
                else if (radius.equals(TopRight()))
                    radiusName = "getTopRightRadius";
                else if (radius.equals(BottomLeft()))
                    radiusName = "getBottomLeftRadius";
                else if (radius.equals(BottomRight()))
                    radiusName = "getBottomRightRadius";
                else
                    return 0;
                return px2dp((Float) this.getProperty(id, radiusName));
            } else
                throw new YailRuntimeError("This block is not applicable to this shape", this.instance.get(id));
        } else
            return 0;
    }

    @SimpleFunction
    public String GetShape(String id) {
        if (isIdExist(id))
            return this.instance.get(id);
        else
            return null;
    }

    @SimpleFunction
    public void AnimateCornerRadius(final String id, final String radius, final int duration, final float from, final float to, final int repeatCount, final boolean repeatReverse) {
        if (isIdExist(id)) {
            if (this.instance.get(id).equals(Rectangle())) {
                String radiusName = "setTopLeftRadius";
                if (radius.equals(TopLeft()))
                    radiusName = "setTopLeftRadius";
                else if (radius.equals(TopRight()))
                    radiusName = "setTopRightRadius";
                else if (radius.equals(BottomLeft()))
                    radiusName = "setBottomLeftRadius";
                else if (radius.equals(BottomRight()))
                    radiusName = "setBottomRightRadius";
                else
                    return;
                final String type = radiusName;
                final ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
                valueAnimator.setDuration(duration);
                valueAnimator.setRepeatCount(repeatCount);
                valueAnimator.setRepeatMode(repeatReverse ? 2 : 1);
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation, boolean isReverse) {
                        AnimationStart(id, type.replaceAll("set", ""), duration, from, to, repeatCount, repeatReverse);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation, boolean isReverse) {
                        AnimationEnd(id, type.replaceAll("set", ""), duration, from, to, repeatCount, repeatReverse);
                    }
                });
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        setProperty(id, type, dp2px((int) (float) valueAnimator.getAnimatedValue()));
                    }
                });
                valueAnimator.start();
            } else
                throw new YailRuntimeError("This block is not applicable to this shape", this.instance.get(id));
        }
    }

    @SimpleFunction
    public YailList GetAllIds() {
        return YailList.makeList(this.instance.keySet());
    }

    @SimpleProperty
    public String TopLeft() {
        return "TL";
    }

    @SimpleProperty
    public String TopRight() {
        return "TR";
    }

    @SimpleProperty
    public String BottomLeft() {
        return "Bl";
    }

    @SimpleProperty
    public String BottomRight() {
        return "BR";
    }

    @SimpleProperty
    public String Bottom() {
        return "Bottom";
    }

    @SimpleProperty
    public String Top() {
        return "Top";
    }

    @SimpleProperty
    public String Left() {
        return "Left";
    }

    @SimpleProperty
    public String Right() {
        return "Right";
    }

    @SimpleProperty
    public String Circle() {
        return "Circle View";
    }

    @SimpleProperty
    public String Arc() {
        return "Arc View";
    }

    @SimpleProperty
    public String Bubble() {
        return "Bubble View";
    }

    @SimpleProperty
    public String CutCorner() {
        return "Cut Corner View";
    }

    @SimpleProperty
    public String Diagonal() {
        return "Diagonal View";
    }

    @SimpleProperty
    public String Polygon() {
        return "Polygon View";
    }

    @SimpleProperty
    public String Rectangle() {
        return "Rect View";
    }

    @SimpleProperty
    public String Star() {
        return "Star View";
    }

    @SimpleProperty
    public String Triangle() {
        return "Triangle View";
    }

    private float dp2px(float dp) {
        return (dp * this.activity.getResources().getDisplayMetrics().density);
    }

    private int dp2px(int dp) {
        return (int) (dp * this.activity.getResources().getDisplayMetrics().density);
    }

    private int px2dp(float dp) {
        return (int) (dp / this.activity.getResources().getDisplayMetrics().density);
    }

    private boolean isIdExist(String id) {
        return this.dictionary.containsKey(id);
    }

    private Class getInstance(String id) {
        String shape = this.instance.get(id);
        if (shape.equals(Circle()))
            return CircleView.class;
        else if (shape.equals(Rectangle()))
            return RoundRectView.class;
        else if (shape.equals(Arc()))
            return ArcView.class;
        else if (shape.equals(Bubble()))
            return BubbleView.class;
        else if (shape.equals(CutCorner()))
            return CutCornerView.class;
        else if (shape.equals(Diagonal()))
            return DiagonalView.class;
        else if (shape.equals(Polygon()))
            return PolygonView.class;
        else if (shape.equals(Star()))
            return StarView.class;
        else if (shape.equals(Triangle()))
            return TriangleView.class;
        else
            return null;
    }

    private ShapeOfView makeLayout(AndroidViewComponent component, ShapeOfView styleView) {
        View view= component.getView();
        Log.d(TAG, "makeLayout: Make layout called");
        styleView.setLayoutParams(new LinearLayout.LayoutParams(this.dp2px(component.Width()), this.dp2px(component.Height()), 17));
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.addView(styleView, viewGroup.indexOfChild(view));
        Log.d(TAG, "makeLayout: Shape view is added to the layout");
        viewGroup.removeView(view);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        Log.d(TAG, "makeLayout: view removed from its parent view");
        FrameLayout frameLayout = new FrameLayout(this.activity);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1, 17));
        frameLayout.addView(view);
        frameLayout.setClickable(true);
        Log.d(TAG, "makeLayout: view added to the layout");
        styleView.addView(frameLayout);
        Log.d(TAG, "makeLayout: layout added to the shape view");
        Log.d(TAG, "makeLayout: returning shape view");
        styleView.frameLayout = frameLayout;
        return styleView;
    }

    private Object getProperty(String id, String name) {
        Log.d(TAG, "Getting the "+name+" property");
        if (this.dictionary.containsKey(id)) {
            try {
                return getInstance(id).getMethod(name).invoke(this.dictionary.get(id));
            } catch (IllegalAccessException e) {
                return null;
            } catch (InvocationTargetException e) {
                return null;
            } catch (NoSuchMethodException e) {
                throw new YailRuntimeError("This property is not applicable to this shape", this.instance.get(id));
            }
        } else
            return null;
    }

    private void setProperty(String id, String name, int value) {
        Log.d(TAG, "Setting the "+name+" property");
        try {
            Method[] methods = getInstance(id).getMethods();
            boolean invoked = false;
            for (Method method : methods) {
                if (method.getName().equals(name)) {
                    method.invoke(this.dictionary.get(id), value);
                    invoked = true;
                }
            }
            if (!invoked)
                throw new YailRuntimeError("This property is not applicable to this shape", this.instance.get(id));

        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {
        }
    }

    private void setDiagonalPosition(String id, String position) {
        if (isIdExist(id)) {
            if (this.dictionary.get(id) instanceof DiagonalView) {
                DiagonalView diagonalView = (DiagonalView) this.dictionary.get(id);
                if (position.equals(Right()))
                    diagonalView.setDiagonalPosition(4);
                else if (position.equals(Left()))
                    diagonalView.setDiagonalPosition(3);
                else if (position.equals(Top()))
                    diagonalView.setDiagonalPosition(2);
                else if (position.equals(Bottom()))
                    diagonalView.setDiagonalPosition(1);
            }
        }
    }

    private void setArcPosition(String id, String position) {
        if (isIdExist(id)) {
            if (this.dictionary.get(id) instanceof ArcView) {
                ArcView arcView = (ArcView) this.dictionary.get(id);
                if (position.equals(Right()))
                    arcView.setArcPosition(4);
                else if (position.equals(Left()))
                    arcView.setArcPosition(3);
                else if (position.equals(Bottom()))
                    arcView.setArcPosition(1);
                else if (position.equals(Top()))
                    arcView.setArcPosition(2);
            }

        }
    }
    private int setLayoutBG(AndroidViewComponent layout){
        int bgColor=0;
        try {
            bgColor = (int) layout.getClass().getMethod("BackgroundColor").invoke(layout);
            Method[] methods = layout.getClass().getMethods();
            for (Method method : methods) {
                if (method.getReturnType().getName().equals("void")) {
                    if (method.getName().equals("BackgroundColor")) {
                        method.invoke(layout, COLOR_NONE);
                        break;
                    }
                }
            }
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {

        }
        finally {
            return bgColor;
        }
    }
}