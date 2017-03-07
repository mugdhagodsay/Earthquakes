package com.example.mukulkarni.earthquakes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
        String location = getLocation+earthquake.getLat()+","+earthquake.getLng();
        System.out.println("Endpoint: " + location);
        mMap.addMarker(new MarkerOptions().position(eqMarker)
                .title("Earthquake Details:"))
                .setSnippet("Depth: " + earthquake.getDepth() + " KM" + "\n"+"Magnitude: " + earthquake.getMagnitude() + "\n" + "Time & Date: " + earthquake.getDatetime());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eqMarker));
        // Set a listener for info window events.
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, marker.getSnippet(),
                Toast.LENGTH_LONG).show();
    }

}
