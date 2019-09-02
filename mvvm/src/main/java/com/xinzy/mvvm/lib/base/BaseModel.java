package com.xinzy.mvvm.lib.base;

import androidx.databinding.BaseObservable;

import com.xinzy.mvvm.lib.network.HttpServices;

public class BaseModel extends BaseObservable {

    protected <I> I getService(Class<I> clazz) {
        return HttpServices.getService(clazz);
    }

    protected void onCleared() { }
}
