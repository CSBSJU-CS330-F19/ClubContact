package com.example.eventstrackerapp.ui.carpool.YourCarpoolList;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.profile.User;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.addCarpool.DialogAddCarpool;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.editCarpool.CarpoolDetailsActivity;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors.RecyclerViewCampusAdapter;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors.SwipeRevealLayout;
import com.example.eventstrackerapp.ui.carpool.entities.Car;
import com.example.eventstrackerapp.ui.carpool.entities.Carpool;
import com.example.eventstrackerapp.ui.carpool.entities.Driver;
import com.example.eventstrackerapp.ui.carpool.entities.Passenger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class DriverTab extends Fragment implements DialogAddCarpool.CarpoolDialogListener{

    private static final String TAG = "DriverTab";

    private ArrayList<Carpool> mCarpoolSet;

    private FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CollectionReference eventCollection = firebaseFirestore.collection("Events");
    private CollectionReference carpoolCollection = firebaseFirestore.collection("Carpools");
    private CollectionReference userCollection = firebaseFirestore.collection("Users");


    private RecyclerViewCampusAdapter listAdapter;



    // Singleton?
    public static DriverTab instance(){ return new DriverTab(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_carpool_list_driver_tab, container, false);
        init(view);
        return view;
    }


    public void init(View view){
        /**
         * INITIALIZE
         */
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.listRecyclerView_yourCarpoolList_campus);

        mCarpoolSet = new ArrayList<>();

        listAdapter = new RecyclerViewCampusAdapter(mCarpoolSet);
        recyclerView.setAdapter(listAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        // Sets up the Floating Action Button
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddCarpool dialogAddCarpool = new DialogAddCarpool();
                dialogAddCarpool.setTargetFragment(DriverTab.this, 1);
                dialogAddCarpool.show(getFragmentManager(), "New Carpool"); // getFragmentManager -> getSupportFragmentManager in Activity class
            }
        });



        /**
         * VIEW_CLICK_LISTENER
         */
        ((RecyclerViewCampusAdapter)listAdapter).setOnItemClickListener(new RecyclerViewCampusAdapter.ListItemClickListener2() {
            @Override
            public void onItemClick(int position, View v) {
                //Toast.makeText(getActivity(), "position: "+position, Toast.LENGTH_SHORT).show();
                //viewCarpoolDetails();
            }

            @Override
            public void onDeleteClick(int position) {
                deleteCarpool(position);
            }

            @Override
            public void onInfoClick() {
                viewCarpoolDetails();
            }
        });



        /**
         * LOAD DATA
         * loads data from the entire global area as of the moment
         */

        userCollection.document(firebaseAuth.getCurrentUser().getUid()).collection("CarpoolsAsDriver").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot carpoolDriver : queryDocumentSnapshots){
                    mCarpoolSet.add(carpoolDriver.toObject(Carpool.class));
                }
                listAdapter.notifyDataSetChanged();
            }
        });
    }



    public void viewCarpoolDetails(){
        startActivity(new Intent(getActivity(), CarpoolDetailsActivity.class));
    }

    public void deleteCarpool(int position){

        //Todo: delete from GLOBAL database
        final Carpool carpool = listAdapter.mYourCarpoolList.get(position);
        carpoolCollection.document(carpool.getCarpoolID()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        //TOdo: Delete from user carpoolAsDriver collection
                        userCollection.document(firebaseAuth.getCurrentUser().getUid())
                                .collection("CarpoolsAsDriver").document(carpool.getCarpoolID()).delete();

                        Toast.makeText(getActivity(), "Deletion is successful", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Deletion did not work", Toast.LENGTH_SHORT).show();
            }
        });

        // Remove from screen
        listAdapter.mYourCarpoolList.remove(position);
        listAdapter.notifyItemRemoved(position);
        listAdapter.notifyItemRangeChanged(position, listAdapter.getItemCount());

    }

    public void addCarpool(final String title, final String eventID, final String driver, final String vehicle, final int seat,
                           final String pickuploc, final String dropoffloc, final String time, final String date){

        // Find the event from the database and save it as an Event Object
        eventCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    Event chosenEvent = new Event(); // this is a placeholder for the event the user chooses

                    for(DocumentSnapshot eventDocument : task.getResult()){ // find the event the user chose
                        Event event = eventDocument.toObject(Event.class);
                        if(event.getEventID().equals(eventID)){
                            chosenEvent = event; // assign the event to the placeholder
                        }
                    }

                    // ------------------------------------------------------------------------------------------
                    Driver newDriver = new Driver("", driver);

                    ArrayList<Passenger> passengerArrayList = new ArrayList<>(); // make an empty list for passengers
                    // ------------------------------------------------------------------------------------------
                    String[] dateAttr = date.split("/");
                    String[] timeAttr = time.split(":");

                    int month = Integer.parseInt(dateAttr[0]);
                    int day = Integer.parseInt(dateAttr[1]);
                    int year = Integer.parseInt(dateAttr[2]);

                    int hour = Integer.parseInt(timeAttr[0]);

                    String minuteString = timeAttr[1];
                    int minute = 0;
                    if(minuteString.contains("p")){
                        // Get the first two characters of the time string
                        String m = Character.toString(minuteString.charAt(0));
                        m += Character.toString(minuteString.charAt(1));

                        // Make the string into an int
                        minute = Integer.parseInt(m);

                        // Add 12 to make it into military time since it is pm
                        hour += 12;

                    } else if(minuteString.contains("a")){
                        // Get the first two characters of the time string
                        String m = Character.toString(minuteString.charAt(0));
                        m += Character.toString(minuteString.charAt(1));

                        // Make the string into an int
                        minute = Integer.parseInt(m);
                    }

                    Date pickupTimeDate = new Date(year, month, day, hour, minute);
                    // ------------------------------------------------------------------------------------------
                    final Car newCar = new Car("", newDriver, passengerArrayList, seat, vehicle, pickuploc, dropoffloc, pickupTimeDate);
                    // ------------------------------------------------------------------------------------------

                    final Carpool newCarpool = new Carpool("", title, newCar, chosenEvent);
                    mCarpoolSet.add(newCarpool);

                    //Todo: add to global database

                    // Create a new Document so we can get the new id for the carpool
                    DocumentReference newCarpoolDocRef = carpoolCollection.document();

                    // Set the id into the newCarpool
                    newCarpool.setCarpoolID(newCarpoolDocRef.getId());


                    newCarpoolDocRef.set(newCarpool).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                //Todo: Add to user's carpool list as driver
                                DocumentReference carpoolAsDriverRef = userCollection.document(firebaseAuth.getCurrentUser().getUid()).collection("CarpoolsAsDriver").document(newCarpool.getCarpoolID());
                                carpoolAsDriverRef.set(newCarpool);

                                listAdapter.notifyItemInserted(mCarpoolSet.size());

                            } else {
                                Toast.makeText(getActivity(), "Adding did not work", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }
        });
    }



    /**
     * Interface method to get information from dialog to make a new Carpool
     * @param title
     */
    @Override
    public void sentInput(String title, String eventID, String driver, String vehicle, int seat,
                          String pickuploc, String dropoffloc, String time, String date) {
        addCarpool(title, eventID, driver, vehicle, seat, pickuploc, dropoffloc, time, date);
    }

}
