package com.xinzy.java.wan.biz.chapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.chapter.fragment.TopicsFragment;
import com.xinzy.java.wan.biz.chapter.viewmodel.TopicListViewModel;
import com.xinzy.java.wan.databinding.ActivityTopicListBinding;
import com.xinzy.java.wan.entity.Chapter;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseActivity;

import java.util.List;

import static com.xinzy.java.wan.util.Macro.KEY_CHAPTER;
import static com.xinzy.java.wan.util.Macro.ROUTER_TOPICS;

@Route(path = ROUTER_TOPICS)
@Layout(R.layout.activity_topic_list)
public class TopicListActivity extends BaseActivity<ActivityTopicListBinding, TopicListViewModel> {

    @Autowired(name = KEY_CHAPTER, required = true)
    protected Chapter mChapter;

    @Override
    protected void onViewDataBinding(@NonNull ActivityTopicListBinding dataBinding, @NonNull TopicListViewModel viewModel) {
        ARouter.getInstance().inject(this);

        setSupportActionBar(dataBinding.toolBar);
        mActionBar = getSupportActionBar();
        setTitle(mChapter.getName());
        setDisplayHomeAsUpEnabled(true);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(mChapter.getChildren(), getSupportFragmentManager());
        dataBinding.viewPager.setAdapter(adapter);
        dataBinding.tabs.setupWithViewPager(dataBinding.viewPager);
    }

    private static class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Chapter> mChapters;

        public SectionsPagerAdapter(List<Chapter> chapters, FragmentManager fm) {
            super(fm);
            mChapters = chapters;
        }

        @Override
        public Fragment getItem(int position) {
            return TopicsFragment.newInstance(mChapters.get(position));
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