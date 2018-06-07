package com.testanim.longwu.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import com.testanim.longwu.bean.PointXY;
import com.testanim.longwu.evaluator.PointEvaluator;

/**
 * Created by wujing on 2018/1/23.
 */

public class BallView extends View {
    private float radius = 70f;
    private PointXY currenPointXY;
    private Paint mPaint;
    private float screenWidth;
    private float screenHeight;

    ValueAnimator animator;
    private Context mContext;
    private float touchx;
    private float touchy;

    public BallView(Context context) {
        this(context, null);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void setCurrenPointXY(PointXY currenPointXY) {
        this.currenPointXY = currenPointXY;
        invalidate();
    }

    public void reStartPoint() {
        whichCircle(touchx, touchy);
    }

    private void initBallLocation() {
        if ((int) currenPointXY.getX() == screenWidth - radius || (int) currenPointXY.getY() == screenHeight - radius) {
            this.currenPointXY = new PointXY(radius, radius);
            invalidate();
            animator.start();
        }
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        currenPointXY = new PointXY(radius, radius);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenHeight =getMeasuredHeight();
        screenWidth = getMeasuredWidth();

        PointXY startPointXY = new PointXY(radius, radius);
        PointXY endPointXY = new PointXY(screenWidth - radius, screenHeight - radius);
        animator = ValueAnimator.ofObject(new PointEvaluator(), startPointXY, endPointXY);
        animator.setDuration(3000);
//      animator.setInterpolator(new OvershootInterpolator());
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currenPointXY = (PointXY) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(currenPointXY.getX(), currenPointXY.getY(), radius, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取点击屏幕时的点的坐标
        touchx = event.getX();
        touchy = event.getY();
        whichCircle(touchx, touchy);
        return super.onTouchEvent(event);
    }

    /**
     * 确定点击的点在哪个圆内
     */
    private void whichCircle(float x, float y) {
        float mx = x - currenPointXY.x;
        float my = y - currenPointXY.y;
        float result = mx * mx + my * my;

        if (result <= radius * radius) {
            Toast.makeText(mContext, "点击了圆的区域", Toast.LENGTH_SHORT).show();
            initBallLocation();
        }

    }
}
