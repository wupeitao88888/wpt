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
package co.baselib.nohttp;

import android.app.Activity;
import android.content.Context;

import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import co.baselib.model.OnAgainLoginListener;
import co.baselib.utils.DialogUtil;
import co.baselib.utils.L;

/**
 * Created in Nov 4, 2015 12:02:55 PM.
 *
 * @author Yan Zhenjie.
 */
public class HttpV2ResponseListener<T> implements OnResponseListener<T> {

    private Activity mActivity;

    /**
     * Request.
     */
    private Request<?> mRequest;


    public Class<?> modelClass;
    private Context context;
    private boolean canCancel = true;
    private boolean isLoading = false;

    /**
     * @param activity  context用来实例化dialog.
     * @param request   请求对象.
     * @param canCancel 是否允许用户取消请求.
     * @param isLoading 是否显示dialog.
     */
    public HttpV2ResponseListener(Activity activity, Request<T> request, boolean
            canCancel, boolean isLoading, Class<?> modelClass) {
        this.mActivity = activity;
        this.mRequest = request;
        this.modelClass = modelClass;
        this.canCancel = canCancel;
        this.isLoading = isLoading;
    }

    /**
     * @param request 请求对象.
     */
    public HttpV2ResponseListener(Request<T> request, Class<?> modelClass) {
        this.mRequest = request;
        this.modelClass = modelClass;
    }

    /**
     * @param context 请求对象.
     * @param request 请求对象.
     */
    public HttpV2ResponseListener(Context context, Request<T> request, boolean
            canCancel, boolean isLoading, Class<?> modelClass) {
        this.mRequest = request;
        this.modelClass = modelClass;
        this.context = context;
        this.canCancel = canCancel;
        this.isLoading = isLoading;
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
            DialogUtil.stopDialogLoading(context);
        }
    }

    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, Response<T> response) {

    }



    /**
     * 失败回调.
     */
    @Override
    public void onFailed(int what, Response<T> response) {
        Exception exception = response.getException();
        L.e("错误：" + exception.getMessage());

    }


    public static OnAgainLoginListener onAgainLoginListener;

    /**
     * 重新登陆监听
     *
     * @param onAgainLoginListener
     */

    public static void setAgainLoginState(OnAgainLoginListener onAgainLoginListener) {
        HttpV2ResponseListener.onAgainLoginListener = onAgainLoginListener;
    }

}
