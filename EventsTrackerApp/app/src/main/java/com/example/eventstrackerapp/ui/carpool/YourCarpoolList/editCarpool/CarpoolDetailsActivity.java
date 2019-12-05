package com.example.eventstrackerapp.ui.carpool.YourCarpoolList.editCarpool;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.DriverTab;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.addCarpool.DialogAddCarpool;
import com.example.eventstrackerapp.ui.carpool.entities.Driver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class CarpoolDetailsActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "CarpoolDetailsActivity";

    // widgets
    private TextView mTitleTV, mEventTV, mDriverTV, mVehicleTV, mSeatTV, mPickupLocTV, mDropoffLocTV, mPickupTimeTV, mDescriptionTV;
    private EditText mTitleET, mEventET, mDriverET, mVehicleET, mSeatET, mPickupLocET, mDropoffLocET, mPickupTimeET, mDescriptionET;
    private FloatingActionButton mEdit, mEditConfirm;

    // dialog
    private AlertDialog.Builder builderEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_details);

        init();
    }

    public void init(){
        this.mTitleTV = findViewById(R.id.carpool_details_title_tv);
        this.mEventTV = findViewById(R.id.carpool_details_event_tv);
        this.mDriverTV = findViewById(R.id.carpool_details_driver_tv);
        this.mVehicleTV = findViewById(R.id.carpool_details_vehicle_tv);
        this.mSeatTV = findViewById(R.id.carpool_details_seats_tv);
        this.mPickupLocTV = findViewById(R.id.carpool_details_pickuploc_tv);
        this.mDropoffLocTV = findViewById(R.id.carpool_details_dropoffloc_tv);
        this.mPickupTimeTV = findViewById(R.id.carpool_details_time_date_tv);
        this.mDescriptionTV = findViewById(R.id.carpool_details_description_tv);

        this.mTitleET = findViewById(R.id.carpool_details_title_et);
        this.mEventET = findViewById(R.id.carpool_details_event_et);
        this.mDriverET = findViewById(R.id.carpool_details_driver_et);
        this.mVehicleET = findViewById(R.id.carpool_details_vehicle_et);
        this.mSeatET = findViewById(R.id.carpool_details_seats_et);
        this.mPickupLocET = findViewById(R.id.carpool_details_pickuploc_et);
        this.mDropoffLocET = findViewById(R.id.carpool_details_dropoffloc_et);
        this.mPickupTimeET = findViewById(R.id.carpool_details_time_date_et);
        this.mDescriptionET = findViewById(R.id.carpool_details_description_et);

        this.mEdit = findViewById(R.id.carpool_details_edit);
        this.mEditConfirm = findViewById(R.id.carpool_details_edit_confirm);

        this.mEdit.setOnClickListener(this);
        this.mEditConfirm.setOnClickListener(this);

        // Set up the dialog for the edit button
        this.builderEdit = new AlertDialog.Builder(this);

        builderEdit.setCancelable(true);
        builderEdit.setTitle("Confirm Change");
        builderEdit.setMessage("Are you sure you want to save changes?");
        builderEdit.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Todo: save changes to database
                editCarpoolConfirm();
            }
        });
        builderEdit.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }

    public void setCarpoolInfo(String title, Event event, Driver driver, String car, String seatNum, String pLoc, String dLoc, Date time, String desc){

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.carpool_details_edit:
                editCarpool();
                break;
            case R.id.carpool_details_edit_confirm:
                this.builderEdit.create().show();
                break;
        }
    }

    public void editCarpool(){

        Log.d(TAG, "editCarpool: ");

        // Make the FAB gone and the other FAB visible
        mEdit.hide();
        mEditConfirm.show();

        // Set current information into EditTexts
        this.mTitleET.setText(this.mTitleTV.getText());
        this.mEventET.setText(this.mEventTV.getText());
        this.mDriverET.setText(this.mDriverTV.getText());
        this.mVehicleET.setText(this.mVehicleTV.getText());
        this.mSeatET.setText(this.mSeatTV.getText());
        this.mPickupLocET.setText(this.mPickupLocTV.getText());
        this.mDropoffLocET.setText(this.mDropoffLocTV.getText());
        this.mPickupTimeET.setText(this.mPickupTimeTV.getText());
        this.mDescriptionET.setText(this.mDescriptionTV.getText());

        // Make the EditTexts visible
        this.mTitleET.setVisibility(View.VISIBLE);
        this.mEventET.setVisibility(View.VISIBLE);
        this.mDriverET.setVisibility(View.VISIBLE);
        this.mVehicleET.setVisibility(View.VISIBLE);
        this.mSeatET.setVisibility(View.VISIBLE);
        this.mPickupLocET.setVisibility(View.VISIBLE);
        this.mDropoffLocET.setVisibility(View.VISIBLE);
        this.mPickupTimeET.setVisibility(View.VISIBLE);
        this.mDescriptionET.setVisibility(View.VISIBLE);

        // Make the TextViews gone
        this.mTitleTV.setVisibility(View.GONE);
        this.mEventTV.setVisibility(View.GONE);
        this.mDriverTV.setVisibility(View.GONE);
        this.mVehicleTV.setVisibility(View.GONE);
        this.mSeatTV.setVisibility(View.GONE);
        this.mPickupLocTV.setVisibility(View.GONE);
        this.mDropoffLocTV.setVisibility(View.GONE);
        this.mPickupTimeTV.setVisibility(View.GONE);
        this.mDescriptionTV.setVisibility(View.GONE);
    }

    public void editCarpoolConfirm(){
        // Save into database

        // Set new information into TextViews
        this.mTitleTV.setText(this.mTitleET.getText());
        this.mEventTV.setText(this.mEventET.getText());
        this.mDriverTV.setText(this.mDriverET.getText());
        this.mVehicleTV.setText(this.mVehicleET.getText());
        this.mSeatTV.setText(this.mSeatET.getText());
        this.mPickupLocTV.setText(this.mPickupLocET.getText());
        this.mDropoffLocTV.setText(this.mDropoffLocET.getText());
        this.mPickupTimeTV.setText(this.mPickupTimeET.getText());
        this.mDescriptionTV.setText(this.mDescriptionET.getText());


        // Make the EditTexts visible
        this.mTitleET.setVisibility(View.GONE);
        this.mEventET.setVisibility(View.GONE);
        this.mDriverET.setVisibility(View.GONE);
        this.mVehicleET.setVisibility(View.GONE);
        this.mSeatET.setVisibility(View.GONE);
        this.mPickupLocET.setVisibility(View.GONE);
        this.mDropoffLocET.setVisibility(View.GONE);
        this.mPickupTimeET.setVisibility(View.GONE);
        this.mDescriptionET.setVisibility(View.GONE);

        // Make the TextViews gone
        this.mTitleTV.setVisibility(View.VISIBLE);
        this.mEventTV.setVisibility(View.VISIBLE);
        this.mDriverTV.setVisibility(View.VISIBLE);
        this.mVehicleTV.setVisibility(View.VISIBLE);
        this.mSeatTV.setVisibility(View.VISIBLE);
        this.mPickupLocTV.setVisibility(View.VISIBLE);
        this.mDropoffLocTV.setVisibility(View.VISIBLE);
        this.mPickupTimeTV.setVisibility(View.VISIBLE);
        this.mDescriptionTV.setVisibility(View.VISIBLE);

        // Make the FAB gone and the other FAB visible
        mEdit.show();
        mEditConfirm.hide();
    }

}
