package co.wpt;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;


import co.wpt.login.LoginListener;
import co.wpt.login.LoginPlatform;
import co.wpt.login.LoginResult;
import co.wpt.login.result.QQToken;
import co.wpt.login.result.QQUser;
import co.wpt.login.result.WeiboToken;
import co.wpt.login.result.WeiboUser;
import co.wpt.login.result.WxToken;
import co.wpt.login.result.WxUser;
import co.wpt.model.ShareListener;
import co.wpt.utils.SLog;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by shaohui on 2016/12/3.
 */

public class LoginUtil {

    private static LoginUtil instance;

    /**
     * get the AppManager instance, the AppManager is singleton.
     */
    public static LoginUtil getInstance() {
        if (instance == null) {
            instance = new LoginUtil();
        }
        return instance;
    }

    private int mPlatform;
    static ProgressDialog progressDialog;
    private LoginListener listener;
    Activity context;
    UMShareAPI umShareAPI;
    ShareListener onShareListener;

    public void login(Activity context, @LoginPlatform.Platform int platform,
                      LoginListener listener, ShareListener onShareListener) {
        this.context = context;
        umShareAPI = UMShareAPI.get(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在请求，请稍等...");
        this.listener = listener;
        this.onShareListener = onShareListener;
        login(context, platform, true);
    }

    public void login(Activity context, @LoginPlatform.Platform int platform, boolean fetchUserInfo) {
        mPlatform = platform;
        action(context);
    }

    void action(Activity activity) {
        switch (mPlatform) {
            case LoginPlatform.QQ:
                UMShareAPI.get(activity).getPlatformInfo(activity, SHARE_MEDIA.QQ, authListener);
                break;
            case LoginPlatform.WEIBO:
                umShareAPI.getPlatformInfo(activity, SHARE_MEDIA.SINA, authListener);
                break;
            case LoginPlatform.WX:
                umShareAPI.getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, authListener);
                break;
            default:
                activity.finish();
        }
    }

    /****
     * 第三方登陆
     */
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            progressDialog.show();
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            try {
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    SLog.e("原始数据：____________Key = " + entry.getKey() + ", Value = " + entry.getValue());
                }
            } catch (Exception e) {

            }
            switch (platform) {
                case QQ:
                    if (data == null || TextUtils.isEmpty(data.get("openid"))) {
                        if (onShareListener != null)
                            onShareListener.onFiled("授权失败");
                        SLog.e("____________________________第三方登陆授权成功，但是未取到值______________________________________");
                        return;
                    }
                    formatOpenInfo(platform, data, data.get("openid"));
                    break;
                case WEIXIN:
                    if (data == null || TextUtils.isEmpty(data.get("openid"))) {
                        if (onShareListener != null)
                            onShareListener.onFiled("授权失败");
                        SLog.e("____________________________第三方登陆授权成功，但是未取到值______________________________________");
                        return;
                    }
                    formatOpenInfo(platform, data, data.get("openid"));
                    break;
                case SINA:
                    if (data == null || TextUtils.isEmpty(data.get("uid"))) {
                        if (onShareListener != null)
                            onShareListener.onFiled("授权失败");
                        SLog.e("____________________________第三方登陆授权成功，但是未取到值______________________________________");
                        return;
                    }
                    formatOpenInfo(platform, data, data.get("uid"));
                    break;
            }

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (listener != null)
                listener.loginFailure(t);
            SLog.e("____________________________第三方登陆失败______________________________________");
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (listener != null)
                listener.loginCancel();
            SLog.e("____________________________第三方登陆取消______________________________________");
        }
    };


    private void formatOpenInfo(SHARE_MEDIA shareMedia, Map<String, String> data, String openId) {

        SLog.e("____________________________第三方登陆成功______________________________________");
        try {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                SLog.e("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
        } catch (Exception e) {
        }
        switch (shareMedia) {
            case QQ:
                QQUser qqUser = new QQUser();
                if (!TextUtils.isEmpty(data.get("profile_image_url")))
                    qqUser.setqZoneHeadImage(data.get("profile_image_url"));
                else
                    qqUser.setqZoneHeadImage("");
                if (!TextUtils.isEmpty(data.get("profile_image_url")))
                    qqUser.setqZoneHeadImageLarge(data.get("profile_image_url"));
                if (!TextUtils.isEmpty(data.get("profile_image_url")))
                    qqUser.setHeadImageUrl(data.get("profile_image_url"));
                if (!TextUtils.isEmpty(data.get("profile_image_url")))
                    qqUser.setHeadImageUrlLarge(data.get("profile_image_url"));
                if (!TextUtils.isEmpty(data.get("screen_name")))
                    qqUser.setNickname(data.get("screen_name"));
                if (!TextUtils.isEmpty(openId))
                    qqUser.setOpenId(openId);
                if (!TextUtils.isEmpty(data.get("gender").toString())) {
                    if ("男".equals(data.get("gender").toString())) {
                        qqUser.setSex(1);
                    } else {
                        qqUser.setSex(0);
                    }
                }

                QQToken qqToken = new QQToken();
                if (!TextUtils.isEmpty(data.get("openid"))) {
                    qqToken.setOpenid(data.get("openid"));
                    qqToken.setAccessToken(data.get("openid"));
                }


                LoginResult loginResult = new LoginResult(LoginPlatform.QQ, qqToken, qqUser);
                if (listener != null)
                    listener.loginSuccess(loginResult);
                break;
            case WEIXIN:
                WxUser wxuser = new WxUser();
                WxToken wxtoken = new WxToken();

                if (!TextUtils.isEmpty(data.get("gender"))) {
                    if ("男".equals(data.get("gender"))) {
                        wxuser.setSex(1);
                    } else {
                        wxuser.setSex(0);
                    }
                }
                if (!TextUtils.isEmpty(data.get("uid")))
                    wxuser.setOpenId(data.get("uid"));
                if (!TextUtils.isEmpty(data.get("name")))
                    wxuser.setNickname(data.get("name"));
                if (!TextUtils.isEmpty(data.get("iconurl")))
                    wxuser.setHeadImageUrlLarge(data.get("iconurl"));
                if (!TextUtils.isEmpty(data.get("openid"))) {
                    wxtoken.setAccessToken(data.get("openid"));
                    wxtoken.setOpenid(data.get("openid"));
                }
                LoginResult wxloginResult = new LoginResult(LoginPlatform.WX, wxtoken, wxuser);
                if (listener != null)
                    listener.loginSuccess(wxloginResult);
                break;
            case SINA:
                WeiboUser weibouser = new WeiboUser();
                WeiboToken weibotoken = new WeiboToken();

                if (!TextUtils.isEmpty(data.get("gender").toString())) {
                    if ("男".equals(data.get("gender").toString())) {
                        weibouser.setSex(1);
                    } else {
                        weibouser.setSex(0);
                    }
                }
                if (!TextUtils.isEmpty(openId))
                    weibouser.setOpenId(openId);
                if (!TextUtils.isEmpty(data.get("screen_name")))
                    weibouser.setNickname(data.get("screen_name"));
                if (!TextUtils.isEmpty(data.get("profile_image_url")))
                    weibouser.setHeadImageUrlLarge(data.get("profile_image_url"));
                if (!TextUtils.isEmpty(openId)) {
                    weibotoken.setAccessToken(data.get("id"));
                    weibotoken.setOpenid(data.get("id"));
                }
                LoginResult wbloginResult = new LoginResult(LoginPlatform.WEIBO, weibotoken, weibouser);
                if (listener != null)
                    listener.loginSuccess(wbloginResult);
                break;
        }

    }


    /***
     * 退出登录，删除授权
     * @param activity
     * @param platform
     */
    public void delete(Activity activity, @LoginPlatform.Platform int platform) {
        switch (platform) {
            case LoginPlatform.QQ:
                UMShareAPI.get(context).deleteOauth(activity, SHARE_MEDIA.QQ, authListeners);
                break;
            case LoginPlatform.WEIBO:
                UMShareAPI.get(context).deleteOauth(activity, SHARE_MEDIA.QQ, authListeners);
                break;
            case LoginPlatform.WX:
                UMShareAPI.get(context).deleteOauth(activity, SHARE_MEDIA.WEIXIN, authListeners);
                break;
        }
    }


    /***
     * 取消授权
     */
    UMAuthListener authListeners = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            progressDialog.show();
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            try {
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    SLog.e("原始数据：____________Key = " + entry.getKey() + ", Value = " + entry.getValue());
                }
            } catch (Exception e) {
            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            SLog.e("____________________________第三方登陆失败______________________________________");
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            SLog.e("____________________________第三方登陆取消______________________________________");
        }
    };


}
