package com.xinzy.java.wan.entity;

import androidx.annotation.Keep;

@Keep
public class Banner {

    private int id;
    private String title;
    private String imagePath;
    private String url;
    private String desc;
    private int order;
    private int isVisible;
    private int type;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getUrl() {
        return url;
    }

    public String getDesc() {
        return desc;
    }

    public int getOrder() {
        return order;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public int getType() {
        return type;
    }
}
