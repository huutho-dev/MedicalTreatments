package com.edu.gvn.medicaltreatments.fragment;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.gvn.medicaltreatments.R;
import com.edu.gvn.medicaltreatments.activity.MainActivity;
import com.edu.gvn.medicaltreatments.common.GoogleMapManager;
import com.edu.gvn.medicaltreatments.common.LogUtils;
import com.edu.gvn.medicaltreatments.common.Utils;
import com.edu.gvn.medicaltreatments.service.UpDateLocationService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import static com.edu.gvn.medicaltreatments.activity.MainActivity.mUpdateLocationService;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, UpDateLocationService.LocationChangeListener {

    public static final String _BUNDLE_NAME_LOCATION = "bundle.name";
    public static final String _BUNDLE_ADDRESS_LOCATION = "bundle.address";

    private GoogleMapManager mapManager;
    private MapView mapView;
    private TextView displacementMeter;
    private String name, address;
    private LatLng startPoint, endPoint;

    public static MapFragment newInstance(String name, String address) {

        Bundle args = new Bundle();
        args.putString(_BUNDLE_NAME_LOCATION, name);
        args.putString(_BUNDLE_ADDRESS_LOCATION, address);
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void getDataBundle(Bundle saveInstanceState) {
        name = saveInstanceState.getString(_BUNDLE_NAME_LOCATION);
        address = saveInstanceState.getString(_BUNDLE_ADDRESS_LOCATION);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUpdateLocationService.setLocationChangeListener(this);
        if (savedInstanceState == null) {
            getDataBundle(getArguments());
        } else {
            getDataBundle(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) v.findViewById(R.id.map);
        displacementMeter = (TextView) v.findViewById(R.id.meter);
        initGoogleMap(savedInstanceState);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        endPoint = Utils.getLatLngFromAddress(getContext(), address);
        startPoint = MainActivity.mUpdateLocationService.getLatLng();

        mapManager = new GoogleMapManager(getContext(), getActivity(), googleMap);
        mapManager.setLoadMap(startPoint, endPoint, new GoogleMapManager.IMapLoadComplete() {
            @Override
            public void onComplete() {
                mapManager.drawPlaceStartToEnd(name);
                displacementMeter.setText(mapManager.getDistanceText());

            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    private void initGoogleMap(Bundle savedInstanceState) {
        try {
            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            MapsInitializer.initialize(getActivity().getApplicationContext());
            mapView.getMapAsync(this);
        } catch (Exception e) {
            LogUtils.v("huutho", "MapsInitializer error !");
        }
    }


}

