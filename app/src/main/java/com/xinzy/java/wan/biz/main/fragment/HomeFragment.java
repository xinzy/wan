package com.xinzy.java.wan.biz.main.fragment;

import android.os.Bundle;

import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.main.viewmodel.HomeViewModel;
import com.xinzy.java.wan.databinding.FragmentHomeBinding;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseFragment;

@Layout(R.layout.fragment_home)
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}