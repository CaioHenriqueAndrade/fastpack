package com.fastpack.fastpackandroid.geocouding;

import android.support.annotation.NonNull;

import com.fastpack.fastpackandroid.objetos.Address;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;


/**
 * Created by Caio on 28/01/2018.
 */

public class PresenterGeoCouding  {

    public static final String PATH_BAISC = "https://maps.googleapis.com/maps/api/geocode/json?";
    public static final String KEY = "AIzaSyDf_qo6wHLYCnpJ8k7447BK_712qG0eXRE";

    private ListenerGeocoding listenerGeocoding;
    private volatile boolean isRequisitando = false;

    public PresenterGeoCouding(@NonNull ListenerGeocoding listenerGeocoding) {
        this.listenerGeocoding = listenerGeocoding;
    }

    public void initSearch(final LatLng latLng) {
        if(isRequisitando()) return;
        setRequisitando( true );
        //https://maps.googleapis.com/maps/api/geocode/json?latlng=-23.4459134197085,-46.45877811970851&key=AIzaSyDf_qo6wHLYCnpJ8k7447BK_712qG0eXRE
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Address address = null;
                try {
                    GeoApiContext context = new GeoApiContext.Builder()
                            .apiKey( KEY )
                            .build();

                    GeocodingResult[] results = GeocodingApi.reverseGeocode(context,
                            latLng).await();

                    //Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    //System.out.println(gson.toJson(results[0].));
                    if(results != null && results.length > 0) {
                        if(results[0].addressComponents != null) {
                            address = AddressGeocodingFactory.toAddress( results[0].addressComponents );
                        }
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

                //independente de ter erro ou nao o importante e o resultado dos dados obtidos
                listenerGeocoding.onFinishProcess( address );
                setRequisitando( false );
            }
        };
        new Thread(runnable).start();
    }


    public void initSearch(@NonNull final Address addressEntity) {
        if(isRequisitando()) return;
        setRequisitando( true );
        //https://maps.googleapis.com/maps/api/geocode/json?latlng=-23.4459134197085,-46.45877811970851&key=AIzaSyDf_qo6wHLYCnpJ8k7447BK_712qG0eXRE
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Address address = null;
                try {
                    GeoApiContext context = new GeoApiContext.Builder()
                            .apiKey( KEY )
                            .build();

                    GeocodingResult[] results = GeocodingApi.geocode(context,
                            getWhereByAddress( addressEntity ) ).await();

                    //Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    //System.out.println(gson.toJson(results[0].));
                    if(results != null && results.length > 0) {
                        if(results[0].addressComponents != null) {
                            address = AddressGeocodingFactory.toAddress( results[0] ) ;
                        }
                    }

                } catch(Exception e) {
                    e.printStackTrace();
                }

                //independente de ter erro ou nao o importante e o resultado dos dados obtidos
                listenerGeocoding.onFinishProcess( address );
                setRequisitando( false );
            }
        };
        new Thread(runnable).start();
    }



    private String getWhereByAddress(Address address) {
        return ( address.getStreet() + "+" + address.getCity() + "+" + address.getZipcode() ).replaceAll(" ","+");
    }

    public boolean isRequisitando() {
        return isRequisitando;
    }

    public void setRequisitando(boolean requisitando) {
        isRequisitando = requisitando;
    }
}
