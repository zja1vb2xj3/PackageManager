package com.example.administrator.packagemanager;

import android.widget.TextView;

/**
 * Created by Administrator on 2017-07-27.
 */

public class PackageObserver implements PackageObserverModel.IValueObserver {

    private PackageObserverModel packageObserverModel;
    private TextView textView;

    public PackageObserver(TextView textView) {
        packageObserverModel = PackageObserverModel.getInstance();
        this.textView = textView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onChanged(int beforeValue, int afterValue) {
        int initialValue = packageObserverModel.getInitialValue();

        textView.setText(
                "초기 패키지 수 : " + String.valueOf(initialValue) + "\n" +
                "이전 패키지 수 : " + String.valueOf(beforeValue) + "\n" +
                "변경 패키지 수 : " + String.valueOf(afterValue)
        );

    }
}
