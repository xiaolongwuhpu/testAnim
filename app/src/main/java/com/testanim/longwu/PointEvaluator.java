package com.testanim.longwu;

import android.animation.TypeEvaluator;

import com.testanim.longwu.bean.PointXY;

/**
 * Created by wujing on 2018/1/23.
 */

public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        PointXY startPointXY = (PointXY)startValue;
        PointXY endPointXY = (PointXY)endValue;

        float x = startPointXY.getX()+fraction*(endPointXY.getX()- startPointXY.getX());
        float y = startPointXY.getY()+fraction*(endPointXY.getY()- startPointXY.getY());
        PointXY PointXY = new PointXY(x,y);

        return PointXY;
    }
}
