package com.xinzy.mvvm.lib.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.xinzy.mvvm.lib.util.L;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseViewModel<M extends BaseModel> extends AndroidViewModel implements LifecycleObserver {

    protected M mModel;
    protected LifecycleOwner mLifecycleOwner;

    public BaseViewModel(@NonNull Application application) {
        super(application);

        mModel = createModel();
    }

    private M createModel() {
        M m = onCreateModel();
        if (m != null) return m;

        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Class<M> modelClass = (Class<M>) ((ParameterizedType) type).getActualTypeArguments()[0];
            try {
                return modelClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                try {
                    return modelClass.getConstructor(Context.class).newInstance(getApplication());
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    @Nullable
    protected M onCreateModel() {
        return null;
    }

    void setLifecycleOwner(LifecycleOwner owner) {
        this.mLifecycleOwner = owner;
    }

    @CallSuper
    @Override
    public void onCleared() {
        if (mModel != null) {
            mModel.onCleared();
        }
        mLifecycleOwner = null;
        L.v("ViewModel onCleared");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        L.v("ViewModel lifecycle onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        L.v("ViewModel lifecycle onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        L.v("ViewModel lifecycle onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        L.v("ViewModel lifecycle onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        L.v("ViewModel lifecycle onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        L.v("ViewModel lifecycle onDestroy");
    }
}
