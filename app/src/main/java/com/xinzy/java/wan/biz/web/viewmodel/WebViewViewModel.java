package com.xinzy.java.wan.biz.web.viewmodel;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableInt;

import com.xinzy.mvvm.lib.base.BaseViewModel;
import com.xinzy.mvvm.lib.event.SingleLiveEvent;
import com.xinzy.mvvm.lib.util.L;

public class WebViewViewModel extends BaseViewModel {

    public ObservableBoolean progressVisible = new ObservableBoolean(true);
    public ObservableInt progress = new ObservableInt();

    public SingleLiveEvent<String> receiverTitleAction = new SingleLiveEvent<>();

    private WebViewClient mWebViewClient = new ViewClient();
    private WebChromeClient mWebChromeClient = new ChromeClient();

    public WebViewViewModel(@NonNull Application application) {
        super(application);
    }

    public WebViewClient getWebViewClient() {
        return mWebViewClient;
    }

    public WebChromeClient getWebChromeClient() {
        return mWebChromeClient;
    }

    class ChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            receiverTitleAction.postValue(title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            progress.set(newProgress);
        }
    }

    class ViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressVisible.set(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progressVisible.set(false);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            L.w("receiver ssl error");
            handler.proceed();
        }

    }
}
