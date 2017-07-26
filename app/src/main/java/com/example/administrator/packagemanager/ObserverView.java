package com.example.administrator.packagemanager;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2017-07-27.
 */

public class ObserverView {
    private static ObserverView observerView;
    private TextView observerTextView;


    public static ObserverView getInstance() {
        if (observerView == null)
            observerView = new ObserverView();

        return observerView;
    }

    public TextView getObserverTextView() {
        return observerTextView;
    }

    public void setObserverTextView(TextView observerTextView) {
        this.observerTextView = observerTextView;
    }
}
