package com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors;

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

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors.PageViewModel pageViewModel; // Holds the information to display on the fragment

    public static com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors.PlaceholderFragment newInstance(int index) {
        com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors.PlaceholderFragment fragment = new com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors.PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index); // Sets the section number of each tab
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index); // Gets the section number (what tab you are on) and sets it in the holder
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_your_carpool_list, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s); // Uses the holder pageViewModel to get the text stored in it and set it to the textView
            }
        });
        return root;
    }
}
