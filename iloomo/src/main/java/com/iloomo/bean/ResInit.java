package com.iloomo.bean;

import com.iloomo.paysdk.R;

public class ResInit {
    private int drawableId; //APP提醒正确的图标
    private int filedId;//APP提醒不正确的图标
    private int inAnimToastTime = 250;//进入的动画时间
    private int outAnimToastTime = 250;//出去动画的时间
    private int sleepAnimToastTime = 1000;//停留动画的时间
    private int faliedAnimToastBg = R.color.color_FEDFDF;//错误toast的背景
    private int successAnimToastBg = R.color.color_D8EDFE;//正确Toast的背景


    public int getFaliedAnimToastBg() {
        return faliedAnimToastBg;
    }

    public void setFaliedAnimToastBg(int faliedAnimToastBg) {
        this.faliedAnimToastBg = faliedAnimToastBg;
    }

    public int getSuccessAnimToastBg() {
        return successAnimToastBg;
    }

    public void setSuccessAnimToastBg(int successAnimToastBg) {
        this.successAnimToastBg = successAnimToastBg;
    }

    public int getInAnimToastTime() {
        return inAnimToastTime;
    }

    public void setInAnimToastTime(int inAnimToastTime) {
        this.inAnimToastTime = inAnimToastTime;
    }

    public int getOutAnimToastTime() {
        return outAnimToastTime;
    }

    public void setOutAnimToastTime(int outAnimToastTime) {
        this.outAnimToastTime = outAnimToastTime;
    }

    public int getSleepAnimToastTime() {
        return sleepAnimToastTime;
    }

    public void setSleepAnimToastTime(int sleepAnimToastTime) {
        this.sleepAnimToastTime = sleepAnimToastTime;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public int getFiledId() {
        return filedId;
    }

    public void setFiledId(int filedId) {
        this.filedId = filedId;
    }
}
