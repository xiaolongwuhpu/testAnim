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
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.testanim.longwu.BezierEvaluator;
import com.testanim.longwu.bean.PointXY;

public class PathBezier extends View implements View.OnClickListener {
    private Paint mTextPaint;
    private Paint mPathPaint;
    private Paint mCirclePaint;

    private int mStartPointX;
    private int mStartPointY;
    private int mEndPointX;
    private int mEndPointY;

    private int mMovePointX;
    private int mMovePointY;

    private int mControlPointX;
    private int mControlPointY;

    private Path mPath;
    private Rect rectText;

    public PathBezier(Context context) {
        this(context,null);
    }

    public PathBezier(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathBezier(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(5);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(25);


        mStartPointX = 30;
        mStartPointY = 30;
        mEndPointX = 450;
        mEndPointY = 300;
        mMovePointX = mStartPointX;
        mMovePointY = mStartPointY;
        mControlPointX = 200;
        mControlPointY = 300;
        mPath = new Path();
        setOnClickListener(this);

        rectText = new Rect();
        mTextPaint.getTextBounds(titleText,0, titleText.length(), rectText);


    }
    private  String titleText = "点击一下试试吧!";
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
        mPath.reset();
        mPath.moveTo(mStartPointX,mStartPointY);
        canvas.drawText(titleText,getWidth()/2-rectText.width()/2,rectText.height(),mTextPaint);
        canvas.drawCircle(mStartPointX,mStartPointY,20,mCirclePaint);
        canvas.drawCircle(mEndPointX,mEndPointY,20,mCirclePaint);

        mPath.quadTo(mControlPointX,mControlPointY,mEndPointX,mEndPointY);
        canvas.drawPath(mPath,mPathPaint);
        mCirclePaint.setColor(Color.RED);
        canvas.drawCircle(mMovePointX,mMovePointY,10,mCirclePaint);
        mCirclePaint.reset();
    }

    @Override
    public void onClick(View v) {
        BezierEvaluator bezierEvaluator = new BezierEvaluator(new PointXY(mControlPointX,mControlPointY));

        ValueAnimator animator = ValueAnimator.ofObject(bezierEvaluator,new PointXY(mStartPointX,mStartPointY),new PointXY(mEndPointX,mEndPointY));
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointXY pointXY = (PointXY) animation.getAnimatedValue();
                mMovePointX = (int) pointXY.getX();
                mMovePointY = (int) pointXY.getY();
                invalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

    }

}


