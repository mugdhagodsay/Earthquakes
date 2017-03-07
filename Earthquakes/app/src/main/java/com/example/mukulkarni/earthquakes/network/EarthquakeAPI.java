package com.example.mukulkarni.earthquakes.network;

import android.os.AsyncTask;

import com.example.mukulkarni.earthquakes.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mukulkarni on 3/6/17.
 */

public class EarthquakeAPI extends AsyncTask<String, Void, String> {

    private  String endpoint_1 = "http://api.geonames.org/earthquakesJSON?formatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman";
    private MainActivity mainActivity;

    public EarthquakeAPI(MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
    }

    public  String getEarthquakeData(String endpoint) throws  IOException {
        URL url = new URL(endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        InputStream in = conn.getInputStream();
      //  System.out.println("Response Message is: " + conn.getResponseMessage());
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
     //   System.out.println("Data From the network is: " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    protected void onPreExecute() {
        // Show progress dialog
        super.onPreExecute();
    }


    protected void onPostExecute(String result)
    {
        mainActivity.populate(result);
    }


    @Override
    protected String doInBackground(String... params) {
        try {
      //      System.out.println("ENDPOINT is: " + params[0]);
            String result = getEarthquakeData(params[0]);
      //      System.out.println("The result is : " + result);
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
