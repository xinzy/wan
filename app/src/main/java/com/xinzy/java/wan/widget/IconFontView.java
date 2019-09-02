package com.xinzy.java.wan.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IconFontView extends TextView {

    public IconFontView(Context context) {
        this(context, null);
    }

    public IconFontView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconFontView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        setGravity(Gravity.CENTER);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/iconfont.ttf");
        setTypeface(typeface);
        setIncludeFontPadding(false);
    }

}
