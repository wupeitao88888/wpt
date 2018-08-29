package com.iloomo.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iloomo.global.AppController;
import com.iloomo.global.IloomoConfig;
import com.iloomo.paysdk.R;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.ActivityPageManager;
import com.iloomo.utils.L;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.widget.ChildClickableRelativeLayout;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于tabhost
 * Created by wupeitao on 16/3/8.
 */
public class TabFragmentActivity extends ActivitySupport {


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }
        return false;
    }

    long exitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToastFiled(getResources().getString(R.string.exit));
            exitTime = System.currentTimeMillis();
        } else {
            isExit();
        }
    }

    /**
     * 描述：tuichu
     *
     * @param
     */
    public void isExit() {

        AppController.getInstance().exit();
    }

}
