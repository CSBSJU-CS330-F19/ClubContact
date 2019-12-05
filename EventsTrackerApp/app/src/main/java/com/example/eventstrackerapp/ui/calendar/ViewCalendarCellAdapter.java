package com.example.eventstrackerapp.ui.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;

import java.util.ArrayList;

public class ViewCalendarCellAdapter extends RecyclerView.Adapter<ViewCalendarCellAdapter.CalendarEventViewHolder> {

    private ArrayList<Event> mEventsSet;
    private ListItemClickListener6 listItemClickListener6;

    public ViewCalendarCellAdapter(ArrayList<Event> mEventsSet) {
        this.mEventsSet = mEventsSet;
    }


    @NonNull
    @Override
    public ViewCalendarCellAdapter.CalendarEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        CalendarEventViewHolder viewHolder = new CalendarEventViewHolder(view, listItemClickListener6);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCalendarCellAdapter.CalendarEventViewHolder holder, int position) {
        holder.title.setText(mEventsSet.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mEventsSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setOnItemClickListener(ListItemClickListener6 onItemClickListener6){
        this.listItemClickListener6 = onItemClickListener6;
    }

    /**
     * Inner class for ViewHolder
     */

    public class CalendarEventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ListItemClickListener6 listItemClickListener6;
        private TextView title;

        public CalendarEventViewHolder(@NonNull View itemView, ListItemClickListener6 listItemClickListener6) {
            super(itemView);
            this.listItemClickListener6 = listItemClickListener6;
            title = itemView.findViewById(R.id.event_title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listItemClickListener6 != null){
                listItemClickListener6.onItemClick(getLayoutPosition(), v);
            }
        }
    }

    public interface ListItemClickListener6{
        void onItemClick(int position, View v);
    }
}
