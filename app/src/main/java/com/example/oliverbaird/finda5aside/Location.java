package com.example.oliverbaird.finda5aside;
//
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
///**
// * An activity that displays a Google map with a marker (pin) to indicate a particular location.
// */
//public class Location extends AppCompatActivity
//        implements OnMapReadyCallback {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // Retrieve the content view that renders the map.
//        setContentView(R.layout.activity_location);
//        // Get the SupportMapFragment and request notification
//        // when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        Bundle detailBundleMaps = getIntent().getExtras();
//        String mapLocation = detailBundleMaps.getString("location");
//        Toast.makeText(Location.this, "Here is " + mapLocation, Toast.LENGTH_SHORT).show();
//    }
//
//    /**
//     * Manipulates the map when it's available.
//     * The API invokes this callback when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user receives a prompt to install
//     * Play services inside the SupportMapFragment. The API invokes this method after the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//
//            // Add a marker in Sydney, Australia,
//            // and move the map's camera to the same location.
//            LatLng playBall = new LatLng(54.5957798, -5.8247721);
//            googleMap.addMarker(new MarkerOptions().position(playBall)
//                    .title("PlayBall Stormont"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(playBall));
//
//
//
//    }
//}

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.nio.charset.MalformedInputException;

public class Location extends FragmentActivity implements
        OnMapReadyCallback {

    private static final LatLng PLAYBALL = new LatLng(54.5957798, -5.8247721);
    private static final LatLng PEC = new LatLng(54.5808641,-5.9318286);
    private static final LatLng OLYMPIA = new LatLng(54.5854456,-5.9619947);

    private Marker mPlayball;
    private Marker mPec;
    private Marker mOlympia;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /** Called when the map is ready. */
    @Override
    public void onMapReady(GoogleMap map) {

        Bundle detailBundleMaps = getIntent().getExtras();
        String mapLocation = detailBundleMaps.getString("location");
//        Toast.makeText(Location.this, "Here is " + mapLocation, Toast.LENGTH_SHORT).show();

            mMap = map;

            // Add some markers to the map, and add a data object to each marker.
            mPlayball = mMap.addMarker(new MarkerOptions()
                    .position(PLAYBALL)
                    .title("Playball"));

            mPlayball.setTag(0);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(PLAYBALL, 10));


            mPec = mMap.addMarker(new MarkerOptions()
                    .position(PEC)
                    .title("PEC"));

            mPec.setTag(0);
            map.moveCamera(CameraUpdateFactory.newLatLng(PEC));


            mOlympia = mMap.addMarker(new MarkerOptions()
                    .position(OLYMPIA)
                    .title("Olympia"));


            mOlympia.setTag(0);
            map.moveCamera(CameraUpdateFactory.newLatLng(OLYMPIA));

        if (mapLocation == "PlayBall Stormont"){
            mPlayball.showInfoWindow();
        }

        }
    }




