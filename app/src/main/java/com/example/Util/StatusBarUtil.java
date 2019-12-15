package com.example.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.ecar.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class StatusBarUtil {

    private static final String TAG="StatusBarUtil";

    public static void setStatusBarLayoutStyle(Context context, boolean isChange, int color){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            ((AppCompatActivity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            ((AppCompatActivity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(((AppCompatActivity)context));
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            //判断是否需要更改状态栏颜色
            if(isChange){
                tintManager.setStatusBarTintResource(color);
            }else{
                tintManager.setStatusBarTintResource(android.R.color.white);
            }
            ViewGroup mContentView = (ViewGroup) ((AppCompatActivity) context).getWindow().findViewById(((AppCompatActivity) context).getWindow().ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                mChildView.setFitsSystemWindows(true);
            }
        }
    }

    public static void setStatusBarLayoutStyle(Context context, boolean isChange){
        setStatusBarLayoutStyle(context, isChange, R.color.colorBlue);
    }

    public static void setStatusColor(Activity activity, boolean isTranslate, boolean isDarkText, @ColorRes int bgColor) {
        //如果系统为6.0及以上，就可以使用Android自带的方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); //可有可无
            decorView.setSystemUiVisibility((isTranslate ? View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN : 0) | (isDarkText ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : 0));
            window.setStatusBarColor(isTranslate ? Color.TRANSPARENT : ContextCompat.getColor(activity, bgColor)); //Android5.0就可以用
            ViewGroup mContentView = (ViewGroup) (activity.getWindow().findViewById(android.R.id.content));
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                mChildView.setFitsSystemWindows(true);
            }else{
                mChildView = new View(activity);
                int screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
                int screenHeight = getStatusBarHeight(activity);
                ViewGroup.LayoutParams layoutParams =  new ViewGroup.LayoutParams(screenWidth,screenHeight);
                mChildView.setLayoutParams(layoutParams);
                mChildView.requestLayout();

                // 获取系统布局
                ViewGroup systemContent =activity. findViewById(android.R.id.content);
                // 系统布局的第一个元素（用户布局）为 status 预留空间，文章末尾对该方法进行补充
                //systemContent.getChildAt(0).setFitsSystemWindows(true);
                //systemContent.addView(mChildView, 0);
                // 设置背景颜色，这里也可设置drawable背景
                mChildView.setBackgroundColor(activity.getResources().getColor(R.color.white));
            }
        }
    }
    /** 通过Resource获取status高度 **/
    private static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = -1;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        Log.d(TAG, "getStatusBarHeight: " + resourceId + " " + statusBarHeight);
        return statusBarHeight;
    }
}
