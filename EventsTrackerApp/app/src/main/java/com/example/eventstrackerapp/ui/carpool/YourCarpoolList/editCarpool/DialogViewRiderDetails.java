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

public class DialogViewRiderDetails extends DialogFragment implements View.OnClickListener {

    private TextView mTitleDVRD, mEventDVRD, mDriverDVRD, mVehicleDVRD, mSeatTotalDVRD,
            mSeatRemDVRD, mPickupLocDVRD, mDropoffLocDVRD, mPickupTimeDVRD;
    private TextView mClose;

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
        mClose = view.findViewById(R.id.dia_rider_close);

        mClose.setOnClickListener(this);

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
