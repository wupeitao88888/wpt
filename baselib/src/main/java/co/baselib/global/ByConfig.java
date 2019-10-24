package co.baselib.global;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.InputStream;

import co.baselib.R;

/**
 * Created by wupeitao on 2018/2/22.
 */

public class ByConfig {
    public static ByConfig iloomoConfig;

    public synchronized static ByConfig init(Context context) {
        if (iloomoConfig == null) {
            iloomoConfig = new ByConfig(context);
        }
        return iloomoConfig;
    }

    public Context context;

    public ByConfig(Context context) {
        this.context = context;
    }



    private Drawable blank;
    private Drawable show;
    private Drawable clear;
    private boolean isCoutomClear = false;

    /***
     * 设置clearedit中的按钮
     * @param blank
     * @param show
     * @param clear
     */
    public ByConfig setClearEditIcon(boolean isCoutom, Drawable blank, Drawable show, Drawable clear) {
        this.isCoutomClear = isCoutom;
        this.blank = blank;
        this.show = show;
        this.clear = clear;
        return this;
    }

    public boolean getCoutomClear() {
        return isCoutomClear;
    }

    public Drawable getBlank() {
        return blank;
    }

    public Drawable getShow() {
        return show;
    }

    public Drawable getClear() {
        return clear;
    }


    private int barColor = R.color.white;

    /***
     *
     * 设置默认titlebar 颜色
     */
    public ByConfig setTitleBarBgColor(int barColor) {
        this.barColor = barColor;
        return this;
    }

    public int getTitleBarColor() {
        return barColor;
    }

    private int blackImg = R.drawable.by_back;

    /***
     * 设置统一返回图片
     * @param blackImg
     */
    public ByConfig setBlackImg(int blackImg) {
        this.blackImg = blackImg;
        return this;
    }

    public int getBlackImg() {
        return blackImg;
    }

    private int bwidth=24;
    private int bheight=24;

    public ByConfig setBackWH(int width,int height){
        this.bwidth=width;
        this.bheight=height;
        return this;
    }

    public int getBackImgWidth(){
       return  this.bwidth;
    }

    public int getBackImgHeight(){
        return  this.bheight;
    }

    private int centertextsize = 18;

    /***
     * 设置标题中间的字体大小
     * @param textsize dp
     */
    public ByConfig setCenterTextSize(int textsize) {
        this.centertextsize = centertextsize;
        return this;
    }

    public int getCenterTextSize() {
        return centertextsize;
    }



    private int centerTextColor=R.color.title_text;
    /*****
     *
     *
     * 全局设置中间标题 字体颜色
     *
     *
     * @param color
     * @return
     */
    public ByConfig setCenterTextColor(int color){
        this.centerTextColor=color;
        return this;
    }

    public int getCenterTextColor(){
        return centerTextColor;
    }







    private int lc_right_menu = 15;

    /***
     *
     * 设置左边的第一个title
     * @param size dp
     */
    public ByConfig setRightMenuTextSize(int size) {
        this.lc_right_menu = size;
        return this;
    }

    public int getRightMenuTextSize() {
        return lc_right_menu;
    }

    private int lc_right_second_menu = 15;

    /***
     * 设置左边第二个title
     * @param secondMenu
     */
    public ByConfig setRightMenuSecondMenuTextSize(int secondMenu) {
        this.lc_right_second_menu = secondMenu;
        return this;
    }


    public int getRightMenuSecondMenuTextSize() {
        return lc_right_second_menu;
    }

    private int defaultTitleBarHeight = 45; /***状态栏的默认高度***/


    /***
     * 设置状态栏的默认高度
     * @param titleBarHeight
     */
    public ByConfig setTitleBarHeight(int titleBarHeight) {
        defaultTitleBarHeight = titleBarHeight;
        return this;
    }

    /***
     * 获取状态栏的高度
     * @return
     */
    public int getTitleBarHeight() {
        return defaultTitleBarHeight;
    }



    /***
     * 设置是否是调试
     */
    private boolean IsDebug = true;

    public boolean isDebug() {
        return IsDebug;
    }

    public void setDebug(boolean debug) {
        IsDebug = debug;
    }


    /***
     * 判断是否使用证书
     */
    private boolean isCertificate = false;

    public boolean isCertificate() {
        return isCertificate;
    }

    public void setCertificate(boolean certificate) {
        isCertificate = certificate;
    }

    /****
     * 在assets目录下 证书的文件名称 默认为空 为空 则 isCertificate不起作用
     */
    private InputStream inputStream = null;

    /***
     * 证书名称
     * @return
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

}
