package com.example.administrator.packagemanager;

import android.widget.TextView;

/**
 * Created by Administrator on 2017-07-27.
 */

public class ObserverTextView {
    private static ObserverTextView observerTextView;
    private TextView textView;


    public static ObserverTextView getInstance() {
        if (observerTextView == null)
            observerTextView = new ObserverTextView();

        return observerTextView;
    }

    public TextView getText() {
        return textView;
    }

    public void setText(TextView textView) {
        this.textView = textView;
    }
}
