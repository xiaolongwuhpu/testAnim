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

public class MetaBallView extends View {
    public MetaBallView(Context context) {
        this(context, null);
    }

    public MetaBallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MetaBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    private int cr1 = 120;
    private int cr2 = 120;

    Paint mlinePaint;
    Paint mcirclePaint;
    Path  mPath;

    float dx;
    float dy;
    double distanceX;
    double distanceY;
    private void init() {
        mlinePaint = new Paint();
        mlinePaint.setStyle(Paint.Style.STROKE);
        mlinePaint.setStrokeWidth(3);
        mlinePaint.setAntiAlias(true);
        mcirclePaint = new Paint();
        mcirclePaint.setStyle(Paint.Style.STROKE);
        mcirclePaint.setStrokeWidth(3);
        mcirclePaint.setColor(Color.RED);
        mcirclePaint.setAntiAlias(true);
        mPath = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mlinePaint.setStrokeWidth(3);
        mlinePaint.setColor(Color.BLACK);
        c1.x = 190;
        c1.y = 190;

        c2.x = 420;
        c2.y = 410;

        dx = c2.x-c1.x;
        dy = c2.x-c1.x;

        double a = Math.atan(dx / dy);
        distanceX = Math.cos(a);
        distanceY = Math.sin(a);

        p1.x = (float) (c1.x+cr1*distanceX);
        p1.y = (float) (c1.y-cr1*distanceY);

        p2.x = (float) (c2.x+cr2*distanceX);
        p2.y = (float) (c2.y-cr2*distanceY);

        p3.x = (float) (c2.x-cr2*distanceX);
        p3.y = (float) (c2.y+cr2*distanceY);

        p4.x = (float) (c1.x-cr1*distanceX);
        p4.y = (float) (c1.y+cr1*distanceY);


        control.x = (c2.x+c1.x)/2;
        control.y = (c2.y+c1.y)/2;

        canvas.drawCircle(c1.x,c1.y,cr1,mcirclePaint);
        canvas.drawCircle(c2.x,c2.y,cr2,mcirclePaint);

        mPath.reset();
        mPath.moveTo(p1.x,p1.y);
        mPath.quadTo(control.x, control.y,p2.x,p2.y);
        mPath.lineTo(p3.x,p3.y);
        mPath.quadTo(control.x,control.y,p4.x,p4.y);
        mPath.lineTo(p1.x,p1.y);

        canvas.drawPath(mPath, mlinePaint);

        mlinePaint.setColor(Color.BLUE);
        mlinePaint.setStrokeWidth(1);
        canvas.drawLine(c1.x,c1.y,c2.x,c2.y,mlinePaint);
        canvas.drawLine(c1.x-cr1,c1.y,c1.x+cr1,c1.y,mlinePaint);
        canvas.drawLine(c2.x-cr2,c2.y,c2.x+cr2,c2.y,mlinePaint);

    }
}
