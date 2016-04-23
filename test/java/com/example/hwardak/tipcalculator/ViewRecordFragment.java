package com.example.hwardak.tipcalculator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.johan.carbonfootprint.CarbonFootprintDBAdapter;
import com.example.johan.carbonfootprint.CarbonFootprintMainActivity;
import com.example.tgk.integrationwithfragment.R;

/**
 * Created by HWardak on 16-04-20.
 */
public class ViewRecordFragment extends ListFragment {


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.view_record_fragment, container, false);
//
//
//    }
    TipCalculatorDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    OnTiptSelectedListener mCallback;

    /**
     * interface that has to be implemented
     */
    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnTiptSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onTipSelected(int position, long id);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        dbHelper = new TipCalculatorDbAdapter(getActivity());
//        dbHelper.open();
//
//        //Generate ListView from SQLite Database
//        displayListView();
//        return inflater.inflate(R.layout.view_record_fragment, container, false);
//
//
//    }

    /**
     * on create
     * @param //savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;


        // Create an array adapter for the list view, using the Ipsum headlines array
        // setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));

        dbHelper = new TipCalculatorDbAdapter(getActivity());
        dbHelper.open();

        //Generate ListView from SQLite Database
        displayListView();

    }


    /*
     * Sets up a ListView with a SimpleCursorAdapter and a Cursor with all the rows
     * from the database table.  Also sets up the handler for when an item is selected.
     */
    private void displayListView() {
        // Cursor cursor = dbHelper.fetchAllFootprint();

        // The desired columns to be bound
        String[] columns = new String[]{
               // TipCalculatorDbAdapter.KEY_ROWID,
               TipCalculatorDbAdapter.KEY_NAME,
                TipCalculatorDbAdapter.KEY_TIPP,
                TipCalculatorDbAdapter.KEY_TIPD,
                TipCalculatorDbAdapter.KEY_TOTAL,
//                CarbonfootprintDBAdapter.KEY_NOTE,
//                CarbonfootprintDBAdapter.KEY_FOOTPRINT

        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
               // R.id.rowid,
                R.id.name,
                R.id.tipp,
                R.id.tipd,
                R.id.total,
//                R.id.note,
//                R.id.footprint
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        //cursor is null for now, but will be swapped by the following AsyncTask onPostExecute method
        dataAdapter = new SimpleCursorAdapter(
                getActivity(), R.layout.view_record_fragment,
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
                dbHelper = new TipCalculatorDbAdapter(getActivity());
                dbHelper.open();

//                //Clean all data
//                dbHelper.deleteAllCountries();
//                //Add some data
//                dbHelper.insertSomeCountries();

                return dbHelper.fetchAllRecords();
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

       Toast.makeText(getActivity(),
               countryCode, Toast.LENGTH_SHORT).show();

        mCallback.onTipSelected(Integer.parseInt(countryCode), id);
    }

    //TODO:I GOT THIS FROM INTERENT
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    startActivity(new Intent(getActivity(), com.example.hwardak.tipcalculator.MainActivity.class));
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
            mCallback = (OnTiptSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}