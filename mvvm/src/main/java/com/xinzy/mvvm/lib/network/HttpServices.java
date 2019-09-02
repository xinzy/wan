package com.xinzy.mvvm.lib.network;

import androidx.collection.ArrayMap;

import com.xinzy.mvvm.lib.annotation.BaseUri;
import com.xinzy.mvvm.lib.annotation.HttpConfig;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpServices {

    private static Map<Class<?>, Object> mServices = new ArrayMap<>();

    public static <I> I getService(Class<I> clazz) {
        if (mServices.containsKey(clazz)) {
            return (I) mServices.get(clazz);
        }

        BaseUri uri = clazz.getAnnotation(BaseUri.class);
        if (uri == null) {
            throw new IllegalStateException("retrofit interface must as BaseUri annotation");
        }

        HttpConfig config = clazz.getAnnotation(HttpConfig.class);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (config != null) {
            if (config.unsafe()) {
                builder.hostnameVerifier(new TrustHostnameVerifier())
                        .sslSocketFactory(createSSLSocketFactory(), new TrustAllManager());
            }

            builder.connectTimeout(config.timeout(), TimeUnit.SECONDS)
                    .readTimeout(config.timeout(), TimeUnit.SECONDS)
                    .writeTimeout(config.timeout(), TimeUnit.SECONDS);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(uri.value())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();

        I result = retrofit.create(clazz);
        mServices.put(clazz, result);
        return result;
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return sSLSocketFactory;
    }


    static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    static class TrustHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
