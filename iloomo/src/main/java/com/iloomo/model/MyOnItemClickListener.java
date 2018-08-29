package com.iloomo.model;

import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;

/**
 * Created by wpt on 2017/5/25.
 */

public class MyOnItemClickListener implements AdapterView.OnItemClickListener {
    PopupWindow popWindow;

    public MyOnItemClickListener(PopupWindow popWindow) {
        this.popWindow = popWindow;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (popWindow.isShowing())
            popWindow.dismiss();
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(parent, view, position, id);
    }



    public MOnItemClickListener mOnItemClickListener;

    public void setMOnItemClickListener(MOnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}
