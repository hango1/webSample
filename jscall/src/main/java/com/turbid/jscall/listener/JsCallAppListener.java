package com.turbid.jscall.listener;

import com.turbid.jscall.JsCallback;

public interface JsCallAppListener {
    void onJsCall(String type, String json, JsCallback jsCallback);
}
