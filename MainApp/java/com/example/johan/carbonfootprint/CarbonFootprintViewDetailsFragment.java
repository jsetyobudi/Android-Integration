package com.example.johan.carbonfootprint;

/**
 */
import android.app.Activity;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tgk.integrationwithfragment.R;

/**
 * detail fragment for carbon footprint
 * @author Johan Setyobudi
 * Created by Johan on 19-Apr-2016.
 */
public class CarbonFootprintViewDetailsFragment extends Fragment implements View.OnClickListener {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    long id = -1;
    CarbonFootprintDBAdapter dbHelper;
    Button delete;
    View view;
    OnFootprintListener mCallback;

    /**
     * interface that needs to be implemented
     */
    public interface OnFootprintListener {
        public void onFootprintDeleted();

    }

    /**
     * on create
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.carbonfootprintdetails_info, container, false);

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
            updateFootprintView(args.getInt(ARG_POSITION), id);
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateFootprintView(mCurrentPosition, id);
        }
    }

    /**
     * getting data from database, and displaying detailed info to user
     * @param position
     * @param id
     */
    public void updateFootprintView(int position, long id) {
        dbHelper = new CarbonFootprintDBAdapter(getActivity());
        dbHelper.open();
        Cursor cursor = dbHelper.fetchFootprintByRowID(String.valueOf(position));
        cursor.moveToFirst();
        Log.d("Count", String.valueOf(cursor.getCount()));
        if(cursor.getCount() > 0) {
// get values from cursor here
            String rowid = cursor.getString(cursor.getColumnIndexOrThrow(CarbonFootprintDBAdapter.KEY_ROWID));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(CarbonFootprintDBAdapter.KEY_CATEGORY));
            String vehicle = cursor.getString(cursor.getColumnIndexOrThrow(CarbonFootprintDBAdapter.KEY_VEHICLE));
            String distance = cursor.getString(cursor.getColumnIndexOrThrow(CarbonFootprintDBAdapter.KEY_DISTANCE));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(CarbonFootprintDBAdapter.KEY_DATE));
            String note = cursor.getString(cursor.getColumnIndexOrThrow(CarbonFootprintDBAdapter.KEY_NOTE));
            String footprint = cursor.getString(cursor.getColumnIndexOrThrow(CarbonFootprintDBAdapter.KEY_FOOTPRINT));

            TextView rowidText = (TextView) getActivity().findViewById(R.id.rowid);
            rowidText.setText(rowid);

            TextView categoryText = (TextView) getActivity().findViewById(R.id.category);
            categoryText.setText(category);

            TextView vehicleText = (TextView) getActivity().findViewById(R.id.vehicle);
            vehicleText.setText(vehicle);

            TextView distanceText = (TextView) getActivity().findViewById(R.id.distance);
            distanceText.setText(distance);

            TextView dateText = (TextView) getActivity().findViewById(R.id.date);
            dateText.setText(date);

            TextView noteText = (TextView) getActivity().findViewById(R.id.note);
            noteText.setText(note);

            TextView footprintText = (TextView) getActivity().findViewById(R.id.footprint);
            footprintText.setText(footprint);
        }
        mCurrentPosition = position;

    }

    /**
     * save current selection if we need to recreate
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
     * @param v
     */
    @Override
    public void onClick(View v) {
        dbHelper = new CarbonFootprintDBAdapter(getActivity());
        dbHelper.open();
        dbHelper.deleteCertainFootprint(String.valueOf(mCurrentPosition));
        mCallback.onFootprintDeleted();
        //go(CarbonFootprintMainActivity.class);
    }

    /**
     * attaching the interface
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnFootprintListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFootprintListener");
        }
    }


}
