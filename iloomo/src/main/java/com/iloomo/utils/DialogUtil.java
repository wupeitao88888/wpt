package com.iloomo.utils;


import com.iloomo.paysdk.R;
import com.iloomo.widget.LCDialog;
import com.iloomo.widget.spinnerloading.SpinnerLoading;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


// TODO: Auto-generated Javadoc

public class DialogUtil {

    private static LCDialog dialog;

    public static void startDialogLoading(Context context) {
        startDialogLoading(context, true);
    }

    private static final int paintMode=5;
    private static final int itemCount=8;
    private static final int circleRadius=8;

    public static void startDialogLoading(Context context, boolean isCancelable) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog,
                null);
        SpinnerLoading loading = (SpinnerLoading) dialogView.findViewById(R.id.loading);
        loading.setPaintMode(paintMode);
        loading.setItemCount(itemCount);
        loading.setCircleRadius(circleRadius);
        try {
            if (dialog == null) {
                dialog = new LCDialog(context, R.style.PgDialog, dialogView);
                if (!isCancelable) {
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);// 设置点击屏幕Dialog不消失
                }
                dialog.show();
//                DialogManger.getInstance().addDialogs(dialog);
            }
        } catch (Exception e) {

        }


    }

    public static void startDialogLoading(String progress) {



    }


    public static void startDialogLoadingText(Context context, boolean isCancelable, String content) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog,
                null);
        TextView contents = (TextView) dialogView.findViewById(R.id.content);
        SpinnerLoading loading = (SpinnerLoading) dialogView.findViewById(R.id.loading);
        loading.setPaintMode(paintMode);
        loading.setItemCount(itemCount);
        loading.setCircleRadius(circleRadius);
        StrUtil.setText(contents, content);
        try {
            if (dialog != null) {
                dialog.cancel();
                dialog = null;
            }
            if (dialog == null) {
                dialog = new LCDialog(context, R.style.PgDialog, dialogView);
                if (!isCancelable) {
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);// 设置点击屏幕Dialog不消失
                }
                dialog.show();
//                DialogManger.getInstance().addDialogs(dialog);
            }
        } catch (Exception e) {

        }


    }


    public static void startDialogLoadingText(Context context, String content) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog,
                null);
        TextView contents = (TextView) dialogView.findViewById(R.id.content);
        SpinnerLoading loading = (SpinnerLoading) dialogView.findViewById(R.id.loading);
        loading.setPaintMode(paintMode);
        loading.setItemCount(itemCount);
        loading.setCircleRadius(circleRadius);
        StrUtil.setText(contents, content);
        try {
            if (dialog != null) {
                dialog.cancel();
                dialog = null;
            }
            if (dialog == null) {
                dialog = new LCDialog(context, R.style.PgDialog, dialogView);

                dialog.show();
            }
            DialogManger.getInstance().addDialogs(dialog);
        } catch (Exception e) {

        }


    }

    public static void stopDialogLoading(Context context) {
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
//        OkHttpUtils.getInstance().cancelTag(context);
    }

    public Context context;

    public DialogUtil(Context context) {
        this.context = context;
    }



    public static ProgressDialog getProgressDialog(Context c, String title,
                                                   String message) {
        ProgressDialog mProDialog = ProgressDialog.show(c, null, message, true,
                true);
        mProDialog.setCanceledOnTouchOutside(false);
        return mProDialog;
    }


}
