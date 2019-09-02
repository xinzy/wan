package com.xinzy.mvvm.lib.provider;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    private static ObservableTransformer sUITransformer = upstream ->
            upstream.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());

    public static <T> ObservableTransformer<T, T> applyUi() {
        return (ObservableTransformer<T, T>) sUITransformer;
    }
}
