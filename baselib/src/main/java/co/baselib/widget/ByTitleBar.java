package co.baselib.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.baselib.R;
import co.baselib.global.ByAppController;
import co.baselib.global.ByConfig;
import co.baselib.utils.ByImageLoaderUtils;
import co.baselib.utils.ByStrUtil;


/**
 * 标题栏
 *
 * @author wpt
 */
public class ByTitleBar extends ByBaseToolBar {

    private Context mContext;
    private TextView lc_center_menu, lc_right_menu, lc_right_second_menu;
    private ImageView lc_left_back;


    private RelativeLayout normal_title;
    private RelativeLayout lc_left_back_alls, lc_center_all, lc_right_all, ll_title_content;
    private OnClickListener backListenetForUser;


    private RelativeLayout costom_view;

    private TextView text_below;

    /***左边的小标题***/
    private TextView leftMenu;
    /****箭头后边的大标题***/
    private TextView leftTitle;

    public ByTitleBar(Context context) {
        this(context, null, 0);
    }

    public ByTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ByTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    protected void initCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.by_layout_title, null);

        leftMenu = (TextView) inflate.findViewById(R.id.leftMenu);
        leftTitle = (TextView) inflate.findViewById(R.id.leftTitle);

        ll_title_content = (RelativeLayout) inflate.findViewById(R.id.ll_title_content);


        lc_center_menu = (TextView) inflate.findViewById(R.id.title);
        lc_left_back = (ImageView) inflate.findViewById(R.id.lc_left_back);
        text_below = (TextView) inflate.findViewById(R.id.text_below);

        costom_view = (RelativeLayout) inflate.findViewById(R.id.costom_view);

        lc_left_back_alls = (RelativeLayout) inflate
                .findViewById(R.id.lc_left_back_all);
        lc_center_all = (RelativeLayout) inflate
                .findViewById(R.id.lc_center_all);


        lc_right_all = (RelativeLayout) inflate.findViewById(R.id.lc_right_all);
        lc_right_menu = (TextView) inflate.findViewById(R.id.lc_right_menu);
        lc_right_second_menu = (TextView) inflate.findViewById(R.id.lc_right_second_menu);

        normal_title = (RelativeLayout) inflate.findViewById(R.id.normal_title);



        lc_left_back_alls.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (backListenetForUser != null) {
                    backListenetForUser.onClick(v);
                } else {
                    ((Activity) mContext).onBackPressed();
                }
            }
        });

        /****
         * 设置返回按钮图片
         */
        ByImageLoaderUtils.getInstance().displayIMG(ByConfig.init(context).getBlackImg(), lc_left_back, context);


        setBackImageWH(ByConfig.init(context).getBackImgWidth(),ByConfig.init(context).getBackImgHeight());
//        ViewGroup.MarginLayoutParams  m= (ViewGroup.MarginLayoutParams)layoutParams;
//        m.leftMargin

//        lc_left_back.setLayoutParams(layoutParams);



        /***设置标题栏字体颜色***/
        lc_center_menu.setTextColor(ContextCompat.getColor(context, ByConfig.init(context).getCenterTextColor()));
        /***设置背景颜色***/
        ll_title_content.setBackgroundColor(ContextCompat.getColor(context, ByConfig.init(context).getTitleBarColor()));
        /***设置中间字体的大小***/
        lc_center_menu.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ByConfig.init(context).getCenterTextSize());
        /****设置右边标题的字体大小***/
        lc_right_menu.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ByConfig.init(context).getRightMenuTextSize());
        /****设置右边第二个标题字体大小****/
        lc_right_second_menu.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ByConfig.init(context).getRightMenuSecondMenuTextSize());
        /***设置默认状态栏的高度***/
        setTitleBarHeight(ByConfig.init(context).getTitleBarHeight());


        /****去除左右空白边距***/
        this.setPadding(0, 0, 0, 0);
        this.setContentInsetsRelative(0, 0);
        this.setContentInsetStartWithNavigation(0);

        addView(inflate);
    }


    /****
     *
     * 设置返回图片的宽高
     *
     * @param width
     * @param height
     */
    public void setBackImageWH(int width,int height){
        ViewGroup.LayoutParams layoutParams = lc_left_back.getLayoutParams();
        layoutParams.height=ByAppController.getInstance().dp2px(height);
        layoutParams.width =ByAppController.getInstance().dp2px(width);
        lc_left_back.setLayoutParams(layoutParams);
    }


    public void setBackImageMarginLeft(int left){

    }

    /***
     * 设置小菜单的内容
     *
     * @param text
     */
    public void setLeftMenuText(String text) {
        leftMenu.setText(text);
        leftMenu.setVisibility(View.VISIBLE);
        leftTitle.setVisibility(View.GONE);
        lc_left_back.setVisibility(View.GONE);
    }

    /***
     * 设置小菜单的可见性
     * @param visible
     */
    public void setLeftMenuVisible(int visible) {
        if (View.VISIBLE == visible) {
            leftTitle.setVisibility(View.GONE);
            lc_left_back.setVisibility(View.GONE);
        }
        leftMenu.setVisibility(visible);
    }

    /****
     * 设置小菜单字体大小
     * @param size
     */
    public void setLeftMenuTextSize(int size) {
        leftMenu.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    /***
     * 设置小菜单字体颜色
     * @param color
     */
    public void setLeftMenuTextColor(int color) {
        leftMenu.setTextColor(ContextCompat.getColor(mContext, color));
    }

    /***
     * 设置小菜单背景
     * @param bg
     */
    public void setLeftMenuBackground(int bg) {
        leftMenu.setBackgroundResource(bg);
    }

    /***
     * 设置小菜单宽高  单位：dp
     * @param h
     * @param w
     */
    public void setleftMenuHW(int h, int w) {
        ViewGroup.LayoutParams layoutParams = leftMenu.getLayoutParams();
        layoutParams.width = ByAppController.getInstance().dp2px(w);
        layoutParams.height = ByAppController.getInstance().dp2px(h);
        leftMenu.setLayoutParams(layoutParams);
    }


    /***
     * 设置箭头后边的标题 (该位置等同于中间的标题，所以当中间的标题显示时，此标题不显示，如果显示此标题，这不显示中间的标题和副标题)
     * @param text
     */
    public void setLeftTitleText(String text) {
        leftTitle.setText(text);
        leftTitle.setVisibility(View.VISIBLE);
        lc_left_back.setVisibility(View.VISIBLE);
        lc_center_menu.setVisibility(View.GONE);
        text_below.setVisibility(View.GONE);
    }

    /****
     *
     * 设置箭头后边的标题的字体颜色
     * @param color
     */
    public void setLeftTitleTextColor(int color) {
        leftTitle.setTextColor(ContextCompat.getColor(mContext, color));
    }

    /***
     *
     * 设置箭头后边的标题的字体大小
     *
     * @param size
     */
    public void setLeftTitleTextSize(int size) {
        leftTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }


    /***
     *
     * 设置状态栏的高度 单位：dp
     *
     * @param height
     */
    public void setTitleBarHeight(int height) {
        ViewGroup.LayoutParams layoutParams = normal_title.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        int px = ByAppController.getInstance().dp2px(height);
        layoutParams.height = px;
        normal_title.setLayoutParams(layoutParams);

    }


    /***
     * 设置中间的字体大小
     * @param size
     */
    public void setCenterTextSize(int size) {
        lc_center_menu.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    /***
     * 设置左边第一个字体大小
     * @param size
     */
    public void setRightMenuTextSize(int size) {
        lc_right_menu.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    /***
     * 设置左边第二个字体大小
     * @param size
     */
    public void setRightMenuSecondMenuTextSize(int size) {
        lc_right_second_menu.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }



    /**
     * 设置title lineview 是否展示 需求改变
     */
    @Deprecated
    public void isLineViewVisibility(boolean isVisibit) {

    }


    /**
     * 设置title背景颜色
     */
    public void setBackGb(int color) {
        ll_title_content.setBackgroundColor(color);
    }


    /**
     * 设置title Content背景颜色
     */
    public void setTitleContentBackGb(int color) {
        ll_title_content.setBackgroundColor(color);
        this.setBackgroundColor(color);
    }


    /**
     * 设置返回监听
     */
    public void setOnclickBackListener(OnClickListener l) {
        lc_left_back_alls.setOnClickListener(l);
    }


    /**
     * 设置左边的图片
     */
    public void setLeftImage(int draw) {
        ByImageLoaderUtils.getInstance().displayIMG(draw, lc_left_back, mContext);
        lc_left_back.setVisibility(VISIBLE);
    }


    /**
     * 设置左边的图片
     */
    public void setLeftImageBg(int draw) {
        lc_left_back.setBackgroundResource(draw);
    }


    /****
     * 设置左边图片的宽高
     * @param h
     * @param w
     */
    public void setLeftBackImageHW(int h, int w) {
        ViewGroup.LayoutParams layoutParams = lc_left_back.getLayoutParams();
        layoutParams.height = ByAppController.getInstance().dp2px(h);
        layoutParams.width = ByAppController.getInstance().dp2px(w);
        lc_left_back.setLayoutParams(layoutParams);
    }

    /**
     * 设置左边的是否显示
     */
    public void isLeftVisibility(boolean isVisibit) {
        if (isVisibit)
            lc_left_back_alls.setVisibility(View.VISIBLE);
        else
            lc_left_back_alls.setVisibility(View.GONE);
    }

    /**
     * 设置中间标题是否显示
     */
    public void isCenterVisibility(boolean isVisibit) {
        if (isVisibit)
            lc_center_all.setVisibility(View.VISIBLE);
        else
            lc_center_all.setVisibility(View.GONE);
    }

    /**
     * 设置中间标题内容
     */
    public void setCenterTitle(String msg) {
        lc_center_menu.setText(msg);
    }

    public String getCenterTitle() {
        return lc_center_menu.getText().toString();
    }

    /**
     * 设置中间标题内容字体颜色
     */
    public void setCenterTitleColor(int msg) {
        lc_center_menu.setTextColor(msg);
    }


    /**
     * 设置右边标题内容
     */
    public void setRightTitle(String msg) {
        lc_right_menu.setText(msg);
        lc_right_menu.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右边标题内容
     */
    public void setRightSecondTitle(String msg) {
        lc_right_second_menu.setText(msg);
        lc_right_second_menu.setVisibility(View.VISIBLE);
    }


    /**
     * 设置右边标题内容字体颜色
     */
    public void setRightTitle(int msg) {
        lc_right_menu.setTextColor(msg);
    }


    /**
     * 设置右边标题内容
     */
    public void setRightTitleListener(OnClickListener l) {
        lc_right_menu.setOnClickListener(l);
    }

    /**
     * 设置右边标题内容
     */
    public void setRightSecondTitleListener(OnClickListener l) {
        lc_right_second_menu.setOnClickListener(l);
    }

    /**
     * 设置右边标题是否隐藏
     */
    public void setRightTitleVisibility(int visibility) {
        lc_right_menu.setVisibility(visibility);
    }



    /**
     * 标题下边的小标题
     *
     * @param titlebelow
     */
    public void setCenterBelowTitle(String titlebelow) {
        ByStrUtil.setText(text_below, titlebelow);
        text_below.setVisibility(View.VISIBLE);
    }

    /**
     * 标题下边的小标题的字体颜色
     *
     * @param color
     */
    public void setCenterBelowTitleColor(String color) {
        text_below.setTextColor(Color.parseColor(color));
    }

    /**
     * 添加view
     *
     * @param childview
     */
    public void addCostomView(View childview) {
//        costom_view.addView(childview);
//        costom_view.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        this.removeAllViews();
        this.addView(childview);

        ViewGroup.LayoutParams layoutParams = childview.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        int px = ByAppController.getInstance().dp2px(ByConfig.init(mContext).getTitleBarHeight());
        layoutParams.height = px;
        childview.setLayoutParams(layoutParams);

    }


}
