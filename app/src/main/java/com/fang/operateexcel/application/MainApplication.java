package com.fang.operateexcel.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.my.common.base.BaseApplication;
import com.my.common.utils.LogUtil;

import org.jetbrains.annotations.NotNull;

/**
 * UI模块的application
 */
public class MainApplication extends BaseApplication {
    private static Application context;
    private static Handler mHandler = new Handler();

    @Override
    public void onCreate () {
        context = this;
        String curProcess = getProcessName(this, Process.myPid());
        initBackgroundCallback();

        LogUtil.i("taskFLow---MainApplication 启动...." + curProcess);
        if (!getPackageName().equals(curProcess)) {
            return;
        }
        super.onCreate();

    }

    public static Application getContext () {
        return context;
    }

    private int appCount;
    private boolean isRunInBackground;

    private void initBackgroundCallback () {
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated (@NonNull @NotNull Activity activity, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted (@NonNull @NotNull Activity activity) {
                appCount++;
                if (isRunInBackground) {
                    isRunInBackground = false;
                    LogUtil.d("taskFlow---应用回到前台");
                }
            }

            @Override
            public void onActivityResumed (@NonNull @NotNull Activity activity) {
            }

            @Override
            public void onActivityPaused (@NonNull @NotNull Activity activity) {
            }

            @Override
            public void onActivityStopped (@NonNull @NotNull Activity activity) {
                appCount--;
                if (appCount == 0) {
                    isRunInBackground = true;
                    LogUtil.d("taskFlow---应用进入后台");
                }
            }

            @Override
            public void onActivitySaveInstanceState (@NonNull @NotNull Activity activity, @NonNull @NotNull Bundle outState) {
            }

            @Override
            public void onActivityDestroyed (@NonNull @NotNull Activity activity) {
            }
        });
    }
}
