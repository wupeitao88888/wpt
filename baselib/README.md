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

#2、初始化SDK，添加umengsdk,初始化umengsdk，如果使用thirdpartylib的话不用添加👇的jar包

            umeng-common-1.5.3.jar

#3、在application中添加初始化代码块

        AppController.getInstance().init(this, getApplicationContext());
        UMConfigure.init(this, "appid", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

#4、添加APPconfig.gradle并且在项目根目录的bulid.gradle中添加代码

        apply from: 'APPconfig.gradle'



### 一、权限获取方式


            RxPermissions.getInstance(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe { granted ->
                    if (granted!!) { // 在android 6.0之前会默认返回true
                        Log.e("获取权限", "获取权限成功");
                    } else {
                        // 未获取权限
                        Log.e("获取权限", "获取权限失败");
                    }
                }


### 二、欢迎页面

a、在xml页面中添加

        <co.baselib.widget.StartPic
            android:id="@+id/welcome"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>


b、设置图片

            StartPic对象.setStartPicImage（int img_id）

c、启动动画

            StartPic对象.start()

d、设置动画监听

             welcome.setAnimationListener(object : Animator.AnimatorListener {
                        override fun onAnimationRepeat(p0: Animator?) {

                        }

                        override fun onAnimationCancel(p0: Animator?) {

                        }

                        override fun onAnimationStart(animator: Animator) {

                        }

                        override fun onAnimationEnd(animator: Animator) {

                        }
                    })

e、去掉titlebar和去掉状态栏

      override fun onCreate(savedInstanceState: Bundle?) {
            isHideStatusBar(true)
            super.onCreate(savedInstanceState)
            setContentView(R.layout.layout_app)
            setRemoveTitle()
       }


### 二、tabhost详见"FlycoTablayout_README_CN.md"这个文件，因为是引用"H07000223"这个哥们写的库，鸣谢

### 三、MLImageViewR.class

### 特点

* 基于AppCompatImageView扩展
* 支持圆角、圆形显示

### 支持的属性、方法
|属性名|含义|默认值|对应方法
|---|---|---|---|
|is_circle|是否显示为圆形（默认为矩形）|false|isCircle()
|radius_top_left|左上角圆角半径|0dp|setCornerTopLeftRadius()
|radius_top_right|右上角圆角半径|0dp|setCornerTopRightRadius()
|radius_btm_left|左下角圆角半径|0dp|setCornerBottomLeftRadius()
|radius_btm_right|右下角圆角半径|0dp|setCornerBottomRightRadius()
|corner_radius|统一设置四个角的圆角半径|0dp|setCornerRadius()





### 三、基本设置 IloomoConfig.class

a、数据库名称设置

    setDbName(String dbname)  dbname: 数据库名字

    





