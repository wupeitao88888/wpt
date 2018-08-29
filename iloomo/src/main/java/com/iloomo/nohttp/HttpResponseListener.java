/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iloomo.nohttp;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.iloomo.bean.BaseModel;
import com.iloomo.global.AppConfig;
import com.iloomo.global.AppController;
import com.iloomo.model.OnAgainLoginListener;
import com.iloomo.paysdk.R;
import com.iloomo.utils.DateUtil;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.LCSharedPreferencesHelper;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.UnicodeUtils;
import com.yanzhenjie.nohttp.error.NetworkError;
import com.yanzhenjie.nohttp.error.NotFoundCacheError;
import com.yanzhenjie.nohttp.error.TimeoutError;
import com.yanzhenjie.nohttp.error.URLError;
import com.yanzhenjie.nohttp.error.UnKnownHostError;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created in Nov 4, 2015 12:02:55 PM.
 *
 * @author Yan Zhenjie.
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {

    private Activity mActivity;

    Gson gson;
    /**
     * Request.
     */
    private Request<?> mRequest;
    /**
     * 结果回调.
     */
    private HttpListener<T> callback;

    private Class<?> modelClass;
    private Context context;
    private boolean canCancel = true;
    private boolean isLoading = false;

    /**
     * @param activity     context用来实例化dialog.
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     * @param canCancel    是否允许用户取消请求.
     * @param isLoading    是否显示dialog.
     */
    public HttpResponseListener(Activity activity, Request<T> request, HttpListener<T> httpCallback, boolean
            canCancel, boolean isLoading, Class<?> modelClass) {
        this.mActivity = activity;
        this.mRequest = request;
        this.modelClass = modelClass;
        this.callback = httpCallback;
        this.canCancel = canCancel;
        this.isLoading = isLoading;
        gson=new Gson();
    }

    /**
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     */
    public HttpResponseListener(Request<T> request, HttpListener<T> httpCallback, Class<?> modelClass) {
        this.mRequest = request;
        this.modelClass = modelClass;
        this.callback = httpCallback;
        gson=new Gson();
    }

    /**
     * @param context      请求对象.
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     */
    public HttpResponseListener(Context context, Request<T> request, HttpListener<T> httpCallback, boolean
            canCancel, boolean isLoading, Class<?> modelClass) {
        this.mRequest = request;
        this.modelClass = modelClass;
        this.callback = httpCallback;
        this.context = context;
        this.canCancel = canCancel;
        this.isLoading = isLoading;
        gson=new Gson();
    }


    /**
     * 开始请求, 这里显示一个dialog.
     */
    @Override
    public void onStart(int what) {
        start();

    }


    private void start() {
        if (mActivity != null) {
            if (isLoading) {
                if (canCancel) {
                    DialogUtil.startDialogLoadingText(mActivity, "加载中...");
                } else {
                    DialogUtil.startDialogLoading(mActivity, true);
                }
            }
        }
        if (context != null) {
            if (isLoading) {
                if (canCancel) {
                    DialogUtil.startDialogLoadingText(context, "加载中...");
                } else {
                    DialogUtil.startDialogLoading(context, true);
                }
            }
        }

    }

    /**
     * 结束请求, 这里关闭dialog.
     */
    @Override
    public void onFinish(int what) {
        if (mActivity != null) {
            if (isLoading) {
                DialogUtil.stopDialogLoading(mActivity);
            }
        }
        if (context != null) {
            if (isLoading) {
                DialogUtil.stopDialogLoading(context);
            }
        }
    }

    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {
        if (callback != null) {
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
                    callback.onSucceed(what, response, obj);
                } else if ("20018".equals(bmodel.getErrorCode())) {
                    Object obj = gson.fromJson(request, modelClass);
                    callback.onSucceed(what, response, obj);
                } else if ("20004".equals(bmodel.getErrorCode())) {
                    if (HttpResponseListener.onAgainLoginListener != null) {
                        HttpResponseListener.onAgainLoginListener.onAgainLogin();
                    }
                    bmodel.setErrorMessage("");
                    callback.onFailed(what, response, bmodel);
                } else if ("20010".equals(bmodel.getErrorCode())) {
                    if (HttpResponseListener.onAgainLoginListener != null) {
                        HttpResponseListener.onAgainLoginListener.onAgainLogin();
                    }
                    bmodel.setErrorMessage("");
                    callback.onFailed(what, response, bmodel);
                } else if ("10010".equals(bmodel.getErrorCode())) {
                    if (HttpResponseListener.onAgainLoginListener != null) {
                        HttpResponseListener.onAgainLoginListener.onAgainLogin();
                    }
                    bmodel.setErrorMessage("");
                    callback.onFailed(what, response, bmodel);
                } else if ("5000".equals(bmodel.getErrorCode())) {
                    if (HttpResponseListener.onAgainLoginListener != null) {
                        HttpResponseListener.onAgainLoginListener.onOutLogin(bmodel);
                    }
                    bmodel.setErrorMessage("");
                    callback.onFailed(what, response, bmodel);
                } else {
                    callback.onFailed(what, response, bmodel);
                }

            } catch (Exception e) {
                L.e("异常：" + e.getMessage());
                if (TextUtils.isEmpty(e.getMessage())) {
                    BaseModel baseModel = new BaseModel();
                    baseModel.setErrorCode("699");
                    baseModel.setErrorMessage("异常信息为空！");
                    callback.onFailed(what, response, baseModel);
                } else if (e.getMessage().equals("You cannot start a load for a destroyed activity")) {

                } else {
                    BaseModel baseModel = new BaseModel();
                    baseModel.setErrorCode("698");
                    baseModel.setErrorMessage("数据解析异常！");
                    callback.onFailed(what, response, baseModel);
                }

            }

        }
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


    /**
     * 失败回调.
     */
    @Override
    public void onFailed(int what, Response<T> response) {
        Exception exception = response.getException();
        BaseModel baseModel = new BaseModel();
        if (exception instanceof NetworkError) {// 网络不好
            baseModel.setErrorMessage(AppController.getInstance().getResources(R.string.error_please_check_network));
            baseModel.setErrorCode("404");
        } else if (exception instanceof TimeoutError) {// 请求超时
            baseModel.setErrorMessage(AppController.getInstance().getResources(R.string.error_timeout));
            baseModel.setErrorCode("404");
        } else if (exception instanceof UnKnownHostError) {// 找不到服务器
            baseModel.setErrorMessage(AppController.getInstance().getResources(R.string.error_not_found_server));
            baseModel.setErrorCode("404");
        } else if (exception instanceof URLError) {// URL是错的
            baseModel.setErrorMessage(AppController.getInstance().getResources(R.string.error_url_error));
            baseModel.setErrorCode("404");
        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
            // 没有缓存一般不提示用户，如果需要随你。
        } else {
            baseModel.setErrorMessage(AppController.getInstance().getResources(R.string.error_unknow));
            baseModel.setErrorCode("304");
        }


        L.e("错误：" + exception.getMessage());
        if (callback != null) {
            callback.onFailed(what, response, baseModel);
        }

    }


    public static OnAgainLoginListener onAgainLoginListener;

    /**
     * 重新登陆监听
     *
     * @param onAgainLoginListener
     */

    public static void setAgainLoginState(OnAgainLoginListener onAgainLoginListener) {
        HttpResponseListener.onAgainLoginListener = onAgainLoginListener;
    }

}
