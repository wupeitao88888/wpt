package co.ryit.activity.webview;

import android.os.Bundle;

import co.baselib.base.WebActivitySupport;

public class MyWebViewActivity extends WebActivitySupport {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init("https://www.baidu.com");
    }
}
