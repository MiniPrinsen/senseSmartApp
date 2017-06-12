package com.example.viktor.sensesmart;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

/**
 * This class is the one that updates the location. Most of these functions are auto generated
 * which explains why those doesn't have comments.
 */
public class LocationAPI implements
        LocationListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, Runnable {
    private static final long ONE_MIN = 1000 * 60;
    private static final long TWO_MIN = ONE_MIN * 2;
    private static final long FIVE_MIN = ONE_MIN * 5;
    private static final long POLLING_FREQ = 1000 * 30;
    private static final long FASTEST_UPDATE_FREQ = 1000 * 5;
    private static final float MIN_ACCURACY = 25.0f;
    private static final float MIN_LAST_READ_ACCURACY = 500.0f;

    Context mContext;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;


    Location volleyboll;
    Location mCurrentPosition;
    Handler handler;


    public LocationAPI(Context mContext, Handler handler) {

        this.handler = handler;
        this.mContext = mContext;

        volleyboll = new Location("");
        volleyboll.setLatitude(64.74512696);
        volleyboll.setLongitude(20.9547472);

        buildGoogleApiClient();
        mGoogleApiClient.connect();
        createLocationRequest();
    }

    public boolean checkApp(){
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

        // get the info from the currently running task
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        ComponentName componentInfo = taskInfo.get(0).topActivity;
        if (componentInfo.getPackageName().equalsIgnoreCase("com.example.gustaf.touchpoint.menutest")) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Get first reading. Get additional location updates if necessary
        if (servicesAvailable()) {
            // Get best last location measurement meeting criteria
            bestLastKnownLocation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location) {

        mCurrentPosition = location;
        Message msg = handler.obtainMessage();
        msg.obj = location;
        handler.sendMessage(msg);
        Log.v("LOCATION CHANGED", location.getLongitude()+", "+location.getLatitude());

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(POLLING_FREQ);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_FREQ);
    }
    private void bestLastKnownLocation() {
        // Get the best most recent location currently available
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private boolean servicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);

        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
        } else {
            Toast.makeText(mContext, "Google play services connection failed! Won't be able to get location.", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public void removeLocationUpdates () {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void run() {
        boolean error = false;
        while(!error) {
            try {
                Thread.sleep(5000);
                Log.v("HEJ", "UPDATED FROM GETLOCATION");


            } catch (InterruptedException e) {
                e.printStackTrace();
                error = true;
            }
        }
    }


}