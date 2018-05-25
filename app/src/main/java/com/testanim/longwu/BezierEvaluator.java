package com.testanim.longwu;

import android.animation.TypeEvaluator;

import com.testanim.longwu.bean.PointXY;
import com.testanim.longwu.util.BezierUtil;

public class BezierEvaluator implements TypeEvaluator<PointXY> {
    private PointXY mControlpointXY;

    public BezierEvaluator(PointXY ControlpointXY) {
        mControlpointXY = ControlpointXY;
    }

    @Override
    public PointXY evaluate(float fraction, PointXY startValue, PointXY endValue) {
        return BezierUtil.CalculateBezierSqrt(fraction,startValue,mControlpointXY,endValue);
    }
}
