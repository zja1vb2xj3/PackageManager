package com.example.administrator.packagemanager;

import java.util.Vector;

/**
 * Created by Administrator on 2017-07-27.
 */

public class PackageObserverModel {

//    private static PackageObserverModel packageObserverModel;

    private static int initialValue;
    private int beforeValue;
    private int afterValue;
    private Vector<IValueObserver> IValueObservers;
//
//    public static PackageObserverModel getInstance(){
//        if(packageObserverModel == null){
//            packageObserverModel = new PackageObserverModel();
//        }
//        return packageObserverModel;
//    }

    public void addObserver(IValueObserver observer){
        IValueObservers = new Vector<>();
        IValueObservers.add(observer);
    }

    public static void setInitialValue(int value) {
        initialValue = value;
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
        IValueObservers.forEach(x -> x.onChanged(beforeValue, afterValue));//x는 옵저버 개수
    }

    interface IValueObserver {
        void onChanged(int beforeValue, int afterValue);
    }

}
