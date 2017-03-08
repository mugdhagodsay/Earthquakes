package com.example.mukulkarni.earthquakes.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mukulkarni.earthquakes.R;
import com.example.mukulkarni.earthquakes.adapters.EarthquakeAdapter;
import com.example.mukulkarni.earthquakes.model.Earthquake;
import com.example.mukulkarni.earthquakes.network.EarthquakeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.mukulkarni.earthquakes.R.id.lvItems;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Earthquake> arrayOfEarthquakes;
    private EarthquakeAdapter earthquakeAdapter;
    private ListView lvEarthquakes;
    private String eqDataEndpoint = "http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman";
    private final String EARTHQUAKES = "earthquakes";
    private final String DATETIME = "datetime";
    private final String DEPTH = "depth";
    private final String MAGNITUDE = "magnitude";
    private final String LATITUDE = "lat";
    private final String LONGITUDE = "lng";
    private final String SRC = "src";
    private final String EQID = "eqid";
    private final String STATUS = "status";
    private final String MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting reference to the ListView
        lvEarthquakes = (ListView) findViewById(lvItems);

        // Getting reference to Array of Earthquakes
        arrayOfEarthquakes = new ArrayList<>();

        //Getting reference to the EarthQuakeAdapter
        earthquakeAdapter = new EarthquakeAdapter(this, arrayOfEarthquakes);

        //Check if network is available and we are online get earthquake data else show Alert
        if (isNetworkAvailable() && isOnline()) {
            EarthquakeAPI eqAPI = new EarthquakeAPI(this);
            eqAPI.execute(eqDataEndpoint);
        } else {
            showAlert("No Network Detected", "Network Error");
        }

        //Set Adapter
        lvEarthquakes.setAdapter(earthquakeAdapter);

        //ListViewListener
        setupListViewListener();
    }

    //Setup setOnItemClickListener to take the user to the MapActivity
    private void setupListViewListener() {
        lvEarthquakes.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        intent.putExtra("position", pos);
                        intent.putExtra("earthquake", earthquakeAdapter.getItem(pos));
                        startActivity(intent);
                    }
                }
        );
    }

    /**
     * Show Alert dialogue when there is an error
     */
    public void showAlert(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(message)
                .setTitle(title);
        // Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        //If network is available and we are online get earthquake data else show Alert
        if (isNetworkAvailable() && isOnline()) {
            if (arrayOfEarthquakes == null) {
                EarthquakeAPI eqAPI = new EarthquakeAPI(this);
                eqAPI.execute(eqDataEndpoint);
            }
        } else {
            showAlert("No Network Detected", "Network Error");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Read the JSON returned by the server and populate the ListView
    public void populate(String result) {
        if (result == null) {
            showAlert("Oops! Sorry about that.", "Something is not right");
        } else {
                try {
                    JSONObject json = new JSONObject(result);
                    JSONArray jsonArray = json.getJSONArray(EARTHQUAKES);
                    int count = jsonArray.length();
                    for (int i = 0; i < count; i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Earthquake earthquake = new Earthquake();
                        earthquake.setDatetime(object.getString(DATETIME));
                        earthquake.setDepth(object.getInt(DEPTH));
                        earthquake.setEqid(object.getString(EQID));
                        earthquake.setLat(object.getDouble(LATITUDE));
                        earthquake.setLng(object.getDouble(LONGITUDE));
                        earthquake.setMagnitude(object.getDouble(MAGNITUDE));
                        earthquake.setSrc(object.getString(SRC));
                        arrayOfEarthquakes.add(earthquake);
                    }
                    lvEarthquakes.setAdapter(earthquakeAdapter);

                } catch (JSONException e){
                    try {
                        JSONObject json = new JSONObject(result);
                        showAlert(json.getJSONObject(STATUS).getString(MESSAGE), "ERROR");

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }
    }
}
