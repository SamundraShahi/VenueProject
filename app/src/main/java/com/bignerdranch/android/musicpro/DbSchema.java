package com.bignerdranch.android.musicpro;

public class DbSchema
{
    public static final class VenueTable
    {
        public static final String NAME = "venue";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String ADDRESS = "address";
            public static final String OPENING_TIME = "time";
            public static final String LAT = "lat";
            public static final String LON = "lon";
        }
    }
}