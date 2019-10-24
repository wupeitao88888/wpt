package co.baselib.utils;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.baselib.R;
import co.baselib.global.ByAppController;

/**
 * 无数据和无网络处理，首先 在布局中include  layout_error.xml 然后在onCreate中调用initNetStatus()方法  然后显示状态
 * Created by wupeitao on 2017/8/29.
 */

public class ByActivityErrorUtils {


    private int nodate_icon;
    private int nonet_icon;
    private String error_content;
    private String nodata_content;
    private int btnBg = -1;
    private String btn_content;
    private int btn_textcolor = -1;
    private int bg = -1;
    private int error_img_margin_top = -1;
    private int nodata_img_margin_top = -1;

    private int error_text_margin_top = -1;
    private int nodata_text_margin_top = -1;

    private int nodata_btn_margin_top = -1;

    private RelativeLayout.LayoutParams imglayoutParams;
    private RelativeLayout.LayoutParams btnlayoutParams;

    public RelativeLayout.LayoutParams getImglayoutParams() {
        return imglayoutParams;
    }

    public void setImglayoutParams(RelativeLayout.LayoutParams imglayoutParams) {
        this.imglayoutParams = imglayoutParams;
    }

    public int getError_img_margin_top() {
        return error_img_margin_top;
    }

    public void setError_img_margin_top(int error_img_margin_top) {
        this.error_img_margin_top = error_img_margin_top;
    }

    public int getNodata_img_margin_top() {
        return nodata_img_margin_top;
    }

    public void setNodata_img_margin_top(int nodata_img_margin_top) {
        this.nodata_img_margin_top = nodata_img_margin_top;
    }

    public int getError_text_margin_top() {
        return error_text_margin_top;
    }

    public void setError_text_margin_top(int error_text_margin_top) {
        this.error_text_margin_top = error_text_margin_top;
    }

    public int getNodata_text_margin_top() {
        return nodata_text_margin_top;
    }

    public void setNodata_text_margin_top(int nodata_text_margin_top) {
        this.nodata_text_margin_top = nodata_text_margin_top;
    }

    public int getNodata_btn_margin_top() {
        return nodata_btn_margin_top;
    }

    public void setNodata_btn_margin_top(int nodata_btn_margin_top) {
        this.nodata_btn_margin_top = nodata_btn_margin_top;
    }

    public RelativeLayout.LayoutParams getBtnlayoutParams() {
        return btnlayoutParams;
    }

    public void setBtnlayoutParams(RelativeLayout.LayoutParams btnlayoutParams) {
        this.btnlayoutParams = btnlayoutParams;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public String getError_content() {
        return error_content;
    }

    public void setError_content(String error_content) {
        this.error_content = error_content;
    }

    public String getNodata_content() {
        return nodata_content;
    }

    public void setNodata_content(String nodata_content) {
        this.nodata_content = nodata_content;
    }

    public int getBtnBg() {
        return btnBg;
    }

    public void setBtnBg(int btnBg) {
        this.btnBg = btnBg;
    }

    public String getBtn_content() {
        return btn_content;
    }

    public void setBtn_content(String btn_content) {
        this.btn_content = btn_content;
    }

    public int getBtn_textcolor() {
        return btn_textcolor;
    }

    public void setBtn_textcolor(int btn_textcolor) {
        this.btn_textcolor = btn_textcolor;
    }

    /**
     * constructor
     */
    private ByActivityErrorUtils() {

    }

    private static ByActivityErrorUtils instance;

    /**
     * get the AppManager instance, the AppManager is singleton.
     */
    public static ByActivityErrorUtils getInstance() {
        if (instance == null) {
            instance = new ByActivityErrorUtils();
        }
        return instance;
    }


    /***
     * 设置没有数据的空白页面里面的icon
     * @param nodate_icon
     */
    public void setNoDateIcon(int nodate_icon) {
        this.nodate_icon = nodate_icon;
    }

    /***
     * 设置没有网的空白页面里面的icon
     * @param nonet_icon
     */
    public void setNoNetIcon(int nonet_icon) {
        this.nonet_icon = nonet_icon;
    }

    /***
     * 获取没有数据的ICON
     * @return
     */
    public int getNoDateIcon() {
        return this.nodate_icon;
    }

    /***
     * 获取没有网的ICON
     * @return
     */
    public int getNoNetIcon() {
        return this.nonet_icon;
    }


    private RelativeLayout error;
    private RelativeLayout rl_nocontent;//这里什么都没有
    private RelativeLayout ll_no_network;//没有网络
    private ImageView empty_nocontent;//没有内容图片
    private ImageView network;//内有网络图片
    private Button btn_refresh;
    Context context;
    private TextView nodata_text;
    private TextView errortext;

    public void initNetStatus(View view, Context context) {
        error = view.findViewById(R.id.error);
        rl_nocontent = view.findViewById(R.id.rl_nocontent);
        ll_no_network = view.findViewById(R.id.ll_no_network);
        empty_nocontent = view.findViewById(R.id.empty_nocontent);
        network = view.findViewById(R.id.network);
        btn_refresh = view.findViewById(R.id.btn_refresh);
        nodata_text = (TextView) view.findViewById(R.id.nodata_text);
        errortext = (TextView) view.findViewById(R.id.errortext);

        this.context = context;
    }

    public void NoDate(boolean blean, View.OnClickListener onClickListener) {
        if (blean) {
            error.setVisibility(View.VISIBLE);
            rl_nocontent.setVisibility(View.VISIBLE);
            ll_no_network.setVisibility(View.GONE);
            ByImageLoaderUtils.getInstance().displayIMG(nodate_icon, empty_nocontent, context);
            rl_nocontent.setOnClickListener(onClickListener);
        } else {
            ll_no_network.setVisibility(View.GONE);
            rl_nocontent.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            rl_nocontent.setOnClickListener(null);
        }
        if (!TextUtils.isEmpty(nodata_content)) {
            nodata_text.setText(nodata_content);
        }
        if (bg != -1) {
            error.setBackgroundResource(bg);
        }
        if (nodata_img_margin_top != -1) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) empty_nocontent.getLayoutParams();
            layoutParams.setMargins(0, ByAppController.getInstance().dp2px(nodata_img_margin_top), 0, 0);
            empty_nocontent.setLayoutParams(layoutParams);
        }

        if (nodata_text_margin_top != -1) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) nodata_text.getLayoutParams();
            layoutParams.setMargins(0, ByAppController.getInstance().dp2px(nodata_text_margin_top), 0, 0);
            nodata_text.setLayoutParams(layoutParams);
        }

        if (imglayoutParams != null) {
            empty_nocontent.setLayoutParams(imglayoutParams);
        }

    }


    /***
     * 单一布局中使用，第一次加载失败时使用
     *
     * @param blean
     */
    public void netError(boolean blean, View.OnClickListener onClickListener) {
        if (blean) {
            error.setVisibility(View.VISIBLE);
            ll_no_network.setVisibility(View.VISIBLE);
            rl_nocontent.setVisibility(View.GONE);
            ByImageLoaderUtils.getInstance().displayIMG(nonet_icon, network, context);
            btn_refresh.setOnClickListener(onClickListener);
        } else {
            rl_nocontent.setVisibility(View.GONE);
            ll_no_network.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            btn_refresh.setOnClickListener(null);
        }

        if (!TextUtils.isEmpty(error_content)) {
            errortext.setText(error_content);
        }

        if (btnBg != -1) {
            btn_refresh.setBackgroundResource(btnBg);
        }
        if (!TextUtils.isEmpty(btn_content)) {
            btn_refresh.setText(btn_content);
        }
        if (btn_textcolor != -1) {
            btn_refresh.setTextColor(ContextCompat.getColor(context, btn_textcolor));
        }
        if (bg != -1) {
            error.setBackgroundResource(bg);
        }

        if (error_img_margin_top != -1) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) network.getLayoutParams();
            layoutParams.setMargins(0, ByAppController.getInstance().dp2px(error_img_margin_top), 0, 0);
            network.setLayoutParams(layoutParams);
        }

        if (error_text_margin_top != -1) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) errortext.getLayoutParams();
            layoutParams.setMargins(0, ByAppController.getInstance().dp2px(error_text_margin_top), 0, 0);
            errortext.setLayoutParams(layoutParams);
        }

        if (nodata_btn_margin_top != -1) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) btn_refresh.getLayoutParams();
            layoutParams.setMargins(0, ByAppController.getInstance().dp2px(nodata_btn_margin_top), 0, 0);
            btn_refresh.setLayoutParams(layoutParams);
        }
        if (imglayoutParams != null) {
            network.setLayoutParams(imglayoutParams);
        }

        if (btnlayoutParams != null) {
            btn_refresh.setLayoutParams(btnlayoutParams);
        }

    }

}
