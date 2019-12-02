package com.example.eventstrackerapp.ui.carpool.YourCarpoolList.addCarpool;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventstrackerapp.Event;
import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.entities.Car;
import com.example.eventstrackerapp.ui.carpool.entities.Carpool;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class DialogAddEvent extends DialogFragment implements View.OnClickListener {

    private ArrayList<Event> mEventsSets = new ArrayList<>();
    private RecyclerViewDialogAddEventAdapter mAdapter = new RecyclerViewDialogAddEventAdapter(mEventsSets);

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = firebaseFirestore.collection("Events");

    // Widgets
    private TextView mClose;
    private RecyclerView recyclerView;

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
        View view = inflater.inflate(R.layout.dialog_add_event_to_carpool, container, false);

        /**
         * INIT
         */
        mClose = view.findViewById(R.id.close_dia_add_event);
        mClose.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerView_addEvent_dia);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);

        /**
         * CLICK_LISTENER
         */
        mAdapter.setOnItemClickListener(new RecyclerViewDialogAddEventAdapter.ListItemClickListener5() {
            @Override
            public void onItemClick(int position, View v) {
                // Todo: Add event name to AddCarpool dialog
                String title = mAdapter.mEventList.get(position).getTitle();
                String id = mAdapter.mEventList.get(position).getEventID();

                DialogAddEvent.CarpoolDialogListener2 listener = getListener2();
                if(listener != null){
                    listener.sentInput2(title, id);
                }

                getDialog().dismiss();
            }
        });

        /**
         * LOAD DATA
         */

//        mEventsSets.add(new Event("ID", "title", "location", new Date(), new Date(),
//                new ArrayList<String>(), "description"));

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        Event event = documentSnapshot.toObject(Event.class);
                        mEventsSets.add(event);
                    }
                    mAdapter.notifyDataSetChanged();
                    getDialog().setTitle("Select an Event");

                } else { // Else if the task is un-successful
                    Toast.makeText(getContext(), "Ordering the Collection Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        mAdapter.notifyDataSetChanged();
//        getDialog().setTitle("Select an Event");

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_dia_add_event:
                getDialog().dismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * This interface will allow information to be sent to the RecyclerView located in a fragment
     */
    public interface CarpoolDialogListener2{
        // There is new Carpool Information
        // Todo: When the Carpool class changes in terms of its instances, add or delete those instances here
        void sentInput2(String title, String id);
    }

    /**
     * This tries to find a suitable listener. It examines the hosting Fragment and then the Activity.
     * Will return null if fails
     */
    private CarpoolDialogListener2 getListener2(){
        CarpoolDialogListener2 listener;
        try{
            Fragment OnInputSelected_Fragment = getTargetFragment();
            if(OnInputSelected_Fragment != null){
                listener = (CarpoolDialogListener2) OnInputSelected_Fragment;
            } else {
                Activity OnInputSelected_Activity = getActivity();
                listener = (CarpoolDialogListener2) OnInputSelected_Activity;
            }
            return listener;
        } catch(ClassCastException cce){
            Log.e("Custom Dialog", "onAttach: ClassCastException: " + cce.getMessage());
        }
        return null;
    }
}
