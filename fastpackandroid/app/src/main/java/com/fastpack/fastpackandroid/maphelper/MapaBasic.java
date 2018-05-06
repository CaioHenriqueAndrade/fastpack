package com.fastpack.fastpackandroid.maphelper;

import android.content.Context;
import android.os.Bundle;

import com.fastpack.fastpackandroid.interfaces.Interfaces;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MapaBasic {

    private Interfaces.CallBackMapa callBack;

    public MapaBasic(Interfaces.CallBackMapa callBack ) {
        this.callBack = callBack;
    }
    public void onCreateView(Context context , final MapView mMapView, Bundle savedInstanceState) {

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(context);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                if (mMap == null) return;
                callBack.onMapaCompleted( mMap , mMapView);
            }
        });

    }
}
