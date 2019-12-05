package com.example.eventstrackerapp.ui.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.home.upcoming.details.EventDetailsActivity;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewCalendarCellActivity extends AppCompatActivity {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Event> myEventsSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_calendar_cell);

        initializeRecycler();
        viewClickListener();
        loadData();
    }

    public void initializeRecycler(){
        mContext = this;

        mRecyclerView = findViewById(R.id.upcoming_events_recycler_view);

        // Use the Linear layout-manager
        // This specifies the what orientation the layout is. Here, we want vertical/up-down
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify the Adapter (RecyclerViewEventsAdapter) and set it to the RecyclerView
        // Note: the adapter will set an empty list of events. We load the events in loadData()
        myEventsSet = new ArrayList<>();
        mAdapter = new ViewCalendarCellAdapter(myEventsSet);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void viewClickListener(){
        ((ViewCalendarCellAdapter)mAdapter).setOnItemClickListener(new ViewCalendarCellAdapter.ListItemClickListener6() {
            @Override
            public void onItemClick(int position, View v) {
                viewEventDetails(position, v);
            }
        });
    }

    public void viewEventDetails(int position, View view){
        Intent gotoDetailsPage = new Intent(ViewCalendarCellActivity.this, EventDetailsActivity.class);

        // Set the format for the Date so that we can convert the Date into a String
        SimpleDateFormat simpleStartDateFormat = new SimpleDateFormat("MMM dd, yyyy - ");
        SimpleDateFormat simpleStartTimeFormat = new SimpleDateFormat("hh:mma - ");
        SimpleDateFormat simpleEndDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat simpleEndTimeFormat = new SimpleDateFormat("hh:mma");
        String sdate = simpleStartDateFormat.format(myEventsSet.get(position).getStart());
        String stime = simpleStartTimeFormat.format(myEventsSet.get(position).getStart());
        String edate = simpleEndDateFormat.format(myEventsSet.get(position).getEnd());
        String etime = simpleEndTimeFormat.format(myEventsSet.get(position).getEnd());

        // Send information on Event to the EventDetailsActivity.java class so it can set up the details page
        gotoDetailsPage.putExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_INDEX", position);
        gotoDetailsPage.putExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_NAME", myEventsSet.get(position).getTitle());
        gotoDetailsPage.putExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_LOCATION", myEventsSet.get(position).getLocation());
        gotoDetailsPage.putExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_DATE", sdate + edate);
        gotoDetailsPage.putExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_TIME", stime + etime);
        gotoDetailsPage.putExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_ID", myEventsSet.get(position).getEventID());

        startActivity(gotoDetailsPage);
    }

    public void loadData(){

    }

}
