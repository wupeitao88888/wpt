package com.iloomo.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iloomo.paysdk.R;
import com.iloomo.widget.WebLayout;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.ChromeClientCallbackManager;


/**
 * Created by cenxiaozhong on 2017/5/26.
 *
 * source CODE  https://github.com/Justson/AgentWeb
 *
 * <p>
 */

public class BaseWebActivity extends ActivitySupport {



    protected AgentWeb mAgentWeb;
    private LinearLayout mLinearLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);
        mLinearLayout = (LinearLayout) this.findViewById(R.id.container);
        long p = System.currentTimeMillis();

        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(mLinearLayout,new LinearLayout.LayoutParams(-1,-1) )//
                .useDefaultIndicator()//
                .defaultProgressBarColor()
                .setReceivedTitleCallback(mCallback)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
//                .setSecutityType(AgentWeb.SecurityType.strict)
                .setWebLayout(new WebLayout(this))
                .createAgentWeb()//
                .ready()
                .go(getUrl());

        //mAgentWeb.getLoader().loadUrl(getUrl());

        long n = System.currentTimeMillis();
        Log.i("Info", "init used time:" + (n - p));

    }

    private WebViewClient mWebViewClient=new WebViewClient(){
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
           //do you  work
            Log.i("Info","BaseWebActivity onPageStarted");
        }
    };
    private WebChromeClient mWebChromeClient=new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
//            Log.i("Info","progress:"+newProgress);
        }
    };
    public String getUrl(){

        return "https://www.ryit.co/ryit_h5/html/redPacket/appGive.html?ads_id=68708562670453764&version=38&app=android&date=";
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
          setCtenterTitle(title);
        }
    };




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("Info", "result:" + requestCode + " result:" + resultCode);
        mAgentWeb.uploadFileResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWeb.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }
}
