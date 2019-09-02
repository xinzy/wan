package com.xinzy.java.wan.biz.main.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.main.viewmodel.MineViewModel;
import com.xinzy.java.wan.databinding.FragmentMineBinding;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseFragment;

import java.util.Random;

@Layout(R.layout.fragment_mine)
public class MineFragment extends BaseFragment<FragmentMineBinding, MineViewModel> {

    public static MineFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected void onViewDataBinding(@NonNull FragmentMineBinding dataBinding, @NonNull MineViewModel viewModel) {
        dataBinding.testBtn.setOnClickListener(v -> {
            int count = new Random().nextInt(100) % 3;

            switch (count) {
                case 0:
                    dataBinding.statusView.showData();
                    break;
                case 1:
                    dataBinding.statusView.showEmpty();
                    break;
                case 2:
                    dataBinding.statusView.showError();
                    break;
            }
        });
    }
}