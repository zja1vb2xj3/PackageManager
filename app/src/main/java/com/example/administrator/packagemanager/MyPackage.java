package com.example.administrator.packagemanager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MyPackage {

    private static MyPackage myPackage;
    private static PackageManager packageManager;
    private static SimpleDateFormat sdf;

    private PackageInfo packageInfo;


    public static MyPackage getInstance(Context context) {
        if (myPackage == null) {
            myPackage = new MyPackage();
        }
        packageManager = context.getPackageManager();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return myPackage;
    }


    public List<ApplicationInfo> getMyPackageName() {
        List<ApplicationInfo> myPackageName = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        return myPackageName;
    }


    public String getApplicationAppName(String packageName) {
        try {
            String appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
            return appName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Drawable getMyPackageIcon(String packageName) {


        try {
            Drawable icon = packageManager.getApplicationIcon(packageName);
            return icon;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getApplicationVersion(String packageName) {
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);


            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getApplicationFirstInstalledTime(String packageName) {
        try {


            packageInfo = packageManager.getPackageInfo(packageName, 0);
            String firstInstalledTime = sdf.format(new Date(packageInfo.firstInstallTime));


            return firstInstalledTime;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getApplicationLastUpdatedTime(String packageName) {
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
            String lastUpdatedTime = sdf.format(new Date(packageInfo.lastUpdateTime));


            return lastUpdatedTime;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getApplicationInstalledFileSize(String packageName) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);

            File file = new File(applicationInfo.sourceDir);

            String fileSize = String.valueOf(file.length());

            return fileSize + " Byte";

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
