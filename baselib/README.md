#本项目是为减少开发的代码量，本项目含有网络、ActivitySupport、FragmentSupport、广告位、线程池、数据库，进度条
#1、添加依赖
#a、在项目根目录里的build.gradle里添加


        allprojects {
            repositories {
              maven { url 'http://58.87.124.224:8081/repository/baselib/' }
            }
        }


#b、在项目中的APP里的build.gradle里的dependencies添加


        api 'co.baselib:baselib:1.0.2'

#2、初始化SDK，添加umengsdk,初始化umengsdk

            umeng-common-1.5.3.jar

#3、在application中添加初始化代码块

        AppController.getInstance().init(this, getApplicationContext());
        UMConfigure.init(this, "appid", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

#4、添加APPconfig.gradle并且在项目根目录的bulid.gradle中添加代码

        apply from: 'APPconfig.gradle'



