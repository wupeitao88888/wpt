package co.baselib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class ChildClickableRelativeLayout extends FrameLayout {
    // 子控件是否可以接受点击事件
    private boolean childClickable = true;

    public ChildClickableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public ChildClickableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ChildClickableRelativeLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * 重写onInterceptTouchEvent（）
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 返回true则拦截子控件所有点击事件，如果childclickable为true，则需返回false
        return !childClickable;
    }

    /**
     * 然后就像正常LinearLayout一样使用这个控件就可以了。在需要的时候调用一下setChildClickable，
     * 参数为true则所有子控件可以点击，false则不可点击。
     *
     * @param clickable
     */

    public void setChildClickable(boolean clickable) {
        childClickable = clickable;
    }

}
