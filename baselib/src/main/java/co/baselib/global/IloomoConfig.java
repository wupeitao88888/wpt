package co.baselib.global;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;

import co.baselib.R;
import co.baselib.bean.IloomoSDKModel;
import co.baselib.bean.ResInit;

/**
 * Created by wupeitao on 2018/2/22.
 */

public class IloomoConfig {
    public static IloomoConfig iloomoConfig;

    public synchronized static IloomoConfig init(Context context) {
        if (iloomoConfig == null) {
            iloomoConfig = new IloomoConfig(context);
        }
        return iloomoConfig;
    }

    public final static int PHOTOS = 1230000;
    public int style;
    public Context context;

    public boolean isToastActivity = false;

    public IloomoConfig(Context context) {
        this.context = context;
    }

    /**
     * 设置SDK主题
     *
     * @param style
     */
    public IloomoConfig setStyle(int style) {
        this.style = style;
        return this;
    }

    /***
     * 设置配置文件
     * @return
     */
    public IloomoSDKModel getIloomoSDKModel() {
        return new IloomoSDKModel();
    }

    /***
     *设置是否在activity中显示,自定义toast,或者是系统的toast
     */
    public IloomoConfig setActivityToast(boolean isToastActivity) {
        this.isToastActivity = isToastActivity;
        return this;
    }

    /***
     * 获取是否显示系统的Toast
     * @return
     */
    public boolean getActivityToast() {
        return isToastActivity;
    }


    private ResInit resInit = new ResInit();

    public ResInit getResInit() {
        return resInit;
    }

    public IloomoConfig setResInit(ResInit resInit) {
        this.resInit = resInit;
        return this;
    }


    private boolean showCoutom = false;

    private View coutomToastView;
    private TextView showText;

    /***
     * 设置自定义view
     * @param coutomToastView
     */
    public IloomoConfig setCoutomToast(View coutomToastView, TextView showText) {
        this.coutomToastView = coutomToastView;
        this.showText = showText;
        return this;
    }

    /***
     * 获取自定义toast中的view
     * @return
     */
    public View getCoutomToast() {
        return coutomToastView;
    }

    /***
     * 获取自定义view中的TextView
     * @return
     */
    public TextView getCoutomToastTextView() {
        return showText;
    }

    /**
     * 设置是否显示自定义toast
     *
     * @param showCoutom
     */
    public IloomoConfig setShowCoutomToast(boolean showCoutom) {
        this.showCoutom = showCoutom;
        return this;
    }

    /***
     * 获取是否是自定义toast
     * @return
     */
    public boolean isShowCoutomToast() {
        return showCoutom;
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
    public IloomoConfig setClearEditIcon(boolean isCoutom, Drawable blank, Drawable show, Drawable clear) {
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
    public IloomoConfig setTitleBarColor(int barColor) {
        this.barColor = barColor;
        return this;
    }

    public int getTitleBarColor() {
        return barColor;
    }

    private int blackImg = R.drawable.pc_back;

    /***
     * 设置统一返回图片
     * @param blackImg
     */
    public IloomoConfig setBlackImg(int blackImg) {
        this.blackImg = blackImg;
        return this;
    }

    public int getBlackImg() {
        return blackImg;
    }

    private int centertextsize = 18;

    /***
     * 设置标题中间的字体大小
     * @param textsize dp
     */
    public IloomoConfig setCenterTextSize(int textsize) {
        this.centertextsize = centertextsize;
        return this;
    }

    public int getCenterTextSize() {
        return centertextsize;
    }

    private int lc_right_menu = 15;

    /***
     *
     * 设置左边的第一个title
     * @param size dp
     */
    public IloomoConfig setRightMenuTextSize(int size) {
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
    public IloomoConfig setRightMenuSecondMenuTextSize(int secondMenu) {
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
    public IloomoConfig setTitleBarHeight(int titleBarHeight) {
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


    private String dbName = "baselib";
    private int db_version = 1;

    /****
     *
     * 数据库名称
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /***
     * 获取数据库名称
     * @return
     */
    public String getDbName() {
        return dbName;
    }


    /***
     * 设置数据版本号
     * @param version
     */
    public void setDbVersion(int version) {
        this.db_version = version;
    }

    /****
     * 获取数据库版本号
     * @return
     */
    public int getDbVersion() {
        return this.db_version;
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
