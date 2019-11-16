package com.example.eventstrackerapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.eventstrackerapp.R;
import com.example.eventstrackerapp.ui.home.animations.MyBounceInterpolator;
import com.example.eventstrackerapp.ui.home.animations.MyRotateInterpolator;
import com.example.eventstrackerapp.ui.home.upcoming.UpcomingEvents;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;
    private static final String TAG = "HomeFragment";

    // widgets
    Button upcomingEventsButton;
    Button sharedEventsButton;
    Button clubsButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        // set up widgets and set animation
        tapButton(root);

        // set up onClick listeners
        this.upcomingEventsButton.setOnClickListener(this);

        return root;
    }

    /** Set up the Button to go to the UPCOMING_EVENTS (activity_upcoming_events.xml) page */
    // Animation Bounce
    private void tapButton(View view){

        this.upcomingEventsButton = (Button)view.findViewById(R.id.btn_upcoming_events);
        this.sharedEventsButton = (Button)view.findViewById(R.id.btn_shared_events);
        this.clubsButton = (Button)view.findViewById(R.id.btn_clubs);

        // Load the animation that expands the button's size
        final Animation myBounceAnim1 = AnimationUtils.loadAnimation(getContext(), R.anim.btn_bounce);
        final Animation myBounceAnim2 = AnimationUtils.loadAnimation(getContext(), R.anim.btn_bounce);
        final Animation myBounceAnim3 = AnimationUtils.loadAnimation(getContext(), R.anim.btn_bounce);

        // Use bounce interprolator with amplitude 0.2 and frequency 20.
        // This will add to the button's animation a spring effect
        MyBounceInterpolator interpolator1 = new MyBounceInterpolator(0.2, 20);
        myBounceAnim1.setInterpolator(interpolator1);
        upcomingEventsButton.startAnimation(myBounceAnim1);

        MyBounceInterpolator interpolator2 = new MyBounceInterpolator(0.175, 25);
        myBounceAnim2.setInterpolator(interpolator2);
        sharedEventsButton.startAnimation(myBounceAnim2);

        MyBounceInterpolator interpolator3 = new MyBounceInterpolator(0.15, 30);
        myBounceAnim3.setInterpolator(interpolator3);
        clubsButton.startAnimation(myBounceAnim3);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_upcoming_events:

                // Perform the Animation
                new MyRotateInterpolator(v, upcomingEventsButton).rotateAnimation();

                // Go to the Upcoming Events page
                Intent goToUpcomingEvents = new Intent(getContext(), UpcomingEvents.class);
                startActivity(goToUpcomingEvents);
        }
    }
}