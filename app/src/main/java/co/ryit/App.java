package co.ryit;

import android.app.Application;

import co.baselib.global.ByAppController;
import co.baselib.global.ByConfig;


public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        /***
         * 初始化框架
         */
        ByAppController.getInstance().init(this,this.getApplicationContext());
        ByConfig.init(this).setDebug(true);
    }
}
