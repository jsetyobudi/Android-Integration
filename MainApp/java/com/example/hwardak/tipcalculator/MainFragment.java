package com.example.hwardak.tipcalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tgk.integrationwithfragment.R;

/**
 * Created by HWardak on 16-04-20.
 */




public class MainFragment extends Fragment {

    double tipNumber;




    public interface onUpdate{
      void updateDisplay(Double d1, Double d2);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);

    }

   // EditText customTipField = (EditText) getView().findViewById(R.id.customTipField);


    public void onClickCustomTip(View view) {
        Button customTipButton = (Button) view;

        if(customTipButton.getText().toString().equals("+")){
            tipNumber++;
        }
        else if(tipNumber >0){
            tipNumber--;
        }
         EditText customTipField = (EditText) getView().findViewById(R.id.customTipField);

        customTipField.setText(tipNumber+"%");

    }

}
