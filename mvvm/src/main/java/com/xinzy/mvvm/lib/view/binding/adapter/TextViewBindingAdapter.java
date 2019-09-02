package com.xinzy.mvvm.lib.view.binding.adapter;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

import com.xinzy.mvvm.lib.view.binding.command.BindingConsumer;

public class TextViewBindingAdapter {

    @BindingAdapter("onTextChangedAction")
    public static void setTextChangeListener(EditText editText, BindingConsumer<String> consumer) {
        editText.addTextChangedListener(new TextChange(consumer));
    }

    private static class TextChange implements TextWatcher {
        private BindingConsumer<String> consumer;
        private Handler mHandler = new Handler();

        public TextChange(BindingConsumer<String> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(() -> consumer.call(s.toString()), 300);
        }

        @Override
        public void afterTextChanged(Editable s) { }
    }
}
