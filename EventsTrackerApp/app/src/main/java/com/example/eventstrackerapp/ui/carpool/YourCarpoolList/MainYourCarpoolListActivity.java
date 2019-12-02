package com.example.eventstrackerapp.ui.carpool.YourCarpoolList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.addCarpool.DialogAddCarpool;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainYourCarpoolListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_your_carpool_list);

        initFragments();

    }

    public void initFragments(){
        // Initializes the adapter that helps with selecting different fragments
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        // Holds the Fragment Screens
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        // Holds the Tabs
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

}
