package com.xinzy.java.wan.biz.main.fragment;

import android.os.Bundle;

import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.main.viewmodel.KnowledgeViewModel;
import com.xinzy.java.wan.databinding.FragmentKnowledgeBinding;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseFragment;

@Layout(R.layout.fragment_knowledge)
public class KnowledgeFragment extends BaseFragment<FragmentKnowledgeBinding, KnowledgeViewModel> {

    public static KnowledgeFragment newInstance() {
        Bundle args = new Bundle();
        KnowledgeFragment fragment = new KnowledgeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}