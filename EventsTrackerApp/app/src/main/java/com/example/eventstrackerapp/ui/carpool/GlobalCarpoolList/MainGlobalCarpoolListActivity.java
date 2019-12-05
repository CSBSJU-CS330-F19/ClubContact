package com.example.eventstrackerapp.ui.carpool.GlobalCarpoolList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.MainYourCarpoolListActivity;
import com.example.eventstrackerapp.ui.carpool.entities.Car;
import com.example.eventstrackerapp.ui.carpool.entities.Carpool;
import com.example.eventstrackerapp.ui.home.upcoming.RecyclerViewEventsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MainGlobalCarpoolListActivity extends AppCompatActivity {

    private ArrayList<Carpool> mCarpoolSet = new ArrayList<>();
    private RecyclerViewGlobalCarpoolAdapter mAdapter = new RecyclerViewGlobalCarpoolAdapter(mCarpoolSet);

    private FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
    private CollectionReference carpoolCollection = firebaseFirestore.collection("Carpools");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_global_carpool_list);

        init();
        initClickListener();
        loadData();
    }
    //----------------------------------------------------------------------------------------------

    public void init(){

        // Sets up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView_global);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Sets up the Adapter
        recyclerView.setAdapter(mAdapter);
    }
    //----------------------------------------------------------------------------------------------

    public void initClickListener(){
        ((RecyclerViewGlobalCarpoolAdapter)mAdapter).setOnItemClickListener(new RecyclerViewGlobalCarpoolAdapter.ListItemClickListener3() {
            @Override
            public void onItemClick(int position, View v) {
                // Todo: open up a dialog
                DialogShowCarpool dialogShowCarpool = new DialogShowCarpool(mAdapter.mGlobalCarpoolList.get(position));
                dialogShowCarpool.show(getSupportFragmentManager(), "Carpool Information");
            }
        });
    }
    //----------------------------------------------------------------------------------------------

    public void loadData(){
//        Car car = new Car();
//
//        for(int i=1; i<=20; i++){
//            mCarpoolSet.add(new Carpool("ID" + i, "DUMMY" + i, car));
//
//        }

        carpoolCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot carpool : task.getResult()){
                        mCarpoolSet.add(carpool.toObject(Carpool.class));
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        mAdapter.notifyDataSetChanged();
    }


    /** ====================================================================================
     * Inner Class Dialog
     * =====================================================================================
     */


    /** ====================================================================================
     * Inner Class RecyclerViewAdapter
     * This class sets up the recycler view in the MainGlobalCarpoolListActivity layout.
     * =====================================================================================
     */

    public static class RecyclerViewGlobalCarpoolAdapter extends RecyclerView.Adapter<RecyclerViewGlobalCarpoolAdapter.GlobalViewHolder> {

        public ArrayList<Carpool> mGlobalCarpoolList;
        private ListItemClickListener3 listItemClickListener3;

        public RecyclerViewGlobalCarpoolAdapter(ArrayList<Carpool> mGlobalCarpoolList) {
            this.mGlobalCarpoolList = mGlobalCarpoolList;
        }

        /**
         * When a recycler view is created, this method creates the ViewHolder for the
         * recycler view so that the items are constructed properly.
         * @param parent
         * @param viewType
         * @return
         */
        @NonNull
        @Override
        public RecyclerViewGlobalCarpoolAdapter.GlobalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_global_list_item, parent, false);
            GlobalViewHolder viewHolder = new GlobalViewHolder(view, listItemClickListener3);
            return viewHolder;
        }

        /**
         * When the item is created, this method will bind data to the item's widgets.
         * It can also bind click listeners to those widgets
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(@NonNull RecyclerViewGlobalCarpoolAdapter.GlobalViewHolder holder, int position) {
            holder.title.setText(mGlobalCarpoolList.get(position).getCarpoolTitle());
            //holder.time.setText(mGlobalCarpoolList.get(position).getCars().get(0).getAllPickUpTimes().get(0).toString());
            //holder.location.setText(mGlobalCarpoolList.get(position).getCars().get(0).getAllPickUpLocations().get(0));
            //holder.event.setText(mGlobalCarpoolList.get(position).getEvent().getTitle());
        }

        /**
         * Returns how many items will be shown
         * @return
         */
        @Override
        public int getItemCount() {
            return mGlobalCarpoolList.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView){
            super.onAttachedToRecyclerView(recyclerView);
        }

        public void setOnItemClickListener(ListItemClickListener3 onItemClickListener){
            this.listItemClickListener3 = onItemClickListener;
        }

        /**====================================================================================
         * Inner-Inner Class GlobalViewHolder
         * This Class sets up the properties of each item in the recycler view.
         * ====================================================================================
         */

        public class GlobalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            ListItemClickListener3 listItemClickListener3;
            TextView title, event, time, location;


            /**
             * Constructor GlobalViewHolder
             * This initiates and sets click listeners to the widgets of the item.
             *
             * @param itemView
             */
            public GlobalViewHolder(View itemView, final ListItemClickListener3 listItemClickListener) {
                super(itemView);
                this.listItemClickListener3 = listItemClickListener;
                itemView.setOnClickListener(this);
                title = itemView.findViewById(R.id.title_global_item);
                event = itemView.findViewById(R.id.event_global_item);
                time = itemView.findViewById(R.id.time_global_item);
                location = itemView.findViewById(R.id.location_global_item);
            }

            @Override
            public void onClick(View v) {
                if(listItemClickListener3 != null){
                    listItemClickListener3.onItemClick(getLayoutPosition(), v);
                }
            }
        }

        /**====================================================================================
         * Inner Interface ListItemClickListener3
         * This interface enables each item to be clickable.
         * The MainGlobalCarpoolListActivity will be in charge of dictating what to do with item.
         * ====================================================================================
         */
        public interface ListItemClickListener3{
            void onItemClick(int position, View v);
        }
    }
}
