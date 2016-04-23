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
 * Created by Johan on 23-Apr-2016.
 */
public class CarbonFootprintDate   extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }


        public void populateSetDate(int year, int month, int day) {
            TextView tripDateDisplay = (TextView) getActivity().findViewById(R.id.showMyDate);

            tripDateDisplay.setText(new StringBuilder()
                    .append(month + 1).append("-") //adding 1 since month starts from 0
                    .append(day).append("-")
                    .append(year).append(" "));
        }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear+1, dayOfMonth);


    }
}

