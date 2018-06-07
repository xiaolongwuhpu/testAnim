package com.testanim.longwu.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.testanim.longwu.bean.PointXY;
import com.testanim.longwu.evaluator.MoveBallPointEvaluator;
import com.testanim.longwu.util.DensityUtils;

/**
 * 移动小球
 */
public class MoveBallView extends View {

    private float disX;
    private float disY;
    private AnimatorListenerAdapter animatorListenerAdapter;

    public MoveBallView(Context context) {
        this(context, null);
    }

    public MoveBallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    int ballRadius;

    PointXY currentPoint = new PointXY();
    PointXY startPoint = new PointXY();
    PointXY endPoint = new PointXY();


    Paint mPaint;
    Path mPath;
    ValueAnimator valueAnimator;

    double angle;

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPath = new Path();
        ballRadius = DensityUtils.dp2px(30);


    }

    int mWidth;
    int mHeight;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        startPoint.x = ballRadius;
        startPoint.y = h / 2;

        endPoint.x = w / 2;
        endPoint.y = h - ballRadius;

        currentPoint = startPoint;
        animatorListenerAdapter = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (currentPoint.y == mHeight - ballRadius) { //下
                    disX = mWidth - currentPoint.x;
                    disY = disX * (mHeight - startPoint.y) / currentPoint.x;

                    endPoint.SetPointXY(mWidth - ballRadius, mHeight - disY);
                    secondinit();
                } else if (currentPoint.x >= mWidth - ballRadius) { //右
//                }else if (currentPoint.y >= 0 && currentPoint.x >= mWidth-ballRadius) { //右
                    disX = disX * currentPoint.y / disY;
                    disY = mHeight - currentPoint.y;

                    endPoint.SetPointXY(mWidth - disX, ballRadius);
                    secondinit();
                } else if (/*currentPoint.x >= mWidth-ballRadius &&*/ currentPoint.y <= ballRadius) { //上
                    disY = disY * currentPoint.x / disX;
                    disX = currentPoint.x;
                    endPoint.SetPointXY(ballRadius, disY);
                    secondinit();
                } else if (currentPoint.x <= ballRadius) { //左
                    disX = disX * currentPoint.y / disY;
                    disY = currentPoint.y;
                    endPoint.SetPointXY(disX, mHeight - ballRadius);
                    secondinit();
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        };
        valueAnimator = ValueAnimator.ofObject(new MoveBallPointEvaluator(), startPoint, endPoint);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.addListener(animatorListenerAdapter);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (PointXY) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    void secondinit() {
        startPoint = currentPoint;
        valueAnimator.setEvaluator(new MoveBallPointEvaluator());
        valueAnimator.setObjectValues(startPoint, endPoint);
        valueAnimator.setDuration(1000);
        valueAnimator.addListener(animatorListenerAdapter);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(currentPoint.x, currentPoint.y, ballRadius, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
