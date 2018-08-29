package com.iloomo.base;


import android.view.View;

/**
 * Activity帮助支持类接口.
 *
 * @author wpt
 */
public interface IFragmentSupport {
    /**
     * 获取EimApplication.
     *
     * @author wupeitao
     * @update 2012-7-6 上午9:05:51
     */
     abstract View initView();

     abstract View setTitleBar(View view);
}
