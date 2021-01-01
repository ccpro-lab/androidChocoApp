package ir.ccpro.utils;


import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import ir.ccpro.utils.Api.AdsApi;
import ir.ccpro.utils.Api.NormalApi;
import ir.ccpro.utils.Models.AdsDataMoldel;
import ir.ccpro.utils.Models.NormalDataModel;
import ir.ccpro.utils.Models.VolleyCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdsVisit {
    final AdsDataMoldel data;
    final Context context;
    int step;

    WebView webView;

    public AdsVisit(Context context, AdsDataMoldel data) {
        this.data = data;
        this.context = context;
        step = 0;
        Log.e("EEEEEE", "Start Ads Visit by key: " + data.keyword);
    }

    public void start() {
        if (data.id == 0) {
            return;
        }

        if(webView==null) {
            webView = new WebView(context);
        }
        //Make sure No cookies are created
        CookieManager.getInstance().setAcceptCookie(false);

        //Make sure no caching is done
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(false);
        webView.clearHistory();
        webView.clearCache(true);

        //Make sure no autofill for Forms/ user-name password happens for the app
        webView.clearFormData();
        webView.getSettings().setSavePassword(false);
        webView.getSettings().setSaveFormData(false);


        webView.setWebViewClient(new AdsVisit.MyBrowser());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36");



       // webView.loadUrl("https://google.com");
        step++;

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("EEEEEE", "step: " + step);

                if(step ==0){
                    step++;
                    // webView.loadUrl("https://www.google.com/search?q="+data.keyword);

                    //webView.loadUrl("https://www.google.com/search?q="+data.keyword+"&rlz=1C1CHWL_enIR857IR857&oq="+data.keyword+"&aqs=chrome..69i57j0l7.5775j0j9&sourceid=chrome&ie=UTF-8");
                }
                else if(step == 1){
                    step++;
                    findAndClick();
                }
                else{
                    Log.e("EEEEEE", "visit Done!");
                    Log.e("EEEEEE", "current url: " + webView.getUrl());
                    String currentUrl = webView.getUrl();
                    if(!currentUrl.startsWith("https://www.google.com")){
                        //save ads attack
                        try {
                            new AdsApi().SaveVisit(context, new VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    Log.e("EEEEEE save ads", result);

                                }

                                @Override
                                public void onError(String error) {
                                    Log.e("EEEEEE save ads:", error);
                                }
                            }, data.id,true,false, currentUrl);
                        }catch (Exception e){
                            Log.e("EEEEEE save ads error", e.getMessage());

                        }
                    }else{
                        Log.e("EEEEEE save ads save:", !currentUrl.startsWith("https://www.google.com")+"");

                    }
                }
            }
        });
        //webView.loadUrl("https://www.google.com/search?q="+ data.keyword);
        webView.loadUrl("https://www.google.com/search?hl=en&q="+data.keyword);
        //webView.loadUrl("https://www.google.com/search?newwindow=1&rlz=1C1CHWL_enIR857IR857&sxsrf=ALeKk01ZHmsgNyBenjYOLUEEPU2buPxlSQ%3A1605901731809&ei=ox24X7D8MNGG1fAPpoyXkAg&q=%D8%AA%D8%A8%D9%84%DB%8C%D8%BA+%DA%AF%D9%88%DA%AF%D9%84&oq=%D8%AA%D8%A8%D9%84%DB%8C%D8%BA+%DA%AF%D9%88%DA%AF%D9%84&gs_lcp=CgZwc3ktYWIQAzIFCAAQywEyBQgAEMsBMgUIABDLATIFCAAQywEyBggAEBYQHjIGCAAQFhAeMgYIABAWEB4yBggAEBYQHjIGCAAQFhAeMgYIABAWEB46BwgAEEcQsAM6CAgAEMkDEMsBOgsILhDHARCvARDLAToGCAAQDRAeSgUIOhIBMVDDIli6KmCKLWgAcAB4AIAB0wOIAf8OkgEHMi00LjAuMpgBAKABAaoBB2d3cy13aXrIAQjAAQE&sclient=psy-ab&ved=0ahUKEwjw2ZKS8pHtAhVRQxUIHSbGBYIQ4dUDCA0&uact=5");
    }

    void findAndClick() {
        Log.e("EEEEEE findAndClick", "current url: " + webView.getUrl());

        webView.loadUrl(
                "javascript:(function() { " +
                        "setTimeout(function(){" +
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
                        "},3000);" +
                        "})()");
    }


    public static class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
