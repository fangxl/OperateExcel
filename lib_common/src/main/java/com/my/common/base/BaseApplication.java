package com.my.common.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.my.common.utils.LogUtil;
import com.my.common.utils.SPUtil;
import com.my.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用基础类
 */
public class BaseApplication extends MultiDexApplication {
    public synchronized static BaseApplication getInstance () {
        return instant;
    }

    private static BaseApplication instant;
//    protected static String TAG;

    @Override
    public void onCreate () {
        super.onCreate();
        //当前的进程
//        TAG=this.getClass().getSimpleName();
        instant = this;

        SPUtil.init(this);
        ToastUtils.init(this);
    }

    private List<Activity> mActivities = new ArrayList<>();
    protected static Activity currentActivity;

    public void addActivtyty (Activity activity) {
        LogUtil.d("addActivtyty....");
        mActivities.add(activity);
    }

    public void removeActivity (Activity activity) {
        LogUtil.d("removeActivity...");
        mActivities.remove(activity);
    }

    public void removeAllActivity () {
        LogUtil.e("removeAllActivity");
        currentActivity = null;
        for (Activity activity : mActivities) {
            LogUtil.d("---finish掉一个Activity:" + activity.getClass().getSimpleName());
            activity.finish();
        }
        mActivities.clear();
    }

    @Override
    protected void attachBaseContext (Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 返回当前在前台的Activity
     * @return
     */
    public Activity getCurrentActivity () {
        LogUtil.d("getCurrentActivity");
        return currentActivity;
    }

    /**
     * 设置当前在前台的activity
     * @param activity
     */
    public void setCurrentActivity (Activity activity) {
        if (activity != null) {
            LogUtil.d("setCurrentActivity:" + activity.getClass().getName());
        } else {
            LogUtil.d("setCurrentActivity:null");
        }
        currentActivity = activity;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public Handler getHandler () {
        return mHandler;
    }

    public static Application getContext () {
        return instant;
    }

    public String getProcessName (Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }
}
