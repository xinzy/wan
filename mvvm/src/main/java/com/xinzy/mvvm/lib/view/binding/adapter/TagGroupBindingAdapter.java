package com.xinzy.mvvm.lib.view.binding.adapter;

import androidx.databinding.BindingAdapter;

import com.xinzy.mvvm.lib.view.binding.command.BindingConsumer;
import com.xinzy.mvvm.lib.view.widget.TagGroup;

import java.util.List;

public class TagGroupBindingAdapter {

    @BindingAdapter("tags")
    public static void setTags(TagGroup tagGroup, List<String> tags) {
        tagGroup.setTags(tags);
    }

    @BindingAdapter("onItemTagClickConsumer")
    public static void setItemTagClick(TagGroup group, BindingConsumer<String> consumer) {
        group.setOnTagClickListener(consumer::call);
    }
}
