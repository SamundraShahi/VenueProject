package com.bignerdranch.android.musicpro;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.musicpro.DbSchema.VenueTable;

import java.util.UUID;

public class DbCursorWrapper extends CursorWrapper
{
    public DbCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Venue getItem()
    {
        String uuidString = getString(getColumnIndex(VenueTable.Cols.UUID));
        String name = getString(getColumnIndex(VenueTable.Cols.NAME));
        String address = getString(getColumnIndex(VenueTable.Cols.ADDRESS));
        String openingTime = getString(getColumnIndex(VenueTable.Cols.OPENING_TIME));
        Double lat = getDouble(getColumnIndex(VenueTable.Cols.LAT));
        Double lon = getDouble(getColumnIndex(VenueTable.Cols.LON));

        Venue venue = new Venue(UUID.fromString(uuidString));
        venue.setName(name);
        venue.setAddress(address);
        venue.setOpeningTime(openingTime);
        venue.setLat(lat);
        venue.setLon(lon);

        return venue;
    }
}
