package com.example.viktor.sensesmart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.skyfishjy.library.RippleBackground;

/**
 * This frament is used to demonstrate the starting chat tab. The fragment includes a circle with
 * animations which will zoom in and change picture when a touchpoint is nearby, which enables
 * the user to enter the chat window and starts chatting with the touchpoint.
 */
public class ActiveChatFragment extends Fragment {

    /**
     *
     */
    public ActiveChatFragment() {
        // Required empty public constructor
    }
    View rootView;
    Bitmap bm;
    Bitmap ba;
    RippleBackground rippleBackground;
    ImageView backgroundAnim;
    ImageView goToChatt;
    ImageView goToChatt2;
    Location volleyboll;
    Boolean firstTime = true;
    Boolean isShown = false;
    ImageView tja;
    GradientDrawable drawable;

    /**
     * This is where we create the view. The code is not beautiful, but we needed to set
     * a lot of starting requirements for the animations to work as we want and to not be able
     * to enter the chat without you being close to a touchpoint.
     * @param inflater A parameter for which layout to inflate.
     * @param container
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_active_chat, container, false);
        rippleBackground = (RippleBackground) rootView.findViewById(R.id.content);
        goToChatt = (ImageView) rootView.findViewById(R.id.go_to_chat_btn);
        goToChatt2 = (ImageView) rootView.findViewById(R.id.go_to_chat_btn2);
        goToChatt.setOnClickListener(chattObjectListener);
        backgroundAnim = (ImageView) rootView.findViewById(R.id.backgroundImage);
        assert goToChatt != null;
        goToChatt.setClickable(false);
        goToChatt2.setVisibility(View.VISIBLE);
        rippleBackground.startRippleAnimation();
        volleyboll = new Location("");
        volleyboll.setLatitude(64.74512696);
        volleyboll.setLongitude(20.9547472);
        return rootView;
    }

    /**
     * Updates the location. If the location is nearby a touchpoint, the animation will start.
     * If you have been inside the radius and walks out of it, the image will zoom out.
     * @param location
     */
    public void updateLocation(Location location) {

        if (inRadius(location,volleyboll,70) && !isShown) {
            zoomIn();
        }
        if (!inRadius(location, volleyboll, 70) && !firstTime) {
           zoomOut();
        }
    }

    /**
     * Method that uses BlurActivity. Simple as it is, the BlurActivity loops through
     * the pixels of the image and blurs them. This function is what creates OutOfMemoryError 99%
     * of the time.
     * @param picture
     */
    public void blur(int picture) {
        bm = BitmapFactory.decodeResource(getResources(), picture);
        tja = (ImageView) rootView.findViewById(R.id.backgroundImage);
        ba = BlurActivity.fastblur(bm, 0.2f, 70);
        assert tja != null;
        tja.setImageBitmap(ba);
    }

    /**
     * Clicklistener to when the user presses the active picture.
     */
    private View.OnClickListener chattObjectListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {

            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, new ChatFragment()).commit();
        }
    };

    /**
     *
     * @param currentLocation A parameter to specify where the user is at the moment.
     * @param objectLocation A parameter to specify where the touchpoint is located.
     * @param radius A parameter to specify how far the radius from the touchpoint is.
     * @return
     */
    public static boolean inRadius(Location currentLocation, Location objectLocation, int radius) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(currentLocation.getLatitude() - objectLocation.getLatitude());
        double dLng = Math.toRadians(currentLocation.getLongitude() - objectLocation.getLongitude());
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(objectLocation.getLatitude())) * Math.cos(Math.toRadians(currentLocation.getLatitude())) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        if (dist <= radius) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The zoom-in animation. This takes a background image and blurs it into the screen while a
     * circle with a picture of the touchpoint gets bigger and bigger, filling most of the screen.
     * It also draws a green stroke around the circle to make it look more online. Last it makes
     * the button clickable so the user can enter the chat.
     */
    public void zoomIn() {
        Animation backgroundAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        Animation resize = AnimationUtils.loadAnimation(getContext(), R.anim.fadeinzoomin);
        Animation hej = AnimationUtils.loadAnimation(getContext(), R.anim.fadeoutzoomin);
        resize.setFillAfter(true);
        blur(R.drawable.image_lejonstromsbron);
        resize.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                goToChatt.setVisibility(View.VISIBLE);
                goToChatt.setImageResource(R.drawable.image_lejoncirkel);
                goToChatt2.setVisibility(View.INVISIBLE);
                drawable = (GradientDrawable) goToChatt.getBackground();
                drawable.setStroke(3, getResources().getColor(R.color.colorGreen));
            }
            public void onAnimationRepeat(Animation animation) {
            }
            public void onAnimationEnd(Animation animation) {
                goToChatt.setClickable(true);
            }
        });
        goToChatt.startAnimation(resize);
        goToChatt2.startAnimation(hej);
        backgroundAnim.startAnimation(backgroundAnimation);
        rippleBackground.stopRippleAnimation();
        goToChatt.setClickable(true);
        isShown = true;
        firstTime = false;
    }

    /**
     * The zoom-out animation. This does the opposite of the zoom-in animation.
     */
    public void zoomOut() {
        Animation resize2 = AnimationUtils.loadAnimation(getContext(), R.anim.fadeoutzoomout);
        Animation resize3 = AnimationUtils.loadAnimation(getContext(), R.anim.fadeinzoomout);
        final Animation backgroundAnimation2 = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);
        resize2.setFillAfter(true);
        rippleBackground.startRippleAnimation();
        goToChatt.setClickable(false);
        resize2.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                backgroundAnim.startAnimation(backgroundAnimation2);
            }
            public void onAnimationRepeat(Animation animation) {
            }
            public void onAnimationEnd(Animation animation) {
                goToChatt2.setVisibility(View.VISIBLE);
                goToChatt.setVisibility(View.INVISIBLE);
                backgroundAnim.setVisibility(View.INVISIBLE);
                //drawable.setStroke(3, getResources().getColor(R.color.colorWhite));
                goToChatt.setClickable(false);
                drawable.setVisible(false,false);
            }
        });
        goToChatt.startAnimation(resize2);
        goToChatt2.startAnimation(resize3);
        isShown = false;
        firstTime = true;
    }
}
