package com.bignerdranch.android.musicpro;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    Venue mVenue;

    private static final String TAG = "PhotoGalleryFragment";
    private List<Venue> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // the execution of AsyncTask is held here which is runtime
        new FetchItemsTask().execute();

        DbProvider.get(this).loadTestData();
    }

    public void onVenueClick(View view)
    {
        Intent intent = new Intent(MainActivity.this, VenueListActivity.class);
        startActivity(intent);
    }

    // when user clicks on map button
    public void onMapClick(View view)
    {
        // intent passed to maps activity
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);

        // extra data to pass through the intent process
        intent.putExtra("name", mItems.get(0).getName()); //from 1st array (new), fetch the name
        intent.putExtra("lat", mItems.get(0).getLat()); // the latitude
        intent.putExtra("lon", mItems.get(0).getLon()); // the longitude

        startActivity(intent); // start the intent for maps
    }

    // this will do the background task on thread
    // change is passed to the UI
    private class FetchItemsTask extends AsyncTask<Void,Void, List<Venue>>
    {
        // the background process
        @Override
        protected List<Venue> doInBackground(Void... params) {
            return new VenueFetchr().fetchItems();
        }

        // on successful execution, it will return the JSON object in mItems
        @Override
        protected void onPostExecute(List<Venue> items) {
            mItems = items;
        }
    }

    // just for testing the map without retrieving top-rated venue
    void getTestVenue()
    {
        mVenue = new Venue();
        mVenue.setName("Reef Hotel");
        mVenue.setAddress("Wharf Street, Cairns");
        mVenue.setLat(-16.9238);
        mVenue.setLon(145.7797);
    }
}


