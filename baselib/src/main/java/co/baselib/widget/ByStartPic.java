package co.baselib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import co.baselib.R;
import co.baselib.utils.ByImageLoaderUtils;

/***
 *
 * 启动页
 *
 * @author wpt
 *
 */
public class ByStartPic extends FrameLayout {

    private ImageView start_pic_image;
    private Context context;
    private int duration = 2000;
    Animation alphaAnimation;

    public ByStartPic(Context context) {
        super(context);
        init(context);
    }

    public ByStartPic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    RelativeLayout StartPicView;

    private void init(Context context) {
        // TODO Auto-generated method stub
        this.context = context;
        StartPicView = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.by_startpic_layout, null);
        start_pic_image = (ImageView) StartPicView
                .findViewById(R.id.start_pic_image);
        alphaAnimation=new AlphaAnimation(0, 1);
        // 1. fromAlpha:动画开始时视图的透明度(取值范围: -1 ~ 1)
        // 2. toAlpha:动画结束时视图的透明度(取值范围: -1 ~ 1)
        alphaAnimation.setDuration(duration);

        start_pic_image.startAnimation(alphaAnimation);



        addView(StartPicView);
    }

    /**
     * 添加自定义
     *
     * @param view
     */
    public void addCustomView(View view) {
        StartPicView.removeAllViews();
        StartPicView.addView(view);
    }

    /***
     * 启动动画
     */
    public void start() {
        alphaAnimation.start();
    }


    /***
     *
     * 设置背景图片
     *
     * @param id
     */
    @SuppressLint("NewApi")
    public void setStartPicImage(int id) {
        ByImageLoaderUtils.getInstance().displayIMG(id, start_pic_image, context);
    }


    /***
     *
     * 设置动画时长
     *
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }


    /***
     * 设置图片的显示方式
     * @param scaleType
     */
    public void setImageScaleType(ImageView.ScaleType scaleType) {
        start_pic_image.setScaleType(scaleType);
    }


    /****
     * 监听动画加载结束
     * @param animationListener
     */
    public void  setAnimationListener(Animation.AnimationListener animationListener){
        alphaAnimation.setAnimationListener(animationListener);
    }



}
