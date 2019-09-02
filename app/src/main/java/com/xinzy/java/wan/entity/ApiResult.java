package com.xinzy.java.wan.entity;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class ApiResult<T> {
    @SerializedName("errorCode")
    private int code;
    @SerializedName("errorMsg")
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return code == 0;
    }
}
