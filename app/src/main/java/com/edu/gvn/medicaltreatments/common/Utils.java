package com.edu.gvn.medicaltreatments.common;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by hnc on 25/10/2016.
 */

public class Utils {
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getAddressFromLocation(Context context, double lat, double lng) {
        try {

            StringBuilder addressBuilder = new StringBuilder();

            List<Address> listAddress = new ArrayList<>();
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            listAddress = geocoder.getFromLocation(lat, lng, 1);

            if (listAddress.size() > 0) {
                String address = listAddress.get(0).getAddressLine(0);
                String city = listAddress.get(0).getAddressLine(1);
                String country = listAddress.get(0).getAddressLine(2);

                addressBuilder.append(address);
                addressBuilder.append(city);
                addressBuilder.append(country);
            }
            return addressBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LatLng getLatLngFromAddress(Context context, String address) {
        try {
            Geocoder geocoder = new Geocoder(context);
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                LatLng latLng = new LatLng(latitude,longitude);
                return latLng;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
