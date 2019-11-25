package com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.entities.Carpool;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecyclerViewCampusAdapter extends RecyclerView.Adapter<RecyclerViewCampusAdapter.CampusViewHolder> {

    private ArrayList<Carpool> mYourCarpoolList;
    private ListItemClickListener2 listItemClickListener;

    public RecyclerViewCampusAdapter(ArrayList<Carpool> mYourCarpoolList) {
        this.mYourCarpoolList = mYourCarpoolList;
    }

    @Override
    public RecyclerViewCampusAdapter.CampusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_your_carpool_list, parent, false);

        CampusViewHolder viewHolder = new CampusViewHolder(view, listItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCampusAdapter.CampusViewHolder holder, int position) {
        holder.title.setText(mYourCarpoolList.get(position).getCarpoolTitle());
    }

    @Override
    public int getItemCount() {
        return mYourCarpoolList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void setOnItemClickListener(ListItemClickListener2 onItemClickListener){
        this.listItemClickListener = onItemClickListener;
    }

    public class CampusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ListItemClickListener2 listItemClickListener;
        private TextView title;

        public CampusViewHolder(View itemView, ListItemClickListener2 listItemClickListener) {
            super(itemView);

            this.listItemClickListener = listItemClickListener;
            itemView.setOnClickListener(this);

            this.title = itemView.findViewById(R.id.carpool_title);

        }

        @Override
        public void onClick(View v) {
            if(listItemClickListener != null){
                listItemClickListener.onItemClick(getLayoutPosition(), v);
            }
        }
    }

    public interface ListItemClickListener2{
        void onItemClick(int position, View v);
    }
}
