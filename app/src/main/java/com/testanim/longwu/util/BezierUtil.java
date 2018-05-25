package com.testanim.longwu.util;

import com.testanim.longwu.bean.PointXY;

public class BezierUtil {

    public static PointXY CalculateBezierSqrt(float t, PointXY p0, PointXY p1, PointXY p2) {
        PointXY mPoint = new PointXY();
        mPoint.x = (1 - t) * (1 - t) * p0.x + 2*(1 - t) * t * p1.x + t * t * p2.x;
        mPoint.y = (1 - t) * (1 - t) * p0.y + 2*(1 - t) * t * p1.y + t * t * p2.y;
        return mPoint;
    }
}
