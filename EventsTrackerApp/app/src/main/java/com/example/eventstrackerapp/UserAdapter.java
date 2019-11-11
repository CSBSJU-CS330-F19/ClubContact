package com.example.eventstrackerapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventstrackerapp.profile.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private ArrayList<User> mDataset;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MyViewHolder(TextView v){
            super(v);
            textView = v;
        }
    }

    public UserAdapter(ArrayList<User> myDataset){
        mDataset = myDataset;
    }

    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType){
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_manage_users, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = mDataset.get(position);
        String name = user.getFirstName();
        holder.textView.setText(name);
    }

    public int getItemCount(){
        return mDataset.size();
    }
}
