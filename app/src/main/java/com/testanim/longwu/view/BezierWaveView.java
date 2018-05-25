package com.testanim.longwu.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.testanim.longwu.R;
import com.testanim.longwu.bean.PointXY;

public class BezierWaveView extends View{
    public BezierWaveView(Context context) {
        this(context,null);
    }

    public BezierWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(getContext().getResources().getColor(R.color.color_2D64C8));
        mPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(3);
    }

    int mWidth = 0;
    int mHeight = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth =  getWidth();
        mHeight =  getHeight();
        LinearGradient linearGradient = new LinearGradient(mWidth/2,100,mWidth/2,mHeight,getContext().getResources().getColor(R.color.color_2D64C8),getContext().getResources().getColor(R.color.color_000080), Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        setVaule(0);
        mHasInit = true;
        ValueAnimator animX = ValueAnimator.ofFloat(0, mWidth);
        animX.setDuration(1800);
        animX.setInterpolator(new LinearInterpolator());
        animX.setRepeatCount(ValueAnimator.INFINITE);
        animX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float mMove = (float) animation.getAnimatedValue();
                setVaule(mMove);
            }
        });
        animX.start();
        mIsRunning = true;
    }

    PointXY mControlleft1 = new PointXY();
    PointXY mControlleft2 = new PointXY();
    PointXY mControlfirst = new PointXY();
    PointXY mControlsecond = new PointXY();

    PointXY mleft1 = new PointXY();
    PointXY mleft2 = new PointXY();
    PointXY mfirst = new PointXY();
    PointXY msecond = new PointXY();
    PointXY mRight = new PointXY();

    boolean mIsRunning = false;
    boolean mHasInit = false;
    Path mPath;
    Paint mPaint;
    int mWaveHeight =60;
    int mHorizontal =200;
    private void setVaule(float mMove) {
        mControlleft1.x = -mWidth*3/4 + mMove;
        mControlleft1.y = mHorizontal- mWaveHeight;

        mControlleft2.x = -mWidth*1/4+ mMove;
        mControlleft2.y = mHorizontal+ mWaveHeight;

        mControlfirst.x = mWidth*1/4+ mMove;
        mControlfirst.y = mHorizontal- mWaveHeight;

        mControlsecond.x = mWidth*3/4+ mMove;
        mControlsecond.y =  mHorizontal+ mWaveHeight;

        mleft1.x = -mWidth+ mMove;
        mleft1.y = mHorizontal;

        mleft2.x = -mWidth/2+ mMove;
        mleft2.y = mHorizontal;

        mfirst.x = 0+ mMove;
        mfirst.y = mHorizontal;

        msecond.x = mWidth/2+ mMove;
        msecond.y = mHorizontal;

        mRight.x = mWidth+ mMove;
        mRight.y = mHorizontal;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (!mIsRunning || !mHasInit)
//            return;
        mPath.reset();
        mPath.moveTo(mleft1.x, mleft1.y);
        mPath.quadTo(mControlleft1.x, mControlleft1.y,mleft2.x, mleft2.y);
        mPath.quadTo(mControlleft2.x, mControlleft2.y,mfirst.x, mfirst.y);
        mPath.quadTo(mControlfirst.x, mControlfirst.y, msecond.x, msecond.y);
        mPath.quadTo(mControlsecond.x, mControlsecond.y,mRight.x, mRight.y);
        mPath.lineTo(mRight.x, mHeight);
        mPath.lineTo(mleft1.x, mHeight);
        mPath.lineTo(mleft1.x, mleft1.y);
        canvas.drawPath(mPath, mPaint);
    }


}
