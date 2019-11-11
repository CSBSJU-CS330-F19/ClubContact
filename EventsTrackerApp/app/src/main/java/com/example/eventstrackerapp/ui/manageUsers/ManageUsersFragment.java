package com.example.eventstrackerapp.ui.manageUsers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.UserAdapter;
import com.example.eventstrackerapp.profile.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ManageUsersFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference allUsersRef = db.collection("Users");

    private ManageUsersViewModel manageUsersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        manageUsersViewModel =
                ViewModelProviders.of(this).get(ManageUsersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_manage_users, container, false);
        final TextView textView = root.findViewById(R.id.nav_manageUsers);
        manageUsersViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s){
                textView.setText(s);
            }

        });
        recyclerView = (RecyclerView) root.findViewById(R.id.nav_manageUsers);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        final ArrayList<User> users = new ArrayList<User>();
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        allUsersRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot dSnap: task.getResult()){
                        User user = dSnap.toObject(User.class);
                        users.add(user);
                    }
                }
            }
        });
        // specify an adapter (see also next example)
        mAdapter = new UserAdapter(users);
        recyclerView.setAdapter(mAdapter);
        return root;
    }
}
