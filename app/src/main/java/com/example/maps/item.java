package com.example.maps;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class item implements ClusterItem {
    LatLng a;
    String b;

    item(double lat, double lng, String loc)
    {
        a=new LatLng(lat,lng);
        b=loc;
    }

    @Override
    public LatLng getPosition()
    {

        return a;
    }

    @Override
    public String getTitle()
    {
        return b;
    }

    @Override
    public String getSnippet()
    {
        return null;
    }
}
