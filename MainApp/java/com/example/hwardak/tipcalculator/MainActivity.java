package com.example.hwardak.tipcalculator;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.johan.carbonfootprint.CarbonFootprintViewAllRecords;
import com.example.johan.carbonfootprint.CarbonFootprintViewDetailsFragment;
import com.example.tgk.integrationwithfragment.R;

import java.text.DecimalFormat;

public class MainActivity extends ActionBarActivity implements MainFragment.onUpdate, ViewRecordFragment.OnTiptSelectedListener, ViewDetailsRecordFragment.OnTipsDetailListener {
    DecimalFormat df = new DecimalFormat("#.00");

    TipCalculatorDbAdapter dbAdapter = new TipCalculatorDbAdapter(MainActivity.this);


    //Fields
    EditText billField;
    EditText tipField;
    EditText totalField;
    EditText name;
    EditText note;


    Button addRecordButton;
    Button viewRecordButton;
    Button helpButton;

    String name2;
    String note2;


    Double tipPercent=0.0;
    Double tipNumber=0.0;
    Double tipValue=0.0;
    Double billValue=0.0;
    Double totalValue=0.0;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    ViewRecordFragment viewRecordFragment;
    HelpFragment helpFragment;
    StoreRecordFragment storeRecordFragment;
    MainFragment mainFragment = new MainFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tipField = (EditText) findViewById(R.id.tipField);
        billField = (EditText) findViewById(R.id.billField);
        totalField = (EditText) findViewById(R.id.totalField);
        name = (EditText) findViewById(R.id.name);
        note = (EditText) findViewById(R.id.note);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


         addRecordButton = (Button) findViewById(R.id.addRecordButton);
         viewRecordButton = (Button) findViewById(R.id.viewRecordButton);
         helpButton = (Button) findViewById(R.id.helpButton);

        viewRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecordButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewRecordFragment firstFragment = new ViewRecordFragment();
                        setContentView(R.layout.activity_main);
                        firstFragment.setArguments(getIntent().getExtras());
                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.mainFrameLayout, firstFragment).commit();
                    }
                });
            }
        });

        //mainFragment = new MainFragment();

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.mainFrameLayout, mainFragment).commit();


    }
    /**
     * creating menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public void onClickQuickTip(View view) {
        EditText customTipField = (EditText) findViewById(R.id.customTipField);


        Button quickTipButton = (Button) view;

        tipNumber = Double.parseDouble(quickTipButton.getText().toString().substring(0, 2));

        tipPercent = tipNumber/100;

        customTipField.setText(tipNumber+"%");

        calcTotal();

    }

    public void onClickCustomTip(View view) {

         EditText customTipField = (EditText) findViewById(R.id.customTipField);

        Button customTipButton = (Button) view;

        if(customTipButton.getText().toString().equals("+")){
            tipNumber++;
        }
        else if(tipNumber >0){
            tipNumber--;
        }

        customTipField.setText(tipNumber+"%");

        tipPercent = tipNumber/100;

        calcTotal();

    }


    public void calcTotal(){

        billValue = Double.parseDouble(billField.getText().toString());

        tipValue = billValue * tipPercent;

        tipField.setText(""+ df.format(tipValue), TextView.BufferType.EDITABLE);

        totalValue = tipValue + billValue;

        totalField.setText("" + df.format(totalValue), TextView.BufferType.EDITABLE);
    }


    public void onHelpButtonClick(View view) {
        Button button = (Button) view;
        if(button.getText().toString().equals("Help")) {

            button.setText(" << Back");

            helpFragment = new HelpFragment();

            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.remove(mainFragment);

            fragmentTransaction.add(R.id.mainFrameLayout, helpFragment).commit();

            addRecordButton.setVisibility(View.INVISIBLE);
            viewRecordButton.setVisibility(View.INVISIBLE);
        }


        else {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(helpFragment);
            fragmentTransaction.add(R.id.mainFrameLayout, mainFragment).commit();
            button.setText("Help");
            addRecordButton.setVisibility(View.VISIBLE);
            viewRecordButton.setVisibility(View.VISIBLE);

        }
    }


    public void onAddRecordButtonClick(View view) {

        Button button =(Button) view;
        if(button.getText().toString().equals("Add Record")) {
            storeRecordFragment = new StoreRecordFragment();
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mainFragment);
        fragmentTransaction.add(R.id.mainFrameLayout, storeRecordFragment).commit();
        button.setText(" << Back");

            helpButton.setVisibility(View.INVISIBLE);
            viewRecordButton.setVisibility(View.INVISIBLE);

    }else {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(storeRecordFragment);
            fragmentTransaction.add(R.id.mainFrameLayout, mainFragment).commit();


            button.setText("Add Record");
            helpButton.setVisibility(View.VISIBLE);
            viewRecordButton.setVisibility(View.VISIBLE);


        }

    }


    @Override
    public void updateDisplay(Double d1, Double d2) {

    }

    public void onAddRecordButtonClickToDatabase(View view) {

        dbAdapter.open();
        dbAdapter.createCountry(name.getText().toString(), tipPercent.toString(), tipValue.toString(), totalValue.toString(), note.getText().toString());
        dbAdapter.close();
    }

    public void onViewRecordButtonClick(View view) {

       // Button button = (Button) view;
//        button.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                ViewRecordFragment firstFragment = new ViewRecordFragment();
////                setContentView(R.layout.activity_main);
////                firstFragment.setArguments(getIntent().getExtras());
////                getSupportFragmentManager().beginTransaction()
////                        .add(R.id.mainFrameLayout, firstFragment).commit();
////            }
////        });
////        if(button.getText().toString().equals("View Records")) {
//
//            button.setText(" << Back");
//
//            viewRecordFragment = new ViewRecordFragment();
//
//            fragmentManager = getFragmentManager();
//            fragmentTransaction = fragmentManager.beginTransaction();
//
//            fragmentTransaction.remove(mainFragment);
//
//            fragmentTransaction.add(R.id.mainFrameLayout, viewRecordFragment).commit();
//            addRecordButton.setVisibility(View.INVISIBLE);
//            helpButton.setVisibility(View.INVISIBLE);
//    } else {
//        fragmentManager = getFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.remove(viewRecordFragment);
//        fragmentTransaction.add(R.id.mainFrameLayout, mainFragment).commit();
//
//
//        button.setText("View Records");
//        helpButton.setVisibility(View.VISIBLE);
//        addRecordButton.setVisibility(View.VISIBLE);
//
//
//    }
}

    @Override
    public void onTipSelected(int position, long id) {
        ViewDetailsRecordFragment newFragment = new ViewDetailsRecordFragment();
        Bundle args = new Bundle();
        args.putInt(ViewDetailsRecordFragment.ARG_POSITION, position);
        newFragment.setArguments(args);
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.mainFrameLayout, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onTipDeleted() {

        //go(CarbonFootprintMainActivity.class);
        ViewRecordFragment newFragment = new ViewRecordFragment();
        Bundle args = new Bundle();
        newFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLayout, newFragment);

        transaction.addToBackStack(null);
        //transaction.disallowAddToBackStack();
        // Commit the transaction
        transaction.commit();

    }
}
