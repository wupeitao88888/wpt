package com.iloomo.base;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.iloomo.bean.BaseModel;

import com.iloomo.global.AppController;
import com.iloomo.global.HttpTaskID;
import com.iloomo.global.IloomoConfig;
import com.iloomo.model.OnAdapterToastListener;
import com.iloomo.nohttp.HttpResponseListener;
import com.iloomo.nohttp.HttpV2ResponseListener;
import com.iloomo.paysdk.R;
import com.iloomo.statusbar.Eyes;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.update.utils.HotUpdateUtils;
import com.iloomo.utils.ActivityErrorUtils;
import com.iloomo.utils.ActivityPageManager;
import com.iloomo.utils.DateUtil;
import com.iloomo.utils.DialogManger;
import com.iloomo.utils.L;
import com.iloomo.utils.LCSharedPreferencesHelper;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.UnicodeUtils;
import com.iloomo.widget.ChildClickableRelativeLayout;
import com.iloomo.widget.TitleBar;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

;

/**
 * Actity 基类
 *
 * @author wpt
 */
@SuppressLint("NewApi")
public class ActivitySupport extends AppCompatActivity implements
        IActivitySupport {


    protected Activity context = null;
    protected LCSharedPreferencesHelper sharedPreferencesHelper = null;
    public TitleBar titleBar;
    public ViewGroup all_super;
    RelativeLayout layout_parent;
    public OnAdapterToastListener onAdapterToastListener;
    /**
     * 用来标记取消。
     */
    private Object object = new Object();

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeInput();
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean isIndex = true;
    public boolean islogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityPageManager.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题
        L.e("android版本号：" + Build.VERSION.SDK_INT);
        super.onCreate(savedInstanceState);
        // 初始化请求队列，传入的参数是请求并发值。
        mQueue = NoHttp.newRequestQueue();
        initDate();

        onAdapterToastListener = new OnAdapterToastListener() {
            @Override
            public void onSuccess(String msg) {
                showToastSuccess(msg);
            }

            @Override
            public void onFiled(String msgl) {
                showToastFiled(msgl);
            }
        };

        setStatusBar();
        islogin = sharedPreferencesHelper.getBoolean(LCSharedPreferencesHelper.ISLOGIN);
    }


    public void setStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            Eyes.translucentStatusBar(this, true);
            Eyes.setStatusBarLightMode(this, Color.TRANSPARENT);
        }else{
            Window window = getWindow();
            // 沉浸通知栏
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * Activity用户行为 子类进行重写
     *
     * @return
     */
    protected Bundle getBehaviour(Bundle bundle) {
        return bundle;
    }


    /***
     * 获取资源里面的文本
     * @param resId
     * @return
     */
    public CharSequence getResCharSequence(int resId) {
        return getResources().getText(resId);
    }

    /***
     * 获取资源里面的文本
     * @param resId
     * @return
     */
    public String getResString(int resId) {
        return getResources().getText(resId).toString();
    }


    /***
     * 单一布局中使用，第一次加载失败时使用
     *
     * @param blean
     */
    public void netError(boolean blean, OnClickListener onClickListener) {
        if (layout_error == null) {
            layout_error = (RelativeLayout) LayoutInflater.from(context).inflate(
                    R.layout.layout_error, null);
            layout_parent.addView(layout_error);
            ActivityErrorUtils.getInstance().initNetStatus(layout_parent, context);
        }
        ActivityErrorUtils.getInstance().netError(blean, onClickListener);
    }


    /***
     * 单一布局中使用，第一次无数据时使用
     *
     * @param blean
     */
    public void noData(boolean blean, OnClickListener onClickListener) {
        if (layout_error == null) {
            layout_error = (RelativeLayout) LayoutInflater.from(context).inflate(
                    R.layout.layout_error, null);
            layout_parent.addView(layout_error);
            ActivityErrorUtils.getInstance().initNetStatus(layout_parent, context);
        }
        ActivityErrorUtils.getInstance().NoDate(blean, onClickListener);

    }


    /***
     * 单一布局中使用，第一次加载失败时使用
     *
     * @param blean
     */
    public void netError(ViewGroup viewGroup, boolean blean) {
        if (layout_error == null) {
            layout_error = (RelativeLayout) LayoutInflater.from(context).inflate(
                    R.layout.layout_error, null);
            viewGroup.addView(layout_error);
            ActivityErrorUtils.getInstance().initNetStatus(layout_parent, context);
        }
        ActivityErrorUtils.getInstance().netError(blean, new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    RelativeLayout layout_error;

    /***
     * 单一布局中使用，第一次无数据时使用
     *
     * @param blean
     */
    public void noData(ViewGroup viewGroup, boolean blean) {
        if (layout_error == null) {
            layout_error = (RelativeLayout) LayoutInflater.from(context).inflate(
                    R.layout.layout_error, null);
            viewGroup.addView(layout_error);
            ActivityErrorUtils.getInstance().initNetStatus(layout_parent, context);
        }
        ActivityErrorUtils.getInstance().NoDate(blean, new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    private void initDate() {
        // TODO Auto-generated method stub
        context = this;
        sharedPreferencesHelper = new LCSharedPreferencesHelper(context,
                LCSharedPreferencesHelper.SHARED_PATH);

    }

    ViewGroup customtitle;
    ChildClickableRelativeLayout childclick;

    public int version = 0;

    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        if (version == 0) {
            all_super = (ViewGroup) LayoutInflater.from(context).inflate(
                    R.layout.layout_super, null);
            titleBar = (TitleBar) LayoutInflater.from(this).inflate(
                    R.layout.layout_activitytitle, null);
            layout_parent = (RelativeLayout) LayoutInflater.from(context).inflate(
                    R.layout.layout_parent, null);
            childclick = (ChildClickableRelativeLayout) layout_parent.findViewById(R.id.childclick);
//        // 自己的布局
            customtitle = (ViewGroup) LayoutInflater.from(this).inflate(layoutResID,
                    null);
            childclick.addView(customtitle);
            all_super.addView(titleBar, 0);
            all_super.addView(layout_parent);
            super.setContentView(all_super);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                L.e("状态栏的高度：" + getStatusBarHeight());
                titleBar.setTitleBarStatus(getStatusBarHeight());
            }


        }
    }


    @Override
    public void setContentView(View view) {
        // TODO Auto-generated method stub
        if (version == 0) {
            all_super = (ViewGroup) LayoutInflater.from(context).inflate(
                    R.layout.layout_super, null);
            titleBar = (TitleBar) LayoutInflater.from(this).inflate(
                    R.layout.layout_activitytitle, null);
            layout_parent = (RelativeLayout) LayoutInflater.from(context).inflate(
                    R.layout.layout_parent, null);
            childclick = (ChildClickableRelativeLayout) layout_parent.findViewById(R.id.childclick);

            childclick.addView(view);
            all_super.addView(titleBar, 0);
            all_super.addView(layout_parent);
            super.setContentView(all_super);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                L.e("状态栏的高度：" + getStatusBarHeight());
                titleBar.setTitleBarStatus(getStatusBarHeight());
            }
        }

    }


    Handler toastHandler = new Handler();

    boolean isShow = false; //是否显示提示语

    String lastMsg = "";

    private List<String> toastList = new ArrayList<>();

    /***
     * 设置所有布局子布局是否可以点击
     * @param isclick
     */
    public void allViewIsClick(boolean isclick) {
        childclick.setChildClickable(isclick);
    }

    /***
     * 显示提示语
     *
     * 替代方法   showToastSuccess  和  showToastFiled
     * @param msg
     */
    public void showToastBig(String msg) {
        if (isShow) {
            if (!lastMsg.equals(msg)) {
                toastList.add(msg);
            }
            return;
        }
        lastMsg = msg;
        isShow = true;
        showToastFinish(msg, true, false);
    }

    /***
     * 显示成功提示语
     * @param msg
     */
    public void showToastSuccess(String msg) {
        if (isShow) {
            if (!lastMsg.equals(msg)) {
                toastList.add(msg);
            }
            return;
        }
        lastMsg = msg;
        isShow = true;
        showToastFinish(msg, true, true);
    }

    /***
     * 显示失败提示语
     * @param msg
     */
    public void showToastFiled(String msg) {
        if (isShow) {
            if (!lastMsg.equals(msg)) {
                toastList.add(msg);
            }
            return;
        }
        lastMsg = msg;
        isShow = true;
        showToastFinish(msg, true, false);
    }

    /***
     * 显示成功提示语,并且关闭Activity
     * @param msg
     */
    public void showToastSuccessFinish(String msg) {
        if (isShow) {
            if (!lastMsg.equals(msg)) {
                toastList.add(msg);
            }
            return;
        }
        lastMsg = msg;
        isShow = true;
        showToastFinish(msg, false, true);
    }


    /***
     * 显示失败提示语,并且关闭Activity
     * @param msg
     */
    public void showToastFiledFinish(String msg) {
        if (isShow) {
            if (!lastMsg.equals(msg)) {
                toastList.add(msg);
            }
            return;
        }
        lastMsg = msg;
        isShow = true;
        showToastFinish(msg, false, false);
    }


    /***
     * 显示之后是否关闭activity
     * @param msg  显示成功或者失败的状态
     * @param blean  是否关闭Activity  false 是关闭  true 不关闭
     * @param isSuccess  是否是成功对话框   true  是成功对话框  false  是失败对话框
     */
    public void showToastFinish(String msg, boolean blean, boolean isSuccess) {
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout toast_layout = (RelativeLayout) inflate.inflate(R.layout.toast_layout, null);
        RelativeLayout relativeLayout = (RelativeLayout) toast_layout.findViewById(R.id.toast_view);
        RelativeLayout topview = (RelativeLayout) toast_layout.findViewById(R.id.topview);

        ImageView show = (ImageView) toast_layout.findViewById(R.id.show);
        TextView content_msg = (TextView) toast_layout.findViewById(R.id.toast_text);

        if (!isSuccess) {
            relativeLayout.setBackgroundColor(ContextCompat.getColor(context, IloomoConfig.init(context).getResInit().getFaliedAnimToastBg()));
            PImageLoaderUtils.getInstance().displayIMGLocal(IloomoConfig.init(context).getResInit().getFiledId(), show, context);
        } else {
            relativeLayout.setBackgroundColor(ContextCompat.getColor(context, IloomoConfig.init(context).getResInit().getSuccessAnimToastBg()));
            PImageLoaderUtils.getInstance().displayIMGLocal(IloomoConfig.init(context).getResInit().getDrawableId(), show, context);
        }
        content_msg.setText(msg);
        layout_parent.addView(toast_layout);

        if (titleBar.getVisibility() == View.GONE) {
            topview.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = topview.getLayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                layoutParams.height = getStatusBarHeight();
            } else {
                layoutParams.height = 0;
            }
            topview.setLayoutParams(layoutParams);
        } else {
            topview.setVisibility(View.GONE);
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(relativeLayout, "translationY", -AppController.getInstance().dp2px(44), 0)
        );
        set.setDuration(IloomoConfig.init(context).getResInit().getInAnimToastTime()).start();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

                MyThreadPool.getInstance().submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(IloomoConfig.init(context).getResInit().getSleepAnimToastTime());
                        } catch (InterruptedException e) {

                        }
                        toastHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                AnimatorSet setting = new AnimatorSet();
                                setting.playTogether(
                                        ObjectAnimator.ofFloat(relativeLayout, "translationY", 0, -AppController.getInstance().dp2px(44))
                                );
                                setting.setDuration(IloomoConfig.init(context).getResInit().getOutAnimToastTime()).start();
                                setting.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        layout_parent.removeView(toast_layout);
                                        L.e("移除后子布局的个数：" + layout_parent.getChildCount());
                                        isShow = false;
                                        if (blean) {
                                            if (toastList.size() > 0) {
                                                showToastFinish(toastList.get(0), blean, isSuccess);
                                                toastList.remove(0);
                                            } else {
                                                toastOver();
                                            }
                                        } else {
                                            toastFinish();
                                        }
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animator) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animator) {

                                    }
                                });
                            }
                        });

                    }
                });


            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    public void toastFinish() {
        finish();
    }

    public void toastOver() {

    }


    public void setBackgroundColor(int color) {
        all_super.setBackgroundColor(color);
    }


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void mIntent(Context packageContext, Class<?> cls) {
        Intent intent = new Intent(packageContext, cls);
        packageContext.startActivity(intent);
    }


    /////////////////////////////////////////////////////////////////////////////////搜索栏目start

    /**
     * 设置搜索框是否隐藏
     */
    public void isSearchTitleBarVisibility(boolean isVisibit) {
        titleBar.isSearchTitleBarVisibility(isVisibit);
    }

    /**
     * 设置搜索框的背景
     */
    public void searchTitleBarBackground(int draw) {
        titleBar.searchTitleBarBackground(draw);
    }

    /**
     * 设置搜索Hint
     */
    public void mEtSearchBarHint(CharSequence hint) {
        titleBar.mEtSearchBarHint(hint);
    }

    /**
     * 设置搜索按钮点击事件
     */
    public void mIvSearchClick(OnClickListener onClickListener) {
        titleBar.mIvSearchClick(onClickListener);
    }


    /**
     * 设置搜索按钮背景图片
     */
    public void mIvSearchBackGround(int draw) {
        titleBar.mIvSearchBackGround(draw);
    }

    /////////////////////////////////////////////////////////////////////////////////搜索栏目end


    /**
     * 设置titleb中间
     *
     * @param
     */
    protected void setRemoveTitle() {
        titleBar.setVisibility(View.GONE);
    }

    protected void setTitle() {
        titleBar.setVisibility(View.VISIBLE);
    }

    /**
     * 设置titleb中间
     *
     * @param title
     */
    protected void setCtenterTitle(String title) {
        titleBar.setCenterTitle(title);
    }

    /**
     * 设置titleb中间
     *
     * @param title
     */
    protected void setCtenterTitle(int title) {
        titleBar.setCenterTitle(mString(title));
    }

    /**
     * 设置左边的是否显示
     */
    protected void isLeftVisibility(boolean isVisibit) {
        titleBar.isLeftVisibility(isVisibit);
    }

    /**
     * 设置中间标题是否显示
     */
    protected void isCenterVisibility(boolean isVisibit) {
        titleBar.isCenterVisibility(isVisibit);
    }

    /**
     * 设置右边标题内容
     */
    protected void setRightTitle(String msg) {
        titleBar.setRightTitle(msg);
    }

    /**
     * 设置右边第二个标题内容
     */
    protected void setRightSecondTitle(String msg) {
        titleBar.setRightSecondTitle(msg);
    }


    /**
     * 设置右边标题内容字体颜色
     */
    protected void setRightTitle(int msg) {
        titleBar.setRightTitle(msg);
    }

    /**
     * 设置右边图片内容
     */
    protected void setRightTitleRes(int msg) {
        titleBar.setRightTitleRes(msg);
    }

    /**
     * 设置右边标题内容
     */
    protected void setRightTitleListener(OnClickListener l) {
        titleBar.setRightTitleListener(l);
    }

    /**
     * 设置右边标题内容
     */
    protected void setRightSecondTitleListener(OnClickListener l) {
        titleBar.setRightSecondTitleListener(l);
    }

    /**
     * 设置右边图片内容
     */
    protected void setRightTitleImageListener(OnClickListener l) {
        titleBar.setRightTitleImageListener(l);
    }

    /**
     * 设置title背景颜色
     */
    public void setBackGb(int color) {
        titleBar.setBackGb(color);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HotUpdateUtils.setBackground(false);
        /**
         * 友盟统计
         */
        if (titleBar != null) {
            String centerTitle = titleBar.getCenterTitle();
            if (TextUtils.isEmpty(centerTitle))
                centerTitle = "Activity";


            MobclickAgent.onPageStart(centerTitle);
            MobclickAgent.onResume(this);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        HotUpdateUtils.setBackground(true);
        /**
         * 友盟统计
         */
        if (titleBar != null) {
            String centerTitle = titleBar.getCenterTitle();
            if (TextUtils.isEmpty(centerTitle))
                centerTitle = "Activity";
            MobclickAgent.onPageEnd(centerTitle);
            MobclickAgent.onPause(this);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    protected void onDestroy() {
        // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
        mQueue.cancelBySign(object);
        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();
        DialogManger.getInstance().cancelsDialogs();
        super.onDestroy();
        //可以取消同一个tag的网络请求
        //Acitvity 释放子view资源
        ActivityPageManager.unbindReferences(all_super);
        ActivityPageManager.getInstance().removeActivity(this);

    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();

    }


    /**
     * 描述：tuichu
     *
     * @param
     */
    @Override
    public void isExit() {
        ActivityPageManager.getInstance().exit(this);
    }

    @Override
    public boolean hasInternetConnected() {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo network = manager.getActiveNetworkInfo();
            if (network != null && network.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean validateInternet() {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            openWirelessSet();
            return false;
        } else {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        openWirelessSet();
        return false;
    }

    @Override
    public boolean hasLocationNetWork() {
        LocationManager manager = (LocationManager) context
                .getSystemService(context.LOCATION_SERVICE);
        if (manager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void checkMemoryCard() {

        if (!Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {

            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);

        }
    }

    public void openWirelessSet() {

        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);

    }

    /**
     * 显示toast
     *
     * @param text
     * @author wpt
     * @update 2012-6-28 下午3:46:18
     */
    public void showToast(String text) {
        showToastFiled(text);
    }

    @Override
    public void onBackPressed() {
        closeInput();
        super.onBackPressed();
    }

    /**
     * 关闭键盘事件
     *
     * @author wpt
     * @update 2012-7-4 下午2:34:34
     */
    public void closeInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && this.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public Context getContext() {
        return context;
    }


    @Override
    public String mString(int string) {
        // TODO Auto-generated method stub
        return getResources().getString(string);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        return super.getSupportActionBar();
    }

    @Override
    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }


    /**
     * 存储当前时间和服务器时间对比时间差
     *
     * @param serveTimesTamp 服务器返回时间戳
     */
    private void setTimeSync(String serveTimesTamp) {
        if (!TextUtils.isEmpty(serveTimesTamp) || !"0".equals(serveTimesTamp)) {
            String currentDate = DateUtil.getCurrentDate(DateUtil.dateFormatYMDHMS);
            //当前时间转换为时间戳
            String currentStamp = DateUtil.date2TimeStamp(currentDate, DateUtil.dateFormatYMDHMS);
            //系统时间戳格式转换
            int parseIntCurrentStamp = StrUtil.parseInt(currentStamp);
            //服务起时间格式转换
            int parseDoubleServeTimesTamp = StrUtil.parseInt(serveTimesTamp);
            //服务器时间戳-系统时间戳=时间差
            int timeDifference = parseDoubleServeTimesTamp - parseIntCurrentStamp;
            //存储时间差
            LCSharedPreferencesHelper lcSharedPreferencesHelper = new LCSharedPreferencesHelper(AppController.getInstance().getContext(), LCSharedPreferencesHelper.SHARED_PATH);
            lcSharedPreferencesHelper.putInt(LCSharedPreferencesHelper.TIME_SYNC, timeDifference);
        }
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    public <T> void request(int what, Request<T> request,
                            boolean canCancel, boolean isLoading, Class<?> modelClass) {
        request.setCancelSign(object);
        mQueue.add(what, request, new HttpBaseResponListener<>(this, request, canCancel, isLoading, modelClass));
    }


    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    public <T> void requestV2(int what, Request<T> request,
                              boolean canCancel, boolean isLoading, Class<?> modelClass) {
        request.setCancelSign(object);
        mQueue.add(what, request, new HttpBaseResponListener<>(this, request, canCancel, isLoading, modelClass));
    }


    private class HttpBaseResponListener<T> extends HttpV2ResponseListener {
        private Gson gson;

        public HttpBaseResponListener(Activity activity, Request request, boolean canCancel, boolean isLoading, Class<?> modelClass) {
            super(activity, request, canCancel, isLoading, modelClass);
            gson = new Gson();
        }

        @Override
        public void onSucceed(int what, Response response) {
            super.onSucceed(what, response);
            // 这里判断一下http响应码，这个响应码问下你们的服务端你们的状态有几种，一般是200成功。
            // w3c标准http响应码：http://www.w3school.com.cn/tags/html_ref_httpmessages.asp
            String request = response.get().toString();
            L.json("返回值：" + UnicodeUtils.decodeUnicode(request));
            L.e("返回值：" + UnicodeUtils.decodeUnicode(request));
            try {
                BaseModel bmodel = gson.fromJson(request, BaseModel.class);
                setTimeSync(bmodel.getTimestamp());//存储当前时间和服务器时间对比时间差
                if ("0".equals(bmodel.getErrorCode())) {
                    Object obj = gson.fromJson(request, modelClass);
                    onSucceedV2(what, obj);
                    onSucceedV2(what, request, obj);
                } else if ("20004".equals(bmodel.getErrorCode())) {
                    if (HttpResponseListener.onAgainLoginListener != null) {
                        HttpResponseListener.onAgainLoginListener.onOutLogin(bmodel);
                    }
                    bmodel.setErrorMessage("");
                    onFailedV2(what, bmodel);
                } else if ("20010".equals(bmodel.getErrorCode())) {
                    if (HttpResponseListener.onAgainLoginListener != null) {
                        HttpResponseListener.onAgainLoginListener.onOutLogin(bmodel);
                    }
                    bmodel.setErrorMessage("");
                    onFailedV2(what, bmodel);
                } else if ("10010".equals(bmodel.getErrorCode())) {
                    if (HttpResponseListener.onAgainLoginListener != null) {
                        HttpResponseListener.onAgainLoginListener.onOutLogin(bmodel);
                    }
                    bmodel.setErrorMessage("");
                    onFailedV2(what, bmodel);
                } else if ("5000".equals(bmodel.getErrorCode())) {
                    if (HttpResponseListener.onAgainLoginListener != null) {
                        HttpResponseListener.onAgainLoginListener.onOutLogin(bmodel);
                    }
                    bmodel.setErrorMessage("");
                    onFailedV2(what, bmodel);
                } else if ("20018".equals(bmodel.getErrorCode())) {
                    onFailedV2(what, bmodel);
                } else if ("10000".equals(bmodel.getErrorCode())) {
                    onFailedV2(what, bmodel);
                } else if ("100011".equals(bmodel.getErrorCode())) {
                    onFailedV2(what, bmodel);
                } else {
                    if (what != HttpTaskID.LOGIN) {
                        showToastBig(bmodel.getErrorMessage());
                    }
                    onFailedV2(what, bmodel);
                }

            } catch (Exception e) {
                L.e("异常：" + e.getMessage());
                if (TextUtils.isEmpty(e.getMessage())) {
                    BaseModel baseModel = new BaseModel();
                    baseModel.setErrorCode("699");
                    baseModel.setErrorMessage("异常信息为空！");
                    showToastBig(baseModel.getErrorMessage());
                    onFailedV2(what, baseModel);
                } else if (e.getMessage().equals("You cannot start a load for a destroyed activity")) {

                } else {
                    BaseModel baseModel = new BaseModel();
                    baseModel.setErrorCode("698");
                    baseModel.setErrorMessage("数据解析异常！");
                    showToastBig(baseModel.getErrorMessage());
                    onFailedV2(what, baseModel);
                }
            }
        }

        @Override
        public void onFailed(int what, Response response) {
            super.onFailed(what, response);
            Exception exception = response.getException();
            BaseModel baseModel = new BaseModel();

            if (exception instanceof NetworkError) {// 网络不好
                baseModel.setErrorMessage(getResString(R.string.error_please_check_network));
                baseModel.setErrorCode("404");
            } else if (exception instanceof TimeoutError) {// 请求超时
                baseModel.setErrorMessage(getResString(R.string.error_timeout));
                baseModel.setErrorCode("404");
            } else if (exception instanceof UnKnownHostError) {// 找不到服务器
                baseModel.setErrorMessage(getResString(R.string.error_not_found_server));
                baseModel.setErrorCode("404");
            } else if (exception instanceof URLError) {// URL是错的
                baseModel.setErrorMessage(getResString(R.string.error_url_error));
                baseModel.setErrorCode("404");
            } else if (exception instanceof NotFoundCacheError) {
                // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
                // 没有缓存一般不提示用户，如果需要随你。
            } else {
                baseModel.setErrorMessage(getResString(R.string.error_unknow));
                baseModel.setErrorCode("304");
            }
            showToastBig(baseModel.getErrorMessage());
            onFailedV2(what, baseModel);
        }
    }


    /***
     *
     * @param what
     * @param modelClass
     */


    public void onFailedV2(int what, Object modelClass) {

    }


    public void onSucceedV2(int what, Object modelClass) {

    }

    public void onSucceedV2(int what, String result, Object modelClass) {

    }


}
