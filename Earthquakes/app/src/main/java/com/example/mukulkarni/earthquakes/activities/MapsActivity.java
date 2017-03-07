package com.example.mukulkarni.earthquakes.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.example.mukulkarni.earthquakes.R;
import com.example.mukulkarni.earthquakes.model.Earthquake;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private String getLocation = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";
    Earthquake earthquake;
    Marker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent originalIntent = getIntent();
        Bundle bundle = originalIntent.getExtras();
        earthquake = (Earthquake) bundle.getSerializable("earthquake");

        // Add a marker in Sydney and move the camera
        LatLng eqMarker = new LatLng(earthquake.getLat(), earthquake.getLng());

        //Get Location information for the earthquake
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(earthquake.getLat(), earthquake.getLng(), 1);
            for (Address add : addresses) {
                earthquake.setLocation(add.getCountryName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Add marker
        mMap.addMarker(new MarkerOptions().position(eqMarker)
                .title("Earthquake Details:"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eqMarker));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(eqMarker));
        // Set a listener for info window events.
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        String countryNameString = "Country name: ";
        if (earthquake.getLocation() == null) {
            countryNameString += "Cannot be determined";
        } else {
            countryNameString += earthquake.getLocation();
        }
        marker.setSnippet(countryNameString + "\n" + "Depth: " + earthquake.getDepth() +
                " KM" + "\n" + "Magnitude: " + earthquake.getMagnitude() + "\n" + "Time & Date: "
                + earthquake.getDatetime());
        Toast toast = Toast.makeText(this, marker.getSnippet(),
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.TOP, 0, 0);
        toast.show();
    }

}
