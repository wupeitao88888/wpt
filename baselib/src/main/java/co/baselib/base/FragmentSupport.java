package co.baselib.base;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import co.baselib.R;
import co.baselib.global.AppController;
import co.baselib.global.IloomoConfig;
import co.baselib.model.OnAdapterToastListener;
import co.baselib.nohttp.HttpV2ResponseListener;
import co.baselib.threadpool.MyThreadPool;
import co.baselib.update.utils.HotUpdateUtils;
import co.baselib.utils.ActivityErrorUtils;
import co.baselib.utils.ActivityPageManager;
import co.baselib.utils.DateUtil;
import co.baselib.utils.DialogManger;
import co.baselib.utils.L;
import co.baselib.utils.LCSharedPreferencesHelper;
import co.baselib.utils.PImageLoaderUtils;
import co.baselib.utils.StrUtil;
import co.baselib.utils.ToastUtil;
import co.baselib.utils.UnicodeUtils;


/**
 * Created by wupeitao on 16/1/7.
 */
public class FragmentSupport extends Fragment implements IFragmentSupport {
    public Context context;
    public String title;
    public View rootView;// 缓存Fragment view
    View all_super;
    LinearLayout content_layout;

    public OnAdapterToastListener onAdapterToastListener;
    protected LCSharedPreferencesHelper sharedPreferencesHelper = null;


    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    RelativeLayout layout_error;
    public Gson gson;

    /***
     * 单一布局中使用，第一次加载失败时使用
     *
     * @param blean
     */
    public void netError(boolean blean, View.OnClickListener onClickListener) {
        if (layout_error == null) {
            layout_error = (RelativeLayout) LayoutInflater.from(context).inflate(
                    R.layout.layout_error, null);
            layout_parent.addView(layout_error);
            initNetStatus(layout_parent, context);
        }
        nError(blean, onClickListener);
    }


    /***
     * 单一布局中使用，第一次无数据时使用
     *
     * @param blean
     */
    public void noData(boolean blean, View.OnClickListener onClickListener) {
        if (layout_error == null) {
            layout_error = (RelativeLayout) LayoutInflater.from(context).inflate(
                    R.layout.layout_error, null);
            layout_parent.addView(layout_error);
            initNetStatus(layout_parent, context);
        }
        noDate(blean, onClickListener);

    }


    private RelativeLayout error;
    private RelativeLayout rl_nocontent;//这里什么都没有
    private LinearLayout ll_no_network;//没有网络
    private ImageView empty_nocontent;//没有内容图片
    private ImageView network;//内有网络图片
    private Button btn_refresh;


    public void initNetStatus(View view, Context context) {
        error = view.findViewById(R.id.error);
        rl_nocontent = view.findViewById(R.id.rl_nocontent);
        ll_no_network = view.findViewById(R.id.ll_no_network);
        empty_nocontent = view.findViewById(R.id.empty_nocontent);
        network = view.findViewById(R.id.network);
        btn_refresh = view.findViewById(R.id.btn_refresh);
        this.context = context;
    }

    public void noDate(boolean blean, View.OnClickListener onClickListener) {
        if (blean) {
            error.setVisibility(View.VISIBLE);
            rl_nocontent.setVisibility(View.VISIBLE);
            ll_no_network.setVisibility(View.GONE);
            PImageLoaderUtils.getInstance().displayIMG(ActivityErrorUtils.getInstance().getNoDateIcon(), empty_nocontent, context);
            rl_nocontent.setOnClickListener(onClickListener);
        } else {
            ll_no_network.setVisibility(View.GONE);
            rl_nocontent.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            rl_nocontent.setOnClickListener(null);
        }

    }


    /***
     * 单一布局中使用，第一次加载失败时使用
     *
     * @param blean
     */
    public void nError(boolean blean, View.OnClickListener onClickListener) {
        if (blean) {
            error.setVisibility(View.VISIBLE);
            ll_no_network.setVisibility(View.VISIBLE);
            rl_nocontent.setVisibility(View.GONE);
            PImageLoaderUtils.getInstance().displayIMG(ActivityErrorUtils.getInstance().getNoNetIcon(), network, context);
            btn_refresh.setOnClickListener(onClickListener);
        } else {
            rl_nocontent.setVisibility(View.GONE);
            ll_no_network.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            btn_refresh.setOnClickListener(null);
        }
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
            initNetStatus(layout_parent, context);
        }
        netError(blean, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    RelativeLayout layout_parent;
    public boolean islogin = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        context = this.getActivity();
        sharedPreferencesHelper = new LCSharedPreferencesHelper(context,
                LCSharedPreferencesHelper.SHARED_PATH);
        islogin = sharedPreferencesHelper.getBoolean(LCSharedPreferencesHelper.ISLOGIN);
        if (rootView == null) {
            viewInit();
            all_super = LayoutInflater.from(context).inflate(
                    R.layout.layout_fragmentsuper, null);

            content_layout = (LinearLayout) all_super.findViewById(R.id.content_layout);
            layout_parent = (RelativeLayout) all_super.findViewById(R.id.content_layout_parent);
            // 自己的布局
            View customContentView = initView();
            content_layout.addView(customContentView);
            rootView = all_super;
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        View view = setTitleBar(this.rootView);
        return view;
    }

    public void viewInit() {
        // 初始化请求队列，传入的参数是请求并发值。
        mQueue = NoHttp.newRequestQueue();
    }


    public View findViewById(View view, int id) {
        return view.findViewById(id);
    }


    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public void onResume() {
        super.onResume();
        HotUpdateUtils.setBackground(false);
        /**
         * 友盟统计
         */
        if (title != null) {
            if (TextUtils.isEmpty(title))
                title = "Fragment";
            MobclickAgent.onPageStart(title);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        HotUpdateUtils.setBackground(true);
        /**
         * 友盟统计
         */
        if (title != null) {
            if (TextUtils.isEmpty(title))
                title = "Fragment";
            MobclickAgent.onPageEnd(title);
        }

    }

    @Override
    public void onDestroy() {
        mQueue.cancelBySign(object);
        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();
        DialogManger.getInstance().cancelsDialogs();
        super.onDestroy();
        //Acitvity 释放子view资源
        ActivityPageManager.unbindReferences(all_super);
        all_super = null;
        ActivityPageManager.unbindReferences(rootView);
        rootView = null;
    }


    public void setContent(int layout) {
        this.rootView = LayoutInflater.from(context).inflate(layout, null);
    }

    @Override
    public View initView() {
        gson = new Gson();
        return rootView;
    }

    @Override
    public View setTitleBar(View view) {
        return view;
    }

    public String mString(int string) {
        // TODO Auto-generated method stub
        return getResources().getString(string);
    }

    /**
     * 设置titleb中间
     */
    @Deprecated
    protected void setRemoveTitle() {

    }

    /**
     * 设置titleb中间
     *
     * @param title
     */
    @Deprecated
    protected void setCtenterTitle(String title) {

    }

    /**
     * 设置titleb中间
     *
     * @param title
     */
    @Deprecated
    protected void setCtenterTitle(int title) {

    }


    /**
     * 设置右边标题内容
     */
    @Deprecated
    protected void setRightTitle(String msg) {

    }

    /**
     * 设置右边标题内容字体颜色
     */
    @Deprecated
    protected void setRightTitle(int msg) {
    }

    /**
     * 设置右边图片内容
     */
    @Deprecated
    protected void setRightTitleRes(int msg) {

    }

    /**
     * 设置右边标题内容
     */
    @Deprecated
    protected void setRightTitleListener(View.OnClickListener l) {

    }

    /**
     * 设置右边图片内容
     */
    @Deprecated
    protected void setRightTitleImageListener(View.OnClickListener l) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    Handler toastHandler = new Handler();

    boolean isShow = false; //是否显示提示语

    String lastMsg = "";

    private List<String> toastList = new ArrayList<>();


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


    public boolean IS_ONTITLEBAR = false;

    /***
     * 显示之后是否关闭activity
     * @param msg  显示成功或者失败的状态
     * @param blean  是否关闭Activity  false 是关闭  true 不关闭
     * @param isSuccess  是否是成功对话框   true  是成功对话框  false  是失败对话框
     */
    /***
     * 显示之后是否关闭activity
     * @param msg  显示成功或者失败的状态
     * @param blean  是否关闭Activity  false 是关闭  true 不关闭
     * @param isSuccess  是否是成功对话框   true  是成功对话框  false  是失败对话框
     */
    public void showToastFinish(String msg, final boolean blean, final boolean isSuccess) {
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final RelativeLayout toast_layout = (RelativeLayout) inflate.inflate(R.layout.toast_layout, null);
        final RelativeLayout toast_view = (RelativeLayout) toast_layout.findViewById(R.id.toast_view);
        RelativeLayout topview = (RelativeLayout) toast_layout.findViewById(R.id.topview);
        ImageView show = (ImageView) toast_layout.findViewById(R.id.show);
        TextView content_msg = (TextView) toast_layout.findViewById(R.id.toast_text);
        if (!isSuccess) {
            toast_view.setBackgroundColor(ContextCompat.getColor(context, IloomoConfig.init(context).getResInit().getFaliedAnimToastBg()));
            PImageLoaderUtils.getInstance().displayIMGLocal(IloomoConfig.init(context).getResInit().getFiledId(), show, context);
        } else {
            toast_view.setBackgroundColor(ContextCompat.getColor(context, IloomoConfig.init(context).getResInit().getSuccessAnimToastBg()));
            PImageLoaderUtils.getInstance().displayIMGLocal(IloomoConfig.init(context).getResInit().getDrawableId(), show, context);
        }
        content_msg.setText(msg);
        ViewGroup.LayoutParams layoutParams = topview.getLayoutParams();
        if (IS_ONTITLEBAR) {
            topview.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                layoutParams.height = (AppController.getInstance().dp2px(44) + getStatusBarHeight());
            } else {
                layoutParams.height = 0;
            }
        } else {
            topview.setVisibility(View.GONE);
        }

        layout_parent.addView(toast_layout);
        L.e("添加后子布局的个数：" + layout_parent.getChildCount());
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(toast_view, "translationY", -AppController.getInstance().dp2px(44), 0)
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
                                        ObjectAnimator.ofFloat(toast_view, "translationY", 0, -AppController.getInstance().dp2px(44))
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
     * 自定义Toast才会回调
     */
    public void toastFinish() {

    }
    /***
     * 自定义Toast才会回调
     */
    public void toastOver() {

    }

    /**
     * 用来标记取消。
     */
    private Object object = new Object();

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
        mQueue.add(what, request, new HttpBaseResponListener<>(getActivity(), request, canCancel, isLoading, modelClass));
    }


    private class HttpBaseResponListener<T> extends HttpV2ResponseListener {

        public HttpBaseResponListener(Activity activity, Request request, boolean canCancel, boolean isLoading, Class modelClass) {
            super(activity, request, canCancel, isLoading, modelClass);
        }

        @Override
        public void onSucceed(int what, Response response) {
            super.onSucceed(what, response);
            // 这里判断一下http响应码，这个响应码问下你们的服务端你们的状态有几种，一般是200成功。
            // w3c标准http响应码：http://www.w3school.com.cn/tags/html_ref_httpmessages.asp
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
     * 网络返回错误原因
     * @param what
     * @param modelClass
     */
    public void onFailedV2(int what, Object modelClass) {

    }

    /***
     * 网络返回成功对象
     * @param what
     * @param modelClass
     */
    public void onSucceedV2(int what, Object modelClass) {

    }

    /***
     * 网络返回成功对象+原数据
     * @param what
     * @param result
     * @param modelClass
     */
    public void onSucceedV2(int what, String result, Object modelClass) {

    }

    /***
     * 获取资源里面的文本
     * @param resId
     * @return
     */
    public String getResString(int resId) {
        return getResources().getText(resId).toString();
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


}
