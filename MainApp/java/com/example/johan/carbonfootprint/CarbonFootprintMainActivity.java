package com.example.johan.carbonfootprint;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.tgk.integrationwithfragment.R;

/**
 * Main activity for the Carbon Footprint Class
 * Created by Johan on 16-Apr-2016.
 */
public class CarbonFootprintMainActivity extends AppCompatActivity
        implements CarbonFootprintViewAllRecords.OnFootprintSelectedListener, CarbonFootprintViewDetailsFragment.OnFootprintListener
        , CarbonFootprintAdd.OnViewAllRecordsSelectedListener, CarbonFootprintAdd.OnHelpSelectedListener  {

    /**
     * UPDATE: updated code so a lot of commented out
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carbonfootprint_articles);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.footprint_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }
            // Create an instance of ExampleFragment
            CarbonFootprintAdd firstFragment = new CarbonFootprintAdd();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.footprint_container, firstFragment).commit();
            //everything below here is my old code, please ignore
            setSupportActionBar(myToolbar);
        }
    }


    /**
     * creating menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * changing activities on the toolbar
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
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }


    /**
     * changing to detail view based on selection
     * @param position
     * @param id
     */
    @Override
    public void onFootprintSelected(int position, long id) {

            // Create fragment and give it an argument for the selected article
            CarbonFootprintViewDetailsFragment newFragment = new CarbonFootprintViewDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(CarbonFootprintViewDetailsFragment.ARG_POSITION, position);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.footprint_container, newFragment);

            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
}


    /**
     * Go back to the main activity
     */
    @Override
    public void onFootprintDeleted() {

        CarbonFootprintViewAllRecords newFragment = new CarbonFootprintViewAllRecords();
        Bundle args = new Bundle();
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.footprint_container, newFragment);

        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }


    @Override
    public void onHelpSelected() {

        CarbonFootprintHelp newFragment = new CarbonFootprintHelp();
        Bundle args = new Bundle();
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.footprint_container, newFragment);

        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onViewAllRecordsSelected() {

        CarbonFootprintViewAllRecords newFragment = new CarbonFootprintViewAllRecords();
        Bundle args = new Bundle();
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.footprint_container, newFragment);

        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();

    }
}

