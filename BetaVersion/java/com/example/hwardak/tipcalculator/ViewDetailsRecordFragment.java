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
     * This activity will restore the previous article incase the activity is recreated.
     *
     * @param inflater LayoutInflator object
     * @param container ViewGroup object
     * @param savedInstanceState Bundle object.
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tipcalculatordetails_info, container, false);

        delete = (Button) view.findViewById(R.id.deleteButton);
        delete.setOnClickListener(this);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        return view;
    }

    /**
     * During startup, check if there are arguments passed to the fragment.
     * onStart is a good place to do this because the layout has already been
     * applied to the fragment at this point so we can safely call the method
     * below that sets the article text.
     */
    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateTipView(args.getInt(ARG_POSITION), id);
        } else if (mCurrentPosition != -1) {
            updateTipView(mCurrentPosition, id);
        }
    }

    /**
     * Present retrieved data to user.
     *
     * @param position
     * @param id
     */
    public void updateTipView(int position, long id) {
        dbHelper = new TipCalculatorDbAdapter(getActivity());
        dbHelper.open();
        Cursor cursor = dbHelper.fetchRecordsByName(String.valueOf(position));
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String rowId = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_ROWID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_NAME));
            String total = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_TOTAL));
            String tipDollar = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_TIPD));
            String tipPercent = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_TIPP));
            String note = cursor.getString(cursor.getColumnIndexOrThrow(TipCalculatorDbAdapter.KEY_NOTE));

            TextView rowidText = (TextView) getActivity().findViewById(R.id.rowid);
            TextView categoryText = (TextView) getActivity().findViewById(R.id.nametip);
            TextView vehicleText = (TextView) getActivity().findViewById(R.id.total);
            TextView distanceText = (TextView) getActivity().findViewById(R.id.dollar);
            TextView dateText = (TextView) getActivity().findViewById(R.id.percent);
            TextView noteText = (TextView) getActivity().findViewById(R.id.notetip);

            noteText.setText(note);
            rowidText.setText(rowId);
            categoryText.setText(name);
            vehicleText.setText(total);
            distanceText.setText(tipDollar);
            dateText.setText(tipPercent);
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

        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    /**
     * on click handler for delete button
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        //catch
        dbHelper = new TipCalculatorDbAdapter(getActivity());
        dbHelper.open();
        dbHelper.deleteCertainRecord(String.valueOf(mCurrentPosition));
        dbHelper.close();
        mCallback.onTipDeleted();
    }

    /**
     *  This makes sure that the container activity has implemented
     *  the callback interface. If not, it throws an exception.
     *
     * @param activity Activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnTipsDetailListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFootprintListener");
        }
    }
}
