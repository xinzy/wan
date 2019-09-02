package com.xinzy.mvvm.lib.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.xinzy.mvvm.lib.annotation.Layout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<VDB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {

    protected VDB mDataBinding;
    protected VM mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel = createViewModel();

        if (mViewModel != null) {
            bindViewModel();
            getLifecycle().addObserver(mViewModel);
            onViewDataBinding(mDataBinding, mViewModel);
        }
    }

    /**
     * 绑定ViewModel。
     * 1. 如果ViewModel为空，则什么也不做
     * 2. 判断ViewModelID，如果设置则通过ViewModelID来设置，
     * 3. 通过反射setViewModel方法来设置
     * 子类可自己重写
     */
    protected void bindViewModel() {

        mViewModel.setLifecycleOwner(this);
        int viewModelId = getViewModelId();
        if (viewModelId >= 0) {
            mDataBinding.setVariable(viewModelId, mViewModel);
        } else {
            try {
                Method method = mDataBinding.getClass().getDeclaredMethod("setViewModel", mViewModel.getClass());
                method.invoke(mDataBinding, mViewModel);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建ViewModel
     * 会根据第二个泛型类型自动创建，如果没有设置泛型，则默认创建BaseViewModel
     * @return
     */
    @Nullable
    private VM createViewModel() {
        VM vm = onCreateViewModel();
        if (vm != null) return vm;

        Class modelClass;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
        } else {
            modelClass = BaseViewModel.class;
        }
        return (VM) ViewModelProviders.of(this).get(modelClass);
    }

    /**
     * 创建ViewModel
     */
    @Nullable
    protected VM onCreateViewModel() {
        return null;
    }

    /**
     * 设置DataBinding和ViewModel
     */
    protected void onViewDataBinding(@NonNull VDB dataBinding, @NonNull VM viewModel) {}

    /**
     * 获取ViewModel在页面上的id
     * @return
     */
    protected int getViewModelId() {
        return -1;
    }

    protected int getLayoutId() {
        return -1;
    }

    /**
     * 获取layout
     *
     * 可以通过重写getLayoutId()或者给类增加Layout注解
     */
    private int getLayout() {
        int id = getLayoutId();
        if (id != -1) return id;

        Class<?> clazz = getClass();
        if (clazz.isAnnotationPresent(Layout.class)) {
            Layout layout = clazz.getAnnotation(Layout.class);
            if (layout != null) {
                return layout.value();
            }
        }
        throw new IllegalStateException("must override method getLayoutId or set annotation `Layout`");
    }
}
