package com.xinzy.java.wan.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.xinzy.java.wan.R;

public class ErrorView extends LinearLayout {

    public ErrorView(Context context) {
        this(context, null);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_error, this);
    }

}
