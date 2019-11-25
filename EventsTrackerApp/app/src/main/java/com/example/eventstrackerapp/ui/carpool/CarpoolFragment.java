package com.example.eventstrackerapp.ui.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.carpool.GlobalCarpoolList.MainGlobalCarpoolListActivity;
import com.example.eventstrackerapp.ui.carpool.YourCarpoolList.MainYourCarpoolListActivity;
import com.example.eventstrackerapp.ui.home.animations.MyBounceInterpolator;
import com.example.eventstrackerapp.ui.home.animations.MyRotateInterpolator;
import com.example.eventstrackerapp.ui.home.upcoming.UpcomingEvents;

public class CarpoolFragment extends Fragment implements View.OnClickListener{

    private CarpoolViewModel carpoolViewModel;
    private static final String TAG = "CarpoolFragment";

    Button yourCarpoolListButton;
    Button globalCarpoolListButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        carpoolViewModel =
                ViewModelProviders.of(this).get(CarpoolViewModel.class);
        View root = inflater.inflate(R.layout.fragment_carpool, container, false);


        // set up widgets and set animation
        tapButton(root);

        // set up onClick listeners
        this.yourCarpoolListButton.setOnClickListener(this);
        this.globalCarpoolListButton.setOnClickListener(this);


        return root;
    }

    /** Set up the Buttons on the CARPOOL HOMEPAGE (fragment_carpool) */
    // Animation Bounce
    private void tapButton(View view){

        this.yourCarpoolListButton = (Button)view.findViewById(R.id.btn_your_carpool_list);
        this.globalCarpoolListButton = (Button)view.findViewById(R.id.btn_global_carpool_list);

        // Load the animation that expands the button's size
        final Animation myBounceAnim1 = AnimationUtils.loadAnimation(getContext(), R.anim.btn_bounce);
        final Animation myBounceAnim2 = AnimationUtils.loadAnimation(getContext(), R.anim.btn_bounce);

        // Use bounce interprolator with amplitude 0.2 and frequency 20.
        // This will add to the button's animation a spring effect
        MyBounceInterpolator interpolator1 = new MyBounceInterpolator(0.2, 20);
        myBounceAnim1.setInterpolator(interpolator1);
        yourCarpoolListButton.startAnimation(myBounceAnim1);

        MyBounceInterpolator interpolator2 = new MyBounceInterpolator(0.175, 25);
        myBounceAnim2.setInterpolator(interpolator2);
        globalCarpoolListButton.startAnimation(myBounceAnim2);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_your_carpool_list:

                // Go to Your Carpool List
                Intent goToYourCarpoolList = new Intent(getContext(), MainYourCarpoolListActivity.class);
                startActivity(goToYourCarpoolList);
                break;

            case R.id.btn_global_carpool_list:

                // Go to Global Carpool List
                Intent goToGlobalCarpoolList = new Intent(getContext(), MainGlobalCarpoolListActivity.class);
                startActivity(goToGlobalCarpoolList);
                break;
        }
    }
}
