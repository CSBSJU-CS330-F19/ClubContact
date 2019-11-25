package com.example.eventstrackerapp.ui.manageUsers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.profile.User;

import java.util.ArrayList;

public class ManageUsersAdapter extends RecyclerView.Adapter<ManageUsersAdapter.MyViewHolder> {
    private ArrayList<User> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public MyViewHolder(TextView v){
            super(v);
            textView = v;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public ManageUsersAdapter(ArrayList<User> myDataset){
        mDataset = myDataset;
    }

    public ManageUsersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType){
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_manage_users, parent, false);
        ManageUsersAdapter.MyViewHolder vh = new ManageUsersAdapter.MyViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ManageUsersAdapter.MyViewHolder holder, int position) {
        User user = mDataset.get(position);
        String name = user.getFirstName();
        holder.textView.setText(name);
    }

    public int getItemCount(){
        return mDataset.size();
    }
}
