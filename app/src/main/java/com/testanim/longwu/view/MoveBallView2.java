package com.testanim.longwu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.testanim.longwu.bean.PointXY;
import com.testanim.longwu.util.DensityUtils;

/**
 * 移动小球
 */
public class MoveBallView2 extends View {
    public MoveBallView2(Context context) {
        this(context, null);
    }

    public MoveBallView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveBallView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int ballRadius = 30;

    PointXY currentPoint = new PointXY();
    PointXY startPoint = new PointXY();
    PointXY endPoint = new PointXY();

    private int mCount = 1;
    private int minSpeed = 5;
    private int maxSpeed = 20;

    private int currentSpeed = 10;
    int mWidth = 300;
    int mHeight = 300;
    Ball mball;
    Paint mPaint;
    Path mPath;

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPath = new Path();
        ballRadius = DensityUtils.dp2px(30);

        mball = new Ball();
        mball.vx = 7;
        mball.vy = 7;
        mball.paint = mPaint;
        mball.radius = ballRadius;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mball.cx = w / 2;
        mball.cy = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        long startTime = System.currentTimeMillis();

        canvas.drawCircle(mball.cx, mball.cy, mball.radius, mball.paint);

        collisionDetectingAndChangeSpeed(mball);
        mball.move();

        long stopTime = System.currentTimeMillis();

        long runTime = stopTime - startTime;
        // 16毫秒执行一次
        postInvalidateDelayed(Math.abs(runTime - 16));

    }

    private void collisionDetectingAndChangeSpeed(Ball mball) {
        int left = getLeft();
        int top = getTop();
        int right = getRight();
        int bottom = getBottom();
        float speedX = mball.vx;
        float speedY = mball.vy;

        if (mball.left() <= left && speedX < 0) {
            mball.vx = -mball.vx;
        } else if (mball.right() >= right && speedX > 0) {
            mball.vx = -mball.vx;
        } else if (mball.top() <= top && speedY < 0) {
            mball.vy = -mball.vy;
        } else if (mball.bottom() >= bottom && speedY > 0) {
            mball.vy = -mball.vy;
        }

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    /**
     * 获取默认的宽高值
     */
    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }


    class Ball {
        int radius;//半径
        float cx;//x轴圆心
        float cy;//y轴圆心
        float vx; //x轴加速度
        float vy; //y轴加速度
        Paint paint;

        void move() {
            cx += vx;
            cy += vy;
        }

        int left() {
            return (int) (cx - radius);
        }

        int right() {
            return (int) (cx + radius);
        }

        int top() {
            return (int) (cy - radius);
        }

        int bottom() {
            return (int) (cy + radius);
        }
    }
}
