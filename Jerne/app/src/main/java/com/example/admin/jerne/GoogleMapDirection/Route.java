package com.example.admin.jerne.GoogleMapDirection;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by admin on 20/06/2016.
 */
public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
