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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference eventsCol;


    public CalendarAdapter(Context context, List<Date> monthDates, Calendar currentDate){
        super(context, R.layout.calendar_cell_layout);
        this.monthDates = monthDates;
        this.currentDate = currentDate;
        this.mInflater = LayoutInflater.from(context);
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.eventsCol = this.firebaseFirestore.collection("Events");
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
     * Returns what cell the user selected
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return monthDates.get(position);
    }

    /**
     * Returns the position of the cell within the Calendar
     * @param item
     * @return
     */
    @Override
    public int getPosition(Object item) {
        return monthDates.indexOf(item);
    }

    /**
     * This method re-iterates 0-41 times to fill out all the cells.
     *
     * On each iteration, it will focus on one cell and find its day, month, and year.
     * This is important to find all three of those, not just the day, because our calendar
     * can hold the days of the prev and next month and year.
     *
     * It uses two calendars:
     *      1) The calendar that reflects the one in the app called dateCal (meaning its scope is
     *              spread from the prev and next month and year such as Oct, Nov, and Dec if
     *              the current month is Nov). This calendar has been edited to reflect our
     *              app's calendar.
     *      2) The calendar that reflects the real-time one called currentDate (meaning its scope
     *              is limited to only the current month such as Nov, and not including Oct or Dec).
     *              We never edited this calendar.
     *
     * We need the 2 calendars to find out whether the cell we are looking at should be colored
     * gray (a day from the prev or next month/year) or not gray (a day in the current month/year).
     *
     * To get the Events into the Calendar, we fetch the StartDate from the Database and Cast the
     * Date into an int by using the eventCalendar. We then use this eventCalendar and compare the
     * Event's day, month, and year to the app calendar's day, month, and year.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Create a new Calendar that reflects the app's Calendar
        Date mDate = monthDates.get(position); // monthDates is the cellDates (the dates of all the cells in the month, including irrelevant ones) Oct27 - Dec7
        Calendar dateCal = Calendar.getInstance(); // Create a new Calendar
        dateCal.setTime(mDate); // Set the current Day, Month, Day-Number, Time, and Year into the new Calendar. Sun Oct 27 18:46:03 CDT 2019   to   Sat Dec 07 18:46:03 CST 2019

        // Fetch the day, month, and year of the app's Calendar
        // The day and month can change (Current month is Nov but the app's Calendar can hold dates of Oct and Dec). Year is unlikely to change much
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH); // Get the day from the monthDates
        int displayMonth = dateCal.get(Calendar.MONTH); // Get the month from the monthDates
        int displayYear = dateCal.get(Calendar.YEAR); // Get the Year from the monthDates

        // Use the real-time calendar (currentDate) and get the month and year.
        // Month is likely to stay as the current month (Nov) and not change.
        // Meaning, we limit our scope on the real-time calendar to only the current month of Nov while
        // the app's Calendar (dateCal) limits our scope to include the prev and next month.
        int currentMonth = currentDate.get(Calendar.MONTH); // Get the actual current Month
        int currentYear = currentDate.get(Calendar.YEAR); // Get the actual current Year

        // Setup the layout in case if it is not.
        // We need to make sure we are working with the layout that holds the entire calendar.
        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.calendar_cell_layout, parent, false);
        }

        // Set the color of the cell in the grid according to the Month and Year the cell is on
        Log.d(TAG, "DAY (App): " + dayValue);
        Log.d(TAG, "MONTH (App) - (real-time): " + displayMonth + " - " + currentMonth);


        if(displayMonth == currentMonth && displayYear == currentYear){ // if the cell of the app's calendar lies within the scope/limit of the real-time calendar, we make it relevant to the month/year we are looking at in the TextView
            view.setBackgroundColor(Color.parseColor("#6a2dd2")); // Relevant Days
        } else {                                                        // else we set the background color of the cell to gray which tells us that this day is not in the current month/year, but instead, a day from the prev or next month/year.
            view.setBackgroundColor(Color.parseColor("#cccccc")); // Irrelevant Days
        }

        // Add the day-number to the Calendar cell
        TextView cellNumber = view.findViewById(R.id.date);
        cellNumber.setText(String.valueOf(dayValue)); // dayValue holds the number within the limit of 27-7 assuming the current month/year is Nov2019
        Log.d(TAG, "Setting the day-number to " + dayValue);
        Log.d(TAG, "--------------------------------------------------------------");

        // Add the events to the calendar
        findEvents(view, dayValue, displayMonth, displayYear, mDate);

        return view;
    }

    public void findEvents(final View view, final int dayValue, final int displayMonth, final int displayYear, final Date mDate){
        final Calendar eventCalendar = Calendar.getInstance();
        final TextView eventIndicator = view.findViewById(R.id.event_id);

        eventsCol.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot eventDoc : queryDocumentSnapshots){
                    Event event = eventDoc.toObject(Event.class);
                    Date date = event.getStart();
                    Log.d(TAG, "Dates of App///Event: " + mDate + "///" + date);
                    eventCalendar.setTime(date);

                    if(dayValue == eventCalendar.get(Calendar.DAY_OF_MONTH)
                            && displayMonth == eventCalendar.get(Calendar.MONTH)
                            && displayYear == eventCalendar.get(Calendar.YEAR)){
                        Log.d(TAG, "TRUE");
                        eventIndicator.setBackgroundColor(Color.parseColor("#FF4081"));
                        break;
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // ToDO: Write a Toast Message saying we cannot find the Events Collection in the Database (there are not events available)
            }
        });
    }
}
