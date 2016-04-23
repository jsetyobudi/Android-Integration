package com.example.hwardak.tipcalculator;

import android.app.Activity;
import android.app.ListFragment;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
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

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;


        // Create an array adapter for the list view, using the Ipsum headlines array
        // setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));


        dbHelper = new TipCalculatorDbAdapter(getActivity());
        dbHelper.open();


        displayListView();

    }

    private void displayListView() {


        Cursor cursor = dbHelper.fetchAllRecords();

        // The desired columns to be bound
        String[] columns = new String[]{
                TipCalculatorDbAdapter.KEY_ROWID,
                TipCalculatorDbAdapter.KEY_NAME,
                TipCalculatorDbAdapter.KEY_TIPP,
                TipCalculatorDbAdapter.KEY_TIPD,
                TipCalculatorDbAdapter.KEY_TOTAL,
                TipCalculatorDbAdapter.KEY_NOTE
        };

        // the XML defined views which the data will be bound to
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

        //ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        setListAdapter(dataAdapter);
    }


    @Override
    public void onListItemClick(ListView listView, View view,
                                int position, long id) {
        // Get the cursor, positioned to the corresponding row in the result set
        Cursor cursor = (Cursor) listView.getItemAtPosition(position);

        // Get the state's capital from this row in the database.
        String countryCode =
                cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_ROWID));
        Toast.makeText(getActivity(),
                countryCode, Toast.LENGTH_SHORT).show();
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
