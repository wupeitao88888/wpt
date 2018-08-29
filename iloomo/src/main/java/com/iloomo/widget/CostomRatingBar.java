package com.iloomo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iloomo.model.CostomRatingClickListener;

import com.iloomo.paysdk.R;
import com.iloomo.utils.AppUtil;


/**
 * 我的大星星and小星星
 *
 * @author wpt
 */
public class CostomRatingBar extends FrameLayout {

    private Context mContext;
    private ImageView star1, star2, star3, star4, star5;
    private LinearLayout startall;
    private View inflate;

    public CostomRatingBar(Context context) {
        super(context);
        init(context);
    }

    public CostomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;

        inflate = View.inflate(context, R.layout.layout_lcratingbar, null);
        startall = (LinearLayout) inflate.findViewById(R.id.startall);
        star1 = (ImageView) inflate.findViewById(R.id.star1);
        star2 = (ImageView) inflate.findViewById(R.id.star2);
        star3 = (ImageView) inflate.findViewById(R.id.star3);
        star4 = (ImageView) inflate.findViewById(R.id.star4);
        star5 = (ImageView) inflate.findViewById(R.id.star5);
        for (int i = 0; i < startall.getChildCount(); i++) {
            startall.getChildAt(i).setOnClickListener(new MyClick(i));
        }
        addView(inflate);
    }

    class MyClick implements OnClickListener {

        private int index;

        public MyClick(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            setMark(index + 1);
            if (myOnCliclListener != null)
                myOnCliclListener.onClick(index + 1);
        }
    }

    private CostomRatingClickListener myOnCliclListener;

    public void setCostomRatingClickListener(CostomRatingClickListener myOnCliclListener) {
        this.myOnCliclListener = myOnCliclListener;
    }

    public void setRatingNum(int number) {
        switch (number) {
            case 1:
                star1.setVisibility(VISIBLE);
                star2.setVisibility(INVISIBLE);
                star3.setVisibility(INVISIBLE);
                star4.setVisibility(INVISIBLE);
                star5.setVisibility(INVISIBLE);
                break;
            case 2:
                star1.setVisibility(VISIBLE);
                star2.setVisibility(VISIBLE);
                star3.setVisibility(INVISIBLE);
                star4.setVisibility(INVISIBLE);
                star5.setVisibility(INVISIBLE);
                break;
            case 3:
                star1.setVisibility(VISIBLE);
                star2.setVisibility(VISIBLE);
                star3.setVisibility(VISIBLE);
                star4.setVisibility(INVISIBLE);
                star5.setVisibility(INVISIBLE);
                break;
            case 4:
                star1.setVisibility(VISIBLE);
                star2.setVisibility(VISIBLE);
                star3.setVisibility(VISIBLE);
                star4.setVisibility(VISIBLE);
                star5.setVisibility(INVISIBLE);
                break;
            default:
                star1.setVisibility(VISIBLE);
                star2.setVisibility(VISIBLE);
                star3.setVisibility(VISIBLE);
                star4.setVisibility(VISIBLE);
                star5.setVisibility(VISIBLE);
                break;
        }
    }

    /***
     * 设置分数
     */
    public void setMark(float mark) {
        if (mark <= 0.5f) {
            star1.setBackgroundResource(R.drawable.start_normal);
            star2.setBackgroundResource(R.drawable.start_normal);
            star3.setBackgroundResource(R.drawable.start_normal);
            star4.setBackgroundResource(R.drawable.start_normal);
            star5.setBackgroundResource(R.drawable.start_normal);
        } else if (mark >= 0.5f && mark < 1.0f) {
            star1.setBackgroundResource(R.drawable.start_ban);
            star2.setBackgroundResource(R.drawable.start_normal);
            star3.setBackgroundResource(R.drawable.start_normal);
            star4.setBackgroundResource(R.drawable.start_normal);
            star5.setBackgroundResource(R.drawable.start_normal);
        } else if (mark >= 1.0f && mark < 1.5f) {
            star1.setBackgroundResource(R.drawable.start_select);
            star2.setBackgroundResource(R.drawable.start_normal);
            star3.setBackgroundResource(R.drawable.start_normal);
            star4.setBackgroundResource(R.drawable.start_normal);
            star5.setBackgroundResource(R.drawable.start_normal);
        } else if (mark >= 1.5f && mark < 2.0f) {
            star1.setBackgroundResource(R.drawable.start_select);
            star2.setBackgroundResource(R.drawable.start_ban);
            star3.setBackgroundResource(R.drawable.start_normal);
            star4.setBackgroundResource(R.drawable.start_normal);
            star5.setBackgroundResource(R.drawable.start_normal);
        } else if (mark >= 2.0f && mark < 2.5f) {
            star1.setBackgroundResource(R.drawable.start_select);
            star2.setBackgroundResource(R.drawable.start_select);
            star3.setBackgroundResource(R.drawable.start_normal);
            star4.setBackgroundResource(R.drawable.start_normal);
            star5.setBackgroundResource(R.drawable.start_normal);
        } else if (mark >= 2.5f && mark < 3.0f) {
            star1.setBackgroundResource(R.drawable.start_select);
            star2.setBackgroundResource(R.drawable.start_select);
            star3.setBackgroundResource(R.drawable.start_ban);
            star4.setBackgroundResource(R.drawable.start_normal);
            star5.setBackgroundResource(R.drawable.start_normal);
        } else if (mark >= 3.0f && mark < 3.5f) {
            star1.setBackgroundResource(R.drawable.start_select);
            star2.setBackgroundResource(R.drawable.start_select);
            star3.setBackgroundResource(R.drawable.start_select);
            star4.setBackgroundResource(R.drawable.start_normal);
            star5.setBackgroundResource(R.drawable.start_normal);
        } else if (mark >= 3.5f && mark < 4.0f) {
            star1.setBackgroundResource(R.drawable.start_select);
            star2.setBackgroundResource(R.drawable.start_select);
            star3.setBackgroundResource(R.drawable.start_select);
            star4.setBackgroundResource(R.drawable.start_ban);
            star5.setBackgroundResource(R.drawable.start_normal);
        } else if (mark >= 4.0f && mark < 4.5f) {
            star1.setBackgroundResource(R.drawable.start_select);
            star2.setBackgroundResource(R.drawable.start_select);
            star3.setBackgroundResource(R.drawable.start_select);
            star4.setBackgroundResource(R.drawable.start_select);
            star5.setBackgroundResource(R.drawable.start_normal);
        } else if (mark >= 4.5f && mark < 5.0f) {
            star1.setBackgroundResource(R.drawable.start_select);
            star2.setBackgroundResource(R.drawable.start_select);
            star3.setBackgroundResource(R.drawable.start_select);
            star4.setBackgroundResource(R.drawable.start_select);
            star5.setBackgroundResource(R.drawable.start_ban);
        } else if (mark >= 5.0f) {
            star1.setBackgroundResource(R.drawable.start_select);
            star2.setBackgroundResource(R.drawable.start_select);
            star3.setBackgroundResource(R.drawable.start_select);
            star4.setBackgroundResource(R.drawable.start_select);
            star5.setBackgroundResource(R.drawable.start_select);
        }
    }

    public void setSmail(boolean isSmail) {
        if (isSmail) {
            for (int i = 0; i < startall.getChildCount(); i++) {
                LinearLayout.LayoutParams linearParams1 = (LinearLayout.LayoutParams) startall.getChildAt(i)
                        .getLayoutParams();
                linearParams1.height = (int) AppUtil.dip2px(mContext, 11.0f);
                linearParams1.width = (int) AppUtil.dip2px(mContext, 10.0f);
                startall.getChildAt(i).setLayoutParams(linearParams1);
            }
        } else {
            for (int i = 0; i < startall.getChildCount(); i++) {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) startall.getChildAt(i)
                        .getLayoutParams();
                linearParams.height = (int) AppUtil.dip2px(mContext, 19.0f);
                linearParams.width = (int) AppUtil.dip2px(mContext, 18.0f);
                startall.getChildAt(i).setLayoutParams(linearParams);
            }

        }

    }


    public void setRatingBarSize(float heightDpValue, float widthDpValue) {
        for (int i = 0; i < startall.getChildCount(); i++) {
            LinearLayout.LayoutParams linearParams1 = (LinearLayout.LayoutParams) startall.getChildAt(i)
                    .getLayoutParams();
            linearParams1.height = (int) AppUtil.dip2px(mContext, heightDpValue);
            linearParams1.width = (int) AppUtil.dip2px(mContext, widthDpValue);
            startall.getChildAt(i).setLayoutParams(linearParams1);
        }

    }


}
