package com.xinzy.mvvm.lib.view;

import androidx.annotation.Keep;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.xinzy.mvvm.lib.base.BaseViewModel;

@Keep
class Br extends BaseObservable {
    private Object item;
    private int position;
    private BaseViewModel viewModel;

    @Bindable
    public Object getItem() {
        return null;
    }

    @Bindable
    public int getPosition() {
        return 0;
    }

    @Bindable
    public BaseViewModel getViewModel() {
        return viewModel;
    }
}
