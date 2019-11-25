package com.example.eventstrackerapp.ui.home.upcoming.details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eventstrackerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EventDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    // ========================= INSTANCE VARIABLES =========================
    private static final String TAG = "EventDetailsActivity";
    FirebaseFirestore firebaseFirestore;
    private CollectionReference eventsRef;
    LinearLayout hostsLayout;
    private FloatingActionButton calendarFab;
    private FloatingActionButton rsvpFab;
    private FloatingActionButton carpoolFab;


    // ========================= CREATE METHOD =========================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        firebaseFirestore = FirebaseFirestore.getInstance();
        eventsRef = firebaseFirestore.collection("Events");
        hostsLayout = findViewById(R.id.event_details_hosts_layout);

        // Set up the floating action buttons and their click listeners
        calendarFab = findViewById(R.id.fab_add_to_calendar);
        calendarFab.setOnClickListener(this);
        rsvpFab = findViewById(R.id.fab_add_to_rsvp);
        rsvpFab.setOnClickListener(this);
        carpoolFab = findViewById(R.id.fab_add_to_carpool);
        carpoolFab.setOnClickListener(this);

        // FETCH THE INFORMATION FROM HOMEFRAGMENT.JAVA
        Intent in = getIntent();
        int index = in.getIntExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_INDEX", -1);
        String title = in.getStringExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_NAME");
        String location = in.getStringExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_LOCATION");
        String date = in.getStringExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_DATE");
        String time = in.getStringExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_TIME");
        String eventID = in.getStringExtra("com.example.eventstrackerapp.ui.home.upcoming.details.EVENT_ID");


        if(index > -1){ // if the user chooses an event that exists in the homepage
            setEventInfo(title, location, date, time, eventID);
        }
    }

    // ========================= OTHER METHODS =========================
    private void setEventInfo(String title, String location, String date, String time, String eventID){
        // GET THE TEXTVIEWS OF THE TITLE, LOCATION, START AND END TIMES, AND DESCRIPTION
        TextView titleTV = findViewById(R.id.event_details_name);
        TextView locationTV = findViewById(R.id.event_details_location);
        TextView startEndTV = findViewById(R.id.event_details_time_date);
        final TextView descriptionTV = findViewById(R.id.event_details_description);

        // SETUP THE TITLE, LOCATION, START, AND END TIMES
        titleTV.setText(title);
        locationTV.setText(location);
        startEndTV.setText(date + "\n" + time);

        // USE THE EVENTID TO ACCESS THE DATABASE FOR THE EVENT'S DESCRIPTION AND HOSTS
        eventsRef.document(eventID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();

                // ADD DESCRIPTION
                descriptionTV.setText((String)documentSnapshot.get("description"));

                // ADD HOST NAMES AND THEIR SUBSCRIPTION BUTTONS
                ArrayList<String> hosts = (ArrayList<String>) documentSnapshot.get("hosts");
                EventDetailsHostAdapter eventDetailsHostAdapter = new EventDetailsHostAdapter(getApplicationContext(), hosts);
                for(int i=0; i<hosts.size(); i++){
                    View v = eventDetailsHostAdapter.getView(i, null, null);
                    hostsLayout.addView(v);
                }
            }
        });
    }

    public void fabAddToCalendar(View view){
        Snackbar.make(view, "The event has been added to your calendar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void fabRSVP(View view){
        Snackbar.make(view, "The event has been added to your rsvp-list", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void fabAddToCarpool(View view){
        Snackbar.make(view, "The event has been added to your carpool list", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_add_to_calendar:
                fabAddToCalendar(v);
                break;
            case R.id.fab_add_to_rsvp:
                fabRSVP(v);
                break;
            case R.id.fab_add_to_carpool:
                fabAddToCarpool(v);
                break;
            default:
                break;
        }

    }
}
