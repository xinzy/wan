package com.xinzy.mvvm.lib.view.widget;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class BannerView extends FrameLayout implements ViewPager.OnPageChangeListener, Runnable {
    private static final int INDICATION_SIZE = 6;

    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;

    private Adapter mAdapter;
    private InnerAdapter mPagerAdapter = new InnerAdapter();

    private boolean mAutoStart = true;
    private OnItemClickListener mOnItemClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;

    private Handler mHandler = new Handler();

    public BannerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mViewPager = new ViewPager(context);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        addView(mViewPager, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mLinearLayout.setGravity(Gravity.CENTER);

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM;
        lp.bottomMargin = dp2px(12);

        addView(mLinearLayout, lp);
    }

    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
        mViewPager.removeAllViews();
        mPagerAdapter.notifyDataSetChanged();

        createIndication();
        if (mAutoStart) startFlip();
    }

    public void startFlip() {
        mHandler.removeCallbacks(this);
        if (mAutoStart && mAdapter != null && mAdapter.getCount() > 0) {
            mHandler.postDelayed(this, 3000);
        }
    }

    public void stopFlip() {
        mHandler.removeCallbacks(this);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener l) {
        this.mOnItemSelectedListener = l;
    }

    public void setAutoStart(boolean autoStart) {
        this.mAutoStart = autoStart;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        int count = mLinearLayout.getChildCount();
        int rawPosition = position % count;
        for (int i = 0; i < count; i++) {
            mLinearLayout.getChildAt(i).setSelected(rawPosition == i);
        }

        if (mOnItemSelectedListener != null) {
            mOnItemSelectedListener.onItemSelected(this, rawPosition);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) stopFlip();
        else if (state == ViewPager.SCROLL_STATE_IDLE) startFlip();
    }

    @Override
    public void run() {
        int position = mViewPager.getCurrentItem();
        if (position == mAdapter.getCount() - 1) {
            mViewPager.setCurrentItem(0);
        } else {
            mViewPager.setCurrentItem(position + 1);
        }
    }

    private View indication(boolean isSelected) {
        View view = new View(getContext());

        StateListDrawable drawable = new StateListDrawable();

        GradientDrawable selectedDrawable = new GradientDrawable();
        selectedDrawable.setColor(0xFFFFFFFF);
        selectedDrawable.setCornerRadius(dp2px(INDICATION_SIZE));

        GradientDrawable defaultDrawable = new GradientDrawable();
        defaultDrawable.setColor(0xFFCCCCCC);
        defaultDrawable.setCornerRadius(dp2px(INDICATION_SIZE));

        drawable.addState(new int[] {android.R.attr.state_selected}, selectedDrawable);
        drawable.addState(new int[]{},  defaultDrawable);

        view.setBackground(drawable);
        view.setSelected(isSelected);

        return view;
    }

    private void createIndication() {
        mLinearLayout.removeAllViews();
        if (mAdapter == null) return;

        int size = dp2px(6);
        for (int i = 0; i < mAdapter.getCount(); i++) {
            MarginLayoutParams lp = new MarginLayoutParams(size, size);
            lp.leftMargin = size;
            lp.rightMargin = size;
            mLinearLayout.addView(indication(i == 0), lp);
        }
    }

    private int dp2px(int dp) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dp + .5f);
    }

    private class InnerAdapter extends PagerAdapter {
        private LayoutInflater mInflater;

        @Override
        public int getCount() {
            return mAdapter == null ? 0 : mAdapter.getCount();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (mAdapter == null) return new Object();
            if (mInflater == null) mInflater = LayoutInflater.from(container.getContext());

            int rawPosition = position % mAdapter.getCount();
            ViewDataBinding dataBinding = DataBindingUtil.inflate(mInflater, mAdapter.getLayout(), container, false);
            mAdapter.convert(dataBinding, rawPosition);

            View view = dataBinding.getRoot();
            view.setOnClickListener(new ItemClick(rawPosition, mAdapter.getItem(rawPosition)));
            container.addView(view);

            dataBinding.executePendingBindings();

            return view;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) { }
    }

    private class ItemClick implements OnClickListener {
        private int mPosition;
        private Object mItem;

        public ItemClick(int position, Object item) {
            this.mPosition = position;
            this.mItem = item;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(BannerView.this, mItem, mPosition);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(BannerView banner, Object item, int position);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(BannerView banner, int position);
    }

    public interface Adapter {
        int getCount();
        Object getItem(int position);
        void convert(ViewDataBinding dataBinding, int position);
        int getLayout();
    }
}
