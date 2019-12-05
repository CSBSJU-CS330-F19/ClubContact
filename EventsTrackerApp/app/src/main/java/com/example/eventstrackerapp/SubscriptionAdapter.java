package com.example.eventstrackerapp;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.widget.TextView;
import android.view.ViewGroup;
import java.util.ArrayList;
import com.example.eventstrackerapp.profile.Club;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.SubViewHolder> {

    private ArrayList<Club> clubList;

    public static class SubViewHolder extends RecyclerView.ViewHolder
    {
        public TextView textView;
        public SubViewHolder(TextView v)
        {
            super(v);
            textView = v;
        }
    }

    public SubscriptionAdapter(ArrayList<Club> data)
    {
        clubList = data;
    }

    public SubscriptionAdapter.SubViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_subscriptions, parent, false);

        SubViewHolder vh = new SubViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(SubViewHolder holder, int position)
    {
        holder.textView.setText(clubList.get(position).getName());
    }

    public int getItemCount()
    {
        return clubList.size();
    }
}
