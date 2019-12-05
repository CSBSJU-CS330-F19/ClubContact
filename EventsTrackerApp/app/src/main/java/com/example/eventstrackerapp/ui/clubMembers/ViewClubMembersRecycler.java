package com.example.eventstrackerapp.ui.clubMembers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.profile.User;
import com.example.eventstrackerapp.ui.allUsers.UserAdapter;
import com.example.eventstrackerapp.ui.home.upcoming.RecyclerViewEventsAdapter;
import com.example.eventstrackerapp.ui.home.upcoming.UpcomingEvents;
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

public class ViewClubMembersRecycler extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<User> members;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DocumentSnapshot mLastQueriedDocument; // This works alongside mSwipeRefreshLayout to make sure that upon refresh, there are no duplicates

    private static final String TAG = "ViewClubMembersRecycler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_club_members);

        initializeRecycler(); // setup the recyclerView, layoutManager, and Adapter
        viewClickListener(); // setup the click listener
        loadData(); // load data from database and update the adapter
    }

    public void initializeRecycler(){
        mContext = this;

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = findViewById(R.id.nav_club_members);

        // Use the Linear layout-manager
        // This specifies the what orientation the layout is. Here, we want vertical/up-down
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify the Adapter (RecyclerViewEventsAdapter) and set it to the RecyclerView
        // Note: the adapter will set an empty list of events. We load the events in loadData()
        members = new ArrayList<>();
        mAdapter = new UserAdapter(members);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void viewClickListener(){
        ((RecyclerViewEventsAdapter)mAdapter).setOnItemClickListener(new RecyclerViewEventsAdapter.ListItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // We want to view the details of the event here
                //Toast.makeText(mContext, "position: "+position, Toast.LENGTH_SHORT).show();
                viewUserDetails(position, v);
            }
        });
    }

    private void viewUserDetails(int position, View v) {
        
    }

    public void loadData(){

        //Set up a FirebaseFirestore reference and a CollectionReference
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        CollectionReference usersCollectionRef = firestore.collection("Users");
        Log.d(TAG, "loadData: usersCollectionRef");

        //Make a Query to sort the Events by StartDate
        Query eventsQuery = queryEvents(usersCollectionRef, "start");

        //Bring in the Events from the Firestore and update the Adapter
        eventsQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    // Add the Events from the database to the ArrayList
                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        User user = documentSnapshot.toObject(User.class);
                        members.add(user);
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
