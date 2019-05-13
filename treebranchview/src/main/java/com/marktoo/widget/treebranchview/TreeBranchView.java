package com.marktoo.widget.treebranchview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * className: DashTreeView
 * date: 2019/3/28 16:47
 * desc: 树形组织结构展示
 *
 * @author zyy
 */
public class TreeBranchView extends View {

    private static final String TAG = "DashTree";

    public TreeBranchView(Context context) {
        this(context, null);
    }

    public TreeBranchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public static final int DEFAULT_DASH_WIDTH = 100;
    public static final int DEFAULT_LINE_WIDTH = 100;
    public static final int DEFAULT_LINE_HEIGHT = 10;
    public static final int DEFAULT_LINE_COLOR = 0x3e3e3e;

    /**
     * 间距宽度
     */
    private float dashWidth;
    /**
     * 线段宽度,长度
     */
    private float dashLineWidth;
    /**
     * 线段颜色
     */
    private int lineColor;
    /**
     * 线段高度
     */
    private float lineHeight;
    /**
     * 虚线么？
     */
    private boolean isDash;
    /**
     * 线模式
     * 0:normal
     * 1:expand
     * 2:middle son
     * 3:end son
     * 4:vertical line
     * 5:no line
     */
    private int lineMode;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @TargetApi(21)
    public TreeBranchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashTreeView);
        lineHeight = typedArray.getDimension(R.styleable.DashTreeView_dashH, DEFAULT_LINE_HEIGHT);
        lineColor = typedArray.getColor(R.styleable.DashTreeView_lineCo, DEFAULT_LINE_COLOR);
        lineMode = typedArray.getInt(R.styleable.DashTreeView_mode, 0);
        isDash = typedArray.getBoolean(R.styleable.DashTreeView_isDash, true);
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(lineHeight);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        if (isDash) {
            dashLineWidth = typedArray.getDimension(R.styleable.DashTreeView_lineW, DEFAULT_LINE_WIDTH);
            dashWidth = typedArray.getDimension(R.styleable.DashTreeView_dashW, DEFAULT_DASH_WIDTH);
            PathEffect pathEffect = new DashPathEffect(new float[]{dashLineWidth, dashWidth}, 0);
            mPaint.setPathEffect(pathEffect);
        }

        showLog("lineHeight=" + lineHeight + ",dashLineWidth=" + dashLineWidth + ",dashWidth=" + dashWidth);
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        realWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        realHeight = MeasureSpec.getSize(heightMeasureSpec - getPaddingTop() - getPaddingBottom());
        showLog("onMeasure width=" + realWidth + ",height=" + realHeight);
    }

    int realWidth, realHeight;
    int startLeft, startTop, endRight, endBottom;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        realWidth = right - left - getPaddingRight() - getPaddingLeft();
        realHeight = bottom - top - getPaddingTop() - getPaddingBottom();
        showLog("onLayout width=" + realWidth + ",height=" + realHeight);
        initModeData();
    }

    private void initModeData() {

        switch (lineMode) {
            case 0:
                startLeft = 0;
                startTop = realHeight / 2;
                endRight = realWidth;
                endBottom = startTop;
                break;
            case 1:
                startLeft = realWidth / 2;
                startTop = realHeight / 2;
                endRight = realWidth;
                endBottom = realHeight;
                break;
            case 2:
                startLeft = realWidth / 2;
                startTop = 0;
                endRight = realWidth;
                endBottom = realHeight / 2;
                break;
            case 3:
                startLeft = realWidth / 2;
                startTop = 0;
                endRight = realWidth;
                endBottom = realHeight / 2;
                break;
            case 4:
                startLeft = realWidth / 2;
                startTop = 0;
                endRight = realWidth / 2;
                endBottom = realHeight;
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLineWithMode(canvas);
    }

    private void drawLineWithMode(Canvas canvas) {
        switch (lineMode) {
            case 0:
                canvas.drawLine(startLeft, startTop, endRight, endBottom, mPaint);
                break;
            case 1:
                canvas.drawLine(0, startTop, endRight + lineHeight / 2, startTop, mPaint);
                canvas.drawLine(startLeft, startTop - lineHeight / 2, startLeft, endBottom, mPaint);
                break;
            case 2:
                canvas.drawLine(startLeft, startTop, startLeft, realHeight, mPaint);
                canvas.drawLine(startLeft, endBottom, endRight, endBottom, mPaint);
                break;
            case 3:
                canvas.drawLine(startLeft, 0, startLeft, endBottom, mPaint);
                canvas.drawLine(startLeft - lineHeight / 2, endBottom, endRight, endBottom, mPaint);
                break;
            case 4:
                canvas.drawLine(startLeft, startTop, endRight, endBottom, mPaint);
                break;
            default:
                break;
        }
    }

    public void changeMode(int newMode) {
        lineMode = newMode;
        requestLayout();
        //        postInvalidate();
        invalidate();
    }

    private void showLog(String msg) {
        Log.e(TAG, msg);
    }
}
