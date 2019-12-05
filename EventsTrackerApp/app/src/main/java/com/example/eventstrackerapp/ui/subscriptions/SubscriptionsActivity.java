package com.example.eventstrackerapp.ui.subscriptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eventstrackerapp.SubscriptionAdapter;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.profile.Club;
import java.util.ArrayList;

import com.example.eventstrackerapp.profile.ProfileActivity;
import com.example.eventstrackerapp.profile.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.content.Context;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import javax.annotation.Nullable;

public class SubscriptionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DocumentSnapshot mLastQueriedDocument;
    private Context mContext;

    private static final String title = "Subscription List";
    private ArrayList<Club> clubs;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference allUsersRef = db.collection("Users");
    private CollectionReference allClubsRef = db.collection("club");


    @Override
    protected void onCreate(Bundle savedInstanceSpace)
    {
        super.onCreate(savedInstanceSpace);
        setContentView(R.layout.activity_view_subscriptions);
        recyclerView = (RecyclerView) findViewById(R.id.subscriptions_recycler_view);

        recyclerView.setHasFixedSize(true);

        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapter = new SubscriptionAdapter(clubs);
        recyclerView.setAdapter(adapter);
        loadData();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        String id = firebaseAuth.getCurrentUser().getUid();
        DocumentReference userRef = allUsersRef.document(id);
        DocumentReference clubRef = allClubsRef.document(id);


        userRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e != null){
                    Toast.makeText(SubscriptionsActivity.this,"Error while loading!", Toast.LENGTH_SHORT).show();
                    Log.d(title, e.toString());
                    return;
                }
                if(documentSnapshot.exists()) {
//                    String title = documentSnapshot.getString(KEY_TITLE);
//                    String description = documentSnapshot.getString(KEY_DESCRIPTION);

                    // Here, we use the Note class instead
                    User user = documentSnapshot.toObject(User.class);
                }
            }
        });
    }

    public void loadData()
    {
        Query clubQuery = queryClubs(allClubsRef, "name");
        clubQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                 for(QueryDocumentSnapshot docSnap: task.getResult())
                 {
                     Club c = docSnap.toObject(Club.class);
                     
                 }
                }
                else
                {
                    Toast.makeText(mContext, "Retrieving Subscriptions Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onRefresh()
    {
        loadData();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public Query queryClubs(CollectionReference clubReference, String field)
    {
       Query q = null;
       if(mLastQueriedDocument != null)
       {
           q = clubReference.orderBy(field, Query.Direction.ASCENDING).startAfter(mLastQueriedDocument);
       }
       else
       {
           q = clubReference.orderBy(field, Query.Direction.ASCENDING);
       }
       return q;
    }
}
