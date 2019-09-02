package com.xinzy.mvvm.lib.view.binding.adapter;

import android.widget.ProgressBar;

import androidx.databinding.BindingAdapter;

public class ProgressBarBindingAdapter {

    @BindingAdapter("android:progress")
    public static void setProgress(ProgressBar bar, int progress) {
        bar.setProgress(progress);
    }
}
