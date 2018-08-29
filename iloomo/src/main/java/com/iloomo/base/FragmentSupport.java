package com.iloomo.base;


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
import com.iloomo.bean.BaseModel;
import com.iloomo.global.AppController;
import com.iloomo.global.HttpTaskID;
import com.iloomo.global.IloomoConfig;
import com.iloomo.model.OnAdapterToastListener;
import com.iloomo.nohttp.HttpResponseListener;
import com.iloomo.nohttp.HttpV2ResponseListener;
import com.iloomo.paysdk.R;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.ActivityPageManager;
import com.iloomo.utils.DateUtil;
import com.iloomo.utils.DialogManger;
import com.iloomo.utils.L;
import com.iloomo.utils.LCSharedPreferencesHelper;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.UnicodeUtils;
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
            PImageLoaderUtils.getInstance().displayIMG(R.drawable.new_empty_nocontent, empty_nocontent, context);
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
            PImageLoaderUtils.getInstance().displayIMG(R.drawable.new_no_network, network, context);
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

        MobclickAgent.onPageStart(title);

    }

    @Override
    public void onPause() {
        super.onPause();

        MobclickAgent.onPageEnd(title);

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
    public void showToastFinish(String msg, boolean blean, boolean isSuccess) {
        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout toast_layout = (RelativeLayout) inflate.inflate(R.layout.toast_layout, null);
        RelativeLayout toast_view = (RelativeLayout) toast_layout.findViewById(R.id.toast_view);
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

    public void toastFinish() {

    }

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

        private Gson gson;

        public HttpBaseResponListener(Activity activity, Request request, boolean canCancel, boolean isLoading, Class modelClass) {
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

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
