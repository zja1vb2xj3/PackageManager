package com.example.administrator.packagemanager;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by ADMIN on 2017-07-25.
 */
//RecyclerView 내의 모든 item view 데이터 관리
class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<String> dataList = Collections.emptyList();
    private LayoutInflater layoutInflater;
    private MyPackage myPackage;

    public RecyclerViewAdapter(Context context, List<String> dataList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        myPackage = MyPackage.getInstance(context);
    }

    //2 viewType에 해당하는 viewHolder를 생성하여 return
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    //3 생성된 viewHolder와 position을 전달받아서 현재 position에 맞는 data를 viewholder가 관리하는 view들에 binding
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String packageName = dataList.get(position);
        holder.packageName_TextView.setText(packageName);
        holder.packageIcon_ImageView.setImageDrawable(myPackage.getApplicationIcon(packageName));
    }

    //1 아이탬 갯수 판단
    @Override
    public int getItemCount() {
        return dataList.size();
    }


    //각 item의 view 정보를 갖고있는 class
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView packageName_TextView;
        private ImageView packageIcon_ImageView;

        private Context context;

        private ObserverModel observerModel;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();

            packageIcon_ImageView = (ImageView) view.findViewById(R.id.packageIcon);
            packageIcon_ImageView.setOnClickListener(this);
            packageIcon_ImageView.setOnClickListener(this);

            packageName_TextView = (TextView) view.findViewById(R.id.packageName);
            packageName_TextView.setOnClickListener(this);
            packageName_TextView.setOnLongClickListener(this);

            observerModel = ObserverModel.getInstance();
            observerModel.addValueObserver(new Observer());
        }

        //일반 클릭 시 패키지 정보 설치날짜 및 용량 보여주기//현재는 버전만
        private void createPackageInfoDialog() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);

            LayoutInflater factory = LayoutInflater.from(context);
            final View view = factory.inflate(R.layout.dialog_packageinfo, null);

            final ImageView packageIcon = (ImageView) view.findViewById(R.id.selectedPackageIcon);
            final TextView packageName = (TextView) view.findViewById(R.id.selectedPackageName);
            final TextView packageInfo = (TextView) view.findViewById(R.id.selectedPackageInfo);

            setDialog(packageIcon, packageName, packageInfo);

            builder.setTitle("선택한 App 정보")
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

        @Override
        public void onClick(View v) {
            createPackageInfoDialog();
        }

        @Override
        public boolean onLongClick(View v) {
            createAskedToRemoveDialog(v);

            return true;
        }

        private void setDialog(ImageView packageIcon, TextView packageName, TextView packageInfo) {
            if (packageIcon != null)
                packageIcon.setImageDrawable(myPackage.getApplicationIcon(dataList.get(getAdapterPosition())));

            if (packageName != null) {
                packageName.setText(String.format("%s", dataList.get(getAdapterPosition())));
            }

            if (packageInfo != null) {
                packageInfo.setText(
                        "App 이름 : " + myPackage.getApplicationAppName(dataList.get(getAdapterPosition())) + "\n\n" +
                                "App 크기 : " + myPackage.getApplicationInstalledFileSize(dataList.get(getAdapterPosition())) + "\n\n" +
                                "App 버전 : " + myPackage.getApplicationVersion(dataList.get(getAdapterPosition())) + "\n\n" +
                                "설치 날짜 : " + myPackage.getApplicationFirstInstalledTime(dataList.get(getAdapterPosition())) + "\n\n" +
                                "수정 날짜 : " + myPackage.getApplicationLastUpdatedTime(dataList.get(getAdapterPosition())) + "\n\n"
                );
            }
        }


        private void createAskedToRemoveDialog(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);

            final LayoutInflater factory = LayoutInflater.from(context);
            final View view = factory.inflate(R.layout.dialog_askedtoremove, null);

            final ImageView deletePackageIcon = (ImageView) view.findViewById(R.id.deletePackageIcon);
            final TextView deletePackageName = (TextView) view.findViewById(R.id.deletePackageName);

            setDialog(deletePackageIcon, deletePackageName, null);

            builder.setTitle("선택한 Package 를 삭제 하시겠습니까?").setView(view).setCancelable(false)
                    .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean removeSign = removePackage(getAdapterPosition());

                            if (removeSign != false) {
                                Toast.makeText(v.getContext(), "선택한 패키지가 삭제되었습니다. \n패키지 개수 = " + dataList.size(), Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(v.getContext(), "패키지 삭제 중 오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
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

        private boolean removePackage(int position) {
            try {
                observerModel.setBeforeValue(dataList.size());

                dataList.remove(position);
                notifyItemRemoved(position);

                observerModel.setAfterValue(dataList.size());
                observerModel.changedOccur();

                return true;
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}//end recyclerviewadapter
