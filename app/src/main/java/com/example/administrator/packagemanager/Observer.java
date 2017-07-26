package com.example.administrator.packagemanager;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017-07-27.
 */

public class Observer implements ObserverModel.valueObserver {
    private Context context;
    private ObserverModel observerModel;

    public Observer(Context context) {
        this.context = context;
        observerModel = ObserverModel.getInstance();
    }

    @Override
    public void onChanged(int beforeValue, int afterValue) {
        int initialValue = observerModel.getInitialValue();
        Toast.makeText(context.getApplicationContext(),
                "초기 패키지 수 : " + String.valueOf(initialValue) + "\n" +
                        "이전 패키지 수 : " + String.valueOf(beforeValue) + "\n" +
                        "변경 패키지 수 : " + String.valueOf(afterValue),
                        Toast.LENGTH_LONG).show();
    }
}
