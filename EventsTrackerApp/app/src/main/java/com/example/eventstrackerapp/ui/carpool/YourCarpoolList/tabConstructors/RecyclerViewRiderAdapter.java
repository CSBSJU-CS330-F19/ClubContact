package com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.entities.Carpool;

import java.util.ArrayList;

public class RecyclerViewRiderAdapter extends RecyclerView.Adapter<RecyclerViewRiderAdapter.RiderViewHolder> {

    public ArrayList<Carpool> mYourCarpoolList;
    private ListItemClickListener4 listItemClickListener4;

    public RecyclerViewRiderAdapter(ArrayList<Carpool> mYourCarpoolList) {
        this.mYourCarpoolList = mYourCarpoolList;
    }


    @Override
    public RecyclerViewRiderAdapter.RiderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_rider_list_item, parent, false);
        RiderViewHolder viewHolder = new RecyclerViewRiderAdapter.RiderViewHolder(view, listItemClickListener4);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewRiderAdapter.RiderViewHolder holder, final int position) {
        holder.title.setText(mYourCarpoolList.get(position).getCarpoolTitle());
        holder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemClickListener4.onInfoClick(position);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemClickListener4.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mYourCarpoolList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void setOnItemClickListener(ListItemClickListener4 onItemClickListener){
        this.listItemClickListener4 = onItemClickListener;
    }

    public class RiderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ListItemClickListener4 listItemClickListener4;
        protected TextView title;
        protected ImageButton infoButton;
        protected ImageButton deleteButton;

        public RiderViewHolder(@NonNull View itemView, final ListItemClickListener4 listItemClickListener) {
            super(itemView);
            listItemClickListener4 = listItemClickListener;

            this.title = itemView.findViewById(R.id.carpool_title);

            this.infoButton = itemView.findViewById(R.id.info_button);
            this.infoButton.setOnClickListener(this);

            this.deleteButton= itemView.findViewById(R.id.delete_button);
            this.deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listItemClickListener4 != null){
                listItemClickListener4.onItemClick(getLayoutPosition(), v);
                listItemClickListener4.onDeleteClick(getAdapterPosition());
                listItemClickListener4.onInfoClick(getAdapterPosition());
            }
        }
    }

    public interface ListItemClickListener4{
        void onItemClick(int position, View v);
        void onDeleteClick(int position);
        void onInfoClick(int position);
    }
}
