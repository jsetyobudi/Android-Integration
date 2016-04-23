package com.example.johan.carbonfootprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        Toolbar myToolbar = (Toolbar)  getActivity().findViewById(R.id.my_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);
        TextView help = (TextView) view.findViewById(R.id.helpText);
        help.setText("This application was made by Johan Setyobudi for CST 2335. This application is " +
                " a carbon footprint calculator. It calculates your carbon footprint for a vehicle " +
                "and is measured in tonnes. \n\nTo use this app please choose a category, vehicle type, distance " +
                "and a date at minimum. The note is extra information that is not needed. Then press " +
                "the add button to add your trip into the database. \n\nThere is also a view all records button " +
                "that will view all the current records inside the database. Clicking on one will bring you " +
                "to another page that will display the record in more detail, give you a small summary of the total carbon footprint for the category, vehicle type " +
                "and overall total, and also give you the option to delete the certain record.");

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Creating the fragment menu
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    /**
     * Switching to different activities
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch (id){
            case R.id.action_one:
                go(com.example.hwardak.tipcalculator.MainActivity.class);
                break;
            case R.id.action_two:
                go(com.example.diego.activitytracker.MainActivity.class);
                break;
            case R.id.action_three:
                //go(com.example.johan.carbonfootprint.CarbonFootprintMainActivity.class);
                break;
            case R.id.action_four:
                go(com.example.xuan.contactlist.MainActivity.class);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * change activities
     * @param c
     */
    private void go(Class c){
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
    }

    /**
     * onStart
     */
    @Override
    public void onStart() {
        super.onStart();
    }
}

