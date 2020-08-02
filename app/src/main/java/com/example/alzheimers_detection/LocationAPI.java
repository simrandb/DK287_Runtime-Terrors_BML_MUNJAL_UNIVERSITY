package com.example.alzheimers_detection;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

class LocationAPI {

    private GpsTracker gpsTracker;
    Context context;
    String city,state,country;

    public LocationAPI (Context context) {
        this.context = context;
    }

    public String getLocation() throws IOException {
        gpsTracker = new GpsTracker(context);
        final String[] location = {""};
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            Geocoder gcd = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = gcd.getFromLocation(latitude,longitude, 1);
            if (addresses.size() > 0) {
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                location[0] = city + " " + state + " " + country;
            }else {
                //
            }
        }else{
            gpsTracker.showSettingsAlert();
        }

        return location[0];
    }

    public String getCity(){
        return city;
    }

    public String getState(){
        return state;
    }

    public String getCountry(){
        return country;
    }
}
