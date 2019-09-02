package com.xinzy.java.wan;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.xinzy.java.wan.util.Https;

import java.io.InputStream;

import okhttp3.OkHttpClient;

public class WanApplication extends Application {

    private static WanApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);

        Glide.get(this).getRegistry().replace(GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory(createUnsafeClient()));
    }


    private OkHttpClient createUnsafeClient() {
        return new OkHttpClient.Builder()
                .sslSocketFactory(Https.createSSLSocketFactory(), Https.createTrustAllManager())
                .hostnameVerifier(Https.createTrustHostnameVerifier()).build();
    }

    public static WanApplication getInstance() {
        return sInstance;
    }
}
