package com.xinzy.mvvm.lib.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.annotation.ViewModelId;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<VDB extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {

    protected VDB mDataBinding;
    protected VM mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding = DataBindingUtil.setContentView(this, getLayout());

        mViewModel = createViewModel();
        if (mViewModel != null) {
            bindViewModel();
            getLifecycle().addObserver(mViewModel);
            onViewDataBinding(mDataBinding, mViewModel);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mViewModel != null) {
            getLifecycle().removeObserver(mViewModel);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    /**
     * 绑定ViewModel。
     * 1. 如果ViewModel为空，则什么也不做
     * 2. 判断ViewModelID，如果设置则通过ViewModelID来设置，
     * 3. 通过反射setViewModel方法来设置
     * 子类可自己重写
     */
    private void bindViewModel() {
        mViewModel.setLifecycleOwner(this);
        int viewModelId = getViewModelIdInternal();
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

    private int getViewModelIdInternal() {
        int id = getViewModelId();
        if (id >= 0) return id;
        Class<?> clazz = getClass();
        if (clazz.isAnnotationPresent(ViewModelId.class)) {
            ViewModelId viewModelId = clazz.getAnnotation(ViewModelId.class);
            if (viewModelId != null && viewModelId.value() >= 0) return viewModelId.value();
        }
        return -1;
    }

    /**
     * 创建ViewModel
     * 会根据第二个泛型类型自动创建，如果没有设置泛型，则默认创建BaseViewModel
     * @return
     */
    @Nullable
    private VM createViewModel() {
        VM vm = onCreateViewModel();
        if (vm != null) {
            return vm;
        }
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

    /**
     * 当前页面的布局
     * @return
     */
    protected int getLayoutId() {
        return -1;
    }
}
