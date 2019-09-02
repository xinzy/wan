package com.xinzy.java.wan.entity;

import androidx.annotation.Keep;

/**
 * 文章标签
 */
@Keep
public class Tag {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
