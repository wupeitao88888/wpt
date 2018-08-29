package com.iloomo.update.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.iloomo.global.AppConfig;
import com.iloomo.global.AppController;
import com.iloomo.model.MyOnCliclListener;
import com.iloomo.paysdk.R;
import com.iloomo.utils.ActivityPageManager;
import com.iloomo.utils.BigDecimalUtil;
import com.iloomo.utils.DrawableUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.LCSharedPreferencesHelper;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.widget.LCDialog;


/**
 * Created by wpt on 2017/5/16.
 */

public class UpdateDialogUtils {
    static Context context;

    public UpdateDialogUtils() {

    }


    public static UpdateDialogUtils ryDialogUtils;

    public static UpdateDialogUtils getInstaces(Context context) {
        UpdateDialogUtils.context = context;
        if (ryDialogUtils == null) {
            ryDialogUtils = new UpdateDialogUtils();
        }
        return ryDialogUtils;
    }

    protected LCSharedPreferencesHelper sharedPreferencesHelper = null;

    LCDialog lcDialog;

    /***
     * 提示对话框
     *
     * @param
     */
    public void showDialogUpdate(String versionnames, String versioncontent, MyOnCliclListener affirmListener, MyOnCliclListener cancelListener, boolean isBack) {
        sharedPreferencesHelper = new LCSharedPreferencesHelper(UpdateDialogUtils.context,
                LCSharedPreferencesHelper.SHARED_PATH);
        View inflate = LayoutInflater.from(UpdateDialogUtils.context).inflate(R.layout.layout_update, null);
        lcDialog = new LCDialog(UpdateDialogUtils.context, R.style.PgDialog, inflate);
        TextView versionname = (TextView) inflate.findViewById(R.id.versionname);
        TextView content = (TextView) inflate.findViewById(R.id.content);
        ImageView titleimg = (ImageView) inflate.findViewById(R.id.titleimg);
        String updateicon = sharedPreferencesHelper.getValue("updateicon");
        lcDialog.setIsback(true);
        lcDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (affirmListener != null)
                    affirmListener.onClick(inflate);
            }
        });
        L.e("返回更新对话框背景：" + updateicon);
        RequestOptions requestOptions = new RequestOptions();
//                .centerCrop()
//                .skipMemoryCache(true) // 跳过内存缓存
//                .diskCacheStrategy(DiskCacheStrategy.ALL); // 不缓存到SDCard中
        if (!TextUtils.isEmpty(updateicon)) {
//            Glide.with(UpdateDialogUtils.context)
//                    .asBitmap()
//                    .load(updateicon)
//                    .apply(requestOptions).into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

            String updateiconwidth = sharedPreferencesHelper.getValue("updateiconwidth");
            String updateiconheight = sharedPreferencesHelper.getValue("updateiconheight");
            double div = BigDecimalUtil.div(AppController.getInstance().dp2px(288), StrUtil.parseInt(updateiconwidth));
            double mul = BigDecimalUtil.mul(div, StrUtil.parseInt(updateiconheight));

            ViewGroup.LayoutParams lp = titleimg.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = (int) mul;

            titleimg.setLayoutParams(lp);
            PImageLoaderUtils.getInstance().displayIMGNoCenterGroup(updateicon, titleimg, UpdateDialogUtils.context);
            lcDialog.show();
            lcDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
//                            if (resource.isRecycled())
//                                resource.recycle();
                    ActivityPageManager.unbindReferences(inflate);
                }
            });
//                }
//            });


        } else {
//            Glide.with(UpdateDialogUtils.context)
//                    .asBitmap()
//                    .load(R.drawable.rocket_top)
//                    .apply(requestOptions).into(new SimpleTarget<Bitmap>() {
//                @Override
//                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

            double div = BigDecimalUtil.div(AppController.getInstance().dp2px(288), 864);
            double mul = BigDecimalUtil.mul(div, 531);

            ViewGroup.LayoutParams lp = titleimg.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = (int) mul;

            titleimg.setLayoutParams(lp);
            PImageLoaderUtils.getInstance().displayIMGLocal(R.drawable.rocket_top, titleimg, UpdateDialogUtils.context);
            lcDialog.show();
            lcDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    ActivityPageManager.unbindReferences(inflate);

                }
            });
//                }
//            });
        }
        StrUtil.setText(versionname, "V" + versionnames + "新版上线");
        String replace = versioncontent.replace("\\n", "\n");
        StrUtil.setText(content, replace);

//        lcDialog.setIsback(true);
        lcDialog.setCancelable(isBack);
        lcDialog.setCanceledOnTouchOutside(isBack);

        TextView affirm = (TextView) inflate.findViewById(R.id.affirm);
        TextView cancel = (TextView) inflate.findViewById(R.id.cancel);
        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lcDialog.isShowing()) {
                    lcDialog.dismiss();
                }
                if (affirmListener != null)
                    affirmListener.onClick(v);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lcDialog.isShowing()) {
                    lcDialog.dismiss();
                }
                if (cancelListener != null)
                    cancelListener.onClick(v);
            }
        });


    }


}
