package com.example.viktor.sensesmart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Most of this fragment was automatically generated when we implemented Google Maps.
 */
public class GoogleMapsFragment extends Fragment implements OnMapReadyCallback {

    public GoogleMapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_google_maps, container, false);
        // Inflate the layout for this fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map);
        mapFragment.getMapAsync(this);

        return view;

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


    }

    /*This method is used to set the markers on google map. */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng skelleftea2 = new LatLng(64.7506874,20.933061199999997);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(skelleftea2,13));

        LatLng stadsfontanen = new LatLng(64.7497674, 20.95096460000002);
        googleMap.addMarker(new MarkerOptions()
                .position(stadsfontanen));

        LatLng bonnstan = new LatLng(64.7506874,20.933061199999997);
        googleMap.addMarker(new MarkerOptions()
                .position(bonnstan));

        LatLng lejonstromsbron = new LatLng(64.7501393,20.91393830000004);
        googleMap.addMarker(new MarkerOptions()
                .position(lejonstromsbron));
    }
}