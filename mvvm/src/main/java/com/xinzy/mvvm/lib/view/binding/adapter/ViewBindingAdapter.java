package com.xinzy.mvvm.lib.view.binding.adapter;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.xinzy.mvvm.lib.view.binding.command.BindingAction;

public class ViewBindingAdapter {

    @BindingAdapter(value = "onClickAction")
    public static void setOnClickAction(View view, BindingAction action) {
        view.setOnClickListener(v -> action.call());
    }
}
