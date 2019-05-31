package co.baselib.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

import co.baselib.utils.L;

public abstract class BaseToolBar extends Toolbar {

    protected OnOptionItemClickListener mOnOptionItemClickListener;

    public BaseToolBar(Context context) {
        this(context,null,0);
    }

    public BaseToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public BaseToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    protected void initialize(Context context, AttributeSet attrs, int defStyleAttr) {
        initCustomView(context, attrs, defStyleAttr);
    }


    protected abstract void initCustomView(Context context, AttributeSet attrs, int defStyleAttr);

    public boolean isChild(View view) {
        return view != null && view.getParent() == this;
    }

    public boolean isChild(View view, ViewParent parent) {
        return view != null && view.getParent() == parent;
    }

    public void setOnOptionItemClickListener(OnOptionItemClickListener listener) {
        mOnOptionItemClickListener = listener;
    }

    public interface OnOptionItemClickListener {
        void onOptionItemClick(View v);
    }

}
