package com.xinzy.java.wan.biz.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.main.viewmodel.WeixinViewModel;
import com.xinzy.java.wan.common.adapter.ChapterPagerAdapter;
import com.xinzy.java.wan.databinding.FragmentWeixinBinding;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseFragment;

@Layout(R.layout.fragment_weixin)
public class WeixinFragment extends BaseFragment<FragmentWeixinBinding, WeixinViewModel> {

    public static WeixinFragment newInstance() {
        Bundle args = new Bundle();
        WeixinFragment fragment = new WeixinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onViewDataBinding(@NonNull FragmentWeixinBinding dataBinding, @NonNull WeixinViewModel viewModel) {
        viewModel.weixinListEvent.observe(this, list -> {

            ChapterPagerAdapter adapter = new ChapterPagerAdapter(list, getChildFragmentManager(),
                    WeixinTopicFragment::newInstance);
            dataBinding.viewPager.setAdapter(adapter);
            dataBinding.tabs.setupWithViewPager(dataBinding.viewPager);
        });
    }
}