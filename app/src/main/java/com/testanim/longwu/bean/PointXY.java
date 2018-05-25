package com.testanim.longwu.bean;

/**
 * Created by wujing on 2018/1/23.
 */

public class PointXY{
    // 设置两个变量用于记录坐标的位置
    public float x;
    public float y;

    // 构造方法用于设置坐标
    public PointXY(float x, float y) {
        this.x = x;
        this.y = y;
    }
    // 构造方法用于设置坐标
    public PointXY() {
    }
    // get方法用于获取坐标
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
