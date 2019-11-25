package com.example.eventstrackerapp.ui.home.upcoming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.home.upcoming.details.EventDetailsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UpcomingEvents extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Event> myEventsSet;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DocumentSnapshot mLastQueriedDocument; // This works alongside mSwipeRefreshLayout to make sure that upon refresh, there are no duplicates



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);

        initializeRecycler(); // setup the recyclerView, layoutManager, and Adapter
        viewClickListener(); // setup the click listener
        loadData(); // load data from database and update the adapter
    }

    public void initializeRecycler(){
        mContext = this;

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = findViewById(R.id.upcoming_events_recycler_view);

        // Use the Linear layout-manager
        // This specifies the what orientation the layout is. Here, we want vertical/up-down
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify the Adapter (RecyclerViewEventsAdapter) and set it to the RecyclerView
        // Note: the adapter will set an empty list of events. We load the events in loadData()
        myEventsSet = new ArrayList<>();
        mAdapter = new RecyclerViewEventsAdapter(myEventsSet);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void viewClickListener(){
        ((RecyclerViewEventsAdapter)mAdapter).setOnItemClickListener(new RecyclerViewEventsAdapter.ListItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // We want to view the details of the event here
                //Toast.makeText(mContext, "position: "+position, Toast.LENGTH_SHORT).show();
                viewEventDetails(position, v);
            }
        });
    }

    public void loadData(){

        //Set up a FirebaseFirestore reference and a CollectionReference
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        CollectionReference eventsCollectionRef = firestore.collection("Events");

        //Make a Query to sort the Events by StartDate
        Query eventsQuery = queryEvents(eventsCollectionRef, "start");

        //Bring in the Events from the Firestore and update the Adapter
        eventsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    // Add the Events from the database to the ArrayList
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        Event event = documentSnapshot.toObject(Event.class);
                        myEventsSet.add(event);
                    }
                    // Inside the newly ordered collection, find the last document. This is for refreshing the page
                    if(task.getResult().size() != 0){
                        mLastQueriedDocument = task.getResult().getDocuments().get(task.getResult().size() -1);
                    }
                    // Tell the adapter that the ArrayList<Event> has changed and the recyclerview must be updated
                    mAdapter.notifyDataSetChanged();
                } else { // Else if the task is un-successful
                    Toast.makeText(mContext, "Ordering the Collection Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void viewEventDetails(int position, View view){
        Intent gotoDetailsPage = new Intent(UpcomingEvents.this, EventDetailsActivity.class);

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

    public Query queryEvents(CollectionReference eventsCollectionRef, String field){
        Query eventsQuery = null;
        // If the page is refreshed and the last document is not in order, end at the new last document
        if(mLastQueriedDocument != null){
            eventsQuery = eventsCollectionRef
                    .orderBy(field, Query.Direction.ASCENDING)
                    .startAfter(mLastQueriedDocument);
        } else { // If the page is refreshed and the last document is in order, stay with the document
            eventsQuery = eventsCollectionRef
                    .orderBy(field, Query.Direction.ASCENDING);
        }

        return eventsQuery;
    }
}
