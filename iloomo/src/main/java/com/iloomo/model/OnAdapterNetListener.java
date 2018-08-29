package com.iloomo.model;

import java.util.HashMap;
import java.util.Map;

/***
 * 适配器中网络回调
 */
public interface OnAdapterNetListener {
    void onNet(int task, String url, Map<String, String> parameter, Class<?> mclass, int position);
}
