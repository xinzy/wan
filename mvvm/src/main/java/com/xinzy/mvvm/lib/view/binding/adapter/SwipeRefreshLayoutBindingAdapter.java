package com.xinzy.mvvm.lib.view.binding.adapter;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.xinzy.mvvm.lib.view.binding.command.BindingAction;

public class SwipeRefreshLayoutBindingAdapter {

    @BindingAdapter("isRefreshing")
    public static void showRefresh(SwipeRefreshLayout layout, boolean refresh) {
        layout.setRefreshing(refresh);
    }

    @BindingAdapter("onRefreshAction")
    public static void setOnRefreshListener(SwipeRefreshLayout layout, BindingAction action) {
        layout.setOnRefreshListener(action::call);
    }
}
