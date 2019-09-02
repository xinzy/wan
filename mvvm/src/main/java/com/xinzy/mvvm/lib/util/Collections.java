package com.xinzy.mvvm.lib.util;

import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Objects;

import io.reactivex.functions.BiConsumer;

public class Collections {

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static <E> void forEach(Iterable<? extends E> iterable, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(iterable);
        Objects.requireNonNull(action);

        try {
            int index = 0;
            for (E e : iterable) {
                action.accept(index++, e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
