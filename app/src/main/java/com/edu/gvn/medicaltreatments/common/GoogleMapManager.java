package com.edu.gvn.medicaltreatments.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;

import com.edu.gvn.medicaltreatments.custom.GMapV2Direction;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import org.w3c.dom.Document;

import java.util.ArrayList;


public class GoogleMapManager {
    public interface IMapLoadComplete {
        void onComplete();
    }

    private static final int MAP_ZOOM = 17;

    private Context context;
    private GoogleMap mGoogleMap;
    private GMapV2Direction v2Direction;


    private String distanceText;
    private int distanceValue;
    private ArrayList<LatLng> listDirection;
    private LatLng start, end;

    public GoogleMapManager(Context context, Activity activity, GoogleMap googleMap) {
        this.context = context;
        this.mGoogleMap = googleMap;
        v2Direction = new GMapV2Direction();

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }


        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                //TODO
                return false;
            }
        });

    }

    public void setLoadMap(LatLng start, LatLng end, IMapLoadComplete loadMap) {
        this.start = start;
        this.end = end;
        MapInfoAsyncTask mapInfo = new MapInfoAsyncTask(loadMap);
        mapInfo.execute(start, end);
    }

    // call sau khi đã load complete
    public void drawPlaceStartToEnd(final String snipetPoint) {

        PolylineOptions polylineOptions = new PolylineOptions().width(8).color(Color.GREEN);
        int numberDirection = listDirection.size();
        for (int i = 0; i < numberDirection; i++) {
            polylineOptions.add(listDirection.get(i));
        }
        mGoogleMap.addPolyline(polylineOptions);
        mGoogleMap.addPolyline(polylineOptions);
        mGoogleMap.addMarker(new MarkerOptions().position(end).title("Destination").snippet(snipetPoint));
        CameraPosition cameraPosition = new CameraPosition(start, 15, 15, MAP_ZOOM);
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public String getDistanceText(){
        return distanceText;
    }
    public int  getDistanceValue(){
        return distanceValue;
    }

    private class MapInfoAsyncTask extends AsyncTask<LatLng, Void, Void> {

        private IMapLoadComplete mapLoadComplete;

        private MapInfoAsyncTask(IMapLoadComplete mapLoadComplete) {
            this.mapLoadComplete = mapLoadComplete;
        }

        @Override
        protected Void doInBackground(LatLng... params) {
           Document document = v2Direction.getDocument(params[0], params[1]);
            listDirection = v2Direction.getDirection(document);
            distanceText = v2Direction.getDistanceText(document);
            distanceValue = v2Direction.getDistanceValue(document);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mapLoadComplete.onComplete();
        }
    }


}
