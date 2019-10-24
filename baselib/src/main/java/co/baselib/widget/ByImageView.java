package co.baselib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import co.baselib.R;
import co.baselib.global.ByAppController;


/***
 *
 * 如果不生效 需要在AndroidManifest.xml把        android:hardwareAccelerated="false"去掉
 * imageview 圆角
 */
public class ByImageView extends AppCompatImageView {

    private Context context;

    private int cornerRadius; // 统一设置圆角半径，优先级高于单独设置每个角的半径
    private int cornerTopLeftRadius; // 左上角圆角半径
    private int cornerTopRightRadius; // 右上角圆角半径
    private int cornerBottomLeftRadius; // 左下角圆角半径
    private int cornerBottomRightRadius; // 右下角圆角半径


    public ByImageView(Context context) {
        this(context, null);
    }

    public ByImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    /**
     * 利用clip剪切的四个角半径，八个数据分别代表左上角（x轴半径，y轴半径），右上角（**），右下角（**），左下角（**）
     */
    private float[] rids = new float[8];

    private Path mPath;
    private RectF mRectF;
    private PaintFlagsDrawFilter paintFlagsDrawFilter;

    private boolean isCircle = false;

    public ByImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ByImageView, 0, 0);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.ByImageView_is_circle) {
                isCircle = ta.getBoolean(attr, isCircle);
            } else if (attr == R.styleable.ByImageView_corner_radius) {
                cornerRadius = ta.getDimensionPixelSize(attr, cornerRadius);
            } else if (attr == R.styleable.ByImageView_radius_top_left) {
                cornerTopLeftRadius = ta.getDimensionPixelSize(attr, cornerTopLeftRadius);
            } else if (attr == R.styleable.ByImageView_radius_top_right) {
                cornerTopRightRadius = ta.getDimensionPixelSize(attr, cornerTopRightRadius);
            } else if (attr == R.styleable.ByImageView_radius_btm_left) {
                cornerBottomLeftRadius = ta.getDimensionPixelSize(attr, cornerBottomLeftRadius);
            } else if (attr == R.styleable.ByImageView_radius_btm_right) {
                cornerBottomRightRadius = ta.getDimensionPixelSize(attr, cornerBottomRightRadius);
            }
        }
        ta.recycle();
        /***
         * 判断是否是圆角
         */
        if (isCircle) {
            rids[0] = 100;
            rids[1] = 100;
            rids[2] = 100;
            rids[3] = 100;
            rids[4] = 100;
            rids[5] = 100;
            rids[6] = 100;
            rids[7] = 100;
        } else if (cornerRadius>0) {
            rids[0] = cornerRadius;
            rids[1] = cornerRadius;
            rids[2] = cornerRadius;
            rids[3] = cornerRadius;
            rids[4] = cornerRadius;
            rids[5] = cornerRadius;
            rids[6] = cornerRadius;
            rids[7] = cornerRadius;
        } else {
            rids[0] = cornerTopLeftRadius;
            rids[1] = cornerTopLeftRadius;
            rids[2] = cornerTopRightRadius;
            rids[3] = cornerTopRightRadius;
            rids[4] = cornerBottomRightRadius;
            rids[5] = cornerBottomRightRadius;
            rids[6] = cornerBottomLeftRadius;
            rids[7] = cornerBottomLeftRadius;
        }


        mPath = new Path();
        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        mPath.addRoundRect(mRectF, rids, Path.Direction.CW);
        canvas.setDrawFilter(paintFlagsDrawFilter);
        canvas.save();
        canvas.clipPath(mPath);
        super.onDraw(canvas);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF = new RectF(0, 0, w, h);
    }


    /***
     * 是否显示为圆形（默认为矩形）
     * @param isCircle
     */
    public void isCircle(boolean isCircle) {
        this.isCircle = isCircle;
        rids[0] = 100;
        rids[1] = 100;
        rids[2] = 100;
        rids[3] = 100;
        rids[4] = 100;
        rids[5] = 100;
        rids[6] = 100;
        rids[7] = 100;
        invalidate();
    }


    /***
     * 统一设置四个角的圆角半径
     * @param cornerRadius
     */
    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = ByAppController.getInstance().dp2px(cornerRadius);
        rids[0] = cornerRadius;
        rids[1] = cornerRadius;
        rids[2] = cornerRadius;
        rids[3] = cornerRadius;
        rids[4] = cornerRadius;
        rids[5] = cornerRadius;
        rids[6] = cornerRadius;
        rids[7] = cornerRadius;
        invalidate();
    }

    /***
     * 左上角圆角半径
     * @param cornerTopLeftRadius
     */
    public void setCornerTopLeftRadius(int cornerTopLeftRadius) {
        this.cornerTopLeftRadius = ByAppController.getInstance().dp2px(cornerTopLeftRadius);
        rids[0] = cornerTopLeftRadius;
        rids[1] = cornerTopLeftRadius;
        rids[2] = 0;
        rids[3] = 0;
        rids[4] = 0;
        rids[5] = 0;
        rids[6] = 0;
        rids[7] = 0;
        invalidate();
    }

    /***
     * 右上角圆角半径
     * @param cornerTopRightRadius
     */
    public void setCornerTopRightRadius(int cornerTopRightRadius) {
        this.cornerTopRightRadius = ByAppController.getInstance().dp2px(cornerTopRightRadius);
        rids[0] = 0;
        rids[1] = 0;
        rids[2] = cornerTopRightRadius;
        rids[3] = cornerTopRightRadius;
        rids[4] = 0;
        rids[5] = 0;
        rids[6] = 0;
        rids[7] = 0;
        invalidate();
    }

    /***
     * 左下角圆角半径
     * @param cornerBottomLeftRadius
     */
    public void setCornerBottomLeftRadius(int cornerBottomLeftRadius) {
        this.cornerBottomLeftRadius = ByAppController.getInstance().dp2px(cornerBottomLeftRadius);
        rids[0] = 0;
        rids[1] = 0;
        rids[2] = 0;
        rids[3] = 0;
        rids[4] = 0;
        rids[5] = 0;
        rids[6] = cornerBottomLeftRadius;
        rids[7] = cornerBottomLeftRadius;
        invalidate();
    }

    /***
     * 右下角圆角半径
     * @param cornerBottomRightRadius
     */
    public void setCornerBottomRightRadius(int cornerBottomRightRadius) {
        this.cornerBottomRightRadius = ByAppController.getInstance().dp2px(cornerBottomRightRadius);
        rids[0] = 0;
        rids[1] = 0;
        rids[2] = 0;
        rids[3] = 0;
        rids[4] = cornerBottomRightRadius;
        rids[5] = cornerBottomRightRadius;
        rids[6] = 0;
        rids[7] = 0;
        invalidate();
    }

}
