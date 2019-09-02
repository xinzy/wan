package com.xinzy.java.wan.biz.main.fragment;

import android.os.Bundle;

import com.xinzy.java.wan.R;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseFragment;

@Layout(R.layout.fragment_project)
public class ProjectFragment extends BaseFragment {

    public static ProjectFragment newInstance() {
        Bundle args = new Bundle();
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }
}