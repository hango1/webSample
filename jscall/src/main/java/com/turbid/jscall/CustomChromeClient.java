package com.turbid.jscall;

import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.turbid.jscall.listener.CustomChromeListener;


public class CustomChromeClient extends InjectedChromeClient {

    private boolean isFrist = true;
    private CustomChromeListener listener;
    public void setOnCustomChromeListener(CustomChromeListener listener) {
        this.listener = listener;
    }
    public CustomChromeClient(String injectedName, HostJsScope injectedCls) {
        super(injectedName, injectedCls);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        // to do your work
        // ...
        if (listener!=null){
            listener.onJsAlert(view,url,message,result);
        }
        Log.e("-onJsAlert-",message+"?");
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onProgressChanged (final WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        // to do your work
        // ...
        if (listener!=null){
            listener.onProgressChanged(view,newProgress);
        }
        if (newProgress == 100) {
            if (isFrist) {
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        view.loadUrl("javascript:apiReady()");
                    }
                });
                isFrist = false;
            }
        }
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        // to do your work
        // ...
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }
}
