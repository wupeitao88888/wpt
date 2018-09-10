#说明：本项目是基于UMengSDK进行的二次封装，进一步减少分享与登陆的代码数量，方便程序员直接对接，添加umeng的APPid

#1、在包名目录下创建wxapi文件夹，新建一个名为WXEntryActivity的activity继承WXCallbackActivity

#2、添加配置文件

#umeng 需要添加在主程序的AndroidManifest.xml  start

#微信

                <activity
                        android:name=".wxapi.WXEntryActivity"
                        android:configChanges="keyboardHidden|orientation|screenSize"
                        android:exported="true"
                        android:theme="@android:style/Theme.Translucent.NoTitleBar" />
#微信end
#QQ 把APPID换成自己的

                <activity
                    android:name="com.tencent.tauth.AuthActivity"
                    android:launchMode="singleTask"
                    android:noHistory="true">
                    <intent-filter>
                        <action android:name="android.intent.action.VIEW" />
                        <category android:name="android.intent.category.DEFAULT" />
                        <category android:name="android.intent.category.BROWSABLE" />
                        <data android:scheme="tencent100424468" />
                    </intent-filter>
                </activity>

                <activity
                    android:name="com.tencent.connect.common.AssistActivity"
                    android:configChanges="orientation|keyboardHidden|screenSize"
                    android:theme="@android:style/Theme.Translucent.NoTitleBar" />
#QQ  end

#umeng 需要添加在主程序的AndroidManifest.xml end


#QQ和新浪不需要添加activity，但是需要在activity里面的时候添加如下代码  登陆（）


            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                    super.onActivityResult(requestCode, resultCode, data);
                    UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
            }


#在Application中添加代码


             {
                    PlatformConfig.setWeixin(co.baselib.BuildConfig.wxAPPID, co.baselib.BuildConfig.wxSecret);
                    PlatformConfig.setQQZone(co.baselib.BuildConfig.qqAPPID, co.baselib.BuildConfig.qqKey);
                    PlatformConfig.setSinaWeibo(co.baselib.BuildConfig.wbKEY, co.baselib.BuildConfig.wbSecret, co.baselib.BuildConfig.wbRedirectUrl);
                }


#在Application中的onCreate方法中加入

           UmengUtils.setShareUtilsInit(this,"umeng的appid",true);


#以下是代码混淆

                        -dontshrink
                        -dontoptimize
                        -dontwarn com.google.android.maps.**
                        -dontwarn android.webkit.WebView
                        -dontwarn com.umeng.**
                        -dontwarn com.tencent.weibo.sdk.**
                        -dontwarn com.facebook.**
                        -keep public class javax.**
                        -keep public class android.webkit.**
                        -dontwarn android.support.v4.**
                        -keep enum com.facebook.**
                        -keepattributes Exceptions,InnerClasses,Signature
                        -keepattributes *Annotation*
                        -keepattributes SourceFile,LineNumberTable

                        -keep public interface com.facebook.**
                        -keep public interface com.tencent.**
                        -keep public interface com.umeng.socialize.**
                        -keep public interface com.umeng.socialize.sensor.**
                        -keep public interface com.umeng.scrshot.**

                        -keep public class com.umeng.socialize.* {*;}


                        -keep class com.facebook.**
                        -keep class com.facebook.** { *; }
                        -keep class com.umeng.scrshot.**
                        -keep public class com.tencent.** {*;}
                        -keep class com.umeng.socialize.sensor.**
                        -keep class com.umeng.socialize.handler.**
                        -keep class com.umeng.socialize.handler.*
                        -keep class com.umeng.weixin.handler.**
                        -keep class com.umeng.weixin.handler.*
                        -keep class com.umeng.qq.handler.**
                        -keep class com.umeng.qq.handler.*
                        -keep class UMMoreHandler{*;}
                        -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
                        -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
                        -keep class im.yixin.sdk.api.YXMessage {*;}
                        -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
                        -keep class com.tencent.mm.sdk.** {
                           *;
                        }
                        -keep class com.tencent.mm.opensdk.** {
                           *;
                        }
                        -keep class com.tencent.wxop.** {
                           *;
                        }
                        -keep class com.tencent.mm.sdk.** {
                           *;
                        }
                        -dontwarn twitter4j.**
                        -keep class twitter4j.** { *; }

                        -keep class com.tencent.** {*;}
                        -dontwarn com.tencent.**
                        -keep class com.kakao.** {*;}
                        -dontwarn com.kakao.**
                        -keep public class com.umeng.com.umeng.soexample.R$*{
                            public static final int *;
                        }
                        -keep public class com.linkedin.android.mobilesdk.R$*{
                            public static final int *;
                        }
                        -keepclassmembers enum * {
                            public static **[] values();
                            public static ** valueOf(java.lang.String);
                        }

                        -keep class com.tencent.open.TDialog$*
                        -keep class com.tencent.open.TDialog$* {*;}
                        -keep class com.tencent.open.PKDialog
                        -keep class com.tencent.open.PKDialog {*;}
                        -keep class com.tencent.open.PKDialog$*
                        -keep class com.tencent.open.PKDialog$* {*;}
                        -keep class com.umeng.socialize.impl.ImageImpl {*;}
                        -keep class com.sina.** {*;}
                        -dontwarn com.sina.**
                        -keep class  com.alipay.share.sdk.** {
                           *;
                        }

                        -keepnames class * implements android.os.Parcelable {
                            public static final ** CREATOR;
                        }

                        -keep class com.linkedin.** { *; }
                        -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
                        -keepattributes Signature


大致就这样！能省多少就剩多少吧，哈哈哈


本项目用到的第三方包  umeng用的是精简版的哦

    implementation files('libs/SecurityEnvSDK-release-1.1.0.jar')
    implementation files('libs/umeng-common-1.5.3.jar')
    implementation files('libs/umeng-share-core-6.9.3.jar')
    implementation files('libs/umeng-share-QQ-simplify-6.9.3.jar')
    implementation files('libs/umeng-share-sina-simplify-6.9.3.jar')
    implementation files('libs/umeng-share-wechat-simplify-6.9.3.jar')
    implementation files('libs/umeng-shareboard-widget-6.9.3.jar')
    implementation files('libs/umeng-sharetool-6.9.3.jar')
    implementation files('libs/utdid4all-1.1.5.3_proguard.jar')
    //noinspection GradleCompatible
    api 'com.android.support:design:27.1.1'


#接下来是登陆的代码，一行代码实现第三方登陆LoginPlatform.QQ、LoginPlatform.WEIBO、LoginPlatform.WX

    LoginUtil.getInstance().login(this, LoginPlatform.QQ, listener, shareListener);


#接下来是回调方法，根据实际业务需要，做相应的操作


    final LoginListener listener = new LoginListener() {
                      @Override
                      public void beforeFetchUserInfo(BaseToken token) {
                          super.beforeFetchUserInfo(token);
                      }

                      @Override
                      public void loginSuccess(LoginResult result) {
                          //登录成功， 如果你选择了获取用户信息，可以通过

                          // 处理result
                          switch (result.getPlatform()) {
                              case LoginPlatform.QQ:
                                  QQUser user = (QQUser) result.getUserInfo();
                                  QQToken token = (QQToken) result.getToken();
                                  L.e("openid:" + token.getOpenid());
                                  Bundle bundle = new Bundle();
                                  bundle.putString("type", "2");
                                  bundle.putString("openid", token.getOpenid());
                                  bundle.putString("nick", user.getNickname());
                                  bundle.putString("avatar", user.getHeadImageUrlLarge());

                                  break;
                              case LoginPlatform.WEIBO:
                                  // 处理信息
                                  WeiboUser Weibouser = (WeiboUser) result.getUserInfo();
                                  WeiboToken Weibotoken = (WeiboToken) result.getToken();
                                  L.e("openid:" + Weibotoken.getOpenid());
                                  Bundle bd = new Bundle();
                                  bd.putString("type", "3");
                                  bd.putString("openid", Weibotoken.getOpenid());
                                  if (Weibouser == null) {
                                      bd.putString("nick", "");
                                      bd.putString("avatar", "");
                                  } else {
                                      bd.putString("nick", Weibouser.getNickname());
                                      bd.putString("avatar", Weibouser.getHeadImageUrlLarge());
                                  }

                                  break;
                              case LoginPlatform.WX:
                                  WxUser wxUser = (WxUser) result.getUserInfo();
                                  WxToken wxtoken = (WxToken) result.getToken();
                                  L.e("openid:" + wxtoken.getOpenid());
                                  Bundle bds = new Bundle();
                                  bds.putString("type", "1");
                                  bds.putString("openid", wxtoken.getOpenid());
                                  bds.putString("nick", wxUser.getNickname());
                                  bds.putString("avatar", wxUser.getHeadImageUrlLarge());

                                  break;
                          }

                      }

                      @Override
                      public void loginFailure(Throwable e) {
                          Log.i("TAG", "登录失败" + e);
                          try {
                              showToastBig(StrUtil.getResString(R.string.loginFailure, context));
                          } catch (Exception e1) {

                          }
                      }

                      @Override
                      public void loginCancel() {
                          Log.i("TAG", "登录取消");
                          try {
                              showToastBig(StrUtil.getResString(R.string.loginCancel, context));
                          } catch (Exception e) {

                          }
                      }
                  };

#这个回调方法是错误回调和成功回调，主要是提示语作用

     ShareListener shareListener = new ShareListener() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFiled(String s) {

            }
        };


#剩下的就是分享了，使用者可以自定义UI控件，只需要调用方法即可，SHARE_MEDIA.QQ、SHARE_MEDIA.WEIXIN、SHARE_MEDIA.QZONE、SHARE_MEDIA.WEIXIN_CIRCLE、SHARE_MEDIA.SINA

    UMImage umImage = new UMImage(context, shareModel.getImgurl());
    ShareUtil.getInstance().share(SHARE_MEDIA.QQ, context, shareModel, umImage, mShareListener);

#回调方法

      UMShareListener mShareListener = new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                L.e("—————————————————————————分享开始—————————————————————————");
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                L.e("—————————————————————————分享成功—————————————————————————");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                L.e("—————————————————————————分享失败—————————————————————————");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                L.e("—————————————————————————取消分享—————————————————————————");
            }
        };


#结束谢谢





