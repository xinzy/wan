package com.xinzy.java.wan.biz.main.fragment;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.main.viewmodel.WeixinTopicViewModel;
import com.xinzy.java.wan.databinding.FragmentWeixinTopicBinding;
import com.xinzy.java.wan.entity.Chapter;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseFragment;

import static com.xinzy.java.wan.util.Macro.KEY_CHAPTER;

@Layout(R.layout.fragment_weixin_topic)
public class WeixinTopicFragment extends BaseFragment<FragmentWeixinTopicBinding, WeixinTopicViewModel> {

    private Chapter mChapter;

    public static WeixinTopicFragment newInstance(Chapter chapter) {

        Bundle args = new Bundle();
        args.putParcelable(KEY_CHAPTER, chapter);

        WeixinTopicFragment fragment = new WeixinTopicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle args = getArguments();
        if (args != null) {
            mChapter = args.getParcelable(KEY_CHAPTER);
        }
    }

    @Nullable
    @Override
    protected WeixinTopicViewModel onCreateViewModel() {
        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                try {
                    return modelClass.getConstructor(Application.class, Chapter.class)
                            .newInstance(getContext().getApplicationContext(), mChapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }).get(WeixinTopicViewModel.class);
    }
}
