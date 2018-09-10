package co.baselib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by wupeitao on 2018/1/7.
 */

public class DrawableUtil {

    /**
     * 定义一个shape资源
     *
     * @param rgb
     * @param corneradius
     * @return
     */
    @SuppressLint("WrongConstant")
    public static GradientDrawable getDrawable(int rgb, int corneradius, Context context) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(rgb);
        gradientDrawable.setGradientType(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(corneradius);
        gradientDrawable.setStroke(AppUtil.dip2px(context,0), rgb);
        return gradientDrawable;
    }



//     <!-- Non focused states -->
//    <item android:drawable="@mipmap/shouye" android:state_focused="false" android:state_pressed="false" android:state_selected="false"/>
//    <item android:drawable="@mipmap/shouye_select" android:state_focused="false" android:state_pressed="false" android:state_selected="true"/>
//    <!-- Focused states -->
//    <item android:drawable="@mipmap/shouye_select" android:state_focused="true" android:state_pressed="false" android:state_selected="false"/>
//    <item android:drawable="@mipmap/shouye_select" android:state_focused="true" android:state_pressed="false" android:state_selected="true"/>
//    <!-- Pressed -->
//    <item android:drawable="@mipmap/shouye_select" android:state_pressed="true" android:state_selected="true"/>
//    <item android:drawable="@mipmap/shouye_select" android:state_pressed="true"/>

    /***
     * 设置选择器 选中的时候
     * @param normalDrawable
     * @param pressDrawable
     * @return
     */
    public static StateListDrawable getSelector(Drawable normalDrawable, Drawable pressDrawable) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        //给当前的颜色选择器添加选中图片指向状态，未选中图片指向状态
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, pressDrawable);
        //设置默认状态
        stateListDrawable.addState(new int[]{-android.R.attr.state_selected}, normalDrawable);
        return stateListDrawable;
    }


    /***
     * 对TextView设置不同状态时其文字颜色。
     * @param normal  正常
     * @param pressed 按下
     * @param focused 获取焦点
     * @param unable  不可点击
     * @return
     */
    public static ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[] { pressed, focused, normal, focused, unable, normal };
        int[][] states = new int[6][];
        states[0] = new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled };
        states[1] = new int[] { android.R.attr.state_enabled, android.R.attr.state_focused };
        states[2] = new int[] { android.R.attr.state_enabled };
        states[3] = new int[] { android.R.attr.state_focused };
        states[4] = new int[] { android.R.attr.state_window_focused };
        states[5] = new int[] {};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    public static ColorStateList createColorStateList(int normal, int pressed) {
        int[] colors = new int[]{ pressed,normal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{-android.R.attr.state_selected};

        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }




}
