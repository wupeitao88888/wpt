本项目是为快速开发项目而定制的

1、添加依赖

    a、在项目根目录里的build.gradle里添加,版本号是你上传的版本号

  
        allprojects {
            repositories {
              maven { url 'http://***自己搭建云仓库*******/repository/xbaselib/' }
            }
        }
        
        dependencies {
             implementation fileTree(include: ['*.jar'], dir: 'libs')
             implementation 'co.baselib:xbaselib:1.0.0'
        }
        
        
2、初始化框架        
        
          ByAppController.getInstance().init(this);
          //是否是调试
          ByConfig.init(this).setDebug(true);
        
        
        
        





    





