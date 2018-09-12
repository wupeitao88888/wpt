package co.baselib.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.baselib.R;
import co.baselib.utils.PImageLoaderUtils;
import co.baselib.utils.StrUtil;
import co.baselib.widget.badgeview.BGABadgeRelativeLayout;


/**
 * 标题栏
 *
 * @author wpt
 */
public class TitleBar extends FrameLayout {

    private Context mContext;
    private TextView lc_center_menu, lc_right_menu, lc_right_second_menu;
    private ImageView lc_left_back, lc_right_image;
    private ImageView frist_menu;
    private ImageView second_menu;
    private ImageView lc_left_back_other;

    private ImageView mIvSearch;
    private EditText mEtSearchBar;
    private ClearEditText search_bar_edit;
    private BGABadgeRelativeLayout second_menu_re;
    private RelativeLayout mRlSearchTitleBar;
    private ImageView cancel;
    private RelativeLayout left_menu;
    private TextView left_menu_title;
    private RelativeLayout sreach_re;
    private RelativeLayout normal_title;
    private RelativeLayout lc_left_back_all, lc_center_all, lc_right_all, ll_title_content;
    private OnClickListener backListenetForUser;

    private RelativeLayout fileName;
    private RelativeLayout lc_right_image_re;


    private TextView text_below;

    private OnClickListener backListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (backListenetForUser != null) {
                backListenetForUser.onClick(v);
            } else {
                ((Activity) mContext).onBackPressed();
            }
        }
    };

    public TitleBar(Context context) {
        super(context);
        init(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(final Context context) {
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.plc_layout_title, null);
        ll_title_content = (RelativeLayout) inflate.findViewById(R.id.ll_title_content);
        lc_center_menu = (TextView) inflate.findViewById(R.id.title);
        lc_left_back = (ImageView) inflate.findViewById(R.id.lc_left_back);
        lc_left_back_other = (ImageView) inflate.findViewById(R.id.lc_left_back_other);
        text_below = (TextView) inflate.findViewById(R.id.text_below);

        lc_left_back_all = (RelativeLayout) inflate
                .findViewById(R.id.lc_left_back_all);
        lc_center_all = (RelativeLayout) inflate
                .findViewById(R.id.lc_center_all);
        search_bar_edit = (ClearEditText) inflate
                .findViewById(R.id.search_bar_edit);
        search_bar_edit.setMaxLines(1);
        search_bar_edit.setMaxLength(50);
        search_bar_edit.setInputType(InputType.TYPE_CLASS_TEXT);
        search_bar_edit.setHinit("搜帖子 话题 如新闻发布会");
        search_bar_edit.setLookVisibility(View.GONE);
        search_bar_edit.setInputIconVisibility(View.GONE);
        search_bar_edit.setOnDeleteListenr(new ClearEditText.OnDeleteListenr() {
            @Override
            public void onDelete() {
                if (onDeleteListener != null)
                    onDeleteListener.onDelete();
            }
        });

        lc_right_all = (RelativeLayout) inflate.findViewById(R.id.lc_right_all);
        lc_right_menu = (TextView) inflate.findViewById(R.id.lc_right_menu);
        lc_right_second_menu = (TextView) inflate.findViewById(R.id.lc_right_second_menu);
        lc_right_image = (ImageView) inflate.findViewById(R.id.lc_right_image);
        frist_menu = (ImageView) inflate.findViewById(R.id.frist_menu);
        second_menu = (ImageView) inflate.findViewById(R.id.second_menu);
        second_menu_re = (BGABadgeRelativeLayout) inflate.findViewById(R.id.second_menu_re);
        sreach_re = (RelativeLayout) inflate.findViewById(R.id.sreach_re);
        normal_title = (RelativeLayout) inflate.findViewById(R.id.normal_title);
        left_menu = (RelativeLayout) inflate.findViewById(R.id.left_menu);
        mRlSearchTitleBar = (RelativeLayout) inflate.findViewById(R.id.search_title_bar);
        mEtSearchBar = (EditText) inflate.findViewById(R.id.search_bar);
        mIvSearch = (ImageView) inflate.findViewById(R.id.search);
        left_menu_title = (TextView) inflate.findViewById(R.id.left_menu_title);
        cancel = (ImageView) inflate.findViewById(R.id.cancel);
        TextView quxiao = (TextView) inflate.findViewById(R.id.quxiao);

        fileName = (RelativeLayout) inflate.findViewById(R.id.fileName);
        lc_right_image_re = (RelativeLayout) inflate.findViewById(R.id.lc_right_image_re);
        lc_left_back_all.setOnClickListener(backListener);

        PImageLoaderUtils.getInstance().displayIMGLocal(R.drawable.search, cancel, mContext);

        PImageLoaderUtils.getInstance().displayIMGLocal(R.drawable.cloose_sreach, cancel, context);
        cancel.setOnClickListener(backListener);
        quxiao.setOnClickListener(backListener);
        search_bar_edit.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
//                    cancel.setText("搜索");
                    PImageLoaderUtils.getInstance().displayIMGLocal(R.drawable.search, cancel, context);
                    cancel.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onSreachListener != null)
                                onSreachListener.onSreach(search_bar_edit.getText().toString());
                        }
                    });
                } else {
//                    cancel.setText("取消");
                    cancel.setOnClickListener(backListener);
                    PImageLoaderUtils.getInstance().displayIMGLocal(R.drawable.cloose_sreach, cancel, context);
                    if (onClearListener != null) {
                        onClearListener.onClear();
                    }
                }
                if (onAutoSreachListener != null) {
                    onAutoSreachListener.onAuto(search_bar_edit.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addView(inflate);
    }

///////////////////////////////////////////////////////////////////////////////搜索栏  start


    public void setTitleBarStatus(float height) {
        ll_title_content.setPadding(0, (int) height, 0, 0);
    }

    /**
     * 设置搜索框是否隐藏
     */
    public void isSearchTitleBarVisibility(boolean isVisibit) {
        if (isVisibit) {
            mRlSearchTitleBar.setVisibility(View.VISIBLE);
        } else
            mRlSearchTitleBar.setVisibility(View.GONE);
    }

    /**
     * 设置搜索框的背景
     */
    public void searchTitleBarBackground(int draw) {
        mRlSearchTitleBar.setBackgroundResource(draw);
    }

    /**
     * 设置搜索Hint
     */
    public void mEtSearchBarHint(CharSequence hint) {
        mEtSearchBar.setHint(hint);
    }

    /***
     * 给搜索框添加点击事件
     *
     * @param onClickListener
     */
    public void setOnSearchBar(OnClickListener onClickListener) {
        mEtSearchBar.setOnClickListener(onClickListener);
    }

    /**
     * 设置搜索按钮点击事件
     */
    public void mIvSearchClick(OnClickListener onClickListener) {
        mIvSearch.setOnClickListener(onClickListener);
    }


    /**
     * 设置搜索按钮背景图片
     */
    public void mIvSearchBackGround(int draw) {
        mIvSearch.setBackgroundResource(draw);
    }

    /**
     * 获取搜索按钮背景图片
     */
    public ImageView getIvsecondMenu() {

        return second_menu;
    }

    public BGABadgeRelativeLayout getSecond_menu_re() {
        return second_menu_re;
    }


///////////////////////////////////////////////////////////////////////////////搜索栏  end


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
    }


    /**
     * 设置返回监听
     */
    public void setOnclickBackListener(OnClickListener l) {
        lc_left_back_all.setOnClickListener(l);
    }


    /**
     * 设置左边的图片
     */
    public void setLeftImage(int draw) {
        lc_left_back.setVisibility(INVISIBLE);
        lc_left_back_other.setBackgroundResource(draw);
    }


    /**
     * 设置左边的图片
     */
    public void setLeftImageBg(int draw) {
        lc_left_back.setBackgroundResource(draw);
    }


//    /**
//     * 旋转-90度
//     */
//    public void setAnimetion(final int draw) {
//
//
//
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(
////                ObjectAnimator.ofFloat(lc_left_back, "rotationX", 0, 360)
////                ObjectAnimator.ofFloat(lc_left_back, "rotationY", 0, 180)
//                ObjectAnimator.ofFloat(lc_left_back, "rotation", 0, -90)
////                ObjectAnimator.ofFloat(myView, "translationX", 0, 90),
////                ObjectAnimator.ofFloat(myView, "translationY", 0, 90),
////                ObjectAnimator.ofFloat(myView, "scaleX", 1, 1.5f),
////                ObjectAnimator.ofFloat(myView, "scaleY", 1, 0.5f),
////                ObjectAnimator.ofFloat(myView, "alpha", 1, 0.25f, 1)
//        );
//        set.setDuration(500).start();
//    }
//
//    /**
//     * 复位
//     */
//    public void setAnmin() {
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(
////                ObjectAnimator.ofFloat(lc_left_back, "rotationX", 0, 360)
////                ObjectAnimator.ofFloat(lc_left_back, "rotationY", 0, 180)
//                ObjectAnimator.ofFloat(lc_left_back, "rotation", -90, 0)
////                ObjectAnimator.ofFloat(myView, "translationX", 0, 90),
////                ObjectAnimator.ofFloat(myView, "translationY", 0, 90),
////                ObjectAnimator.ofFloat(myView, "scaleX", 1, 1.5f),
////                ObjectAnimator.ofFloat(myView, "scaleY", 1, 0.5f),
////                ObjectAnimator.ofFloat(myView, "alpha", 1, 0.25f, 1)
//        );
//        set.setDuration(500).start();
//
//    }


    /**
     * 设置左边的是否显示
     */
    public void isLeftVisibility(boolean isVisibit) {
        if (isVisibit)
            lc_left_back_all.setVisibility(View.VISIBLE);
        else
            lc_left_back_all.setVisibility(View.GONE);
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
     * 设置右边图片内容
     */
    public void setRightTitleRes(int msg) {
        lc_right_image.setImageResource(msg);
        lc_right_image.setVisibility(View.VISIBLE);
        lc_right_image_re.setVisibility(View.VISIBLE);
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
     * 设置右边字体监听
     */
    public void setRightTitleImageListener(OnClickListener l) {
        lc_right_image.setOnClickListener(l);
    }

    /**
     * 设置右边第一个图片内容
     */
    public void setRightFristMenuimg(int draw) {
        frist_menu.setBackgroundResource(draw);
    }

    /***
     * 设置右边第一个图片监听
     *
     * @param l
     */
    public void setRightFristMenuimgListener(OnClickListener l) {
        frist_menu.setOnClickListener(l);
    }

    /***
     * 设置右边第一个图片是否显示
     *
     * @param visbility
     */
    public void setFristMenuimgIsVisbility(int visbility) {
        frist_menu.setVisibility(visbility);
        fileName.setVisibility(visbility);
    }

    /**
     * 设置右边第二个图片内容
     */
    public void setRightSecondMenuimg(int draw) {
        second_menu.setBackgroundResource(draw);
    }

    /***
     * 设置右边第二个图片监听
     *
     * @param l
     */
    public void setRightSecondMenuimgListener(OnClickListener l) {
        second_menu.setOnClickListener(l);
    }

    /***
     * 设置右边第一个图片是否显示
     *
     * @param visbility
     */
    public void setSecondMenuimgIsVisbility(int visbility) {
        second_menu.setVisibility(visbility);
        second_menu_re.setVisibility(visbility);
    }

    /***
     * 设置搜索框是否显示
     *
     * @param visbility
     */
    public void setSreachVisbility(int visbility) {
        if (View.GONE == visbility) {
            sreach_re.setVisibility(View.GONE);
            normal_title.setVisibility(View.VISIBLE);
        } else if (View.INVISIBLE == visbility) {
            sreach_re.setVisibility(View.GONE);
            normal_title.setVisibility(View.VISIBLE);
        } else {
            sreach_re.setVisibility(View.VISIBLE);
            normal_title.setVisibility(View.GONE);
        }

    }

    /***
     * 设置搜索菜单点击事件
     *
     * @param onClickListener
     */
    public void setOnLeftMenuClickListener(OnClickListener onClickListener) {
        left_menu.setOnClickListener(onClickListener);
    }

    /***
     * 设置搜索框右边的字体
     *
     * @param str
     */
    public void setLeftInSreachText(String str) {
        left_menu_title.setText(str);
    }

    /***
     * 设置文本
     *
     * @param txt
     */
    public void setEditText(String txt) {
        search_bar_edit.setText(txt);
    }

    private OnSreachListener onSreachListener;

    public void setOnSreachListener(OnSreachListener onSreachListener) {
        this.onSreachListener = onSreachListener;
    }

    /***
     * 搜索按钮添加
     */
    public interface OnSreachListener {
        void onSreach(String content);
    }

    private OnClearListener onClearListener;

    public void setOnClearListener(OnClearListener onClearListener) {
        this.onClearListener = onClearListener;
    }

    /***
     * 监听edit内容清空
     */
    public interface OnClearListener {
        void onClear();
    }

    /***
     * 获取文本
     */
    public String getEditText() {
        return search_bar_edit.getText().toString();
    }

    private OnDeleteListener onDeleteListener;

    /**
     * 清空监听
     *
     * @param onDeleteListener
     */
    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface OnDeleteListener {
        void onDelete();
    }

    private OnAutoSreachListener onAutoSreachListener;

    /***
     * 自动搜索监听
     * @param onAutoSreachListener
     */
    public void setOnAutoSreachListener(OnAutoSreachListener onAutoSreachListener)

    {
        this.onAutoSreachListener = onAutoSreachListener;
    }

    public interface OnAutoSreachListener {
        void onAuto(String str);
    }

    /**
     * 标题下边的小标题
     *
     * @param titlebelow
     */
    public void setCenterBelowTitle(String titlebelow) {
        StrUtil.setText(text_below, titlebelow);
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

}
