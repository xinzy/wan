package com.xinzy.mvvm.lib.view.binding.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;

public class ImageViewBindingAdapter {

    @BindingAdapter(value = {"url", "placeholder", "error"}, requireAll = false)
    public static void loadImage(ImageView view, String url, Drawable placeholder, Drawable error) {
        RequestBuilder builder = Glide.with(view).load(url);
        if (placeholder != null) builder.placeholder(placeholder);
        if (error != null) builder.error(error);
        builder.into(view);
    }
}
