package com.example.eventstrackerapp.ui.carpool.YourCarpoolList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.GlobalCarpoolList.MainGlobalCarpoolListActivity;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.addCarpool.DialogAddCarpool;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.editCarpool.CarpoolDetailsActivity;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.editCarpool.DialogViewRiderDetails;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors.RecyclerViewCampusAdapter;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.tabConstructors.RecyclerViewRiderAdapter;
import com.example.eventstrackerapp.ui.carpool.entities.Car;
import com.example.eventstrackerapp.ui.carpool.entities.Carpool;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RiderTab extends Fragment {

    private ArrayList<Carpool> mCarpoolSet;
    private RecyclerViewRiderAdapter listAdapter;

    private FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CollectionReference userCollection = firebaseFirestore.collection("Users");
    private CollectionReference carpoolsAsRider = userCollection.document(firebaseAuth.getCurrentUser().getUid()).collection("CarpoolsAsRider");
    private CollectionReference carpoolCollection = firebaseFirestore.collection("Carpools");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_carpool_list_rider_tab, container, false);
        init(view);
        return view;
    }

    public void init(View view){
        /**
         * INITIALIZE
         */
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.listRecyclerView_yourCarpoolList_rider);

        mCarpoolSet = new ArrayList<>();

        listAdapter = new RecyclerViewRiderAdapter(mCarpoolSet);
        recyclerView.setAdapter(listAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        // Sets up the Floating Action Button
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainGlobalCarpoolListActivity.class));
            }
        });



        /**
         * VIEW_CLICK_LISTENER
         */
        ((RecyclerViewRiderAdapter)listAdapter).setOnItemClickListener(new RecyclerViewRiderAdapter.ListItemClickListener4() {
            @Override
            public void onItemClick(int position, View v) {

            }
            @Override
            public void onDeleteClick(int position) {
                deleteCarpool(position);
                Snackbar.make(getView(), "You have opted out of the Carpool", Snackbar.LENGTH_LONG).show();
            }
            @Override
            public void onInfoClick(int position) {
                viewCarpoolDetails(position);
            }
        });

        /**
         * LOAD DATA
         */
//        Car car = new Car();
//
//        for(int i=1; i<=20; i++){
//            mCarpoolSet.add(new Carpool("ID" + i, "DUMMY" + i, car));
//        }


        carpoolsAsRider.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot carpoolRider : queryDocumentSnapshots){
                    mCarpoolSet.add(carpoolRider.toObject(Carpool.class));
                }
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    public void viewCarpoolDetails(int position){
        DialogFragment dialogFragment = new DialogViewRiderDetails(listAdapter.mYourCarpoolList.get(position), carpoolCollection);
        dialogFragment.show(getFragmentManager(), "Rider Information");
    }

    public void deleteCarpool(int position){

        //Todo: remove from database

        // Remove the passenger from global database rider list
        String carpoolID = listAdapter.mYourCarpoolList.get(position).getCarpoolID();
        Carpool carpool = listAdapter.mYourCarpoolList.get(position);
        carpool.getCar().removeRiders(firebaseAuth.getCurrentUser().getUid());
        carpoolCollection.document(carpoolID).set(carpool);
        // delete carpool from database carpoolsAsRider collection
        carpoolsAsRider.document(listAdapter.mYourCarpoolList.get(position).getCarpoolID()).delete();

        // Remove from screen
        listAdapter.mYourCarpoolList.remove(position);
        listAdapter.notifyItemRemoved(position);
        listAdapter.notifyItemRangeChanged(position, listAdapter.getItemCount());
    }
}
