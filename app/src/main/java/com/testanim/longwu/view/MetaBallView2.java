package com.testanim.longwu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.testanim.longwu.bean.PointXY;

import java.util.Random;

import static com.testanim.longwu.util.DensityUtils.dp2px;

public class MetaBallView2 extends View {

    private Rect textRect;

    public MetaBallView2(Context context) {
        this(context, null);
    }

    public MetaBallView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MetaBallView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private PointXY p1 = new PointXY();
    private PointXY p2 = new PointXY();
    private PointXY p3 = new PointXY();
    private PointXY p4 = new PointXY();
    private PointXY c1 = new PointXY();
    private PointXY c2 = new PointXY();

    private PointXY control = new PointXY();

    private float circleRadiusStart;
    private float circleRadiusEnd;

    private float CurrentCircleRadiusStart;
    private float CurrentCircleRadiusEnd;

    Paint mlinePaint;
    Paint mcirclePaint;
    Path mPath;

    float dx;
    float dy;
    /**
     * 是否可以拖拽
     */
    private boolean mIsCanDrag = false;
    /**
     * 是否超出最大拖拽距离
     */
    private boolean mIsOutOfRang = false;
    /**
     * 是否可以消失
     */
    private boolean mIsDisappear = false;
    /**
     * 最大拖拽距离
     */
    private float maxDistance = dp2px(100);

    /**
     * 模拟消息数量
     */
    private String msgCount = "1";

    /**
     * text画笔
     */
    private Paint textPaint;

    private void init() {
        mlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mlinePaint.setColor(Color.RED);
        mlinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mlinePaint.setStrokeWidth(3);
        mlinePaint.setAntiAlias(true);
        mcirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mcirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mcirclePaint.setStrokeWidth(3);
        mcirclePaint.setColor(Color.RED);
        mcirclePaint.setAntiAlias(true);
        mPath = new Path();
        int count = new Random().nextInt(200);
        msgCount = count > 100 ? "99+" : count + "";
        initText(msgCount);
    }

    private void initText(String msg) {
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textRect = new Rect();

        getTextBound();

    }

    private void getTextBound() {
        textPaint.getTextBounds(msgCount, 0, msgCount.length(), textRect);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        c1.SetPointXY(w / 2, h / 2);
        circleRadiusStart = dp2px(20);
        circleRadiusEnd = dp2px(20);

        CurrentCircleRadiusEnd = circleRadiusEnd;
        CurrentCircleRadiusStart = circleRadiusStart;
        maxDistance = dp2px(100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsOutOfRang) {
            if (!mIsDisappear) {
                draEndball(canvas, c2, CurrentCircleRadiusEnd);
            }
        } else {
            draStartball(canvas, c1, CurrentCircleRadiusStart);
            if (mIsCanDrag) {
                draEndball(canvas, c2, CurrentCircleRadiusEnd);
                draBezier(canvas);
            }
        }

        if (!mIsDisappear) {
            if (msgCount.length() > 0) {
                if (c2.x == 0 || c2.y == 0) {
                    drawText(canvas, c1);
                } else {
                    drawText(canvas, c2);
                }
            }
        }
    }

    private void drawText(Canvas canvas, PointXY point) {
        textRect.left = (int) (point.x - circleRadiusStart);
        textRect.top = (int) (point.y - circleRadiusStart);
        textRect.right = (int) (point.x + circleRadiusStart);
        textRect.bottom = (int) (point.y + circleRadiusStart);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(msgCount, textRect.centerX(), baseline, textPaint);
    }

    private void draBezier(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(p1.x, p1.y);
        mPath.quadTo(control.x, control.y, p2.x, p2.y);
        mPath.lineTo(p3.x, p3.y);
        mPath.quadTo(control.x, control.y, p4.x, p4.y);
        mPath.lineTo(p1.x, p1.y);
        mPath.close();
        canvas.drawPath(mPath, mlinePaint);
    }

    private void setPoint() {
        dx = c2.x - c1.x;
        dy = c2.y - c1.y;

        control.x = (c2.x + c1.x) / 2.0f;
        control.y = (c2.y + c1.y) / 2.0f;
        double angle = Math.atan(dx / dy);

        double offsetX = CurrentCircleRadiusStart * Math.cos(angle);
        double offsetY = CurrentCircleRadiusStart * Math.sin(angle);

        //right-top
        p1.x = (float) (c1.x + offsetX);
        p1.y = (float) (c1.y - offsetY);


        //right-bottom
        p2.x = (float) (c2.x + Math.cos(angle) * CurrentCircleRadiusEnd);
        p2.y = (float) (c2.y - Math.sin(angle) * CurrentCircleRadiusEnd);

        //left-bottom
        p3.x = (float) (c2.x - Math.cos(angle) * CurrentCircleRadiusEnd);
        p3.y = (float) (c2.y + Math.sin(angle) * CurrentCircleRadiusEnd);

        //left-top
        p4.x = (float) (c1.x - Math.cos(angle) * CurrentCircleRadiusStart);
        p4.y = (float) (c1.y + Math.sin(angle) * CurrentCircleRadiusStart);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX;
        float currentY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Rect rect = new Rect();
                rect.left = (int) (c1.x - circleRadiusStart);
                rect.top = (int) (c1.y - circleRadiusStart);
                rect.right = (int) (c1.x + circleRadiusStart);
                rect.bottom = (int) (c1.y + circleRadiusStart);
                mIsCanDrag = rect.contains((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsCanDrag) {
                    currentX = event.getX();
                    currentY = event.getY();
                    //设置拖拽圆的坐标
                    c2.SetPointXY(currentX, currentY);

                    if (!mIsOutOfRang) {
                        setCurrentRadius();
                        setPoint();
                    }

                    invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                if (mIsCanDrag) {
                    if (mIsOutOfRang) {
                        mIsDisappear = true;

                    } else {
                        mIsDisappear = false;
                        c2.SetPointXY(c1.x, c1.y);
                        setPoint();
                    }
                    invalidate();
                }
                break;
        }
        return true;
    }

    public void reset() {
        int count = new Random().nextInt(200);
        msgCount = count > 100 ? "99+" : count + "";
        mIsCanDrag = false;
        mIsOutOfRang = false;
        mIsDisappear = false;
        //因为起始点c1是始终固定不变
        c2.SetPointXY(c1.x, c1.y);
        setPoint();
        invalidate();
    }

    void setCurrentRadius() {
        float distance = (float) Math.sqrt(Math.pow(c2.x - c1.x, 2) + Math.pow(c2.y - c1.y, 2));

        if (distance <= maxDistance) {
            float rate = (distance / maxDistance);

            CurrentCircleRadiusStart = (float) (circleRadiusStart * (1 - rate * 0.6));
            CurrentCircleRadiusEnd = (float) (circleRadiusEnd * (1 + rate * 0.2));
            mIsOutOfRang = false;

        } else {
            CurrentCircleRadiusEnd = circleRadiusEnd;
            CurrentCircleRadiusStart = circleRadiusStart;
            mIsOutOfRang = true;
        }
    }

    private void draStartball(Canvas canvas, PointXY pointXY, float radius) {
        canvas.drawCircle(pointXY.x, pointXY.y, radius, mcirclePaint);
    }

    private void draEndball(Canvas canvas, PointXY pointXY, float radius) {
        canvas.drawCircle(pointXY.x, pointXY.y, radius, mcirclePaint);
    }
}
