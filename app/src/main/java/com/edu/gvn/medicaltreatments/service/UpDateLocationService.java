package com.edu.gvn.medicaltreatments.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.edu.gvn.medicaltreatments.common.LogUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by hnc on 26/10/2016.
 */

public class UpDateLocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final long TIME_TO_UP_DATE_LOCATION = 5000;
    private static final long INTERVAL_TIME_TO_UP_DATE_LOCATION = 20000;
    private static final float SMALLEST_DISPLACEMENT = 5;

    private LocationChangeListener locationChangeListener;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;


    private LocalBinder binder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("huutho", "onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildGoogleApiClient();
        LogUtils.e("huutho", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mGoogleApiClient.connect();

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setFastestInterval(TIME_TO_UP_DATE_LOCATION);
        mLocationRequest.setInterval(INTERVAL_TIME_TO_UP_DATE_LOCATION);
        mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (locationChangeListener != null)
            locationChangeListener.onLocationChanged(location);
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    public LatLng getLatLng() {
        if (mLastLocation != null) {
            Location location = mLastLocation;
            return new LatLng(location.getLatitude(), location.getLongitude());
        }
        return null;
    }

    public void setLocationChangeListener(LocationChangeListener locationChangeListener) {
        this.locationChangeListener = locationChangeListener;
    }


    // inner class
    public class LocalBinder extends Binder {
        public UpDateLocationService getService() {
            return UpDateLocationService.this;
        }

    }

    public interface LocationChangeListener {
        public void onLocationChanged(Location location);
    }

}
