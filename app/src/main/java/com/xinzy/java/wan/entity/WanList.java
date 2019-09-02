package com.xinzy.java.wan.entity;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 分页数据
 */
@Keep
public class WanList<T> {
    @SerializedName("curPage")
    private int page;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<T> datas;

    public int getPage() {
        return page;
    }

    public int getOffset() {
        return offset;
    }

    public boolean isOver() {
        return over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getSize() {
        return size;
    }

    public int getTotal() {
        return total;
    }

    public List<T> getDatas() {
        return datas;
    }
}
