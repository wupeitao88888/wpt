package co.baselib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import co.baselib.R;
import co.baselib.utils.PImageLoaderUtils;

/***
 *
 * 启动页
 *
 * @author wpt
 *
 */
public class StartPic extends FrameLayout {

    private ImageView start_pic_image;
    private Context context;
    private AnimatorSet set;
    private int Duration = 2000;

    public StartPic(Context context) {
        super(context);
        init(context);
    }

    public StartPic(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    RelativeLayout StartPicView;

    private void init(Context context) {
        // TODO Auto-generated method stub
        this.context = context;
        StartPicView = (RelativeLayout) LayoutInflater.from(context).inflate(
                R.layout.startpic_layout, null);
        start_pic_image = (ImageView) StartPicView
                .findViewById(R.id.start_pic_image);

        set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(StartPicView, "alpha", 0.5f, 1, 1)
        );

        addView(StartPicView);
    }

    /**
     * 添加自定义
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
        set.setDuration(Duration).start();
    }


    /***
     * 设置动画，必须放在start方法之前  默认动画是淡出
     * @param items
     */
    public void setAnimatorSet(Animator... items) {
        set.playTogether(items);
    }


    /***
     *
     * 设置背景图片
     *
     * @param id
     */
    @SuppressLint("NewApi")
    public void setStartPicImage(int id) {
        PImageLoaderUtils.getInstance().displayIMG(id, start_pic_image, context);
    }

    /***
     *
     * 设置动画监听
     *
     * @param animationListener
     */
    public void setAnimationListener(AnimatorListener animationListener) {
        set.addListener(animationListener);
    }


    /***
     *
     * 设置动画时长
     *
     * @param Duration
     */
    public void setDuration(int Duration) {
        this.Duration = Duration;
    }


    /***
     * 设置图片的显示方式
     * @param scaleType
     */
    public void setImageScaleType(ImageView.ScaleType scaleType) {
        start_pic_image.setScaleType(scaleType);
    }
}
