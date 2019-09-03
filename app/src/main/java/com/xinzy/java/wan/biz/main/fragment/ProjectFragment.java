package com.xinzy.java.wan.biz.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.main.viewmodel.ProjectViewModel;
import com.xinzy.java.wan.common.adapter.ChapterPagerAdapter;
import com.xinzy.java.wan.databinding.FragmentProjectBinding;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseFragment;

@Layout(R.layout.fragment_project)
public class ProjectFragment extends BaseFragment<FragmentProjectBinding, ProjectViewModel> {

    public static ProjectFragment newInstance() {
        Bundle args = new Bundle();
        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onViewDataBinding(@NonNull FragmentProjectBinding dataBinding, @NonNull ProjectViewModel viewModel) {
        viewModel.projectChapters.observe(this, chapters -> {

            ChapterPagerAdapter adapter = new ChapterPagerAdapter(chapters, getChildFragmentManager(),
                    ProjectTopicFragment::newInstance);
            dataBinding.viewPager.setAdapter(adapter);
            dataBinding.tabs.setupWithViewPager(dataBinding.viewPager);
        });
    }
}