package com.example.eventstrackerapp.ui.allEvents;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private ArrayList<Event> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(TextView v){
            super(v);
            textView = v;
        }
    }

    public EventAdapter(ArrayList<Event> myDataset){
        mDataset = myDataset;
    }

    public EventAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType){
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_manage_events, parent, false);
        EventAdapter.MyViewHolder vh = new EventAdapter.MyViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(@NonNull EventAdapter.MyViewHolder holder, int position) {
        Event event = mDataset.get(position);
        String name = event.getTitle();
        holder.textView.setText(name);
    }

    public int getItemCount(){
        return mDataset.size();
    }
}
