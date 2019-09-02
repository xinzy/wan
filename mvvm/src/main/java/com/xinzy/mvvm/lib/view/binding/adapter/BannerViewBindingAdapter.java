package com.xinzy.mvvm.lib.view.binding.adapter;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ViewDataBinding;

import com.xinzy.mvvm.lib.view.widget.BannerView;

import java.util.List;

public class BannerViewBindingAdapter {

    @BindingAdapter("autoStart")
    public static void setAutoFlip(BannerView view, boolean auto) {
        view.setAutoStart(auto);
    }

    @BindingAdapter("onItemClick")
    public static void setOnItemClickListener(BannerView banner, BannerView.OnItemClickListener listener) {
        banner.setOnItemClickListener(listener);
    }

    @BindingAdapter("onItemSelected")
    public static void setOnItemSelectedListener(BannerView banner, BannerView.OnItemSelectedListener l) {
        banner.setOnItemSelectedListener(l);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(BannerView banner, BannerView.Adapter adapter) {
        banner.setAdapter(adapter);
    }

    @BindingAdapter(value = {"data", "layoutId"})
    public static void setAdater(BannerView banner, List<Object> data, int layoutId) {
        banner.setAdapter(new BannerView.Adapter() {
            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int position) {
                return data.get(position);
            }

            @Override
            public void convert(ViewDataBinding binding, int position) {
                Object item = data.get(position);
                binding.setVariable(com.xinzy.mvvm.lib.BR.item, item);
                binding.setVariable(com.xinzy.mvvm.lib.BR.position, position);
            }

            @Override
            public int getLayout() {
                return layoutId;
            }
        });
    }
}
