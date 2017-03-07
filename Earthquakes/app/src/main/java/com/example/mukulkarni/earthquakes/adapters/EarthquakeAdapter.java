package com.example.mukulkarni.earthquakes.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mukulkarni.earthquakes.R;
import com.example.mukulkarni.earthquakes.model.Earthquake;

import java.util.ArrayList;

/**
 * Created by mukulkarni on 3/6/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes)
    {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        Earthquake earthquake = getItem(position);
        System.out.println("Item Position: " + position);
        System.out.println("Magnitude: " + earthquake.getMagnitude());
        System.out.println("Lat:" + earthquake.getLat());

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_row, parent, false);
        }

        //Getting reference to TextView to set Depth
        TextView tvDepth = (TextView) convertView.findViewById(R.id.etDepth);

        //Getting reference to TextView to set Magnitude
        TextView tvMagnitude = (TextView) convertView.findViewById(R.id.etMagnitude);

        tvDepth.setText(earthquake.getDepth().toString() + " KM");
        tvMagnitude.setText(earthquake.getMagnitude().toString());
        if(earthquake.getMagnitude() > 8) {
            tvMagnitude.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.red));
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
