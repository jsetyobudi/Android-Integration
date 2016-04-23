package com.example.hwardak.tipcalculator;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.johan.carbonfootprint.CarbonFootprintDBAdapter;
import com.example.johan.carbonfootprint.CarbonFootprintMainActivity;
import com.example.tgk.integrationwithfragment.R;

/**
 * Created by Johan on 22-Apr-2016.
 */
public class ViewDetailsRecordFragment extends Fragment implements View.OnClickListener {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    long id = -1;
    TipCalculatorDbAdapter dbHelper;
    Button delete;
    View view;
    OnTipsDetailListener mCallback;

    /**
     * interface that needs to be implemented
     */
    public interface OnTipsDetailListener {
        public void onTipDeleted();

    }

    /**
     * on create
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tipcalculatordetails_info, container, false);

        delete = (Button) view.findViewById(R.id.deleteButton);
        delete.setOnClickListener(this);
        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * on start
     */
    @Override
    public void onStart() {
        super.onStart();
        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateTipView(args.getInt(ARG_POSITION), id);
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateTipView(mCurrentPosition, id);
        }
    }

    /**
     * getting data from database, and displaying detailed info to user
     *
     * @param position
     * @param id
     */
    public void updateTipView(int position, long id) {
        dbHelper = new TipCalculatorDbAdapter(getActivity());
        dbHelper.open();
        Cursor cursor = dbHelper.fetchRecordsByName(String.valueOf(position));
       // Cursor cursor = dbHelper.fetchRecordsByName(String.valueOf(id));

        cursor.moveToFirst();
        Log.d("Count", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0) {
// get values from cursor here
            //TODO: EDIT THIS
            String rowid = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_ROWID));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_NAME));
            String vehicle = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_TOTAL));
            String distance = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_TIPD));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_TIPP));
            String note = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_NOTE));

            TextView rowidText = (TextView) getActivity().findViewById(R.id.rowid);
            rowidText.setText(rowid);

            TextView categoryText = (TextView) getActivity().findViewById(R.id.nametip);
            categoryText.setText(category);

            TextView vehicleText = (TextView) getActivity().findViewById(R.id.total);
            vehicleText.setText(vehicle);

            TextView distanceText = (TextView) getActivity().findViewById(R.id.dollar);
            distanceText.setText(distance);

            TextView dateText = (TextView) getActivity().findViewById(R.id.percent);
            dateText.setText(date);

            TextView noteText = (TextView) getActivity().findViewById(R.id.notetip);
            noteText.setText(note);


        }
        mCurrentPosition = position;

    }

    /**
     * save current selection if we need to recreate
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    /**
     * on click handler for delete button
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        dbHelper = new TipCalculatorDbAdapter(getActivity());
        dbHelper.open();
        //TODO: EDIT THIS
        dbHelper.deleteCertainRecord(String.valueOf(mCurrentPosition));
        dbHelper.close();
        mCallback.onTipDeleted();
        //go(CarbonFootprintMainActivity.class);
    }

    /**
     * attaching the interface
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnTipsDetailListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFootprintListener");
        }
    }
}
