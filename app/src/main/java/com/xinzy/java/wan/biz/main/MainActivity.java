package com.xinzy.java.wan.biz.main;

import android.os.SystemClock;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.main.fragment.HomeFragment;
import com.xinzy.java.wan.biz.main.fragment.KnowledgeFragment;
import com.xinzy.java.wan.biz.main.fragment.MineFragment;
import com.xinzy.java.wan.biz.main.fragment.ProjectFragment;
import com.xinzy.java.wan.biz.main.fragment.WeixinFragment;
import com.xinzy.java.wan.biz.main.viewmodel.MainViewModel;
import com.xinzy.java.wan.databinding.ActivityMainBinding;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseActivity;
import com.xinzy.mvvm.lib.base.BaseFragment;
import com.xinzy.mvvm.lib.util.L;

import static com.xinzy.java.wan.util.Macro.ROUTER_MAIN;

@Route(path = ROUTER_MAIN)
@Layout(R.layout.activity_main)
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BaseFragment mSelectedFragment;

    private HomeFragment mHomeFragment = HomeFragment.newInstance();
    private KnowledgeFragment mKnowledgeFragment = KnowledgeFragment.newInstance();
    private WeixinFragment mWeixinFragment = WeixinFragment.newInstance();
    private ProjectFragment mProjectFragment = ProjectFragment.newInstance();
    private MineFragment mMineFragment = MineFragment.newInstance();

    private long mLastPressedBackTimestamp;

    @Override
    protected void onViewDataBinding(@NonNull ActivityMainBinding dataBinding, @NonNull MainViewModel viewModel) {
        dataBinding.navView.setOnNavigationItemSelectedListener(this);
        dataBinding.navView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void onBackPressed() {
        long timestamp = SystemClock.uptimeMillis();
        if (timestamp - mLastPressedBackTimestamp < 2000) {
            super.onBackPressed();
        } else {
            mLastPressedBackTimestamp = timestamp;
            Snackbar.make(mDataBinding.container, "再按一次退出", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        L.d("item selected: " + item.getTitle());

        setTitle(item.getTitle());
        switch (item.getItemId()) {
            case R.id.navigation_home:
                show(mHomeFragment);
                break;

            case R.id.navigation_knowledge:
                show(mKnowledgeFragment);
                break;

            case R.id.navigation_weixin:
                show(mWeixinFragment);
                break;

            case R.id.navigation_project:
                show(mProjectFragment);
                break;

            case R.id.navigation_mine:
                show(mMineFragment);
                break;
        }

        return true;
    }

    private void show(BaseFragment fragment) {
        if (fragment == mSelectedFragment) return;

        String tag =  fragment.getClass().getSimpleName();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mSelectedFragment == null) {
            transaction.add(R.id.fragmentContainer, fragment, fragment.getClass().getSimpleName())
                    .commit();
        } else {
            if (getSupportFragmentManager().findFragmentByTag(tag) != null) {
                getSupportFragmentManager().beginTransaction().show(fragment)
                        .hide(mSelectedFragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().hide(mSelectedFragment)
                        .add(R.id.fragmentContainer, fragment, fragment.getClass().getSimpleName())
                        .commit();
            }
        }
        mSelectedFragment = fragment;
    }
}
