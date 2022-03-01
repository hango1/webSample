package com.turbid.jscall.listener;

import android.webkit.JsResult;
import android.webkit.WebView;

import com.turbid.jscall.JsCallback;

public interface CustomChromeListener {

    default void onJsAlert(WebView view, String url, String message, JsResult result){};
    default void onProgressChanged(WebView view, int newProgress){}
    default void onReceivedTitle(WebView view, String title){}
    void onJsCall(String type, String json, JsCallback jsCallback);
}
