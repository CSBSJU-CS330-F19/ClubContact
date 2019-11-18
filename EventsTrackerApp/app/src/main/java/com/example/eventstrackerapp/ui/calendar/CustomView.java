package com.example.eventstrackerapp.ui.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

public class CustomView extends LinearLayout {

    /** INSTANCE VARIABLES */
    private static final String TAG = "CustomView";
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private static final int MAX_CALENDAR_COLUMNS = 42;

    // Calendar Widgets
    private ImageView previousButton, nextButton;
    private GridView gridView;
    private TextView currentDate;
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private CalendarAdapter mAdapter;

    // Firebase instances
    private FirebaseFirestore firebaseFirestore;

    /** CONSTRUCTORS */
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setGridCellClickEvents();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr){ super(context, attrs, defStyleAttr); }

    /** METHODS */
    private void initializeUILayout(){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout, this);
        previousButton = view.findViewById(R.id.previous_month);
        nextButton = view.findViewById(R.id.next_month);
        currentDate = view.findViewById(R.id.display_current_date); // Month and Year
        gridView = view.findViewById(R.id.calendar_grid);

    }

    private void setUpCalendarAdapter(){
        GridView gridView = findViewById(R.id.calendar_grid);

        // Objective: Get the date of the very first cell in the Calendar (even if the date is not in the current Calendar, we gray it out then)
        // Clone the Calendar to make edits while preserving the real-time one (cal)
        Calendar mCal = (Calendar) cal.clone(); // We make a clone of the real-time calendar (cal) because we need to save our edits in mCal to build the calendar of the current month
        // Reset the current day to the first day of the month
        mCal.set(Calendar.DAY_OF_MONTH, 1); // Sets the current day of the month to 1 which is the first day of the month
        // Tell us how many cells to go back to get to the very first cell
        int lastDayOfThePrevMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1; // Day_of_Week: 1=Sun, 2=Mon, 3=Tues ... 7=Sat. For Nov it is 6 - 1 = 5 (we need this to tell us how many cells to go back to get to the very first cell)
        // Get the day of the very first cell in the Calendar of the current Month
        mCal.add(Calendar.DAY_OF_MONTH, -lastDayOfThePrevMonth); // Subtract the lastDayOfThePrevMonth from the current day of the month (which is 1).
                                                                 // Note: lastDayOfThePrevMonth is not 30 or 31 but the Day_of_Week - 1 (within range of 0 - 6)
                                                                 // Subtracting 1 - 5 will not give you -4 but instead 27 which if you see on Nov 2019 Calendar is the very first cell in the Nov Calendar even though 27 is referring to Oct

        // Add all the dates of the Current Month into the ArrayList<Date>
        // If the current month is Nov2019, it will read from Oct27 to Dec7
        List<Date> cellDates = new ArrayList<Date>();
        while(cellDates.size() < MAX_CALENDAR_COLUMNS){
            cellDates.add(mCal.getTime()); // Example: Mon Nov 11 17:18:47 CST 2019
            mCal.add(Calendar.DAY_OF_MONTH,1); // Increments the Day of the Month from 27 to 28 ... 8 if Current Month is Nov. (Does not add 8)
        }

        // Set the Month and Year in the TextView
        String sDate = dateFormat.format(cal.getTime()); // Takes the real-time date (Mon Nov 11 17:18:47 CST 2019) and extracts the Month and Year and then formats it like November 2019
        currentDate.setText(sDate);

        //
        mAdapter = new CalendarAdapter(context, cellDates, cal);
        gridView.setAdapter(mAdapter);

    }

    private void setPreviousButtonClickEvent(){
        previousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }

    private void setNextButtonClickEvent(){
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }

    private void setGridCellClickEvents(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "Clicked " + position, Toast.LENGTH_LONG).show();
            }
        });
    }
}
