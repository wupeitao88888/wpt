/*
 * Copyright © Yan Zhenjie
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

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>针对队列的一个全局单例封装。</p>
 * Created by YanZhenjie on 2017/6/18.
 */
public class CallServer {

    private static CallServer instance;

    public static CallServer getInstance() {
        if (instance == null)
            synchronized (CallServer.class) {
                if (instance == null)
                    instance = new CallServer();
            }
        return instance;
    }

    private RequestQueue mRequestQueue;
    private DownloadQueue mDownloadQueue;

    List<RequestQueue> listRequestQueues = new ArrayList<>();

    private CallServer() {
        int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 1;// 设置线程池的最大线程数
        mRequestQueue = NoHttp.newRequestQueue(MAX_POOL_SIZE/2);
        mDownloadQueue = NoHttp.newDownloadQueue(MAX_POOL_SIZE/2);
    }

    public <T> void request(int what, Request<T> request, OnResponseListener<T> listener) {
        mRequestQueue.add(what, request, listener);
        listRequestQueues.add(mRequestQueue);
    }

    public <T> void request(Activity activity, int what, Request<T> request, HttpListener<T> callback,
                            boolean canCancel, boolean isLoading, Class<?> modelclass) {
        mRequestQueue.add(what, request, new HttpResponseListener<>(activity, request, callback, canCancel, isLoading, modelclass));
        listRequestQueues.add(mRequestQueue);
    }

    public <T> void request(Context context, int what, Request<T> request, HttpListener<T> callback,
                            boolean canCancel, boolean isLoading, Class<?> modelclass) {
        mRequestQueue.add(what, request, new HttpResponseListener<>(context, request, callback, canCancel, isLoading, modelclass));
        listRequestQueues.add(mRequestQueue);
    }

    public <T> void request(int what, Request<T> request, HttpListener<T> callback,
                            Class<?> modelclass) {
        mRequestQueue.add(what, request, new HttpResponseListener<>(request, callback, modelclass));
        listRequestQueues.add(mRequestQueue);
    }

    public <T> void request(int what, Request<T> request, HttpListener<T> callback, boolean canCancel, boolean isLoading,
                            Class<?> modelclass) {
        mRequestQueue.add(what, request, new HttpResponseListener<>(request, callback, modelclass));
        listRequestQueues.add(mRequestQueue);
    }


    public void download(int what, DownloadRequest request, HttpDownloadListener listener) {
        mDownloadQueue.add(what, request, listener);
    }


    public void stopNetALl(){
        for(int i=0;i<listRequestQueues.size();i++){
            listRequestQueues.get(i).cancelAll();
            listRequestQueues.get(i).stop();
        }
    }

}
