package com.example.hwardak.tipcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tgk.integrationwithfragment.R;

/**
 * To return a summary view.
 */
public class SummaryView extends AppCompatActivity{

    private TextView summaryText;
    TipCalculatorDbAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_summary_view);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        summaryText = (TextView)findViewById(R.id.summarytextView);

        db = new TipCalculatorDbAdapter(this);

        db.open();
        summaryText.setText(db.dataSummary().toString());

        db.close();
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
              //  go(com.example.hwardak.tipcalculator.MainActivity.class);
                break;
            case R.id.action_two:
                go(com.example.diego.activitytracker.MainActivity.class);
                break;
            case R.id.action_three:
                go(com.example.johan.carbonfootprint.CarbonFootprintMainActivity.class);
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
}
