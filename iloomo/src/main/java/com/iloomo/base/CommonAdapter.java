package com.iloomo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by wupeitao on 16/3/8.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context context;
    protected List<T> mDatas;

    public CommonAdapter(Context context, List<T> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {

        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
}
