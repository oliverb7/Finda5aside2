package com.example.oliverbaird.finda5aside;

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

public class Location extends FragmentActivity implements
        OnMapReadyCallback {

    private static final LatLng PLAYBALL = new LatLng(54.5968815,-5.8343839);
    private static final LatLng PEC = new LatLng(54.5808641, -5.9318286);
    private static final LatLng OLYMPIA = new LatLng(54.5854456, -5.9619947);
    private static final LatLng HANWOOD = new LatLng(54.5898985,-5.827632);
    private static final LatLng EDDIES = new LatLng(54.6389856,-5.6762917);
    private static final LatLng LAGAN = new LatLng(54.5060055,-6.0532443);

    private Marker mPlayball;
    private Marker mPec;
    private Marker mOlympia;
    private Marker mHanwood;
    private Marker mEddies;
    private Marker mLagan;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        final Bundle detailBundleMaps = getIntent().getExtras();

        if (detailBundleMaps != null) {
            String mapLocation = detailBundleMaps.getString("location");
            Toast.makeText(Location.this, "Here is " + mapLocation, Toast.LENGTH_SHORT).show();

            mMap = map;
            mMap.clear();

            if (mapLocation.equals("PlayBall Stormont")) {


                // Add some markers to the map, and add a data object to each marker.
                mPlayball = mMap.addMarker(new MarkerOptions()
                        .position(PLAYBALL)
                        .title("Playball Stormont"));
                        mPlayball.showInfoWindow();

                mPlayball.setTag(0);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PLAYBALL, 15));

            } else

            if (mapLocation.equals("Queens PEC")) {


                mPec = mMap.addMarker(new MarkerOptions()
                        .position(PEC)
                        .title("PEC Queens"));
                        mPec.showInfoWindow();

                mPec.setTag(0);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PEC, 15));

            } else

            if (mapLocation.equals("Olympia MUGA")) {


                mOlympia = mMap.addMarker(new MarkerOptions()
                        .position(OLYMPIA)
                        .title("Olympia MUGA"));
                mOlympia.showInfoWindow();


                mOlympia.setTag(0);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(OLYMPIA, 15));

            }

            if (mapLocation.equals("Hanwood Centre")) {


                mHanwood = mMap.addMarker(new MarkerOptions()
                        .position(HANWOOD)
                        .title("Hanwood Centre"));
                mHanwood.showInfoWindow();


                mHanwood.setTag(0);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HANWOOD, 15));

            }

            if (mapLocation.equals("Eddie Irvine's")) {


                mEddies = mMap.addMarker(new MarkerOptions()
                        .position(EDDIES)
                        .title("Eddie Irvine's"));
                mEddies.showInfoWindow();


                mEddies.setTag(0);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(EDDIES, 10));

            }

            if (mapLocation.equals("Lagan Valley")) {


                mLagan = mMap.addMarker(new MarkerOptions()
                        .position(LAGAN)
                        .title("Lagan Valley"));
                mLagan.showInfoWindow();


                mLagan.setTag(0);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LAGAN, 10));

            }


        }
    }
}






