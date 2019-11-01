package com.example.eventstrackerapp.ui.clubMembers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.clubMembers.ViewClubMembersViewModel;

public class ViewClubMembersFragment extends Fragment {

    private ViewClubMembersViewModel viewClubMembersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        viewClubMembersViewModel =
                ViewModelProviders.of(this).get(ViewClubMembersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_club_members, container, false);
        final TextView textView = root.findViewById(R.id.nav_club_members);
        viewClubMembersViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s){
                textView.setText(s);
            }

        });
        return root;
    }
}
