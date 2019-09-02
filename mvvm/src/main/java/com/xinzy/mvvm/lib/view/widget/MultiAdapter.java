package com.xinzy.mvvm.lib.view.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.xinzy.mvvm.lib.BR;
import com.xinzy.mvvm.lib.base.BaseViewModel;
import com.xinzy.mvvm.lib.util.Collections;

import java.util.ArrayList;
import java.util.List;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {
    private List<Object> mItems = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener;
    private ItemBinding mItemBinding;

    public MultiAdapter() { }

    public MultiAdapter(ItemBinding binding) {
        this.mItemBinding = binding;
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = mItemBinding.createDataBinding(parent, viewType);
        return new MultiViewHolder(dataBinding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder holder, int position) {
        Object item = mItems.get(position);
        holder.bind(mItemBinding, item, position);
    }

    @Override
    public int getItemCount() {
        return mItemBinding.getItemCount(mItems);
    }

    @Override
    public int getItemViewType(int position) {
        return mItemBinding.getItemViewType(mItems.get(position));
    }

    public void setItemBinding(ItemBinding binding) {
        this.mItemBinding = binding;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.mOnItemClickListener = l;
    }

    public int indexOf(Object item) {
        return mItems.indexOf(item);
    }

    public void addAll(List<?> items) {
        if (items == null || items.isEmpty()) return;
        int size = mItems.size();
        mItems.addAll(items);
        notifyItemRangeInserted(size, items.size());
    }

    public void add(int position, Object item) {
        if (item == null) return;
        if (position > mItems.size()) return;
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void add(Object item) {
        if (item == null) return;
        int size = mItems.size();
        mItems.add(item);
        notifyItemInserted(size);
    }

    public void remove(int position) {
        if (position < 0) return;
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(Object item) {
        int position = indexOf(item);
        remove(position);
    }

    public void replace(List<?> items) {
        mItems.clear();
        if (Collections.isNotEmpty(items)) {
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        int size = getItemCount();
        if (size == 0) return;
        mItems.clear();
        notifyItemRangeRemoved(0, size);
    }

    public static MultiAdapter createAdapter(BaseViewModel viewModel, int itemLayoutId) {
        return new MultiAdapter(new ItemBinding(viewModel, itemLayoutId));
    }

    @FunctionalInterface
    public interface OnItemClickListener {
        void onItemClick(Object item, int position);
    }

    public static class ItemBinding {
        private LayoutInflater mLayoutInflater;
        private BaseViewModel mViewModel;
        private int mLayoutId;

        public ItemBinding() {
        }

        public ItemBinding(BaseViewModel viewModel) {
            this.mViewModel = viewModel;
        }

        public ItemBinding(BaseViewModel viewModel, int layoutId) {
            this.mViewModel = viewModel;
            this.mLayoutId = layoutId;
        }

        public ItemBinding(int layoutId) {
            this.mLayoutId = layoutId;
        }

        public int getItemViewType(Object object) {
            return mLayoutId;
        }

        private ViewDataBinding createDataBinding(@NonNull ViewGroup parent, int viewType) {
            if (mLayoutInflater == null) mLayoutInflater = LayoutInflater.from(parent.getContext());
            return onCreateDataBinding(parent, viewType);
        }

        public ViewDataBinding onCreateDataBinding(@NonNull ViewGroup parent, int viewType) {
            return DataBindingUtil.inflate(mLayoutInflater, viewType, parent, false);
        }

        private void bindItem(ViewDataBinding dataBinding, Object item, int position) {
            onBindItem(dataBinding, item, position);
            dataBinding.executePendingBindings();
        }

        @CallSuper
        public void onBindItem(ViewDataBinding dataBinding, Object item, int position) {
            dataBinding.setVariable(com.xinzy.mvvm.lib.BR.item, item);
            dataBinding.setVariable(com.xinzy.mvvm.lib.BR.position, position);
            dataBinding.setVariable(com.xinzy.mvvm.lib.BR.viewModel, getViewModel());
        }

        public int getItemCount(List<Object> items) {
            return items.size();
        }

        public ViewModel getViewModel() {
            return mViewModel;
        }
    }

    static final class MultiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ViewDataBinding mDataBinding;
        private OnItemClickListener mItemClickListener;

        private Object mItem;
        private int mPosition;

        MultiViewHolder(@NonNull ViewDataBinding binding,  OnItemClickListener listener) {
            super(binding.getRoot());

            mDataBinding = binding;
            mItemClickListener = listener;
            if (listener != null) {
                binding.getRoot().setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(mItem, mPosition);
            }
        }

        void bind(ItemBinding itemBinding, Object item, int position) {
            mItem = item;
            mPosition = position;
            itemBinding.bindItem(mDataBinding, item, position);
        }
    }
}
