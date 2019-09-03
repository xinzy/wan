package com.xinzy.java.wan.biz.web;

import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xinzy.java.wan.R;
import com.xinzy.java.wan.biz.web.viewmodel.WebViewViewModel;
import com.xinzy.java.wan.databinding.ActivityWebViewBinding;
import com.xinzy.java.wan.util.Macro;
import com.xinzy.mvvm.lib.annotation.Layout;
import com.xinzy.mvvm.lib.base.BaseActivity;

import static com.xinzy.java.wan.util.Macro.ROUTER_WEB;

@Route(path = ROUTER_WEB)
@Layout(R.layout.activity_web_view)
public class WebViewActivity extends BaseActivity<ActivityWebViewBinding, WebViewViewModel> {

    @Autowired(name = Macro.KEY_URL)
    protected String mUrl;
    @Autowired(name = Macro.KEY_TITLE)
    protected String mTitle;

    private WebView mWebView;
    private ActionBar mActionBar;

    @Override
    protected void onViewDataBinding(@NonNull ActivityWebViewBinding dataBinding, @NonNull WebViewViewModel viewModel) {
        ARouter.getInstance().inject(this);

        setSupportActionBar(dataBinding.toolBar);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(mTitle);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mWebView = dataBinding.webView;
        mWebView.setWebChromeClient(viewModel.getWebChromeClient());
        mWebView.setWebViewClient(viewModel.getWebViewClient());

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);

        mWebView.loadUrl(mUrl);

        initAction();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAction() {
        mViewModel.receiverTitleAction.observe(this, title -> mActionBar.setTitle(title));
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
