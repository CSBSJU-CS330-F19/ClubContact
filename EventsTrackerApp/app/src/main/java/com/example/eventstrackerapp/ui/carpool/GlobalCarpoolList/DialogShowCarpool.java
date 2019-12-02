package com.example.eventstrackerapp.ui.carpool.GlobalCarpoolList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.entities.Carpool;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DialogShowCarpool extends DialogFragment implements View.OnClickListener{

    private TextView mTitleDSC, mEventDSC, mDriverDSC, mVehicleDSC, mSeatTotalDSC, mSeatRemDSC, mPickupLocDSC, mDropoffLocDSC, mPickupTimeDSC;
    private TextView mClose, nJoin;;

    private FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CollectionReference userCollection = firebaseFirestore.collection("Users");
    private CollectionReference carpoolsAsRider = userCollection.document(firebaseAuth.getCurrentUser().getUid()).collection("CarpoolsAsRider");

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
        mClose = view.findViewById(R.id.dia_global_close);
        nJoin = view.findViewById(R.id.dia_global_join);

        mClose.setOnClickListener(this);
        nJoin.setOnClickListener(this);

        getDialog().setTitle("Carpool Information");

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dia_global_close:
                getDialog().dismiss();
                break;
            case R.id.dia_global_join:
                carpoolsAsRider.document(carpool.getCarpoolID()).set(carpool);
                getDialog().dismiss();
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
