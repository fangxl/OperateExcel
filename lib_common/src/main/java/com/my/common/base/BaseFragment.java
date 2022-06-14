package com.my.common.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.my.common.utils.LogUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Fragment基类
 *
 * @author zhanglong
 */
public abstract class BaseFragment<T extends ViewBinding> extends Fragment{
    protected String TAG;

    protected BaseFragment() {
    }

    protected T binding;
    public boolean hasInit = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
        return initViewBinding(container);
    }

    public View initViewBinding(ViewGroup container) {
        try {
            Type superclass = getClass().getGenericSuperclass();
            Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            binding = (T) method.invoke(null, getLayoutInflater(), container, false);
            return binding.getRoot();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NullPointerException e) {
            e.printStackTrace();
            LogUtil.d(TAG + "_exception---" + e.getMessage());
            return null;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initView(view);
        hasInit = true;
        initData();
        refreshData();
    }

    protected abstract void initPresenter();

    /**
     * 初始化视图
     *
     * @param view
     */
    protected abstract void initView(View view);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    //  切换后，再次切换回来时刷新ui
    public final void refreshHasInitData() {
        if (hasInit) {
            LogUtil.d(this.getClass().getSimpleName() + ".refreshData()");
            refreshData();
        }
    }

    /**
     * 初始化，或者再次切换回来时，需要初始化的数据
     */
    protected void refreshData() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.d(TAG + "---onHiddenChanged---hidden:" + hidden);
    }
}
