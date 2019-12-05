package com.example.eventstrackerapp.ui.carpool.GlobalCarpoolList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.profile.User;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.MainYourCarpoolListActivity;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.RiderTab;
import com.example.eventstrackerapp.ui.carpool.entities.Carpool;
import com.example.eventstrackerapp.ui.carpool.entities.Passenger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DialogShowCarpool extends DialogFragment implements View.OnClickListener{

    private TextView mTitleDSC, mEventDSC, mDriverDSC, mVehicleDSC, mSeatTotalDSC, mSeatRemDSC, mPickupLocDSC, mDropoffLocDSC, mPickupTimeDSC, mDescriptionDSC;
    private TextView mClose, nJoin;;

    private FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CollectionReference userCollection = firebaseFirestore.collection("Users");
    private CollectionReference carpoolsAsRider = userCollection.document(firebaseAuth.getCurrentUser().getUid()).collection("CarpoolsAsRider");
    private CollectionReference carpoolsAsDriver = userCollection.document(firebaseAuth.getCurrentUser().getUid()).collection("CarpoolsAsDriver");
    private CollectionReference carpoolCollection = firebaseFirestore.collection("Carpools");

    private Carpool carpool = new Carpool();

    public DialogShowCarpool(){
    }
    public DialogShowCarpool(Carpool carpool){
        this.carpool = carpool;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style, theme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_show_carpool, container, false);

        mTitleDSC = view.findViewById(R.id.dia_global_title);
        mEventDSC = view.findViewById(R.id.dia_global_event);
        mDriverDSC = view.findViewById(R.id.dia_global_driver);
        mVehicleDSC = view.findViewById(R.id.dia_global_vehicle);
        mSeatTotalDSC = view.findViewById(R.id.dia_global_seat_total);
        mSeatRemDSC = view.findViewById(R.id.dia_global_seat_rem);
        mPickupLocDSC = view.findViewById(R.id.dia_global_pickup_location);
        mDropoffLocDSC = view.findViewById(R.id.dia_global_dropoff_location);
        mPickupTimeDSC = view.findViewById(R.id.dia_global_pickup_time);
        mDescriptionDSC = view.findViewById(R.id.dia_global_description);
        mClose = view.findViewById(R.id.dia_global_close);
        nJoin = view.findViewById(R.id.dia_global_join);

        // ==== Set the TextViews ====

        // Set the Seats
        int totalSeats = carpool.getCar().getSeats();
        int takenSeats = carpool.getCar().getRiders().size();
        int remainingSeats = totalSeats - takenSeats;
        mSeatTotalDSC.setText(totalSeats+"");
        mSeatRemDSC.setText(remainingSeats+"");
        // Set the Title
        mTitleDSC.setText(carpool.getCarpoolTitle());
        // Set the Description
        mDescriptionDSC.setText(carpool.getDescription());
        // Set the Event Title
        mEventDSC.setText(carpool.getEvent().getTitle());
        // Set the Driver Name
        mDriverDSC.setText(carpool.getCar().getDriver().getDriverName());
        // Set the Vehicle Type
        mVehicleDSC.setText(carpool.getCar().getType());
        // Set the Pickup Location
        mPickupLocDSC.setText(carpool.getCar().getAllPickUpLocation()); // misspelling on *all; it's actually just one
        // Set the Dropoff Location
        mDropoffLocDSC.setText(carpool.getCar().getAllDropOffLocation());
        // Set the Pickup Time/Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma");

        String date = dateFormat.format(carpool.getCar().getAllPickUpTime());
        String time = timeFormat.format(carpool.getCar().getAllPickUpTime());

        mPickupTimeDSC.setText(date + "\n" + time);

        mClose.setOnClickListener(this);
        nJoin.setOnClickListener(this);

        getDialog().setTitle("Carpool Information");

        return view;
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.dia_global_close:
                getDialog().dismiss();
                break;
            case R.id.dia_global_join:

                final String carpoolID = carpool.getCarpoolID();

                // Check first if the carpool you want to join is NOT the one you created to be a driver of
                carpoolsAsDriver.document(carpoolID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if(documentSnapshot.exists()){ // if you are a driver for this carpool
                                Snackbar.make(v, "You are a Driver for this Carpool", Snackbar.LENGTH_LONG).show();
                            } else { //if you are not a driver of the carpool...

                                carpoolsAsRider.document(carpoolID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if(task.isSuccessful()){
                                            DocumentSnapshot documentSnapshot = task.getResult();
                                            if(documentSnapshot.exists()){ // if you already joined this carpool
                                                Snackbar.make(v, "You already joined this Carpool", Snackbar.LENGTH_LONG).show();
                                            } else { // if you have not yet joined this carpool

                                                // Add the current user as a passenger to the Car's passenger list
                                                // Create a new passenger using the information of the current user in the database
                                                final String userID = firebaseAuth.getCurrentUser().getUid();
                                                userCollection.document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        User currentUser = documentSnapshot.toObject(User.class);
                                                        Passenger newPassenger = new Passenger(userID,
                                                                currentUser.getFirstName() + " " + currentUser.getLastName(),
                                                                carpool.getCar().getAllPickUpLocation(), carpool.getCar().getAllPickUpTime(),
                                                                carpool.getCar().getAllDropOffLocation());
                                                        carpool.getCar().addRiders(newPassenger);

                                                        // Access the Carpool Collection to update the carpool's passenger list
                                                        carpoolCollection.document(carpoolID).set(carpool);

                                                        // Add to the User's CarpoolAsRider Collection in the database
                                                        carpoolsAsRider.document(carpoolID).set(carpool);

                                                        Snackbar.make(getView(), "You have joined the Carpool", Snackbar.LENGTH_LONG).show();
                                                        Toast.makeText(getContext(), "You have joined the Carpool", Toast.LENGTH_LONG).show();

                                                        getDialog().dismiss();
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
