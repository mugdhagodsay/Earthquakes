package com.example.mukulkarni.earthquakes.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

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

import static com.example.mukulkarni.earthquakes.R.layout.earthquake_detail;

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
        Marker marker = mMap.addMarker(new MarkerOptions().position(eqMarker)
                .title("Earthquake Details:"));

        // Setting a custom info window adapter for the google map
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file earthquake_detail layout
                View view = getLayoutInflater().inflate(earthquake_detail, null);

                // Getting the position from the marker
                LatLng latLng = arg0.getPosition();

                // Getting reference to the TextView to set the latitude
                TextView tvLat = (TextView) view.findViewById(R.id.tv_lat);

                // Getting reference to the TextView to set the longitude
                TextView tvLng = (TextView) view.findViewById(R.id.tv_lng);

                //Getting reference to the TextView to set the depth
                TextView tvDepth = (TextView) view.findViewById(R.id.tv_depth);

                //Getting reference to the TextView to set the magnitude
                TextView tvMagnitude = (TextView) view.findViewById(R.id.tv_magnitude);

                //Getting reference to the TextView to set the country
                TextView tvCountry = (TextView) view.findViewById(R.id.tv_country);

                // Setting the latitude
                tvLat.setText("Latitude: " + latLng.latitude);

                // Setting the longitude
                tvLng.setText("Longitude: "+ latLng.longitude);

                //Setting the depth
                tvDepth.setText("Depth: " + earthquake.getDepth() + " KM");

                //Setting the magnitude
                tvMagnitude.setText("Magnitude: " + earthquake.getMagnitude());
                if(earthquake.getMagnitude() > 8) {
                    tvMagnitude.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.red));
                }

                String countryNameString = "Country: ";
                if (earthquake.getLocation() == null) {
                    countryNameString += "Cannot be determined";
                } else {
                    countryNameString += earthquake.getLocation();
                }

                //Setting the country name
                tvCountry.setText(countryNameString);

                // Returning the view containing InfoWindow contents
                return view;

            }
        });
        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eqMarker));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(eqMarker));
        // Set a listener for info window events.
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if(marker.isVisible()){
            marker.hideInfoWindow();
        } else {
            marker.showInfoWindow();
        }
    }

}
