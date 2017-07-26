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
    private TextView packageCount_TextView;
    private MyPackage myPackage;
    private Context context;

    public RecyclerViewAdapter(Context context, List<String> dataList, TextView packageCount) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.packageCount_TextView = packageCount;
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
        holder.packageIcon_ImageView.setImageDrawable(myPackage.getMyPackageIcon(packageName));
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
        public ViewHolder(View view) {
            super(view);
            context = view.getContext();

            packageIcon_ImageView = (ImageView)view.findViewById(R.id.packageIcon);

            packageName_TextView = (TextView) view.findViewById(R.id.packageName);
            packageName_TextView.setOnClickListener(this);
            packageName_TextView.setOnLongClickListener(this);

        }
        //일반 클릭 시 패키지 정보 설치날짜 및 용량 보여주기//현재는 버전만
        private void createPackageInfoDialog() {
            LayoutInflater factory = LayoutInflater.from(context);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            final View view = factory.inflate(R.layout.dialog_index, null);

            ImageView packageIcon = (ImageView) view.findViewById(R.id.selectedPackageIcon);
            TextView packageInfo = (TextView)view.findViewById(R.id.selectedPackageInfo);

            packageIcon.setImageDrawable(myPackage.getMyPackageIcon(dataList.get(getAdapterPosition())));
            packageInfo.setText(
                    String.format("%10s, %s" , "패키지 명 = ", dataList.get(getAdapterPosition())+
                            "\n"+"버젼 = " + myPackage.getApplicationVersion(dataList.get(getAdapterPosition()))+
                            "\n"));

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

        private void createAskedToRemoveDialog(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("선택한 Package를 삭제 하시겠습니까?").setMessage(dataList.get(getAdapterPosition())).setCancelable(false)
                    .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean sign = removePackage(getAdapterPosition());

                            if(sign != false) {
                                Toast.makeText(v.getContext(), "선택한 패키지가 삭제되었습니다. \n패키지 게수 = " + dataList.size(), Toast.LENGTH_LONG).show();
                                packageCount_TextView.setText("패키지 개수 = " + String.valueOf(dataList.size()));
                            }
                            else
                                Toast.makeText(v.getContext(),"패키지 삭제 중 오류가 발생하였습니다.",Toast.LENGTH_LONG).show();
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
                dataList.remove(position);
                notifyItemRemoved(position);
                return true;
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            return false;
        }
    }


}//end recyclerviewadapter
