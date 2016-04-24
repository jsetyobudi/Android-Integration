package com.example.hwardak.tipcalculator;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.tgk.integrationwithfragment.R;

/**
 * Created by HWardak on 16-04-20.
 */

/* IMPORTANT NOTE: Code in this file was based on the lecture
 * materials provided by Todd Kelly (2016).
 */
public class StoreRecordFragment extends ListFragment {
    OnHeadlineSelectedListener mCallback;

    TipCalculatorDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnHeadlineSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onArticleSelected(int position);
    }

    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar myToolbar = (Toolbar)  getActivity().findViewById(R.id.my_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);
        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;


        // Create an array adapter for the list view, using the Ipsum headlines array
        // setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));


        dbHelper = new TipCalculatorDbAdapter(getActivity());
        dbHelper.open();


        displayListView();

    }

    /**
     * This will create a String array of the columns to be bound.
     * Also creates an int array of xml defined views.\
     * Also create the adapter used the cursor pointing to the desired data
     * aswell as the layout information, and will assign the adapter to the
     * list view.
     */
    private void displayListView() {
        Cursor cursor = dbHelper.fetchAllRecords();
        String[] columns = new String[]{
                TipCalculatorDbAdapter.KEY_ROWID,
                TipCalculatorDbAdapter.KEY_NAME,
                TipCalculatorDbAdapter.KEY_TIPP,
                TipCalculatorDbAdapter.KEY_TIPD,
                TipCalculatorDbAdapter.KEY_TOTAL,
                TipCalculatorDbAdapter.KEY_NOTE
        };

        int[] to = new int[]{
                R.id.name,
                R.id.customTipField,
                R.id.tipField,
                R.id.totalField,
                R.id.note
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                getActivity(), R.layout.view_record_fragment,
                cursor,
                columns,
                to,
                0);

        // Assign adapter to ListView
        setListAdapter(dataAdapter);
    }


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

        if (id == R.id.action_settings) {
            return true;
        }
        switch (id){
            case R.id.action_one:
               // go(com.example.hwardak.tipcalculator.MainActivity.class);
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


    @Override
    public void onListItemClick(ListView listView, View view,
                                int position, long id) {
        // Get the cursor, positioned to the corresponding row in the result set
        Cursor cursor = (Cursor) listView.getItemAtPosition(position);

        // Get the state's capital from this row in the database.
        String countryCode =
                cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_ROWID));
//        Toast.makeText(getActivity(),
//                countryCode, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // When in two-pane layout, set the listview to highlight the selected list item
//        // (We do this during onStart because at the point the listview is available.)
//        if (getFragmentManager().findFragmentById(R.id.article_fragment) != null) {
//            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        }
//    }

    /**
     * This will make sure the container activity has implemented the callback interface.
     * If not it will throw an exception/
     * @param activity the activity which invoked this method.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
