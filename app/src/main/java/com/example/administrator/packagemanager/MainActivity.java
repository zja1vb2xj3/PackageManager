package com.example.administrator.packagemanager;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

        packageInitialization();

        packageObserverModel = PackageObserverModel.getInstance();
        packageObserverModel.addValueObserver(new PackageObserver(packageCount_TextView));
        packageObserverModel.changedOccur();
    }
    //패키지 초기설정
    private void packageInitialization(){
        acceptPackageList();
        
        packageObserverModel.setInitialValue(packageNames.size());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //LayoutManager : RecyclerView내에 item view들의 크기 측정 및 위치 지정
        //언제 view들을 재사용해야하는지 결정
        adapter = new RecyclerViewAdapter(this, packageNames, packageCount_TextView);
        //Adapter : RecyclerView내에 보여지는 view들의 data set을 binding해주는 역할
        recyclerView.setAdapter(adapter);
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
        List<ApplicationInfo> myPackages = myPackage.getPackageName();//패키지를 가져옴
        packageNames = new Vector<>();

        for(ApplicationInfo mypackage : myPackages) {
            packageNames.add(mypackage.processName);
        }
    }

}