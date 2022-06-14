package com.my.common.base;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewbinding.ViewBinding;

import com.my.common.log4j.Log4jConfigure;
import com.my.common.utils.LogUtil;
import com.my.common.utils.ToastUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zhanglong
 * @date 2020/7/24
 * @description Activity抽取的基类
 */
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity implements View.OnSystemUiVisibilityChangeListener {
    protected T binding;
    protected String TAG;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();
        LogUtil.d(TAG + "_onCreate");
        BaseApplication.getInstance().addActivtyty(this);
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(this);
        setContentViewBefore();
        initViewBinding();

        long start = System.currentTimeMillis();
        initView();
        initPresenter();
        initData();
        //击空白位置 隐藏软键盘
        binding.getRoot().setOnTouchListener((v, event) -> {
            hideInputMethod();
            return true;
        });
        LogUtil.d(TAG + "_执行耗时 --> " + (System.currentTimeMillis() - start));
    }

    public void hideInputMethod () {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * setContentViewBefore
     */
    protected void setContentViewBefore () {
    }

    /**
     * 创建和注册Presenter
     */
    protected abstract void initPresenter ();

    /**
     * 初始化数据
     */
    protected abstract void initData ();

    /**
     * 初始化视图
     */
    protected abstract void initView ();

    public void initViewBinding () {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        try {
            Type superclass = getClass().getGenericSuperclass();
            Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (T) method.invoke(null, getLayoutInflater());
            setContentView(binding.getRoot());
        } catch (NoSuchMethodException e1) {
            LogUtil.d("---NoSuchMethodException:" + e1.getMessage());
        } catch (IllegalAccessException e2) {
            LogUtil.d("---IllegalAccessException:" + e2.getLocalizedMessage());
        } catch (InvocationTargetException e3) {
            LogUtil.d("---InvocationTargetException:" + e3.getMessage());
        } catch (NullPointerException e4) {
            LogUtil.d("---NullPointerException:" + e4.getMessage());
        }
    }

    @Override
    protected void onResume () {
        LogUtil.d(TAG + "onResume");
        super.onResume();
        BaseApplication.getInstance().setCurrentActivity(this);
    }

    @Override
    protected void onPause () {
        super.onPause();
        LogUtil.d(TAG + "onPause");
    }

    @Override
    protected void onStop () {
        LogUtil.d(TAG + "---onStop:");
        super.onStop();
    }

    @Override
    protected void onDestroy () {
        LogUtil.d(TAG + "---onDestroy:");
        BaseApplication.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void onSystemUiVisibilityChange (int visibility) {
        LogUtil.d("onSystemUiVisibilityChange: visibility=" + visibility);
    }

    @Override
    protected void onStart () {
        super.onStart();
        LogUtil.d(TAG + "onStart");
    }
}
