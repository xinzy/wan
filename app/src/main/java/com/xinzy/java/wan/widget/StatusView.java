package com.xinzy.java.wan.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.xinzy.mvvm.lib.view.binding.command.BindingAction;

public class StatusView extends ViewFlipper implements View.OnClickListener {
    private EmptyView mEmptyView;
    private ErrorView mErrorView;

    private OnRetryListener mOnRetryListener;

    public StatusView(Context context) {
        super(context);
        init(context);
    }

    public StatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mErrorView = new ErrorView(context);
        mEmptyView = new EmptyView(context);

        mErrorView.setOnClickListener(this);
    }

    public void setOnRetryListener(OnRetryListener l) {
        this.mOnRetryListener = l;
    }

    @Override
    public void onClick(View v) {
        if (v == mErrorView) {
            if (mOnRetryListener != null) {
                mOnRetryListener.onRetry(this);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 1) {
            throw new IllegalStateException("StatusView must have only on child view");
        }

        addView(mEmptyView);
        addView(mErrorView);
    }

    public void showData() {
        setDisplayedChild(0);
    }

    public void showEmpty() {
        setDisplayedChild(1);
    }

    public void showError() {
        setDisplayedChild(2);
    }

    @BindingAdapter("onRetryAction")
    public static void setRetryListener(@NonNull StatusView view, @NonNull BindingAction action) {
        view.setOnRetryListener(v -> action.call());
    }

    @BindingAdapter("status")
    public static void setStatus(@NonNull StatusView view, @NonNull Status status) {
        view.setDisplayedChild(status.getIndex());
    }

    public enum Status {
        Normal(0),
        Empty(1),
        Error(2);

        private int index;

        Status(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    public interface OnRetryListener {
        void onRetry(StatusView view);
    }
}
