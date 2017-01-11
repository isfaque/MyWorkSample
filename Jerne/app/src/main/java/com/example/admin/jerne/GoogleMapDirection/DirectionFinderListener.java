package com.example.admin.jerne.GoogleMapDirection;

import java.util.List;

/**
 * Created by admin on 20/06/2016.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
