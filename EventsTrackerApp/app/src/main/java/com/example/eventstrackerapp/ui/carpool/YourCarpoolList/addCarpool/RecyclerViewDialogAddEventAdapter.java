package com.example.eventstrackerapp.ui.carpool.YourCarpoolList.addCarpool;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors.RecyclerViewRiderAdapter;
import com.example.eventstrackerapp.ui.carpool.entities.Carpool;

import java.util.ArrayList;

public class RecyclerViewDialogAddEventAdapter extends RecyclerView.Adapter<RecyclerViewDialogAddEventAdapter.EventViewHolder> {

    private static final String TAG = "RecyclerViewDialogAddEv";
    public ArrayList<Event> mEventList;
    private ListItemClickListener5 listItemClickListener5;

    public RecyclerViewDialogAddEventAdapter(ArrayList<Event> mEventList) {
        this.mEventList = mEventList;
    }

    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_dialog_add_event_to_carpool, parent, false);
        EventViewHolder viewHolder = new EventViewHolder(view, listItemClickListener5);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.title.setText(mEventList.get(position).getTitle());
        holder.id.setText(mEventList.get(position).getEventID());
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void setOnItemClickListener(ListItemClickListener5 onItemClickListener){
        this.listItemClickListener5 = onItemClickListener;
    }

    /** ====================================================================================
     * Inner Class EventViewHolder
     * =====================================================================================
     */

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final ListItemClickListener5 listItemClickListener5;
        TextView title, id;

        public EventViewHolder(@NonNull View itemView, final ListItemClickListener5 listItemClickListener) {
            super(itemView);
            listItemClickListener5 = listItemClickListener;
            title = itemView.findViewById(R.id.add_event_title_dia_item);
            id = itemView.findViewById(R.id.add_event_id_dia_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listItemClickListener5 != null){
                listItemClickListener5.onItemClick(getLayoutPosition(), v);
            }
        }
    }

    /** ====================================================================================
     * Inner Class EventViewHolder
     * =====================================================================================
     */

    public interface ListItemClickListener5{
        void onItemClick(int position, View v);
    }
}
