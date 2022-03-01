/**
 * Summary: js脚本所能执行的函数空间
 * Version 1.0
 * Date: 13-11-20
 * Time: 下午4:40
 * Copyright: Copyright (c) 2013
 */

package com.turbid.jscall;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.webkit.WebView;
import android.widget.Toast;

import com.turbid.jscall.listener.JsCallAppListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

//HostJsScope中需要被JS调用的函数，必须定义成public ，且必须包含WebView这个参数
public class HostJsScope{
    /**
     * 短暂气泡提醒
     * @param webView 浏览器
     * @param message 提示信息
     * */
    public void toast(WebView webView, String message) {
        Toast.makeText(webView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 可选择时间长短的气泡提醒
     * @param webView 浏览器
     * @param message 提示信息
     * @param isShowLong 提醒时间方式
     * */
    public void toast(WebView webView, String message, int isShowLong) {
        Toast.makeText(webView.getContext(), message, isShowLong).show();
    }

    /**
     * 弹出记录的测试JS层到Java层代码执行损耗时间差
     * @param webView 浏览器
     * @param timeStamp js层执行时的时间戳
     * */
    public void testLossTime(WebView webView, long timeStamp) {
        timeStamp = System.currentTimeMillis() - timeStamp;
        alert(webView, String.valueOf(timeStamp));
    }

    /**
     * 系统弹出提示框
     * @param webView 浏览器
     * @param message 提示信息
     * */
    public void alert(WebView webView, String message) {
        // 构建一个Builder来显示网页中的alert对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(webView.getContext());
        builder.setTitle("系统弹出");
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

    public void alert(WebView webView, int msg) {
        alert(webView, String.valueOf(msg));
    }

    public void alert(WebView webView, boolean msg) {
        alert(webView, String.valueOf(msg));
    }


    /**
     * 获取用户系统版本大小
     * @param webView 浏览器
     * @return 安卓SDK版本
     * */
    public  int getOsSdk (WebView webView) {
        return Build.VERSION.SDK_INT;
    }

    //---------------- 界面切换类 ------------------

    /**
     * 结束当前窗口
     * @param view 浏览器
     * */
    public  void goBack (WebView view) {
        if (view.getContext() instanceof Activity) {
            ((Activity)view.getContext()).finish();
        }
    }

    /**
     * 传入Json对象
     * @param view 浏览器
     * @param jo 传入的JSON对象
     * @return 返回对象的第一个键值对
     * */
    public String passJson2Java (WebView view, JSONObject jo) {
        Iterator iterator = jo.keys();
        String res = null;
        if(iterator.hasNext()) {
            try {
                String keyW = (String)iterator.next();
                res = keyW + ": " + jo.getString(keyW);
            } catch (JSONException je) {

            }
        }
        return res;
    }

    /**
     * 将传入Json对象直接返回
     * @param view 浏览器
     * @param jo 传入的JSON对象
     * @return 返回对象的第一个键值对
     * */
    public JSONObject retBackPassJson (WebView view, JSONObject jo) {
        return jo;
    }

    public  int overloadMethod(WebView view, int val) {
        return val;
    }

    public String overloadMethod(WebView view, String val) {
        return val;
    }


    public  void delayJsCallBack(WebView view, int ms, final String backMsg, final JsCallback jsCallback) {
        try {
            jsCallback.apply(backMsg);
        } catch (JsCallback.JsCallbackException e) {
            e.printStackTrace();
        }
    }

    public void jsCallApp(WebView view){
        jsCallApp(view,null);
    }

    public void jsCallApp(WebView view, String type){
        jsCallApp(view,type,null);
    }

    public void jsCallApp(WebView view, String type, String json){
        jsCallApp(view, type, json,null);
    }
    public void jsCallApp(WebView view, String type, String json, JsCallback jsCallback){
        if (onJsCallAppListener!=null){
            onJsCallAppListener.onJsCall(type,json,jsCallback);
        }
    }

    private JsCallAppListener onJsCallAppListener;
    public void setonJsCallAppListener(JsCallAppListener listener){
        this.onJsCallAppListener = listener;
    }

}