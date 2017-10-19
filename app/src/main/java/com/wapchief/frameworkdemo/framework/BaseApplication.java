package com.wapchief.frameworkdemo.framework;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.wapchief.frameworkdemo.framework.sp.SharedPrefHelper;


/**
 * Created by wapchief on 2017/4/13 0013 上午 11:23.
 * 描述：自定义Application
 */
public class BaseApplication extends Application {


    public static BaseApplication baseApplication;
    private Context mContext;
    private SharedPrefHelper sharedPrefHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        Utils.init(this);
    }

}
