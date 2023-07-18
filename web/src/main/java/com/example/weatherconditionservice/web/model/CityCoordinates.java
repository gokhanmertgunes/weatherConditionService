package com.example.weatherconditionservice.web.model;

import lombok.AllArgsConstructor;

public class CityCoordinates
{
    double lon,lat;

    public double getLon()
    {
        return lon;
    }

    public double getLat()
    {
        return lat;
    }

    public CityCoordinates(double lon, double lat)
    {
        this.lon = lon;
        this.lat = lat;
    }
}
