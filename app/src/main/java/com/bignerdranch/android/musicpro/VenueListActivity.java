package com.bignerdranch.android.musicpro;

import androidx.fragment.app.Fragment;

public class VenueListActivity extends SingleFragmentActivity
{
    @Override
    protected Fragment createFragment()
    {
        return new VenueListFragment();
    }
}
