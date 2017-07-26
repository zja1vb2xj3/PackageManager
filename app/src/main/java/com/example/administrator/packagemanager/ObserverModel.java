package com.example.administrator.packagemanager;

import java.util.Vector;

/**
 * Created by Administrator on 2017-07-27.
 */

public class ObserverModel {

    private static ObserverModel observerModel;

    private int initialValue;
    private int beforeValue;
    private int afterValue;
    private Vector<valueObserver> valueObservers;

    public static ObserverModel getInstance(){
        if(observerModel == null){
            observerModel = new ObserverModel();
        }
        return observerModel;
    }

    public void addValueObserver(valueObserver observer){
        valueObservers = new Vector<>();
        valueObservers.add(observer);
    }

    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }

    public void setBeforeValue(int beforeValue) {
        this.beforeValue = beforeValue;
    }

    public void setAfterValue(int afterValue) {
        this.afterValue = afterValue;
    }

    public int getInitialValue() {
        return initialValue;
    }

    public void changedOccureed(){
        valueObservers.forEach(x -> x.onChanged(beforeValue, afterValue));
    }

    interface valueObserver{
        void onChanged(int beforeValue, int afterValue);
    }

}
