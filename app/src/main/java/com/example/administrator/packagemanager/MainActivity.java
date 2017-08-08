package com.example.administrator.packagemanager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.util.Vector;

public class MainActivity extends Activity {
    private Vector<String> packageNames;
    private TextView packageCount_TextView;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private Button resetButton;
    private MyPackage myPackage;
    private PackageObserverModel packageObserverModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetButton = (Button)findViewById(R.id.resetButton);
        resetButton.setOnClickListener(this::resetButtonClick);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        packageCount_TextView = (TextView)findViewById(R.id.packageCount);

        packageObserverModel = new PackageObserverModel();
        packageObserverModel.addObserver(new PackageObserver(packageCount_TextView));

        packageInitialization();

        packageObserverModel.setInitialValue(packageNames.size());
        packageObserverModel.changedOccur();

    }
    //패키지 초기설정
    private void packageInitialization(){
        acceptPackageList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //LayoutManager : RecyclerView내에 item view들의 크기 측정 및 위치 지정
        //언제 view들을 재사용해야하는지 결정
        adapter = new RecyclerViewAdapter(this, packageNames, packageCount_TextView);

        adapter.setCreateDialogListener(new RecyclerViewAdapter.createDialogListener() {
            @Override
            public void onPackageInfoDialog(String packageName) {
                createPackageInfoDialog(packageName);
            }

            @Override
            public void onAskedToRemoveDialog(String packageName) {
                createAskedToRemoveDialog(packageName);
            }

        });
        //Adapter : RecyclerView내에 보여지는 view들의 data set을 binding해주는 역할
        recyclerView.setAdapter(adapter);
    }


    //longClick 시 패키지 삭제 물음
    private void createAskedToRemoveDialog(String packageName) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.dialog_askedtoremove, null);

        final ImageView deletePackageIcon = (ImageView) view.findViewById(R.id.deletePackageIcon);
        final TextView deletePackageName = (TextView) view.findViewById(R.id.deletePackageName);

        setDialog(deletePackageIcon,deletePackageName,null, packageName);

        builder.setTitle("선택한 Package 를 삭제 하시겠습니까?").setView(view).setCancelable(false)
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean removeSign = adapter.removeDataList(adapter.getClickPosition());

                        if (removeSign != false) {
                            Toast.makeText(getApplicationContext(), "선택한 패키지가 삭제되었습니다. \n패키지 개수 = " + adapter.getItemCount(), Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(getApplicationContext(), "패키지 삭제 중 오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void setDialog(ImageView packageIcon, TextView packageName, TextView packageInfo, String pakageName) {
        if (packageIcon != null)
            packageIcon.setImageDrawable(myPackage.getPackageIcon(pakageName));

        if (packageName != null) {
            packageName.setText(String.format("%s", pakageName));
        }

        if (packageInfo != null) {
            packageInfo.setText(
                    "App 이름 : " + myPackage.getPackageAppName(pakageName) + "\n\n" +
                            "App 크기 : " + myPackage.getPackageInstalledFileSize(pakageName) + "\n\n" +
                            "App 버전 : " + myPackage.getPackageVersion(pakageName) + "\n\n" +
                            "설치 날짜 : " + myPackage.getPackageFirstInstallTime(pakageName) + "\n\n" +
                            "수정 날짜 : " + myPackage.getPackageLastUpdateTime(pakageName) + "\n\n"
            );
        }
    }

    //onClick 시 패키지 정보 보여주기
    private void createPackageInfoDialog(String pakageName) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.dialog_packageinfo, null);

        final ImageView packageIcon = (ImageView) view.findViewById(R.id.selectedPackageIcon);
        final TextView packageName = (TextView) view.findViewById(R.id.selectedPackageName);
        final TextView packageInfo = (TextView) view.findViewById(R.id.selectedPackageInfo);

        setDialog(packageIcon, packageName, packageInfo, pakageName);

        builder.setTitle("선택한 Package 정보")
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private void resetButtonClick(View view) {
        packageObserverModel.setBeforeValue(adapter.getItemCount());
        packageInitialization();
        packageObserverModel.setAfterValue(packageNames.size());
        packageObserverModel.changedOccur();

        Toast.makeText(getApplicationContext(), "초기화 작동"+"\n"
                +"패키지 개수 = " + String.valueOf(packageNames.size()), Toast.LENGTH_SHORT).show();
    }

    //패키지 이름 받아오기
    private void acceptPackageList() {
        myPackage = MyPackage.getInstance(getApplicationContext());
        List<ApplicationInfo> myPackages = myPackage.getPackageNames();//패키지를 가져옴
        packageNames = new Vector<>();

        for(ApplicationInfo mypackage : myPackages) {
            packageNames.add(mypackage.processName);
        }
    }

}