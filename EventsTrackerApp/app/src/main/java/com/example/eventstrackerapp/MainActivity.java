package com.example.eventstrackerapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.eventstrackerapp.authentication.LoginActivity;
import com.example.eventstrackerapp.profile.ProfileActivity;
import com.example.eventstrackerapp.profile.User;
import com.example.eventstrackerapp.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference usersRef;

    private String myUserType;

    // This method activates when screen (activity_main.xml) pops up
    // It sets up everything
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        usersRef = firebaseFirestore.collection("Users");
        myUserType = "";

        super.onCreate(savedInstanceState);

        /**
         * -----------------------------------------------------------------------------------------------------------------------
         */

        String currentUser = mAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = usersRef.document(currentUser);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                myUserType = user.getUserType();

                /**
                 * -----------------------------------------------------------------------------------------------------------------------
                 */

                // THIS IS WHERE YOU DETERMINE WHAT SCREEN YOU GET DEPENDING ON THE USER TYPE

                switch (myUserType){
                    case "default":
                        setContentView(R.layout. activity_main);
                        Toolbar toolbar = findViewById(R.id.toolbar);
                        setSupportActionBar(toolbar);
                        navigationSetupDefault();
                        break ;
                    case "admin":
                        setContentView(R.layout. activity_main_admin);
                        Toolbar toolbar2 = findViewById(R.id.toolbar);
                        setSupportActionBar(toolbar2);
                        navigationSetupAdmin();
                        break;
                    default:
                        Log.d(TAG, "onCreate: Cannot find user type");
                        break;
                }

                /**
                 * -----------------------------------------------------------------------------------------------------------------------
                 */

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Failed to get Document");
            }
        });
        /**
         * -----------------------------------------------------------------------------------------------------------------------
         */


//        // THIS IS WHERE YOU DETERMINE WHAT SCREEN YOU GET DEPENDING ON THE USER TYPE
//        String currentUserType = mAuth.getCurrentUser().getUid();
//        DocumentReference documentReference = usersRef.document(currentUserType);
//        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                User user = documentSnapshot.toObject(User.class);
//
//                String userType = user.getUserType();
//                if(userType.equals("default")){
//                    navigationSetupDefault(); // Give the user a navigation screen without the Events and Users tab
//                }
//                else if(userType.equals("admin")){
//                    // Give the user a navigation screen with the Events or Users tab
//                    navigationSetupAdmin();
//                }
//                else if(userType.equals("executive")){
//                    // Give the user a navigation screen without the Events tabs but with a Club-Events tab
//                }
//                else{
//                    Toast.makeText(MainActivity.this,"Error! Type = " + user.getUserType(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MainActivity.this,"Error!", Toast.LENGTH_SHORT).show();
//                Log.d(TAG, e.toString());
//            }
//        });
    }

    // This method creates the menu (upper-right of screen/three dots)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // This method sets up the actions of the items in the menu (upper-right of screen/three dots)
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_settings:
                // if the user chooses the Settings item, show settings UI
                return true;
            case R.id.action_search:
                // if the user chooses the Search item, show search UI
                return true;
            case R.id.action_logout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
                return true;
            default:
                // if the user's action was not recognized, invoke superclass to handle it
                return super.onOptionsItemSelected(item);
        }
    }

    // Yeah.... idk. :/ something for supporting the side bar???
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * This method sets up a side bar/navigation bar without the Events, Users, or Club-Events tabs.
     */
    public void navigationSetupDefault(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Profile Navigation goes down here
        View headView = navigationView.getHeaderView(0);
        ImageView imgProfile= (ImageView) headView.findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        /**
         * This is where you set the names of the tabs you created. Names should be stored in
         * mobile_navigation.xml which is found in 'res -> navigation'
         */
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_calendar, R.id.nav_carpool, // names are set and found in res -> navigation file
                R.id.nav_subscriptions, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
  
  /**
     * This method sets up a side bar/navigation bar without the Events, Users, or Club-Events tabs.
     */
    public void navigationSetupAdmin(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Profile Navigation goes down here
        View headView = navigationView.getHeaderView(0);
        ImageView imgProfile= (ImageView) headView.findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        /**
         * This is where you set the names of the tabs you created. Names should be stored in
         * mobile_navigation.xml which is found in 'res -> navigation'
         */
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_calendar, R.id.nav_carpool,
                R.id.nav_subscriptions, R.id.nav_share, R.id.nav_send,
                R.id.nav_viewAllUsers, R.id.nav_manageEvents, R.id.nav_manageUsers)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

}
