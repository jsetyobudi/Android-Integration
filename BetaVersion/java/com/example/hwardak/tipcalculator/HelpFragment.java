package com.example.hwardak.tipcalculator;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.tgk.integrationwithfragment.R;

/**
 * Created by HWardak on 16-04-20.
 */
public class HelpFragment extends Fragment {


    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return LayoutInflator object.
     * This method will call upon the help_fragment to fill its designated container.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Toolbar myToolbar = (Toolbar)  getActivity().findViewById(R.id.my_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.help_fragment, container,  false);
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
    }
}


