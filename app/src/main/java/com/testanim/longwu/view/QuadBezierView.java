package com.testanim.longwu.view;

import android.animation.ValueAnimator;
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
import android.view.animation.AnticipateOvershootInterpolator;

public class QuadBezierView extends View {

    private int startwidth;
    private int endwidth;
    private int controlwidth;
    private int controlheight;

    public QuadBezierView(Context context) {
        this(context, null);
    }

    public QuadBezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuadBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint mLinePaint;
    Paint mTextPaint;

    private void init() {
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(30);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setSubpixelText(true);
        String text = "start";
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        //文本的宽度 String text = "start";
        startwidth = rect.width();

        text = "end";
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        //文本的宽度 String text = "start";
        endwidth = rect.width();

        text = "control";
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        //文本的宽度
        controlwidth = rect.width();
        controlheight = rect.height();

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setDither(true);
        mLinePaint.setAntiAlias(true);

        mBezierPath = new Path();

    }

    float mControlX = 100;
    float mControlY = 100;
    float mStartX = 100f;
    float mStartY = 200f;
    float mEndX = 600f;
    float mEndY = 200f;
    Path mBezierPath;

    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension( getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }
    /**
     * 获取默认的宽高值
     */
    public static int getDefaultSize (int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec. getMode(measureSpec);
        int specSize = MeasureSpec. getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec. UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec. AT_MOST:
            case MeasureSpec. EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mStartX, mStartY, 8, mLinePaint);
        canvas.drawText("start", mStartX - startwidth, mStartY, mTextPaint);

        canvas.drawCircle(mEndX, mEndY, 8, mLinePaint);
        canvas.drawText("end", mEndX + endwidth, mEndY, mTextPaint);


        canvas.drawCircle(mControlX, mControlY, 8, mLinePaint);
        if (mControlY < mEndY && mControlY < mStartY) {
            canvas.drawText("control", mControlX, mControlY - controlheight, mTextPaint);
        } else {
            canvas.drawText("control", mControlX, mControlY + controlheight, mTextPaint);
        }

        mLinePaint.setStrokeWidth(2);
        mLinePaint.setColor(Color.GRAY);
        canvas.drawLine(mStartX, mStartY, mControlX, mControlY, mLinePaint);
        canvas.drawLine(mControlX, mControlY, mEndX, mEndY, mLinePaint);

        mBezierPath.reset();
        mBezierPath.moveTo(mStartX, mStartY);
        mBezierPath.quadTo(mControlX, mControlY, mEndX, mEndY);
        mLinePaint.setStrokeWidth(4);
        mLinePaint.setColor(Color.RED);
        canvas.drawPath(mBezierPath, mLinePaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                mControlX = event.getX();
                mControlY = event.getY();
                invalidate();

                break;
            case MotionEvent.ACTION_UP:
                ValueAnimator animX = ValueAnimator.ofFloat(mControlX, getWidth() / 3);
                animX.setDuration(500);
//                animX.setInterpolator(new OvershootInterpolator());
                animX.setInterpolator(new AnticipateOvershootInterpolator());
                animX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mControlX = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                animX.start();
                ValueAnimator animY = ValueAnimator.ofFloat(mControlY, getHeight() / 5);
                animY.setDuration(500);
//                animY.setInterpolator(new OvershootInterpolator());
                animY.setInterpolator(new AnticipateOvershootInterpolator());
                animY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mControlY = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                animY.start();
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }
}
