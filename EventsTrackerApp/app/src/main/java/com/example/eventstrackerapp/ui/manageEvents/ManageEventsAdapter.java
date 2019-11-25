package com.example.eventstrackerapp.ui.manageEvents;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;

import java.util.ArrayList;

public class ManageEventsAdapter extends RecyclerView.Adapter<ManageEventsAdapter.MyViewHolder> {
    private ArrayList<Event> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(TextView v){
            super(v);
            textView = v;
        }
    }

    public ManageEventsAdapter(ArrayList<Event> myDataset){
        mDataset = myDataset;
    }

    public ManageEventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType){
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_manage_events_recycler, parent, false);
        ManageEventsAdapter.MyViewHolder vh = new ManageEventsAdapter.MyViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(@NonNull ManageEventsAdapter.MyViewHolder holder, int position) {
        Event event = mDataset.get(position);
        String name = event.getTitle();
        holder.textView.setText(name);
    }

    public int getItemCount(){
        return mDataset.size();
    }
}
