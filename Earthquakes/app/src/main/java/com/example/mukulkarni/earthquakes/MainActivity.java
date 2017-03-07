package com.example.mukulkarni.earthquakes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.mukulkarni.earthquakes.adapters.EarthquakeAdapter;
import com.example.mukulkarni.earthquakes.model.Earthquake;
import com.example.mukulkarni.earthquakes.network.EarthquakeAPI;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Earthquake> arrayOfEarthquakes;
    EarthquakeAdapter earthquakeAdapter;
    ListView lvEarthquakes;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvEarthquakes = (ListView) findViewById(R.id.lvItems);
        arrayOfEarthquakes = new ArrayList<>();
        earthquakeAdapter = new EarthquakeAdapter(this, arrayOfEarthquakes);
        readFromJSON();
        for(Earthquake e : arrayOfEarthquakes){
            System.out.println("The Magnitude is: " + e.getMagnitude().toString());
        }
        lvEarthquakes.setAdapter(earthquakeAdapter);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    //Read json data from file
    private void readFromJSON()
    {
        File fileDir = getFilesDir();
        File earthquakeFile = new File(fileDir, "earthquakes.json");
        EarthquakeAPI earthquakeAPI = new EarthquakeAPI(this);
        earthquakeAPI.execute("http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman");
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
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    public void populate(String result)
    {
        try {
            JSONObject json = new JSONObject(result);
            JSONArray jsonArray = json.getJSONArray("earthquakes");
            int count = jsonArray.length();
            for(int i = 0; i < count; i++){
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

        } catch(JSONException e)
        {
            e.printStackTrace();
        }
    }
}
