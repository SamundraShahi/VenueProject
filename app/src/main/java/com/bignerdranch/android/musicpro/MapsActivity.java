package com.bignerdranch.android.musicpro;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // declare variables as name, lat and lon
    private String mName;
    private Double mLat;
    private Double mLon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mName = getIntent().getExtras().getString("name"); // get the string name
        mLat = getIntent().getExtras().getDouble("lat"); // get the double lat
        mLon = getIntent().getExtras().getDouble("lon"); // get the double lon

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // pass the lat and lon from JSON data received
        LatLng sydney = new LatLng(mLat,mLon);

        // add marker position on map
        mMap.addMarker(new MarkerOptions().position(sydney).title(mName)); // will show name of venue when clicked
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); // change the camera angle
        mMap.animateCamera(CameraUpdateFactory.zoomIn()); // to zoom into the fetched top rated venue
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 200, null); // zoom factor to 14
    }
}
