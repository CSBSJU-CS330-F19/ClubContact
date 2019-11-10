package com.example.eventstrackerapp.ui.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventstrackerapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

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
        GridView gridView = (GridView) findViewById(R.id.calendar_grid);

        List<Date> cellDates = new ArrayList<Date>();
        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);

        while(cellDates.size() < MAX_CALENDAR_COLUMNS){
            cellDates.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH,1);
        }

        String sDate = dateFormat.format(cal.getTime());
        currentDate.setText(sDate);

        mAdapter = new CalendarAdapter(context, cellDates, cal, null);
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
