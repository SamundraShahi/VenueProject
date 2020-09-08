package com.bignerdranch.android.musicpro;

import java.io.Serializable;
import java.util.UUID;

////////////////////////////////////////////////////////////////////////////////////////////////
//  Map solution: Venue must implement serializable to pass in an Extra
////////////////////////////////////////////////////////////////////////////////////////////////

public class Venue implements Serializable
{
    private UUID mId;
    private String mName;
    private String mAddress;
    private String mOpeningTime;
    private Double mLat;
    private Double mLon;

    public Venue()
    {
        this(UUID.randomUUID());
    }

    public Venue(UUID id)
    {
        mId = id;
    }

    public UUID getId()
    {
        return mId;
    }

    public void setId(UUID mId)
    {
        this.mId = mId;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public String getOpeningTime()
    {
        return mOpeningTime;
    }

    public void setOpeningTime(String time)
    {
        mOpeningTime = time;
    }

    public String getAddress()
    {
        return mAddress;
    }

    public void setAddress(String address)
    {
        mAddress = address;
    }

    public Double getLat()
    {
        return mLat;
    }

    public void setLat(Double lat)
    {
        mLat = lat;
    }

    public Double getLon()
    {
        return mLon;
    }

    public void setLon(Double lon)
    {
        mLon = lon;
    }
}
