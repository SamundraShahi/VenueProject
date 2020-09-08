package com.bignerdranch.android.musicpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VenueListFragment extends Fragment
{
    private RecyclerView mVenueRecyclerView;
    private VenueAdapter mAdapter;

    private TextView mNameTextView;
    private TextView mAddressTextView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_venue_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.new_venue:
                Intent intent = new Intent(getActivity(), VenueDetailsActivity.class);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class VenueHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        Venue mVenue;

        public VenueHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_venue, parent, false));

            itemView.setOnClickListener(this);

            mNameTextView = (TextView) itemView.findViewById(R.id.venue_name);
            mAddressTextView = (TextView) itemView.findViewById(R.id.venue_address);
        }

        public void bind(Venue venue)
        {
            mVenue = venue;
            mNameTextView.setText(mVenue.getName());
            mAddressTextView.setText(mVenue.getAddress());
        }

        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(getActivity(), VenueDetailsActivity.class);
            intent.putExtra(VenueDetailsFragment.EXTRAS_VENUE_ID, mVenue.getId());

            startActivity(intent);
        }
    }

    private class VenueAdapter extends RecyclerView.Adapter<VenueHolder>
    {
        private List<Venue> mVenues;

        public VenueAdapter(List<Venue> venues)
        {
            mVenues = venues;
        }

        @Override
        public VenueHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new VenueHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(VenueHolder holder, int position)
        {
            Venue venue = mVenues.get(position);
            holder.bind(venue);
        }

        public void setVenues(List<Venue> venues)
        {
            mVenues = venues;
        }

        @Override
        public int getItemCount()
        {
            return mVenues.size();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_venue_list, container, false);

        mVenueRecyclerView = (RecyclerView) view
                .findViewById(R.id.venue_recycler_view);
        mVenueRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI()
    {
        DbProvider dbAdapter = DbProvider.get(getActivity());
        List<Venue> venues = dbAdapter.getVenues();

        if (true) //mAdapter == null)
        {
            mAdapter = new VenueAdapter(venues);
            mVenueRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.setVenues(venues);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updateUI();
    }
}
