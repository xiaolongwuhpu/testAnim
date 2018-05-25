package com.testanim.longwu.bean;

import java.io.Serializable;

/**
 * Created by wujing on 2018/3/8.
 */

public class loanCycleResBean implements Serializable{

    private String loanDays;
    private boolean isShowImage;

    public String getLoanDays() {
        return loanDays;
    }

    public void setLoanDays(String loanDays) {
        this.loanDays = loanDays;
    }

    public boolean isShowImage() {
        return isShowImage;
    }

    public void setShowImage(boolean showImage) {
        isShowImage = showImage;
    }

    @Override
    public String toString() {
        return "loanCycleResBean{" +
                "loanDays='" + loanDays + '\'' +
                ", isShowImage=" + isShowImage +
                '}';
    }
}
