package co.baselib.base;


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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;


import java.util.ArrayList;
import java.util.List;

;import co.baselib.R;
import co.baselib.bean.DownLoad;
import co.baselib.global.AppController;
import co.baselib.global.IloomoConfig;
import co.baselib.model.OnAdapterToastListener;
import co.baselib.nohttp.DownloadCallback;
import co.baselib.nohttp.HttpV2ResponseListener;
import co.baselib.statusbar.Eyes;
import co.baselib.threadpool.MyThreadPool;
import co.baselib.update.utils.HotUpdateUtils;
import co.baselib.utils.ActivityErrorUtils;
import co.baselib.utils.ActivityPageManager;
import co.baselib.utils.DateUtil;
import co.baselib.utils.L;
import co.baselib.utils.LCSharedPreferencesHelper;
import co.baselib.utils.PImageLoaderUtils;
import co.baselib.utils.StrUtil;
import co.baselib.utils.ToastUtil;
import co.baselib.utils.UnicodeUtils;
import co.baselib.widget.ChildClickableRelativeLayout;
import co.baselib.widget.TitleBar;

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
    public Gson gson;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeInput();
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean islogin = false;
    private DownloadQueue mDownloadQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityPageManager.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题
        L.e("android版本号：" + Build.VERSION.SDK_INT);
        super.onCreate(savedInstanceState);
        // 初始化请求队列，传入的参数是请求并发值。
        mQueue = NoHttp.newRequestQueue(); //网络请求
        mDownloadQueue = NoHttp.newDownloadQueue();//下载请求
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
        gson = new Gson();
    }


    public void setStatusBar() {
        if (ishide) {
            //得到当前界面的装饰视图
//            View decorView = getWindow().getDecorView();
//            //设置系统UI元素的可见性
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                Eyes.translucentStatusBar(this, true);
                Eyes.setStatusBarLightMode(this, Color.TRANSPARENT);
            } else {
                Window window = getWindow();
                // 沉浸通知栏
                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }

    }

    private boolean ishide = false;

    /**
     * 是否隐藏状态栏
     */
    public void isHideStatusBar(boolean ishide) {
        this.ishide = ishide;
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
        if (IloomoConfig.init(context).getActivityToast()) {
            ToastUtil.showShort(context, msg);
        } else {
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
    }

    /***
     * 显示成功提示语
     * @param msg
     */
    public void showToastSuccess(String msg) {
        if (IloomoConfig.init(context).getActivityToast()) {
            ToastUtil.showShort(context, msg);
        } else {
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
    }

    /***
     * 显示失败提示语
     * @param msg
     */
    public void showToastFiled(String msg) {
        if (IloomoConfig.init(context).getActivityToast()) {
            ToastUtil.showShort(context, msg);
        } else {
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
    }

    /***
     * 显示成功提示语,并且关闭Activity
     * @param msg
     */
    public void showToastSuccessFinish(String msg) {
        if (IloomoConfig.init(context).getActivityToast()) {
            ToastUtil.showShort(context, msg);
        } else {
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
    }


    /***
     * 显示失败提示语,并且关闭Activity
     * @param msg
     */
    public void showToastFiledFinish(String msg) {
        if (IloomoConfig.init(context).getActivityToast()) {
            ToastUtil.showShort(context, msg);
        } else {
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
    }


    /***
     * 显示之后是否关闭activity
     * @param msg  显示成功或者失败的状态
     * @param blean  是否关闭Activity  false 是关闭  true 不关闭
     * @param isSuccess  是否是成功对话框   true  是成功对话框  false  是失败对话框
     */
    private void showToastFinish(String msg, final boolean blean, final boolean isSuccess) {
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout toast_layout = (RelativeLayout) inflate.inflate(R.layout.toast_layout, null);
        final RelativeLayout relativeLayout = (RelativeLayout) toast_layout.findViewById(R.id.toast_view);
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

    /***
     * 只有自定义Toast的时候才会回调
     */
    public void toastFinish() {
        finish();
    }

    /***
     * 只有自定义Toast的时候才会回调
     */
    public void toastOver() {

    }


    /***
     * 设置APP背景
     * @param color
     */
    public void setBackgroundColor(int color) {
        all_super.setBackgroundColor(color);
    }


    /***
     * 获取状态栏的高度
     * @return
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /***
     * 快速调转方法
     * @param packageContext
     * @param cls
     */
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
//        titleBar.setVisibility(View.GONE);
        all_super.removeView(titleBar);
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
        activityOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        HotUpdateUtils.setBackground(true);
        activityOnPause();
    }

    public void activityOnResume() {
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


    public void activityOnPause() {
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
        try {
            mQueue.cancelBySign(object);
            for (int i = 0; i < filesList.size(); i++) {
                DownLoad downLoad = filesList.get(i);
                downLoad.getmRequest().cancelBySign(i);
            }
        } catch (Exception e) {
        }
        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();
        mDownloadQueue.stop();
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

    /***
     * 打开网络设置
     */
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


    /**
     * 存储当前时间和服务器时间对比时间差
     *
     * @param serveTimesTamp 服务器返回时间戳
     */
    public void setTimeSync(String serveTimesTamp) {
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

    /***
     * 判断网络是否连接
     * @param context
     * @return
     */
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



    /****
     * 内部类  网络回调
     * @param <T>
     */
    private class HttpBaseResponListener<T> extends HttpV2ResponseListener {

        public HttpBaseResponListener(Activity activity, Request request, boolean canCancel, boolean isLoading, Class<?> modelClass) {
            super(activity, request, canCancel, isLoading, modelClass);
        }

        @Override
        public void onSucceed(int what, Response response) {
            super.onSucceed(what, response);
            String request = response.get().toString();
            L.e("返回值：" + UnicodeUtils.decodeUnicode(request));
            onSucceedBase(what, response, request, modelClass);
        }

        @Override
        public void onFailed(int what, Response response) {
            super.onFailed(what, response);
            onFildBase(what, response, modelClass);
        }
    }

    /***
     * 网络返回失败，回调面对activity
     * @param what
     * @param modelClass
     */
    public void onFailedV2(int what, Object modelClass) {

    }

    /***
     * 网络返回成功，原数据
     * @param what
     * @param modelClass
     */
    public void onSucceedV2(int what, Object modelClass) {

    }

    /***
     * 网络返回成功，String数据
     * @param what
     * @param result
     * @param modelClass
     */
    public void onSucceedV2(int what, String result, Object modelClass) {

    }


    /***
     *  网络请求成功的回调（父类调用）
     * @param what
     * @param response
     * @param request
     * @param modelClass
     */
    public void onSucceedBase(int what, Response response, String request, Class<?> modelClass) {

    }

    /***
     * 网络请求失败回调（父类调用）
     * @param what
     * @param response
     * @param modelClass
     */
    public void onFildBase(int what, Response response, Class<?> modelClass) {

    }


    List<DownLoad> filesList = new ArrayList<>();

    int queueCount = 0;

    /**
     * 下载----第一启动下载时调用，用downLoadStop调用停止下载，重新启动调用downLoadRestart
     *
     * @param what
     * @param url
     * @param path
     * @param filename
     * @param downloadCallback
     */
    public int downLoad(int what, String url, String path, String filename, DownloadCallback downloadCallback) {
        DownloadRequest mRequest = new DownloadRequest(url, RequestMethod.GET, path, filename, true, true);
        mRequest.setCancelSign(queueCount);
        mDownloadQueue.add(what, mRequest, downloadCallback);
        DownLoad downLoad = new DownLoad();
        downLoad.setDwhat(what);
        downLoad.setDdownloadCallback(downloadCallback);
        downLoad.setmRequest(mRequest);
        filesList.add(downLoad);
        queueCount++;
        return queueCount - 1;
    }


    /***
     * 重新开始
     */
    public void downLoadRestart(int mqueueCount) {
        DownLoad downLoad = filesList.get(mqueueCount);
        mDownloadQueue.add(downLoad.getDwhat(), downLoad.getmRequest(), downLoad.getDdownloadCallback());
    }

    /***
     * 停止下载
     */
    public void downLoadStop(int mqueueCount) {
        DownLoad downLoad = filesList.get(mqueueCount);
        if (downLoad.getmRequest() != null)
            downLoad.getmRequest().cancel();
    }

}
