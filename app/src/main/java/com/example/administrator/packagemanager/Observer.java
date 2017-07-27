package com.example.administrator.packagemanager;

import android.widget.TextView;

/**
 * Created by Administrator on 2017-07-27.
 */

public class Observer implements ObserverModel.IValueObserver {

    private ObserverModel observerModel;

    public Observer() {
        observerModel = ObserverModel.getInstance();
    }

    @Override
    public void onChanged(int beforeValue, int afterValue) {
        int initialValue = observerModel.getInitialValue();

        ObserverTextView observerView = ObserverTextView.getInstance();
        TextView observerTextView = observerView.getTextView();

        observerTextView.setText(
                "초기 패키지 수 : " + String.valueOf(initialValue) + "\n" +
                "이전 패키지 수 : " + String.valueOf(beforeValue) + "\n" +
                "변경 패키지 수 : " + String.valueOf(afterValue)
        );

    }
}
