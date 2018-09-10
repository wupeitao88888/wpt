package co.baselib.update.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import co.baselib.R;
import co.baselib.global.AppController;
import co.baselib.model.MyOnCliclListener;
import co.baselib.utils.ActivityPageManager;
import co.baselib.utils.BigDecimalUtil;
import co.baselib.utils.L;
import co.baselib.utils.LCDialog;
import co.baselib.utils.LCSharedPreferencesHelper;
import co.baselib.utils.PImageLoaderUtils;
import co.baselib.utils.StrUtil;


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
    public void showDialogUpdate(String versionnames, String versioncontent, final MyOnCliclListener affirmListener, final MyOnCliclListener cancelListener, boolean isBack) {
        sharedPreferencesHelper = new LCSharedPreferencesHelper(UpdateDialogUtils.context,
                LCSharedPreferencesHelper.SHARED_PATH);
        final View inflate = LayoutInflater.from(UpdateDialogUtils.context).inflate(R.layout.layout_update, null);
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
