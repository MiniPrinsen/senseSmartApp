package com.example.viktor.sensesmart;


import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * This fragment is used when you've pressed an item from the grid. You then enter this info page
 * where you have a directions button, 5 star rating, parallax scrolling and a slideshow.
 */
public class InfoFragment extends Fragment
{

    private Toolbar toolbar;
    private View rootView;
    private ViewFlipper flipper;
    private Animation fadein, fadeout;
    private Button directions;
    private static String toolbarTitle;
    private ScrollView mScrollView;
    FrameLayout mWrapperFL;
    private String direction = "https://www.google.se/maps/dir/64.7449073,20.9557912/Lejonströmsbron," +
            "+931+44+Skellefteå/@64.7449891,20.914278,14z/" +
            "data=!3m1!4b1!4m9!4m8!1m1!4e1!1m5!1m1!1s0x467e954ff842f71f" +
            ":0x412452cb329526e!2m2!1d20.9157401!2d64.7510032?hl=sv";

    public InfoFragment() {
        //Empty constructor
    }

    public static InfoFragment newInstance(String toolbar) {
        InfoFragment myFragment = new InfoFragment();

        Bundle args = new Bundle();
        args.putString("string", toolbar);
        myFragment.setArguments(args);
        toolbarTitle = toolbar;
        return myFragment;
    }

    /*
    This method is what creates the view. Here it sets the toolbar title, add the back button
    to the toolbar etc.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_info, container, false);

        findViewsById();
        slideShow();
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ScrollPositionObserver());

        ImageView imgview = new ImageView(getContext());
        imgview.setImageResource(R.drawable.icon_back);
        setToolbarTitle(toolbarTitle);
        imgview.setLayoutParams(new Toolbar.LayoutParams(70,70, Gravity.START));
        toolbar.addView(imgview);
        //On-click listener for the back button. Sets this fragment invisible.
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), BaseActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                rootView.setVisibility(View.INVISIBLE);
            }
        });
        //On-click listener for the directions button. Allows access to switch to the google
        // maps app.
        directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(direction));
                startActivity(intent);
            }
        });

        return rootView;
    }

    public void findViewsById() {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        directions = (Button) rootView.findViewById(R.id.directionsButton);
        flipper = (ViewFlipper) rootView.findViewById(R.id.flipper);
        mScrollView = (ScrollView) rootView.findViewById(R.id.infofragment);
        mWrapperFL = (FrameLayout) rootView.findViewById(R.id.flWrapper);
    }

    /*This method is used to create the slideshow. Different animations from the res
    * folder is used to make the transition smooth and pleasing.*/
    public void slideShow() {
         fadein = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        fadeout = AnimationUtils.loadAnimation(getContext(),R.anim.fadeout);
        flipper.setInAnimation(fadein);
        flipper.setOutAnimation(fadeout);
        flipper.setAutoStart(true);
        flipper.setFlipInterval(5000);
        flipper.startFlipping();
    }

    public void setToolbarTitle(String title){
        TextView toolbarText = (TextView)rootView.findViewById(R.id.toolbarTitle);
        toolbarText.setText(title);
    }

    //This class is what is used to get the parallax scrolling effect to work. Basically, it
    // Checks the scrolling speed the user is scrolling and after that, sets the header picture to
    // % of that speed. By that, a parallax effect is created.
    private class ScrollPositionObserver implements ViewTreeObserver.OnScrollChangedListener {

        private int mImageViewHeight;

        public ScrollPositionObserver() {
            mImageViewHeight = getResources().getDimensionPixelSize(R.dimen.info_photo_height);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onScrollChanged() {
            int scrollY = Math.min(Math.max(mScrollView.getScrollY(), 0), mImageViewHeight);

            // changing position of ImageView
            flipper.setTranslationY(scrollY / 2);

        }
    }
}
