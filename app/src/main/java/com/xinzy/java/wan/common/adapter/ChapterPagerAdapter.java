package com.xinzy.java.wan.common.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.xinzy.java.wan.entity.Chapter;

import java.util.List;

public class ChapterPagerAdapter extends FragmentPagerAdapter {

    private List<Chapter> mChapters;
    private CreateItemPagerListener mItemPagerListener;

    public ChapterPagerAdapter(List<Chapter> chapters, FragmentManager fm, CreateItemPagerListener l) {
        super(fm);

        this.mChapters = chapters;
        this.mItemPagerListener = l;
    }

    @Override
    public Fragment getItem(int position) {
        return mItemPagerListener.create(mChapters.get(position));
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

    public interface CreateItemPagerListener {
        Fragment create(Chapter chapter);
    }
}
