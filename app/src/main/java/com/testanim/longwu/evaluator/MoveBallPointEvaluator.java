package com.testanim.longwu.evaluator;

import android.animation.TypeEvaluator;

import com.testanim.longwu.bean.PointXY;

/**
 * Created by wujing on 2018/5/30/.
 */

public class MoveBallPointEvaluator implements TypeEvaluator {

    private float x;
    private float y;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        PointXY startPointXY = (PointXY) startValue;
        PointXY endPointXY = (PointXY) endValue;
        x = startPointXY.getX() + fraction * (endPointXY.getX() - startPointXY.getX());
        y = startPointXY.getY() + fraction * (endPointXY.getY() - startPointXY.getY());
       /* if (endPointXY.y - startPointXY.y > 0 && endPointXY.x - startPointXY.x > 0) {//第四象限

            x = startPointXY.getX() + fraction * (endPointXY.getX() - startPointXY.getX());
            y = startPointXY.getY() + fraction * (endPointXY.getY() - startPointXY.getY());

        } else if (endPointXY.y - startPointXY.y < 0 && endPointXY.x - startPointXY.x > 0) { //一

            x = startPointXY.getX() + fraction * (endPointXY.getX() - startPointXY.getX());
            y = startPointXY.getY() - fraction * (endPointXY.getY() - startPointXY.getY());

        } else if (endPointXY.y - startPointXY.y < 0 && endPointXY.x - startPointXY.x < 0) {  //二

            x = startPointXY.getX() - fraction * (endPointXY.getX() - startPointXY.getX());
            y = startPointXY.getY() - fraction * (endPointXY.getY() - startPointXY.getY());

        } else if (endPointXY.y - startPointXY.y > 0 && endPointXY.x - startPointXY.x < 0) { //三

            x = startPointXY.getX() - fraction * (endPointXY.getX() - startPointXY.getX());
            y = startPointXY.getY() + fraction * (endPointXY.getY() - startPointXY.getY());

        } else if (endPointXY.y - startPointXY.y > 0 && endPointXY.x - startPointXY.x == 0) { //向下
            x = startPointXY.getX();
            y = startPointXY.getY() + fraction * (endPointXY.getY() - startPointXY.getY());
        } else if (endPointXY.y - startPointXY.y < 0 && endPointXY.x - startPointXY.x == 0) { //向上
            x = startPointXY.getX();
            y = startPointXY.getY() - fraction * (endPointXY.getY() - startPointXY.getY());
        } else if (endPointXY.y - startPointXY.y == 0 && endPointXY.x - startPointXY.x > 0) { //向右
            x = startPointXY.getX() + fraction * (endPointXY.getX() - startPointXY.getX());
            y = startPointXY.getY();

        } else if (endPointXY.y - startPointXY.y == 0 && endPointXY.x - startPointXY.x < 0) { //向左
            x = startPointXY.getX() - fraction * (endPointXY.getX() - startPointXY.getX());
            y = startPointXY.getY();

        } else {
            x = startPointXY.getX();
            y = startPointXY.getY();

        }*/

        PointXY PointXY = new PointXY(x, y);

        return PointXY;
    }
}
