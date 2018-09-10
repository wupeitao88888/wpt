package co.wpt;

import android.app.Activity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import co.wpt.bean.ShareModel;
import co.wpt.utils.SLog;


/**
 * Created by shaohui on 2016/11/18.
 */

public class ShareUtil {



    private static ShareUtil instance;

    /**
     * get the AppManager instance, the AppManager is singleton.
     */
    public static ShareUtil getInstance() {
        if (instance == null) {
            instance = new ShareUtil();
        }
        return instance;
    }


    public void share(SHARE_MEDIA platform, Activity activity, ShareModel shareModel, UMImage thumb, UMShareListener umShareListener) {
        SLog.e("——————————————————————————————————开始分享————————————————————————————");
        UMWeb web = new UMWeb(shareModel.getUrl());
        web.setTitle(shareModel.getTitle());//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(shareModel.getContent());//描述
        new ShareAction(activity)
                .withMedia(web)
                .setPlatform(platform)
                .setCallback(umShareListener)
                .share();

    }
}
