package com.example.johan.carbonfootprint;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.tgk.integrationwithfragment.R;

/**
 * view all records class
 * Created by Johan on 19-Apr-2016.
 */
public class CarbonFootprintViewAllRecords extends ListFragment {

    CarbonFootprintDBAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    OnFootprintSelectedListener mCallback;

    /**
     * interface that has to be implemented
     */
    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnFootprintSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onFootprintSelected(int position, long id);
    }


    /**
     * on create
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;

        Toolbar myToolbar = (Toolbar)  getActivity().findViewById(R.id.my_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);
        // Create an array adapter for the list view, using the Ipsum headlines array
        // setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));

        dbHelper = new CarbonFootprintDBAdapter(getActivity());
        dbHelper.open();

        //Generate ListView from SQLite Database
        displayListView();

    }
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, getActivity().getMenuInflater());
        inflater.inflate(R.menu.fragment_menu, menu);
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
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
    }
    /*
     * Sets up a ListView with a SimpleCursorAdapter and a Cursor with all the rows
     * from the database table.  Also sets up the handler for when an item is selected.
     */
    private void displayListView() {
       // Cursor cursor = dbHelper.fetchAllFootprint();

        // The desired columns to be bound
        String[] columns = new String[]{
               // CarbonfootprintDBAdapter.KEY_ROWID,
                CarbonFootprintDBAdapter.KEY_CATEGORY,
                CarbonFootprintDBAdapter.KEY_VEHICLE,
                CarbonFootprintDBAdapter.KEY_DISTANCE,
                CarbonFootprintDBAdapter.KEY_DATE,
//                CarbonfootprintDBAdapter.KEY_NOTE,
//                CarbonfootprintDBAdapter.KEY_FOOTPRINT

        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
             //   R.id.rowid,
                R.id.category,
                R.id.vehicle,
                R.id.distance,
                R.id.date,
//                R.id.note,
//                R.id.footprint
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        //cursor is null for now, but will be swapped by the following AsyncTask onPostExecute method
        dataAdapter = new SimpleCursorAdapter(
                getActivity(), R.layout.carbonfootprint_info,
                null,      //notice the cursor is null for now
                columns,
                to, 0);

        //This Java statement (beginning with "new" and ending with "}.execute();") executes an new instance
        // of an anonymous class that extends AsyncTask.  The new instance is-a AsyncTask.
        // Executes an AsyncTask to acquire the cursor on a background thread
        //in onPostExecute, the real cursor will replace the null cursor
        new AsyncTask<Void, Void, Cursor>() {
            @Override
            public Cursor doInBackground(Void... v) {
                dbHelper = new CarbonFootprintDBAdapter(getActivity());
                dbHelper.open();

//                //Clean all data
//                dbHelper.deleteAllCountries();
//                //Add some data
//                dbHelper.insertSomeCountries();

                return dbHelper.fetchAllFootprint();
            }

            @Override
            public void onPostExecute(Cursor c) {
                dataAdapter.swapCursor(c);
            }
        }.execute();


      //  ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView

        setListAdapter(dataAdapter);


    }

    /**
     * on item click, get more detail
     * @param listView
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onListItemClick(ListView listView, View view,
                                int position, long id) {
        // Get the cursor, positioned to the corresponding row in the result set
        Cursor cursor = (Cursor) listView.getItemAtPosition(position);

        // Get the state's capital from this row in the database.
        String countryCode =
                cursor.getString(cursor.getColumnIndexOrThrow(CarbonFootprintDBAdapter.KEY_ROWID));

//        Toast.makeText(getActivity(),
//                countryCode, Toast.LENGTH_SHORT).show();

        mCallback.onFootprintSelected(Integer.parseInt(countryCode), id);
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
     * on start
     */
    @Override
    public void onStart() {
        super.onStart();

//
    }// When in two-pane layout, set the listview to highlight the selected list item


    //        // (We do this during onStart because at the point the listview is available.)
//        if (getFragmentManager().findFragmentById(R.id.footprintDetail_fragment) != null) {
//            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        }
    /**
     * attaching interface
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnFootprintSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
