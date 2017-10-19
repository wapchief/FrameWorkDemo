package com.wapchief.frameworkdemo.framework;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.wapchief.frameworkdemo.framework.system.SystemStatusManager;
import com.wapchief.frameworkdemo.R;
import com.wapchief.frameworkdemo.framework.sp.SharedPrefHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by wapchief on 2017/4/13 0013 上午 10:58.
 * 描述：
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    //记录处于前台的Activity
    private static BaseActivity mForegroundActivity = null;

    //记录所有活动的Activity
    private static final List<BaseActivity> mActivities = new LinkedList<BaseActivity>();

    Context mContext;
    //加载中
    private ProgressDialog progressDialog;
    SharedPrefHelper helper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rootContentView());
        ButterKnife.bind(this);
        mContext = BaseActivity.this;
        new SystemStatusManager(this).setTranslucentStatus(R.color.colorPrimary);
        initView();
        initData();
    }

    @Override
    public void onClick(View view) {
    }


        @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * 返回界面布局
     */
    protected abstract int setContentView();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 处理点击事件
     */
    protected void processClick(View v) {
    }

    /**
     * 获取根View
     */
    public View rootContentView() {


        if (setContentView() != 0) {
            return View.inflate(this, setContentView(), null);
        } else {
            return View.inflate(this, R.layout.page_default, null);
        }
    }


    @Override
    protected void onResume() {
        mForegroundActivity = this;
        super.onResume();
    }

    @Override
    protected void onPause() {
        mForegroundActivity = null;
        super.onPause();
    }



    /***
     * 关软件盘
     * @param activity
     */
    protected void dismissKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManage = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManage.hideSoftInputFromWindow(activity
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示软键盘
     * @param v
     */
    public static void ShowKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

    }

    /**
     * 关闭所有activity
     */
    public static void finishAll() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }

    /**
     * 短时间Toast提示
     * @param activity
     * @param s
     */
    public void showToast(Activity activity,String s){
        Toast toast=Toast.makeText(activity,s,Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 长时间Toast提示
     * @param activity
     * @param s
     */
    public void showLongToast(Activity activity,String s){
        Toast toast=Toast.makeText(activity,s,Toast.LENGTH_LONG);
        toast.show();
    }

    /*加载中的进度条*/
    public void showProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("正在加载......");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    /*自定义消息的加载进度条*/
    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 隐藏正在加载的进度条
     *
     */
    public void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing() == true) {
            progressDialog.dismiss();
        }
    }


}
