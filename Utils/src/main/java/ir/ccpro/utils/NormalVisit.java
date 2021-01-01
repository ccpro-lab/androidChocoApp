package ir.ccpro.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import ir.ccpro.utils.Api.NormalApi;
import ir.ccpro.utils.Models.NormalDataModel;
import ir.ccpro.utils.Models.VolleyCallback;

public class NormalVisit {
    final NormalDataModel data;
    final Context context;
    int index;

    WebView webView;

    public NormalVisit(Context context, NormalDataModel data) {
        this.data = data;
        this.context = context;
        index = 0;
    }

    public void start() {
        if (data.id == 0) {
            return;
        } //null

        if(webView== null) {
            webView = new WebView(context);
        }
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setJavaScriptEnabled(true);


        //Make sure no caching is done
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(false);
        webView.clearHistory();
        webView.clearCache(true);

        //Make sure no autofill for Forms/ user-name password happens for the app
        webView.clearFormData();
        webView.getSettings().setSavePassword(false);
        webView.getSettings().setSaveFormData(false);


        NormalDataModel.Item item = data.items.get(index);
        webView.loadUrl(item.url);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadCompleted();
            }
        });
    }


    void loadCompleted() {
        Log.i("EEEEEE", "Load done!");
        int indexOfItem = index;
        if (indexOfItem > data.items.size())
            return;
        try {
            new NormalApi().SaveVisit(context, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.i("EEEEEE", "save success!");

                    int delay = data.items.get(index).watingInPage;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            index++;
                            if (index < data.items.size()) {
                                findAndClick(data.items.get(index).url);
                            } else {
                                webView.loadUrl("http://google.com");
                            }

                        }
                    }, delay * 1000);

                }

                @Override
                public void onError(String error) {
                    Log.i("EEEEEE", "save error!");

                }
            }, data.items.get(indexOfItem).id);
        } catch (Exception e) {
            Log.e("EEEEEE", "index: " + index);
        }
        //Toast.makeText(context,"load completed ....", Toast.LENGTH_SHORT).show();
    }


    void findAndClick(String link) {
        webView.loadUrl(
                "javascript:(function() { " +
                        "var links = document.getElementsByTagName('a');" +
                        "for(var i =0;i<links.length;i++){" +
                        "    var href = links[i].getAttribute('href');" +
                        "    if(href!= null && href.includes('" + link + "')){" +
                        "        links[i].click();break;" +
                        "    }" +
                        "}" +
                        "})()");
    }


    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
