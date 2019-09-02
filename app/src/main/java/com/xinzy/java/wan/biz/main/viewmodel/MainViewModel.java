package com.xinzy.java.wan.biz.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.xinzy.java.wan.biz.main.model.MainModel;
import com.xinzy.mvvm.lib.base.BaseViewModel;

public class MainViewModel extends BaseViewModel<MainModel> {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }


}
