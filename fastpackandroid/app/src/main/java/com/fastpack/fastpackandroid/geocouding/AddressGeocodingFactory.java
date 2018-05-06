package com.fastpack.fastpackandroid.geocouding;

import com.fastpack.fastpackandroid.objetos.Address;
import com.fastpack.fastpackandroid.objetos.Local;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;

/**
 * Created by Caio on 28/01/2018.
 */


class AddressGeocodingFactory {

    public static final String street_number = "street_number";

    //rua
    public static final String route = "route";

    //bairro
    public static final String political = "political";

    //cidade dnv...
    public static final String locality = "locality";

    //cidade
    public static final String administrative_area_level_2 = "administrative_area_level_2";

    //estado
    public static final String administrative_area_level_1 = "administrative_area_level_1";

    public static final String country = "country";
    public static final String postal_code = "postal_code";


    public static Address toAddress(AddressComponent[] addressComponent) {
        if (addressComponent == null) return null;
        Address address = new Address();
        for (AddressComponent component : addressComponent) {
            setAtributoAddress(address, component);
        }
        return address;
    }


    private static void setAtributoAddress(Address address, AddressComponent addressComponent) {
        if (addressComponent.types[0].toString().equals(street_number)) {
            //para nao dar bug nao vamos pegar esse campo...
        } else if (addressComponent.types[0].toString().equals(route)) {
            address.setStreet(addressComponent.longName);
        } else if (addressComponent.types[0].toString().equals(political)) {
            address.setNeighborhood(addressComponent.longName);
        } else if (addressComponent.types[0].toString().equals(administrative_area_level_2)) {
            address.setCity(addressComponent.longName);
        } else if (addressComponent.types[0].toString().equals(administrative_area_level_1)) {
            address.setState(addressComponent.shortName);
        } else if (addressComponent.types[0].toString().equals(country)) {
            address.setCountry(addressComponent.shortName);
        } else if (addressComponent.types[0].toString().equals(postal_code)) {
            address.setZipcode(addressComponent.longName.replace("-", ""));
        }
    }

    public static Address toAddress(GeocodingResult result) {
        Address address = AddressGeocodingFactory.toAddress(result.addressComponents);
        if (result.geometry != null && result.geometry.location != null) {
            Local local = new Local();
            local.setLatitude(result.geometry.location.lat);
            local.setLongitude(result.geometry.location.lng);
            address.setLocal( local );
        }
        return address;
    }
}
