package com.xinzy.java.wan.biz.main.fragment;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.main.viewmodel.WeixinViewModel;
import com.xinzy.java.wan.databinding.FragmentWeixinBinding;
import com.xinzy.java.wan.entity.Chapter;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseFragment;

import java.util.List;

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
            SectionsPagerAdapter adapter = new SectionsPagerAdapter(list, getChildFragmentManager());
            dataBinding.viewPager.setAdapter(adapter);
            dataBinding.tabs.setupWithViewPager(dataBinding.viewPager);
        });
    }

    private static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Chapter> mChapters;

        SectionsPagerAdapter(List<Chapter> chapters, FragmentManager fm) {
            super(fm);
            mChapters = chapters;
        }

        @Override
        public Fragment getItem(int position) {
            return WeixinTopicFragment.newInstance(mChapters.get(position));
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mChapters.get(position).getName();
        }

        @Override
        public int getCount() {
            return mChapters == null ? 0 : mChapters.size();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        }
    }
}