package com.testanim.longwu.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.testanim.longwu.evaluator.PointEvaluator;
import com.testanim.longwu.bean.PointXY;

/**
 * Created by wujing on 2018/1/23.
 */

public class BallView extends View implements View.OnClickListener {
    private float radius = 70f;
    private PointXY currenPointXY;
    private Paint mPaint;


    public BallView(Context context) {
        this(context, null);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setCurrenPointXY(PointXY currenPointXY) {
        this.currenPointXY = currenPointXY;
        invalidate();
    }

    public void reStartPoint() {
        if ((int) currenPointXY.getX() == getWidth() - radius || (int) currenPointXY.getY() == getHeight() - radius) {
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

    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenHeight = getHeight();
        screenWidth = getWidth();

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

    ValueAnimator animator;

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(currenPointXY.getX(), currenPointXY.getY(), radius, mPaint);
    }

    @Override
    public void onClick(View v) {
        if ((int) currenPointXY.getX() == getWidth() - radius || (int) currenPointXY.getY() == getHeight() - radius) {
            this.currenPointXY = new PointXY(radius, radius);
            invalidate();
            animator.start();
        }
    }
}
