package com.example.johan.carbonfootprint;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.tgk.integrationwithfragment.R;

import java.util.Calendar;

/**
 * Another fragment class to deal with the date dialog
 * Created by Johan on 23-Apr-2016.
 */
public class CarbonFootprintDate  extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    /**
     * Creating the dialog
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    /**
     * Updating date to text view
     * @param year
     * @param month
     * @param day
     */
    public void updateDate(int year, int month, int day) {
        TextView tripDateDisplay = (TextView) getActivity().findViewById(R.id.showMyDate);
        tripDateDisplay.setText(new StringBuilder()
                .append(month + 1).append("-") //adding 1 since month starts from 0
                .append(day).append("-")
                .append(year).append(" "));
    }

    /**
     * Catching date
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        updateDate(year, monthOfYear+1, dayOfMonth); //adding 1 since month starts from 0
    }
}