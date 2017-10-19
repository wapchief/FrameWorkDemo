package com.wapchief.frameworkdemo.framework.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.wapchief.frameworkdemo.framework.BaseApplication;


/**
 * Created by wapchief on 2017/9/26.
 */

public class SharedPrefHelper {

    private static volatile SharedPrefHelper sharedPrefHelper = null;
    private static SharedPreferences sharedPreferences;


    public static SharedPrefHelper getInstance(){
        if (null == sharedPrefHelper) {
            synchronized (sharedPrefHelper.getClass()){
                if (null == sharedPrefHelper){
                    sharedPrefHelper = new SharedPrefHelper();

                }
            }
        }

        return sharedPrefHelper;
    }
    private SharedPrefHelper(){
        sharedPreferences =
                BaseApplication.baseApplication.getSharedPreferences("SPH_NAME", Context.MODE_PRIVATE);
    }

    /*保存用户token*/
    public void setToken(String token){
        sharedPreferences.edit().putString("token", token).commit();
    }

    public String getToken(){

        return sharedPreferences.getString("token", "");
    }
}
