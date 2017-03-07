package com.example.mukulkarni.earthquakes.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mukulkarni.earthquakes.R;
import com.example.mukulkarni.earthquakes.adapters.EarthquakeAdapter;
import com.example.mukulkarni.earthquakes.model.Earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.mukulkarni.earthquakes.R.id.lvItems;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Earthquake> arrayOfEarthquakes;
    private EarthquakeAdapter earthquakeAdapter;
    private ListView lvEarthquakes;
    private String eqDataEndpoint = "http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvEarthquakes = (ListView) findViewById(lvItems);
        arrayOfEarthquakes = new ArrayList<>();
        earthquakeAdapter = new EarthquakeAdapter(this, arrayOfEarthquakes);
      //  getEarthquakeData();
        new EarthquakeAPI().execute(eqDataEndpoint);
        for (Earthquake e : arrayOfEarthquakes) {
            System.out.println("The Magnitude is: " + e.getMagnitude().toString());
        }
        lvEarthquakes.setAdapter(earthquakeAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvEarthquakes.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        intent.putExtra("position", pos);
                        intent.putExtra("earthquake", (Serializable) earthquakeAdapter.getItem(pos));
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public void onStart() {
        super.onStart();
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
    public void populate(String result)
    {
        try {
            JSONObject json = new JSONObject(result);
            JSONArray jsonArray = json.getJSONArray("earthquakes");
            int count = jsonArray.length();
            for (int i = 0; i < count; i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Earthquake earthquake = new Earthquake();
                earthquake.setDatetime(object.getString("datetime"));
                earthquake.setDepth(object.getInt("depth"));
                earthquake.setEqid(object.getString("eqid"));
                earthquake.setLat(object.getDouble("lat"));
                earthquake.setLng(object.getDouble("lng"));
                earthquake.setMagnitude(object.getDouble("magnitude"));
                earthquake.setSrc(object.getString("src"));
                arrayOfEarthquakes.add(earthquake);
            }
            lvEarthquakes.setAdapter(earthquakeAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //EarthquakeAPI to make the call to the server and get the data to populate the Listview
    private class EarthquakeAPI extends AsyncTask<String, Void, String> {

        public  String getEarthquakeData(String endpoint) throws  IOException {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPreExecute() {
            // Show progress dialog
            super.onPreExecute();
        }


        protected void onPostExecute(String result)
        {
            populate(result);
        }


        @Override
        protected String doInBackground(String... params) {
            try {
                String result = getEarthquakeData(params[0]);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // Show progress update
            super.onProgressUpdate(values);
        }

    }

}
