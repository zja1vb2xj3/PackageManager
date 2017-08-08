package com.example.administrator.packagemanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ADMIN on 2017-07-25.
 */
//RecyclerView 내의 모든 item view 데이터 관리
class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<String> dataList;
    private LayoutInflater layoutInflater;
    private MyPackage myPackage;
    private TextView packageCount_TextView;
    private int clickPosition;
    private ViewHolder viewHolder;

    public RecyclerViewAdapter(Context context, List<String> dataList, TextView packageCount_TextView) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.packageCount_TextView = packageCount_TextView;
        myPackage = MyPackage.getInstance(context);
    }

    //1 아이탬 갯수 판단
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    //2 viewType에 해당하는 viewHolder를 생성하여 return
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recyclerview_row, parent, false);
        viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    //3 생성된 viewHolder와 position을 전달받아서 현재 position에 맞는 data를 viewholder가 관리하는 view들에 binding
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String packageName = dataList.get(position);
        holder.packageName_TextView.setText(packageName);
        holder.packageIcon_ImageView.setImageDrawable(myPackage.getPackageIcon(packageName));
    }

    public int getClickPosition() {
        return clickPosition;
    }

    public boolean removeDataList(int position){
        dataList.remove(position);
        viewHolder.notifyToRemoveViewItem(position);

        return true;
    }


    //RecyclerView내에 각 item의 view 정보를 갖고있는 class
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView packageName_TextView;
        private ImageView packageIcon_ImageView;

        private PackageObserverModel packageObserverModel;

        public ViewHolder(View view) {
            super(view);

            packageIcon_ImageView = (ImageView) view.findViewById(R.id.packageIcon);
            packageIcon_ImageView.setOnClickListener(this);
            packageIcon_ImageView.setOnLongClickListener(this);

            packageName_TextView = (TextView) view.findViewById(R.id.packageName);
            packageName_TextView.setOnClickListener(this);
            packageName_TextView.setOnLongClickListener(this);

            packageObserverModel = new PackageObserverModel();
            packageObserverModel.addObserver(new PackageObserver(packageCount_TextView));

        }

        @Override
        public void onClick(View v) {
            CreateDialogListener.onPackageInfoDialog(dataList.get(getAdapterPosition()));
        }


        @Override
        public boolean onLongClick(View v) {
            CreateDialogListener.onAskedToRemoveDialog(dataList.get(getAdapterPosition()));
            clickPosition = getAdapterPosition();
            return true;
        }

         void notifyToRemoveViewItem(int position) {
             packageObserverModel.setBeforeValue(dataList.size());

             notifyItemRemoved(position); //등록된 옵저버에 이전에 위치햇던 항목이 데이트 세트에서 제거되었음을 알림.

             packageObserverModel.setAfterValue(dataList.size());
             packageObserverModel.changedOccur();

         }

    }//end ViewHolder

    private CreateDialogListener CreateDialogListener;

    public void setCreateDialogListener(CreateDialogListener CreateDialogListener) {
        this.CreateDialogListener = CreateDialogListener;
    }

    interface CreateDialogListener {
        void onPackageInfoDialog(String packageName);
        void onAskedToRemoveDialog(String pakageName);
    }

}//end recyclerviewadapter
