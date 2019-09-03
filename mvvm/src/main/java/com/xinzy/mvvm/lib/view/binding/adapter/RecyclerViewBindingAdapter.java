package com.xinzy.mvvm.lib.view.binding.adapter;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.xinzy.mvvm.lib.view.binding.command.BindingAction;
import com.xinzy.mvvm.lib.view.widget.MultiAdapter;

import java.util.List;

public class RecyclerViewBindingAdapter {

    @BindingAdapter(value = {"layoutManager", "adapter", "onItemClickListener"},
            requireAll = false)
    public static void setRecyclerViewAdapter(RecyclerView view, LayoutManagerFactory factory, MultiAdapter adapter,
                                              MultiAdapter.OnItemClickListener listener) {

        view.setLayoutManager(factory.create(view));
        view.setAdapter(adapter);

        if (adapter != null) {
            adapter.setOnItemClickListener(listener);
        }
    }

    @BindingAdapter("data")
    public static void setData(RecyclerView view, List data) {
        view.post(() -> {
            RecyclerView.Adapter adapter = view.getAdapter();
            if (adapter instanceof MultiAdapter) {
                ((MultiAdapter) adapter).replace((List<?>) data);
            }
        });
    }

    @BindingAdapter("onScrollToEndAction")
    public static void setRecyclerViewScrollToBottom(RecyclerView view, BindingAction action) {
        view.addOnScrollListener(new OnRecyclerViewScrollListener(action));
    }

    private static class OnRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

        private BindingAction mScrollToEndAction;

        public OnRecyclerViewScrollListener(BindingAction end) {
            this.mScrollToEndAction = end;
        }

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState != RecyclerView.SCROLL_STATE_IDLE) return;

            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

            if (manager instanceof LinearLayoutManager) {
                LinearLayoutManager lManager = (LinearLayoutManager) manager;
                if (lManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    if (!recyclerView.canScrollHorizontally(1)) {
                        if (mScrollToEndAction != null) mScrollToEndAction.call();
                    }
                } else {
                    if (!recyclerView.canScrollVertically(1)) {
                        if (mScrollToEndAction != null) mScrollToEndAction.call();
                    }
                }
            }
        }
    }

    public static class LayoutManagers {
        public static LayoutManagerFactory linear() {
            return view -> new LinearLayoutManager(view.getContext());
        }

        public static LayoutManagerFactory linear(@RecyclerView.Orientation final int orientation, final boolean reverse) {
            return view -> new LinearLayoutManager(view.getContext(), orientation, reverse);
        }

        public static LayoutManagerFactory grid(final int spanCount) {
            return view -> new GridLayoutManager(view.getContext(), spanCount);
        }

        public static LayoutManagerFactory grid(final int spanCount, @RecyclerView.Orientation final int orientation,
                                                final boolean reverse) {
            return view -> new GridLayoutManager(view.getContext(), spanCount, orientation, reverse);
        }

        public static LayoutManagerFactory flexbox() {
            return flexbox(FlexDirection.ROW, FlexWrap.WRAP);
        }

        public static LayoutManagerFactory flexbox(@FlexDirection int direction, @FlexWrap int wrap) {
            return flexbox(direction, wrap, AlignItems.CENTER);
        }

        public static LayoutManagerFactory flexbox(@FlexDirection int direction, @FlexWrap int wrap,
                                                   @AlignItems int alignItems) {
            return view -> {
                FlexboxLayoutManager manager = new FlexboxLayoutManager(view.getContext(), direction, wrap);
                manager.setAlignItems(alignItems);

                return manager;
            };
        }

    }

    public interface LayoutManagerFactory {
        RecyclerView.LayoutManager create(RecyclerView view);
    }
}
