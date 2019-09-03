package com.xinzy.java.wan.biz.main.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.xinzy.java.wan.api.WanApiModel;
import com.xinzy.java.wan.entity.Chapter;
import com.xinzy.mvvm.lib.base.BaseViewModel;

import java.util.List;

public class ProjectViewModel extends BaseViewModel<WanApiModel> {

    public MutableLiveData<List<Chapter>> projectChapters = new MutableLiveData<>();

    public ProjectViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        start();
    }

    @SuppressLint("CheckResult")
    private void start() {
        mModel.projectChapters().subscribe(projectChapters::postValue);
    }
}
