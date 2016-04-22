package com.example.johan.carbonfootprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tgk.integrationwithfragment.R;

/**
 * CarbonFootprintHelp Class, creates the help text
 * Created by Johan on 21-Apr-2016.
 */
public class CarbonFootprintHelp extends Fragment {
    /**
     *
     */
    View view;

    /**
     * onCreateView method, sets the help text
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.carbonfootprint_help, container, false);
        TextView help = (TextView) view.findViewById(R.id.helpText);
        help.setText("This application was made by Johan Setyobudi for CST 2335. This application is " +
                " a carbon footprint calculator. It calculates your carbon footprint for a vehicle " +
                "and is measured in tonnes. \n\nTo use this app please choose a category, vehicle type, distance" +
                "and a date at minimum. The note is extra information that is not needed. Then press " +
                "the add button to add your trip into the database. \n\nThere is also a view all records button" +
                " that will view all the current records inside the database. Clicking on one will bring you" +
                " to another page that will display the record in more detail, and also give you the option to" +
                " delete it.");

        // Inflate the layout for this fragment
        return view;
    }
    /**
     * Back button to main activity,
     * edited code from http://stackoverflow.com/questions/7992216/android-fragment-handle-back-button-press
     */
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    startActivity(new Intent(getActivity(), CarbonFootprintMainActivity.class));
                    return true;
                }
                return false;
            }
        });
    }
    /**
     * onStart
     */
    @Override
    public void onStart() {
        super.onStart();

    }


}

