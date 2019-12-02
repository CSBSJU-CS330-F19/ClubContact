package com.example.eventstrackerapp.ui.carpool.YourCarpoolList;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.addCarpool.DialogAddCarpool;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.editCarpool.CarpoolDetailsActivity;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors.RecyclerViewCampusAdapter;
import com.example.eventstrackerapp.ui.carpool.entities.Car;
import com.example.eventstrackerapp.ui.carpool.entities.Carpool;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Iterator;


public class DriverTab extends Fragment implements DialogAddCarpool.CarpoolDialogListener{

    private ArrayList<Carpool> mCarpoolSet;

    private FirebaseFirestore firebaseFirestore;
    private DocumentReference newCarpoolRef;

    private RecyclerViewCampusAdapter listAdapter;



    // Singleton?
    public static DriverTab instance(){ return new DriverTab(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_carpool_list_driver_tab, container, false);
        init(view);
        return view;
    }


    public void init(View view){
        /**
         * INITIALIZE
         */
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.listRecyclerView_yourCarpoolList_campus);

        mCarpoolSet = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        newCarpoolRef = firebaseFirestore.collection("Carpools").document();

        listAdapter = new RecyclerViewCampusAdapter(mCarpoolSet);
        recyclerView.setAdapter(listAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        // Sets up the Floating Action Button
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddCarpool dialogAddCarpool = new DialogAddCarpool();
                dialogAddCarpool.setTargetFragment(DriverTab.this, 1);
                dialogAddCarpool.show(getFragmentManager(), "New Carpool"); // getFragmentManager -> getSupportFragmentManager in Activity class
            }
        });



        /**
         * VIEW_CLICK_LISTENER
         */
        ((RecyclerViewCampusAdapter)listAdapter).setOnItemClickListener(new RecyclerViewCampusAdapter.ListItemClickListener2() {
            @Override
            public void onItemClick(int position, View v) {
                //Toast.makeText(getActivity(), "position: "+position, Toast.LENGTH_SHORT).show();
                //viewCarpoolDetails();
            }

            @Override
            public void onDeleteClick(int position) {
                deleteCarpool(position);
            }

            @Override
            public void onInfoClick() {
                viewCarpoolDetails();
            }
        });



        /**
         * LOAD DATA
         */
        ArrayList<Car> cars = new ArrayList<>();

        for(int i=1; i<=20; i++){
            mCarpoolSet.add(new Carpool("ID" + i, "DUMMY" + i, cars));

        }
        listAdapter.notifyDataSetChanged();
    }



    public void viewCarpoolDetails(){
        startActivity(new Intent(getActivity(), CarpoolDetailsActivity.class));
    }

    public void deleteCarpool(int position){
        listAdapter.mYourCarpoolList.remove(position);
        listAdapter.notifyItemRemoved(position);
        listAdapter.notifyItemRangeChanged(position, listAdapter.getItemCount());
    }



    /**
     * Interface method to get information from dialog to make a new Carpool
     * @param title
     */
    @Override
    public void sentInput(String title) {
        mCarpoolSet.add(new Carpool(title));
        listAdapter.notifyItemInserted(mCarpoolSet.size());
    }

}
