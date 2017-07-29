package com.example.administrator.packagemanager;

import android.widget.TextView;

/**
 * Created by Administrator on 2017-07-27.
 */

public class PackageObserver implements PackageObserverModel.IValueObserver {

    private PackageObserverModel packageObserverModel;
    private TextView packageCount_TextView;

    public PackageObserver(TextView textView) {
        packageObserverModel = PackageObserverModel.getInstance();
        this.packageCount_TextView = textView;
    }

    public TextView getPackageCount_TextView() {
        return packageCount_TextView;
    }

    public void setPackageCount_TextView(TextView packageCount_TextView) {
        this.packageCount_TextView = packageCount_TextView;
    }

    @Override
    public void onChanged(int beforeValue, int afterValue) {
        int initialValue = packageObserverModel.getInitialValue();

        packageCount_TextView.setText(
                "초기 패키지 수 : " + String.valueOf(initialValue) + "\n" +
                "이전 패키지 수 : " + String.valueOf(beforeValue) + "\n" +
                "변경 패키지 수 : " + String.valueOf(afterValue)
        );

    }
}
