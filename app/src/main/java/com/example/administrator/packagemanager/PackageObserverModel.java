package com.example.administrator.packagemanager;

import java.util.Vector;

/**
 * Created by Administrator on 2017-07-27.
 */

public class PackageObserverModel {

    private static PackageObserverModel packageObserverModel;

    private int initialValue;
    private int beforeValue;
    private int afterValue;
    private Vector<IValueObserver> IValueObservers;

    public static PackageObserverModel getInstance(){
        if(packageObserverModel == null){
            packageObserverModel = new PackageObserverModel();
        }
        return packageObserverModel;
    }

    public void addObserver(IValueObserver observer){
        IValueObservers = new Vector<>();
        IValueObservers.add(observer);
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

    public void changedOccur(){
        IValueObservers.forEach(x -> x.onChanged(beforeValue, afterValue));//
    }

    interface IValueObserver {
        void onChanged(int beforeValue, int afterValue);
    }

}
