package com.testanim.longwu.bean;

/**
 * Created by wujing on 2018/1/23.
 */

public class PointXY {

    // 构造方法用于设置坐标
    public PointXY() {
    }

    // 构造方法用于设置坐标
    public PointXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // 设置两个变量用于记录坐标的位置
    public float x;
    public float y;

    public void SetPointXY(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
