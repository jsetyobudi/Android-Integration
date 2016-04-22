package com.example.tgk.integrationwithfragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Johan on 21-Apr-2016.
 */
public class CarbonFootprintHelp extends Fragment {

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.carbonfootprint_help, container, false);
        TextView help = (TextView) view.findViewById(R.id.helpText);
        help.setText("This application was made by Johan Setyobudi for CST 2335. This application is " +
                " a carbon footprint calculator. It calculates your carbon footprint for a vehicle " +
                "and is measured in tonnes. To use this app please choose a category, vehicle type, distance" +
                "and a date at minimum. The note is extra information that is not needed. Then press " +
                "the add button to add your trip into the database. There is also a view all records button" +
                " that will view all the current records inside the database. Clicking on one will bring you" +
                " to another page that will display the record in more detail, and also give you the option to" +
                " delete it.");

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}

