#1、初始化项目


              JmessageConfig.init(Context context, boolean debug);

#2、在AndroidManifest.xml中添加项目代码



        <!-- Required SDK核心功能-->
        <receiver
            android:name="cn.jpush.android.service.PushReceiver"
            android:enabled="true"
            android:exported="false">
            <intent-filter android:priority="1000">
                <action android:name="cn.jpush.android.intent.NOTIFICATION_RECEIVED_PROXY" />
                <category android:name="包名" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.intent.action.USER_PRESENT" />
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
            </intent-filter>
            <!-- Optional -->
            <intent-filter>
                <action android:name="android.intent.action.PACKAGE_ADDED" />
                <action android:name="android.intent.action.PACKAGE_REMOVED" />

                <data android:scheme="package" />
            </intent-filter>
        </receiver>

        <!-- Required since JCore 1.1.7. SDK 核心功能-->
        <provider
            android:name="cn.jpush.android.service.DataProvider"
            android:authorities="包名.DataProvider"
            android:exported="false" />

        <!-- Required since JMessage 2.7.0 SDK 核心功能-->
        <provider
            android:name="cn.jpush.im.android.helpers.ipc.IMProvider"
            android:authorities="包名.IMProvider"
            android:exported="false" />

        <!-- Required. AppKey copied from Portal -->
        <meta-data
            android:name="JPUSH_APPKEY"
            android:value="appkey" />

        <!--end-->

#3、在app中build.gradle添加

