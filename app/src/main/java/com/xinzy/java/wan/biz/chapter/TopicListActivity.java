package com.xinzy.java.wan.biz.chapter;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.chapter.fragment.TopicsFragment;
import com.xinzy.java.wan.biz.chapter.viewmodel.TopicListViewModel;
import com.xinzy.java.wan.common.adapter.ChapterPagerAdapter;
import com.xinzy.java.wan.databinding.ActivityTopicListBinding;
import com.xinzy.java.wan.entity.Chapter;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseActivity;

import static com.xinzy.java.wan.util.Macro.KEY_CHAPTER;
import static com.xinzy.java.wan.util.Macro.ROUTER_TOPICS;

@Route(path = ROUTER_TOPICS)
@Layout(R.layout.activity_topic_list)
public class TopicListActivity extends BaseActivity<ActivityTopicListBinding, TopicListViewModel> {

    @Autowired(name = KEY_CHAPTER, required = true)
    protected Chapter mChapter;

    private ActionBar mActionBar;

    @Override
    protected void onViewDataBinding(@NonNull ActivityTopicListBinding dataBinding, @NonNull TopicListViewModel viewModel) {
        ARouter.getInstance().inject(this);

        setSupportActionBar(dataBinding.toolBar);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(mChapter.getName());
        mActionBar.setDisplayHomeAsUpEnabled(true);

        ChapterPagerAdapter adapter = new ChapterPagerAdapter(mChapter.getChildren(),
                getSupportFragmentManager(), TopicsFragment::newInstance);
        dataBinding.viewPager.setAdapter(adapter);
        dataBinding.tabs.setupWithViewPager(dataBinding.viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}