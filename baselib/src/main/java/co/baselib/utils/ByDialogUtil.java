package co.baselib.utils;


import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import co.baselib.R;


// TODO: Auto-generated Javadoc

public class ByDialogUtil {

    private static ByDialog dialog;

    public static void startDialogLoading(Context context) {
        startDialogLoading(context, true);
    }

    private static final int paintMode=5;
    private static final int itemCount=8;
    private static final int circleRadius=8;

    public static void startDialogLoading(Context context, boolean isCancelable) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.by_progress_dialog,
                null);
        try {
            if (dialog == null) {
                dialog = new ByDialog(context, R.style.PgDialog, dialogView);
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
        View dialogView = LayoutInflater.from(context).inflate(R.layout.by_progress_dialog,
                null);
        TextView contents = (TextView) dialogView.findViewById(R.id.content);
        ByStrUtil.setText(contents, content);
        try {
            if (dialog != null) {
                dialog.cancel();
                dialog = null;
            }
            if (dialog == null) {
                dialog = new ByDialog(context, R.style.PgDialog, dialogView);
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
        View dialogView = LayoutInflater.from(context).inflate(R.layout.by_progress_dialog,
                null);
        TextView contents = (TextView) dialogView.findViewById(R.id.content);
        ByStrUtil.setText(contents, content);
        try {
            if (dialog != null) {
                dialog.cancel();
                dialog = null;
            }
            if (dialog == null) {
                dialog = new ByDialog(context, R.style.PgDialog, dialogView);

                dialog.show();
            }
            ByDialogManger.getInstance().addDialogs(dialog);
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

    public ByDialogUtil(Context context) {
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
