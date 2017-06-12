package com.example.viktor.sensesmart;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;


/**
 * The AchievementFragment is the fragment that will include all the achievements that will be
 * implemented. Right now though, it's not finished and will not look like this further on.
 * Therefore, no further comments is necessary.
 */
public class AchievementFragment extends Fragment {


    public AchievementFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_achievement, container, false);

        //THIS IS USED TO GET THE CIRCLE TO WORK AS WE WANT
        CircularProgressBar circularProgressBar = (CircularProgressBar)rootView.findViewById(R.id.progress);
        circularProgressBar.setColor(ContextCompat.getColor(getContext(), R.color.colorYellow));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorYellowLight));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));

        //THIS IS USED FOR THE ANIMATION TIME AND HOW LONG THE PROGRESS WILL GO. 70=70% ETC.
        int animationDuration = 1500; // SHOWN IN MS.
        circularProgressBar.setProgressWithAnimation(70, animationDuration);

        return rootView;
    }

}
