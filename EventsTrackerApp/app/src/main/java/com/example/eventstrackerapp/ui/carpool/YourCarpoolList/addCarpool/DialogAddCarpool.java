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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eventstrackerapp.R;

import java.text.DateFormat;
import java.util.Calendar;


public class DialogAddCarpool extends DialogFragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "DialogAddCarpool";

    // widgets
    private EditText mTitle, mDriver, mVehicle, mSeatNumber, mPickupLocation, mDropoffLocation;
    private TextView mSetTime, mSetDate, mPickupTime, mPickupDate, mCreate, mCancel;

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

        mSetTime = view.findViewById(R.id.dia_add_carpool_set_time);
        mSetDate = view.findViewById(R.id.dia_add_carpool_set_date);
        mPickupTime = view.findViewById(R.id.dia_add_carpool_time);
        mPickupDate = view.findViewById(R.id.dia_add_carpool_date);
        mCreate = view.findViewById(R.id.dia_add_carpool_create);
        mCancel = view.findViewById(R.id.dia_add_carpool_cancel);

        mSetTime.setOnClickListener(this);
        mSetDate.setOnClickListener(this);
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
            case R.id.dia_add_carpool_create:
                if(mTitle.equals("") || mDriver.equals("") || mVehicle.equals("") ||
                        mSeatNumber.equals("") || mPickupLocation.equals("") ||
                        mDropoffLocation.equals("") || mPickupTime.equals("Pickup Time") || mPickupDate.equals("Pickup Date")){
                    Toast.makeText(getActivity(), "Fill in Empty Fields", Toast.LENGTH_LONG).show();
                } else {
//                    // Todo: Create a new Carpool Class with information and send it to the DriverTab RecyclerView
                    CarpoolDialogListener listener = getListener();
                    if(listener != null){
                        listener.sentInput(mTitle.getText().toString());
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
        void sentInput(String title);
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

}
