package com.bignerdranch.android.musicpro;

import androidx.fragment.app.Fragment;

public class VenueDetailsActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new VenueDetailsFragment();
    }
}
