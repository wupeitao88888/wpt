package com.iloomo.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iloomo.base.ActivitySupport;
import com.iloomo.global.IloomoConfig;
import com.iloomo.paysdk.R;
import com.iloomo.photoview.PPhotoView;
import com.iloomo.rxbus.BusConfig;
import com.iloomo.rxbus.RxBus;
import com.iloomo.update.utils.xTools;
import com.iloomo.utils.BigDecimalUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.PFileUtils;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.widget.spinnerloading.SpinnerLoading;
import com.yanzhenjie.nohttp.Headers;

import org.xutils.common.Callback;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by wpt on 2015/8/19.
 */
public class ShowImageActivity extends ActivitySupport {

    private ViewPager mPager;
    private ArrayList<String> photoStrings;
    private int index;
    private Button commit;
    private TextView content;
    private static final int paintMode = 5;
    private static final int itemCount = 8;
    private static final int circleRadius = 8;
    private TextView over;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pactivity_view_pager);
        setRemoveTitle();
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));

        commit = (Button) findViewById(R.id.commit);
        content = (TextView) findViewById(R.id.content);
        over = (TextView) findViewById(R.id.over);

        if (getIntent().getBooleanExtra("isOver", false) || getIntent().getBooleanExtra("isdelete", false)) {
            commit.setVisibility(View.GONE);
            over.setVisibility(View.VISIBLE);
            if (getIntent().getBooleanExtra("isdelete", false)) {
                over.setText("删除");
            }
        } else {
            commit.setVisibility(View.VISIBLE);
            over.setVisibility(View.GONE);
        }

        photoStrings = getIntent().getStringArrayListExtra("photos");
        index = getIntent().getIntExtra("index", 0);

        content.setText((index + 1) + "/" + photoStrings.size() + "");
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return photoStrings.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                View inflate = LayoutInflater.from(context).inflate(R.layout.layout_showimg, null);
                PPhotoView view = (PPhotoView) inflate.findViewById(R.id.photoView);
                view.enable();
                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                RelativeLayout progress = (RelativeLayout) inflate.findViewById(R.id.progress);
                SpinnerLoading loading = (SpinnerLoading) inflate.findViewById(R.id.loading);
                TextView content = (TextView) inflate.findViewById(R.id.content);
                loading.setPaintMode(paintMode);
                loading.setItemCount(itemCount);
                loading.setCircleRadius(circleRadius);

                if (!TextUtils.isEmpty(photoStrings.get(position))) {
                    if (photoStrings.get(position).indexOf("https://") > -1 || photoStrings.get(position).indexOf("http://") > -1) {
                        if (photoStrings.get(position) != null && photoStrings.get(position).lastIndexOf("/") > -1) {
                            String fulename = photoStrings.get(position).substring(photoStrings.get(position).lastIndexOf("/"), photoStrings.get(position).length());
                            File file = new File(PFileUtils.getSDPathFILE() + fulename);
                            if (file.exists()) {
                                L.e("存在");
                                L.e("本地地址：" + file.getAbsolutePath());
                                PImageLoaderUtils.getInstance().displayIMGONBG(file.getAbsolutePath(), view, context);
                            } else {
                                L.e("不存在");
                                downLoadImg(progress, content, position, view);
                            }
                        } else {
                            L.e("不存在");
                            downLoadImg(progress, content, position, view);
                        }
                    } else {
                        PImageLoaderUtils.getInstance().displayIMGONBG(photoStrings.get(position), view, context);
                    }
                } else {
                    PImageLoaderUtils.getInstance().displayIMGONBG(photoStrings.get(position), view, context);
                }
                container.addView(inflate);
                return inflate;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View object1 = (View) object;
                unbindDrawables(object1);
                container.removeView(object1);
                container = null;
                object1 = null;
                System.gc();
            }
        });
        mPager.setCurrentItem(index);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getBooleanExtra("isdelete", false)) {
                    Message message = new Message();
                    message.what = BusConfig.DELETE_IMG;
                    RxBus.getDefault().post(message);
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("photos", photoStrings);
                    setResult(IloomoConfig.PHOTOS, intent);
                    finish();
                }
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                content.setText((position + 1) + "/" + photoStrings.size() + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void downLoadImg(RelativeLayout progress, TextView content, int position, PPhotoView view) {
        L.e("原地址：" + photoStrings.get(position));
        String media_url = photoStrings.get(position);
        if (media_url != null && media_url.lastIndexOf("/") > -1) {

            String fulename = media_url.substring(media_url.lastIndexOf("/"), media_url.length());
            File file = new File(PFileUtils.getSDPathFILE() + fulename);
            L.e("保存地址：" + file.getAbsolutePath());
            IloomoConfig.init(context).downLoadFile(media_url, file.getAbsolutePath(), new Callback.ProgressCallback<File>() {
                @Override
                public void onWaiting() {

                }

                @Override
                public void onStarted() {
                    progress.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    int mul = (int) BigDecimalUtil.mul(BigDecimalUtil.div(current, total), 100);
                    L.e("下载进度" + mul);
                    content.setText(mul + "%");
                }

                @Override
                public void onSuccess(File result) {
                    PImageLoaderUtils.getInstance().displayIMGONBG(result.getAbsolutePath(), view, context);
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    L.e("图片加载失败:" + ex.getMessage());
                    PImageLoaderUtils.getInstance().displayIMGONBG("", view, context);
                    showToastFiled("加载失败");
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    progress.setVisibility(View.GONE);
                }
            });
        }
    }


    public void openChoosePhoto(View view) {


    }

    private void unbindDrawables(View view) {
        if (view != null) {
            return;
        }
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }

        if (view instanceof ImageView) {
            releaseImageView((ImageView) view);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    public void releaseImageView(ImageView imageView) {
        Drawable d = imageView.getDrawable();
        if (d != null)
            d.setCallback(null);
        imageView.setImageDrawable(null);
        imageView.setBackgroundDrawable(null);
    }


    public void onBack(View view) {
        onBackPressed();
    }


}
