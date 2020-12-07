package com.ccpro.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import ir.ccpro.utils.App;

public class MainActivity extends AppCompatActivity {

    public WebView webView;
    int step;
    public Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn1);
        //  webView = findViewById(R.id.webView);
        new App(this);
        if (true) return;

        step = 0;

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new MyBrowser());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36");

        //webView.loadUrl("https://www.google.com/search?q=%D9%81%DB%8C%D9%84%D8%AA%D8%B1%D8%B4%DA%A9%D9%86");

        step++;

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("EEEEEE", "step: " + step);

                if (step == 0) {
                    step++;
                    // webView.loadUrl("https://www.google.com/search?q="+data.keyword);

                    //webView.loadUrl("https://www.google.com/search?q="+data.keyword+"&rlz=1C1CHWL_enIR857IR857&oq="+data.keyword+"&aqs=chrome..69i57j0l7.5775j0j9&sourceid=chrome&ie=UTF-8");
                } else if (step == 1) {
                    step++;
                    findAndClick();
                } else if (step == 2) {
                    Log.e("EEEEEE", "visit Done!");
                }
            }
        });
        webView.loadUrl("https://www.google.com/search?newwindow=1&rlz=1C1CHWL_enIR857IR857&sxsrf=ALeKk01ZHmsgNyBenjYOLUEEPU2buPxlSQ%3A1605901731809&ei=ox24X7D8MNGG1fAPpoyXkAg&q=%D8%AA%D8%A8%D9%84%DB%8C%D8%BA+%DA%AF%D9%88%DA%AF%D9%84&oq=%D8%AA%D8%A8%D9%84%DB%8C%D8%BA+%DA%AF%D9%88%DA%AF%D9%84&gs_lcp=CgZwc3ktYWIQAzIFCAAQywEyBQgAEMsBMgUIABDLATIFCAAQywEyBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB46BwgAEEcQsAM6CAgAEMkDEMsBOgsILhDHARCvARDLAToGCAAQDRAeSgUIOhIBMVDDIli6KmCKLWgAcAB4AIAB0wOIAf8OkgEHMi00LjAuMpgBAKABAaoBB2d3cy13aXrIAQjAAQE&sclient=psy-ab&ved=0ahUKEwjw2ZKS8pHtAhVRQxUIHSbGBYIQ4dUDCA0&uact=5");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findAndClick();
            }
        });
    }

    void findAndClick() {
        webView.loadUrl(
                "javascript:(function() { " +
                        "console.log('load completed......');" +
                        "var targetElement =document.querySelector('[aria-label=\"Ads\"] a');" +
                        "if(targetElement!== null){" +
                        "console.log(targetElement);" +
                        "targetElement.dispatchEvent(new Event('onmousedown'));" +
                        "targetElement.dispatchEvent(new Event('onmouseup'));" +
                        "targetElement.dispatchEvent(new Event('click'));" +
                        "targetElement.click();" +
                        "}else{" +
                        "console.log('ads not found!');" +
                        "}" +
                        "})()");
    }

    void loadCompleted() {
        Toast.makeText(getApplicationContext(), webView.getUrl() + "", Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onDestroy() {
        App.init(this);
        super.onDestroy();
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}