package com.xinzy.mvvm.lib.view.binding.adapter;

import androidx.databinding.BindingAdapter;

import com.xinzy.mvvm.lib.view.widget.TagView;

import java.util.List;

public class TagViewBindingAdapter {

    @BindingAdapter("tags")
    public static void setTags(TagView tagView, List<String> tags) {
        tagView.setTags(tags);
    }
}
