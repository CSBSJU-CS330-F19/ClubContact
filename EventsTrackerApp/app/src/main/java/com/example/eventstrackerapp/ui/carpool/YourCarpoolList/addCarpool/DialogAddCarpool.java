package com.example.eventstrackerapp.ui.carpool.YourCarpoolList.addCarpool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DialogAddCarpool extends DialogFragment implements
        View.OnClickListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener,
        DialogAddEvent.CarpoolDialogListener2{

    private static final String TAG = "DialogAddCarpool";

    // widgets
    private EditText mTitle, mDriver, mVehicle, mSeatNumber, mPickupLocation, mDropoffLocation, mDescription;
    private TextView mSetTime, mSetDate, mSetEvent, mPickupTime, mPickupDate, mEvent, mCreate, mCancel;
    private String eventID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Holo_Light_Dialog;
        setStyle(style, theme);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_carpool, container, false);

        mTitle = view.findViewById(R.id.dia_add_carpool_title);
        mDriver = view.findViewById(R.id.dia_add_carpool_driver);
        mVehicle = view.findViewById(R.id.dia_add_carpool_vehicle);
        mSeatNumber = view.findViewById(R.id.dia_add_carpool_seats);
        mPickupLocation = view.findViewById(R.id.dia_add_carpool_pickuploc);
        mDropoffLocation = view.findViewById(R.id.dia_add_carpool_dropoffloc);
        mDescription = view.findViewById(R.id.dia_add_carpool_description);

        mSetTime = view.findViewById(R.id.dia_add_carpool_set_time);
        mSetDate = view.findViewById(R.id.dia_add_carpool_set_date);
        mSetEvent = view.findViewById(R.id.dia_add_carpool_set_event);
        mPickupTime = view.findViewById(R.id.dia_add_carpool_time);
        mPickupDate = view.findViewById(R.id.dia_add_carpool_date);
        mEvent = view.findViewById(R.id.dia_add_carpool_event);
        mCreate = view.findViewById(R.id.dia_add_carpool_create);
        mCancel = view.findViewById(R.id.dia_add_carpool_cancel);

        mSetTime.setOnClickListener(this);
        mSetDate.setOnClickListener(this);
        mSetEvent.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mCreate.setOnClickListener(this);

        getDialog().setTitle("New Carpool");

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dia_add_carpool_set_time:
                showTimePickerDialog(v);
                break;
            case R.id.dia_add_carpool_set_date:
                showDatePickerDialog(v);
                break;
            case R.id.dia_add_carpool_set_event:
                //Todo: show a dialog of all the events
                DialogAddEvent dialogAddEvent = new DialogAddEvent();
                dialogAddEvent.setTargetFragment(DialogAddCarpool.this, 1);
                dialogAddEvent.show(getFragmentManager(), "Select an Event"); // getFragmentManager -> getSupportFragmentManager in Activity class
                break;
            case R.id.dia_add_carpool_create:

                if(
                        this.mTitle.getText().toString().matches("") ||
                        this.mDriver.getText().toString().matches("") ||
                        this.mVehicle.getText().toString().matches("") ||
                        this.mSeatNumber.getText().toString().matches("") ||
                        this.mPickupLocation.getText().toString().matches("") ||
                        this.mDropoffLocation.getText().toString().matches("") ||
                        this.mPickupTime.getText().toString().matches("Pickup Time") ||
                        this.mPickupDate.getText().toString().matches("Pickup Date") ||
                        this.mEvent.getText().toString().matches("Event") ||
                        this.mDescription.getText().toString().matches("")){

                    Toast.makeText(getActivity(), "Fill in Empty Fields", Toast.LENGTH_LONG).show();

                } else {
//                    // Todo: Create a new Carpool Class with information and send it to the DriverTab RecyclerView

                    CarpoolDialogListener listener = getListener();
                    if(listener != null){
                        listener.sentInput(mTitle.getText().toString(),
                                            eventID,
                                            mDriver.getText().toString(),
                                            mVehicle.getText().toString(),
                                            Integer.parseInt(mSeatNumber.getText().toString()),
                                            mPickupLocation.getText().toString(),
                                            mDropoffLocation.getText().toString(),
                                            mPickupTime.getText().toString(),
                                            mPickupDate.getText().toString(),
                                            mDescription.getText().toString());
                    }

                }
                getDialog().dismiss();
                break;
            case R.id.dia_add_carpool_cancel:
                getDialog().dismiss();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //this.mIDriverTab = (IDriverTab)context;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String amPm = "am";
        if(hourOfDay >= 12){
            amPm = "pm";
        }
        if(hourOfDay > 12){
            hourOfDay = hourOfDay - 12;
        } else if(hourOfDay == 0){
            hourOfDay = 12;
        }
        if(minute < 10){
            mPickupTime.setText(hourOfDay + ":" + "0" + minute + amPm);
        } else {
            mPickupTime.setText(hourOfDay + ":" + minute + amPm);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mPickupDate.setText((month+1) + "/" + dayOfMonth + "/" + year);
    }

    /**
     * Inner Classes for TimePickerDialog and DatePickerDialog
     */
    public static class TimePickerFragment extends DialogFragment {
        DialogAddCarpool dialogAddCarpool;

        public TimePickerFragment(){ }

        public TimePickerFragment(DialogAddCarpool dialogAddCarpool){ this.dialogAddCarpool = dialogAddCarpool; }

        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), dialogAddCarpool, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
        }

    }

    public static class DatePickerFragment extends DialogFragment {
        DialogAddCarpool dialogAddCarpool;

        public DatePickerFragment(){ }

        public DatePickerFragment(DialogAddCarpool dialogAddCarpool){ this.dialogAddCarpool = dialogAddCarpool; }

        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), dialogAddCarpool, year, month, day);

        }

    }

    /**
     * These methods show the Time and Date Dialogs
     */

    public void showTimePickerDialog(View view){
        DialogFragment newFragment = new TimePickerFragment(this);
        newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }


    public void showDatePickerDialog(View view){
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }


    /**
     * This interface will allow information to be sent to the RecyclerView located in a fragment
     */
    public interface CarpoolDialogListener{
        // There is new Carpool Information
        // Todo: When the Carpool class changes in terms of its instances, add or delete those instances here
        void sentInput(String title, String eventID, String driver, String vehicle,
                       int seat, String pickuploc, String dropoffloc, String time, String date,
                       String description);
    }

    /**
     * This tries to find a suitable listener. It examines the hosting Fragment and then the Activity.
     * Will return null if fails
     */
    private CarpoolDialogListener getListener(){
        CarpoolDialogListener listener;
        try{
            Fragment OnInputSelected_Fragment = getTargetFragment();
            if(OnInputSelected_Fragment != null){
                listener = (CarpoolDialogListener) OnInputSelected_Fragment;
            } else {
                Activity OnInputSelected_Activity = getActivity();
                listener = (CarpoolDialogListener) OnInputSelected_Activity;
            }
            return listener;
        } catch(ClassCastException cce){
            Log.e("Custom Dialog", "onAttach: ClassCastException: " + cce.getMessage());
        }
        return null;
    }

    /**
     * Interface method to get information from dialog to make a new Carpool
     * @param title
     */
    @Override
    public void sentInput2(String title, String id) {
        mEvent.setText(title);
        eventID = id;
    }

}
