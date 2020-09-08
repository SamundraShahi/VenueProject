package com.bignerdranch.android.musicpro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.musicpro.DbSchema.VenueTable;

public class DbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "venue.db";

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + VenueTable.NAME  + "(" +
                " _id integer primary key autoincrement, " +
                VenueTable.Cols.UUID + ", " +
                VenueTable.Cols.NAME + ", " +
                VenueTable.Cols.ADDRESS + ", " +
                VenueTable.Cols.OPENING_TIME + ", " +
                VenueTable.Cols.LAT +", " +
                VenueTable.Cols.LON +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}

