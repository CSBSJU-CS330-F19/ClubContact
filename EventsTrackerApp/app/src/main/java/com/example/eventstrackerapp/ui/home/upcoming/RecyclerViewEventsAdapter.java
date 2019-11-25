package com.example.eventstrackerapp.ui.home.upcoming;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RecyclerViewEventsAdapter extends RecyclerView.Adapter<RecyclerViewEventsAdapter.EventsViewHolder> {

    private ArrayList<Event> mEventsSet;
    private ListItemClickListener listItemClickListener;

    public RecyclerViewEventsAdapter(ArrayList<Event> mEventsSet){
        this.mEventsSet = mEventsSet;
    }

    @Override
    public RecyclerViewEventsAdapter.EventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);

        EventsViewHolder viewHolder = new EventsViewHolder(view, listItemClickListener);
        return viewHolder;
    }

    /**
     * This method binds to the textViews the data the current event holds.
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(@NonNull EventsViewHolder holder, int position) {
        holder.title.setText(mEventsSet.get(position).getTitle());

        // Cast a Date to a String to put it into the TextView
        SimpleDateFormat simpleStartDateFormat = new SimpleDateFormat("MMM dd   hh:mma - ");
        SimpleDateFormat simpleEndDateFormat = new SimpleDateFormat("hh:mma");
        String sdate = simpleStartDateFormat.format(mEventsSet.get(position).getStart());
        String edate = simpleEndDateFormat.format(mEventsSet.get(position).getEnd());
        holder.time.setText(sdate + edate);
    }

    @Override
    public int getItemCount() {
        return mEventsSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

//    public void insert(int position, Event event){
//        mEventsSet.add(position, event);
//        notifyItemInserted(position);
//    }

//    public void remove(Event event){
//        int position = mEventsSet.indexOf(event);
//        mEventsSet.remove(position);
//        notifyItemRemoved(position);
//    }

    public void setOnItemClickListener(ListItemClickListener onItemClickListener){
        this.listItemClickListener = onItemClickListener;
    }

    /**
     * Inner class for ViewHolder
     */


    public class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ListItemClickListener listItemClickListener;

        private TextView title, time;

        public EventsViewHolder(View itemView, ListItemClickListener listItemClickListener){
            super(itemView);
            this.listItemClickListener = listItemClickListener;
            title = itemView.findViewById(R.id.event_title);
            time = itemView.findViewById(R.id.event_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listItemClickListener != null){
                listItemClickListener.onItemClick(getLayoutPosition(), v);
            }
        }
    }

    public interface ListItemClickListener{
        void onItemClick(int position, View v);
    }


}
