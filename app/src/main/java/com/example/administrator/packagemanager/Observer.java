package com.example.administrator.packagemanager;

import android.widget.TextView;

/**
 * Created by Administrator on 2017-07-27.
 */

public class Observer implements ObserverModel.valueObserver {

    private ObserverModel observerModel;

    public Observer() {
        observerModel = ObserverModel.getInstance();
    }

    @Override
    public void onChanged(int beforeValue, int afterValue) {
        int initialValue = observerModel.getInitialValue();

        ObserverView observerView = ObserverView.getInstance();
        TextView observerTextView = observerView.getObserverTextView();

        observerTextView.setText(
                "초기 패키지 수 : " + String.valueOf(initialValue) + "\n" +
                "이전 패키지 수 : " + String.valueOf(beforeValue) + "\n" +
                "변경 패키지 수 : " + String.valueOf(afterValue)
        );

    }
}
