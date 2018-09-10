package co.baselib.utils;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import co.baselib.R;

/**
 * 无数据和无网络处理，首先 在布局中include  layout_error.xml 然后在onCreate中调用initNetStatus()方法  然后显示状态
 * Created by wupeitao on 2017/8/29.
 */

public class ActivityErrorUtils {


    private int nodate_icon;
    private int nonet_icon;

    /**
     * constructor
     */
    private ActivityErrorUtils() {

    }

    private static ActivityErrorUtils instance;

    /**
     * get the AppManager instance, the AppManager is singleton.
     */
    public static ActivityErrorUtils getInstance() {
        if (instance == null) {
            instance = new ActivityErrorUtils();
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
    private LinearLayout ll_no_network;//没有网络
    private ImageView empty_nocontent;//没有内容图片
    private ImageView network;//内有网络图片
    private Button btn_refresh;
    Context context;

    public void initNetStatus(View view, Context context) {
        error = view.findViewById(R.id.error);
        rl_nocontent = view.findViewById(R.id.rl_nocontent);
        ll_no_network = view.findViewById(R.id.ll_no_network);
        empty_nocontent = view.findViewById(R.id.empty_nocontent);
        network = view.findViewById(R.id.network);
        btn_refresh = view.findViewById(R.id.btn_refresh);
        this.context = context;
    }

    public void NoDate(boolean blean, View.OnClickListener onClickListener) {
        if (blean) {
            error.setVisibility(View.VISIBLE);
            rl_nocontent.setVisibility(View.VISIBLE);
            ll_no_network.setVisibility(View.GONE);
            PImageLoaderUtils.getInstance().displayIMG(nodate_icon, empty_nocontent, context);
            rl_nocontent.setOnClickListener(onClickListener);
        } else {
            ll_no_network.setVisibility(View.GONE);
            rl_nocontent.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            rl_nocontent.setOnClickListener(null);
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
            PImageLoaderUtils.getInstance().displayIMG(nonet_icon, network, context);
            btn_refresh.setOnClickListener(onClickListener);
        } else {
            rl_nocontent.setVisibility(View.GONE);
            ll_no_network.setVisibility(View.GONE);
            error.setVisibility(View.GONE);
            btn_refresh.setOnClickListener(null);
        }

    }


}
