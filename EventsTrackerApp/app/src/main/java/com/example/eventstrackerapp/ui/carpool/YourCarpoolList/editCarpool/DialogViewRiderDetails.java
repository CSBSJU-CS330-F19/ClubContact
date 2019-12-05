package com.example.eventstrackerapp.ui.carpool.YourCarpoolList.editCarpool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.entities.Car;
import com.example.eventstrackerapp.ui.carpool.entities.Carpool;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DialogViewRiderDetails extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "DialogViewRiderDetails";

    private TextView mTitleDVRD, mEventDVRD, mDriverDVRD, mVehicleDVRD, mSeatTotalDVRD,
            mSeatRemDVRD, mPickupLocDVRD, mDropoffLocDVRD, mPickupTimeDVRD, mDescriptionDVRD;
    private TextView mClose;
    private TextView mWarning;

    private Carpool carpool = new Carpool();

    public DialogViewRiderDetails(){
    }

    public DialogViewRiderDetails(Carpool carpool, CollectionReference carpoolCollection){
        this.carpool = carpool;
        // Notify the user if the carpool has been cut off the global list
        carpoolCollection.document(carpool.getCarpoolID()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if(!documentSnapshot.exists()){
                    mWarning.setVisibility(View.VISIBLE);
                } else {
                    mWarning.setVisibility(View.GONE);
                }
            }
        });
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
        View view = inflater.inflate(R.layout.dialog_view_rider_details, container, false);

        mTitleDVRD = view.findViewById(R.id.dia_rider_title);
        mEventDVRD = view.findViewById(R.id.dia_rider_event);
        mDriverDVRD = view.findViewById(R.id.dia_rider_driver);
        mVehicleDVRD = view.findViewById(R.id.dia_rider_vehicle);
        mSeatTotalDVRD = view.findViewById(R.id.dia_rider_seat_total);
        mSeatRemDVRD = view.findViewById(R.id.dia_rider_seat_rem);
        mPickupLocDVRD = view.findViewById(R.id.dia_rider_pickup_location);
        mDropoffLocDVRD = view.findViewById(R.id.dia_rider_dropoff_location);
        mPickupTimeDVRD = view.findViewById(R.id.dia_rider_pickup_time);
        mDescriptionDVRD = view.findViewById(R.id.dia_rider_description);
        mClose = view.findViewById(R.id.dia_rider_close);
        mWarning = view.findViewById(R.id.warning_does_not_exist_in_database);

        mClose.setOnClickListener(this);

        // ==== Set the TextViews ====

        // Set the Seats
        int totalSeats = carpool.getCar().getSeats();
        int takenSeats = carpool.getCar().getRiders().size();
        int remainingSeats = totalSeats - takenSeats;
        mSeatTotalDVRD.setText(totalSeats+"");
        mSeatRemDVRD.setText(remainingSeats+"");
        // Set the Title
        mTitleDVRD.setText(carpool.getCarpoolTitle());
        // Set the Description
        mDescriptionDVRD.setText(carpool.getDescription());
        // Set the Event Title
        mEventDVRD.setText(carpool.getEvent().getTitle());
        // Set the Driver Name
        mDriverDVRD.setText(carpool.getCar().getDriver().getDriverName());
        // Set the Vehicle Type
        mVehicleDVRD.setText(carpool.getCar().getType());
        // Set the Pickup Location
        mPickupLocDVRD.setText(carpool.getCar().getAllPickUpLocation()); // misspelling on *all; it's actually just one
        // Set the Dropoff Location
        mDropoffLocDVRD.setText(carpool.getCar().getAllDropOffLocation());
        // Set the Pickup Time/Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma");

        Date dateAndTime = carpool.getCar().getAllPickUpTime();

        String date = dateFormat.format(dateAndTime);
        String time = timeFormat.format(dateAndTime);

        mPickupTimeDVRD.setText(date + "\n" + time);

        getDialog().setTitle("Rider Information");

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dia_rider_close:
                getDialog().dismiss();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
