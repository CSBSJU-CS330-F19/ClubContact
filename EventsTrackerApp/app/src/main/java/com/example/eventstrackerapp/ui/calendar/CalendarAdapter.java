package com.example.eventstrackerapp.ui.calendar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarAdapter extends ArrayAdapter {

    private static final String TAG = "CalendarAdapter";
    private LayoutInflater mInflater;
    private List<Date> monthDates;
    private Calendar currentDate;
    private List<Event> allEvents;

    public CalendarAdapter(Context context, List<Date> monthDates, Calendar currentDate, final List<Event> allEvents){
        super(context, R.layout.calendar_cell_layout);
        this.monthDates = monthDates;
        this.currentDate = currentDate;
        this.allEvents = allEvents;
        this.mInflater = LayoutInflater.from(context);
    }

    /**
     * Return the number of cells to render
     * @return
     */
    @Override
    public int getCount() {
        return monthDates.size();
    }

    /**
     * Returns what event the user selected
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return monthDates.get(position);
    }

    @Override
    public int getPosition(Object item) {
        return monthDates.indexOf(item);
    }

    /**
     * This is a dummy textView for the cell for now
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date mDate = monthDates.get(position); // Get the current Month
        Calendar dateCal = Calendar.getInstance(); // Get a calendar for the dates of month, year, and days
        dateCal.setTime(mDate); // Set the current Month into the Calendar

        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH); // Get the day of the Month
        int displayMonth = dateCal.get(Calendar.MONTH); // Get the Month the Calendar/Screen is currently showing
        int displayYear = dateCal.get(Calendar.YEAR); // Get the Year the Calendar/Screen is currently showing

        int currentMonth = currentDate.get(Calendar.MONTH); // Get the actual current Month
        int currentYear = currentDate.get(Calendar.YEAR); // Get the actual current Year

        // Setup the layout in case if it is not
        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.calendar_cell_layout, parent, false);
        }

        // Set the color of the Days in the grid according to the current Month and Year
        if(displayMonth == currentMonth && displayYear == currentYear){
            view.setBackgroundColor(Color.parseColor("#D81B60")); // Relevant Days
        } else {
            view.setBackgroundColor(Color.parseColor("#cccccc")); // Irrelevant Days
        }

        // Add the day to the Calendar cell
        TextView cellNumber = (TextView)view.findViewById(R.id.date);
        cellNumber.setText(String.valueOf(dayValue));

        // Add the events to the calendar

        return view;
    }
}
