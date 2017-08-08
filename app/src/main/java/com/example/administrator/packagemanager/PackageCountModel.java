package com.example.administrator.packagemanager;

/**
 * Created by Administrator on 2017-07-27.
 */

public class PackageCountModel {
    private static PackageCountModel packageCountModel;

    public static PackageCountModel getInstance(){
        if(packageCountModel == null){
            packageCountModel = new PackageCountModel();
        }
        return packageCountModel;
    }
//
    private int initialValue;
    private int beforeValue;
    private int afterValue;

    public int getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(int initialValue) {
        this.initialValue = initialValue;
    }

    public int getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(int beforeValue) {
        this.beforeValue = beforeValue;
    }

    public int getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(int afterValue) {
        this.afterValue = afterValue;
    }
}
