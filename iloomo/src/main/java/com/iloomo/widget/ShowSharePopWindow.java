package com.iloomo.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iloomo.bean.ShareModel;
import com.iloomo.global.AppConfig;
import com.iloomo.model.OnAdapterToastListener;
import com.iloomo.paysdk.R;
import com.iloomo.utils.L;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import com.iloomo.shareutil.ShareUtil;


/**
 * 分享的弹出框
 *
 * @author wpt
 */
public class ShowSharePopWindow extends PopupWindow implements View.OnClickListener {


    RelativeLayout write;
    RelativeLayout report;
    RelativeLayout qq;
    RelativeLayout zome;
    RelativeLayout wechat;
    RelativeLayout circle;
    RelativeLayout weibo;
    Activity context;
    TextView cancel;
    String tid;
    String type;
    private LinearLayout all_pop;
    private LinearLayout mLlShareParent;
    private OnAdapterToastListener onAdapterToastListener;

    public ShowSharePopWindow(final Activity context, String id, String type, OnAdapterToastListener onAdapterToastListener) {
        this.onAdapterToastListener = onAdapterToastListener;
        this.context = context;
        this.tid = id;
        this.type = type;
        View conentView = LayoutInflater.from(context).inflate(R.layout.layout_sharepop, null);
        write = (RelativeLayout) conentView.findViewById(R.id.write);
        report = (RelativeLayout) conentView.findViewById(R.id.report);
        qq = (RelativeLayout) conentView.findViewById(R.id.qq);
        zome = (RelativeLayout) conentView.findViewById(R.id.zome);
        wechat = (RelativeLayout) conentView.findViewById(R.id.wechat);
        circle = (RelativeLayout) conentView.findViewById(R.id.circle);
        weibo = (RelativeLayout) conentView.findViewById(R.id.weibo);
        cancel = (TextView) conentView.findViewById(R.id.cancel);
        all_pop = (LinearLayout) conentView.findViewById(R.id.all_pop);
        mLlShareParent = (LinearLayout) conentView.findViewById(R.id.ll_share_parent);
        write.setOnClickListener(this);
        report.setOnClickListener(this);
        qq.setOnClickListener(this);
        zome.setOnClickListener(this);
        wechat.setOnClickListener(this);
        circle.setOnClickListener(this);
        weibo.setOnClickListener(this);
        cancel.setOnClickListener(this);
        all_pop.setOnClickListener(this);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.black_e0));

        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimationPreview);

        Animation myAnimation = AnimationUtils.loadAnimation(context, R.anim.popwindow_enter_anim);
        myAnimation.setDuration(200);
        mLlShareParent.setAnimation(myAnimation);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                Animation myAnimation = AnimationUtils.loadAnimation(context, R.anim.popwindow_out_anim);
                myAnimation.setDuration(200);
                mLlShareParent.setAnimation(myAnimation);
            }
        });


    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        } else {
            this.dismiss();
        }
    }

    UMShareListener mShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            L.e("—————————————————————————分享开始—————————————————————————");

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            L.e("—————————————————————————分享成功—————————————————————————");
            if (onShareListener != null) {
                onShareListener.success(share_media);
            } else {
                if (onAdapterToastListener != null)
                    onAdapterToastListener.onSuccess("分享成功");
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            L.e("—————————————————————————分享失败—————————————————————————");
            if (onAdapterToastListener != null)
                onAdapterToastListener.onFiled("分享失败");
            if (onShareListener != null) {
                onShareListener.fild(share_media);
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            L.e("—————————————————————————取消分享—————————————————————————");
            if (onAdapterToastListener != null)
                onAdapterToastListener.onFiled("取消分享");
            if (onShareListener != null) {
                onShareListener.cancel(share_media);
            }
        }
    };


    @Override
    public void onClick(View v) {
        int i = v.getId();
        this.dismiss();
        if (i == R.id.write) {
            if (onWriteClickListener != null)
                onWriteClickListener.write();
        } else if (i == R.id.report) {
            if (onWriteClickListener != null)
                onWriteClickListener.report(tid, type);
        } else if (i == R.id.qq) {
            if (shareModel == null) {
                if (onAdapterToastListener != null)
                    onAdapterToastListener.onFiled("分享失败");
                if (onShareListener != null) {
                    onShareListener.fild(SHARE_MEDIA.QQ);
                }
                return;
            }
            if (shareModel.getBitmap() != null) {
                UMImage umImage = new UMImage(context, shareModel.getBitmap());
                ShareUtil.getInstance().share(SHARE_MEDIA.QQ, context, shareModel, umImage, mShareListener);
                return;
            }
            UMImage umImage = new UMImage(context, shareModel.getImgurl());
            ShareUtil.getInstance().share(SHARE_MEDIA.QQ, context, shareModel, umImage, mShareListener);
        } else if (i == R.id.wechat) {
            if (shareModel == null) {
                if (onAdapterToastListener != null)
                    onAdapterToastListener.onFiled("分享失败");
                if (onShareListener != null) {
                    onShareListener.fild(SHARE_MEDIA.WEIXIN);
                }
                return;
            }
            if (shareModel.getBitmap() != null) {

                UMImage umImage = new UMImage(context, shareModel.getBitmap());
                ShareUtil.getInstance().share(SHARE_MEDIA.WEIXIN, context, shareModel, umImage, mShareListener);
                return;
            }

            UMImage umImage = new UMImage(context, shareModel.getImgurl());
            ShareUtil.getInstance().share(SHARE_MEDIA.WEIXIN, context, shareModel, umImage, mShareListener);

        } else if (i == R.id.zome) {
            if (shareModel == null) {
                if (onAdapterToastListener != null)
                    onAdapterToastListener.onFiled("分享失败");
                if (onShareListener != null) {
                    onShareListener.fild(SHARE_MEDIA.QZONE);
                }
                return;
            }
            if (shareModel.getBitmap() != null) {
                UMImage umImage = new UMImage(context, shareModel.getBitmap());
                ShareUtil.getInstance().share(SHARE_MEDIA.QZONE, context, shareModel, umImage, mShareListener);
                return;
            }
            UMImage umImage = new UMImage(context, shareModel.getImgurl());
            ShareUtil.getInstance().share(SHARE_MEDIA.QZONE, context, shareModel, umImage, mShareListener);
        } else if (i == R.id.circle) {
            if (shareModel == null) {
                if (onAdapterToastListener != null)
                    onAdapterToastListener.onFiled("分享失败");
                if (onShareListener != null) {
                    onShareListener.fild(SHARE_MEDIA.WEIXIN_CIRCLE);
                }
                return;
            }
            if (shareModel.getBitmap() != null) {
                UMImage umImage = new UMImage(context, shareModel.getBitmap());
                ShareUtil.getInstance().share(SHARE_MEDIA.WEIXIN_CIRCLE, context, shareModel, umImage, mShareListener);
                return;
            }
            UMImage umImage = new UMImage(context, shareModel.getImgurl());
            ShareUtil.getInstance().share(SHARE_MEDIA.WEIXIN_CIRCLE, context, shareModel, umImage, mShareListener);
        } else if (i == R.id.weibo) {
            if (shareModel == null) {
                if (onAdapterToastListener != null)
                    onAdapterToastListener.onFiled("分享失败");
                if (onShareListener != null) {
                    onShareListener.fild(SHARE_MEDIA.SINA);
                }
                return;
            }
            if (shareModel.getBitmap() != null) {
                UMImage umImage = new UMImage(context, shareModel.getBitmap());
                ShareUtil.getInstance().share(SHARE_MEDIA.SINA, context, shareModel, umImage, mShareListener);
                return;
            }
            UMImage umImage = new UMImage(context, shareModel.getImgurl());
            ShareUtil.getInstance().share(SHARE_MEDIA.SINA, context, shareModel, umImage, mShareListener);
        } else if (i == R.id.cancel) {
            if (isShowing()) {
                this.dismiss();
            }
        } else if (i == R.id.all_pop) {
            if (isShowing()) {
                this.dismiss();
            }
        }
    }

    private OnWriteClickListener onWriteClickListener;

    public void setOnWriteClickListener(OnWriteClickListener onWriteClickListener) {
        this.onWriteClickListener = onWriteClickListener;
    }

    public interface OnWriteClickListener {
        void write();

        void report(String id, String type);
    }


    public void setWriteVisiable(int visiable) {
        write.setVisibility(visiable);
    }

    public void setReportVisiable(int visiable) {
        report.setVisibility(visiable);
    }


    private ShareModel shareModel;

    public void setShareModel(ShareModel shareModel) {
        this.shareModel = shareModel;
    }

    private OnShareListener onShareListener;

    public void setOnShareListener(OnShareListener onShareListener) {
        this.onShareListener = onShareListener;
    }

    public interface OnShareListener {
        void success(SHARE_MEDIA share_media);

        void fild(SHARE_MEDIA share_media);

        void cancel(SHARE_MEDIA share_media);


    }

}
