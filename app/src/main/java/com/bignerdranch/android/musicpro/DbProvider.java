package com.bignerdranch.android.musicpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.musicpro.DbSchema.VenueTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DbProvider
{
    private static DbProvider sDbAdapter;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static DbProvider get(Context context)
    {
        if (sDbAdapter == null)
        {
            sDbAdapter = new DbProvider(context);
        }
        return sDbAdapter;
    }

    private DbProvider(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new DbHelper(mContext).getWritableDatabase();
    }

    public List<Venue> getVenues()
    {
        List<Venue> venues = new ArrayList<>();

        DbCursorWrapper cursor = runQuery(null, null);

        try
        {
            cursor.moveToFirst();

            while (!cursor.isAfterLast())
            {
                venues.add(cursor.getItem());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return venues;
    }

    public Venue getVenue(UUID id)
    {
        DbCursorWrapper cursor = runQuery(
                VenueTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try
        {
            if (cursor.getCount() == 0)
            {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getItem();
        }
        finally
        {
            cursor.close();
        }
    }

    public void addVenue(Venue venue)
    {
        ContentValues values = getContentValues(venue);
        mDatabase.insert(VenueTable.NAME, null, values);
    }

    public void updateVenue(Venue venue)
    {
        String uuidString = venue.getId().toString();
        ContentValues values = getContentValues(venue);

        mDatabase.update(VenueTable.NAME, values,
                VenueTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public void deleteVenue(UUID uuid)
    {
        mDatabase.delete(VenueTable.NAME, VenueTable.Cols.UUID + " = ?",
        new String[] { uuid.toString() });
    }

    private DbCursorWrapper runQuery(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                VenueTable.NAME,
                null,           // columns - null selects all columns
                whereClause,
                whereArgs,
                null,           // groupBy
                null,           // having
                null            // orderBy
        );

        return new DbCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Venue venue)
    {
        ContentValues values = new ContentValues();
        values.put(VenueTable.Cols.UUID, venue.getId().toString());
        values.put(VenueTable.Cols.NAME, venue.getName());
        values.put(VenueTable.Cols.ADDRESS, venue.getAddress());
        values.put(VenueTable.Cols.OPENING_TIME, venue.getOpeningTime());
        values.put(VenueTable.Cols.LAT, venue.getLat());
        values.put(VenueTable.Cols.LON, venue.getLon());

        return values;
    }

    public void loadTestData()
    {
        int size = getVenues().size();

        if (size > 0)
        {
            return;
        }

        Venue venue = new Venue();
        venue.setName("The Pier Bar");
        venue.setAddress("36 Pier Point Road, Cairns QLD 4870");
        venue.setOpeningTime("5.00pm");
        addVenue(venue);

        Venue venue2 = new Venue();
        venue2.setName("The Jack");
        venue2.setAddress("Sheridan St &, Spence St, Cairns QLD 4870");
        venue2.setOpeningTime("11.00am");
        addVenue(venue2);

        Venue venue3 = new Venue();
        venue3.setName("The Woolshed");
        venue3.setAddress("24 SHIELDS STREET, Cairns QLD 4870");
        venue3.setOpeningTime("11.45am");
        addVenue(venue3);

        Venue venue4 = new Venue();
        venue4.setName("P.J.O'Brien's");
        venue4.setAddress("87 LAKE Street, Cairns QLD 4870");
        venue4.setOpeningTime("11.30 am");
        addVenue(venue4);

        Venue venue5 = new Venue();
        venue5.setName("Gilligan's");
        venue5.setAddress("57-89 Grafton St, Cairns QLD 4870");
        venue5.setOpeningTime("8.00pm");
        addVenue(venue5);
    }
}

/*

previous version used to test recycler view

package com.bignerdranch.android.ass1_solution;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class DbProvider
{
    private static DbProvider sDbAdapter;
    private List<Venue> mVenues;

    public static DbProvider get(Context context) {
        if (sDbAdapter == null) {
            sDbAdapter = new DbProvider(context);
        }
        return sDbAdapter;
    }

    private DbProvider(Context context)
    {
        mVenues = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Venue venue = new Venue();
            venue.setName("Venue #" + i);
            venue.setAddress("Address #" + i);
            mVenues.add(venue);
        }
    }

    public List<Venue> getVenues()
    {
        return mVenues;
    }
}

*/
