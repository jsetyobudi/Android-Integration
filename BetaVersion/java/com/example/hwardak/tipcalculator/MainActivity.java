package com.example.hwardak.tipcalculator;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tgk.integrationwithfragment.R;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements MainFragment.onUpdate, ViewRecordFragment.OnTiptSelectedListener, ViewDetailsRecordFragment.OnTipsDetailListener {
    DecimalFormat df = new DecimalFormat("#.00");

    TipCalculatorDbAdapter dbAdapter = new TipCalculatorDbAdapter(MainActivity.this);


    Button summaryButton;
    //Fields
    /**
     * Editable bill field.
     */
    EditText billField;
    /**
     * Editable tip field.
     */
    EditText tipField;
    /**
     * Non-Editable total field.
     */
    EditText totalField;
    /**
     * Editable restuarant name field
     */
    EditText name;
    /**
     * Editable note field/
     */
    EditText note;

    /**
     * Add Record button.
     */
    Button addRecordButton;

    /**
     * View Records Button
     */
    Button viewRecordButton;

    /**
     * Help Button to view the help text.
     */
    Button helpButton;

    /**
     * Double tip percentage.
     */
    Double tipPercent=0.0;
    /**
     * Double tip Value.
     */
    Double tipNumber=0.0;

    /**
     * Double tip dollar value
     */
    Double tipValue=0.0;

    /**
     * Double bill value.
     */
    Double billValue=0.0;

    /**
     * Double total value.
     */
    Double totalValue=0.0;

    /**
     * Fragment manager object.
     */
    FragmentManager fragmentManager;

    /**
     * FragmentTransaction object.
     */
    FragmentTransaction fragmentTransaction;

    ViewRecordFragment viewRecordFragment;

    /**
     * HelpFragment handle.
     */
    HelpFragment helpFragment;


    StoreRecordFragment storeRecordFragment;

    /**
     * Mainfragment handle that is instantiated.
     */
    MainFragment mainFragment = new MainFragment();

    /**
     * The onCreate method will inflate the activity_main fragment within the main frame
     * layout, and instantiate all necessary views.
     * @param savedInstanceState
     */
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
        summaryButton = (Button)findViewById(R.id.summaryButton);
        summaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.hwardak.tipcalculator.MainActivity.this, com.example.hwardak.tipcalculator.SummaryView.class);
                startActivity(intent);
            }
        });


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



        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.mainFrameLayout, mainFragment).commit();


    }
    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     * @param menu
     * @return true boolean.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     Handle action bar item clicks here. The action bar will
     automatically handle clicks on the Home/Up button, so long
     as you specify a parent activity in AndroidManifest.xml.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    /**
     * Will process the calculations for quick tip short cut buttons.
     * Will update the tip percentage view, tip dollar value view and total.
     * @param view the button that was clicked with invoked this method.
     */
    public void onClickQuickTip(View view) {
        EditText customTipField = (EditText) findViewById(R.id.customTipField);

        Button quickTipButton = (Button) view;

        tipNumber = Double.parseDouble(quickTipButton.getText().toString().substring(0, 2));

        tipPercent = tipNumber/100;

        customTipField.setText(tipNumber+"%");

        calcTotal();

    }

    /**
     * This method is invoked whenever the user clicks the '+' or '-' buttons to
     * manually increase or decrease the percentage tip.
     * @param view the button that was clicked witch invoked this method.
     */
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

    /**
     * Whenever either of the Quick Tip or Custom Tip buttons are clicked this method is called.
     * it will calculate the tip dollar value and total value, and updating the user interface
     * so the user can see the newly calculated totals.
     */
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
            fragmentTransaction.detach(mainFragment);

            fragmentTransaction.add(R.id.mainFrameLayout, helpFragment).commit();

            addRecordButton.setVisibility(View.INVISIBLE);
            viewRecordButton.setVisibility(View.INVISIBLE);
        }


        else {
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(helpFragment);
            fragmentTransaction.detach(helpFragment);
            fragmentTransaction.add(R.id.mainFrameLayout, mainFragment).commit();
            button.setText("Help");
//            fragmentTransaction.replace(R.id.mainFrameLayout, mainFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
            addRecordButton.setVisibility(View.VISIBLE);
            viewRecordButton.setVisibility(View.VISIBLE);

        }
    }



//    public void onAddRecordButtonClick(View view) {
//        Button button =(Button) view;
//        if(button.getText().toString().equals("Add Record")) {
//            storeRecordFragment = new StoreRecordFragment();
//        fragmentManager = getFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.remove(mainFragment);
//        fragmentTransaction.add(R.id.mainFrameLayout, storeRecordFragment).commit();
//        button.setText(" << Back");
//
//            helpButton.setVisibility(View.INVISIBLE);
//            viewRecordButton.setVisibility(View.INVISIBLE);
//
//    }else {
//            fragmentManager = getFragmentManager();
//            fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.remove(storeRecordFragment);
//            fragmentTransaction.add(R.id.mainFrameLayout, mainFragment).commit();
//
//
//            button.setText("Add Record");
//            helpButton.setVisibility(View.VISIBLE);
//            viewRecordButton.setVisibility(View.VISIBLE);
//
//
//        }
//
//    }


    @Override
    public void updateDisplay(Double d1, Double d2) {

    }

    public void onAddRecordButtonClickToDatabase(View view) {

        dbAdapter.open();
        dbAdapter.createRecord(name.getText().toString(), tipPercent.toString(), tipValue.toString(), totalValue.toString(), note.getText().toString());
        dbAdapter.close();
    }

    public void onViewRecordButtonClick(View view) {

       // Button button = (Button) view;
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ViewRecordFragment firstFragment = new ViewRecordFragment();
//                setContentView(R.layout.activity_main);
//                firstFragment.setArguments(getIntent().getExtras());
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.mainFrameLayout, firstFragment).commit();
//            }
//        });
//        if(button.getText().toString().equals("View Records")) {
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

    /**
     *  Replace whatever is in the fragment_container view with this fragment,
     *  and add the transaction to the back stack so the user can navigate back
     * @param position
     * @param id
     */
    @Override
    public void onTipSelected(int position, long id) {
        ViewDetailsRecordFragment newFragment = new ViewDetailsRecordFragment();
        Bundle args = new Bundle();
        args.putInt(ViewDetailsRecordFragment.ARG_POSITION, position);
        newFragment.setArguments(args);
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLayout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * When this method is used to delete a selected tip record from the database.
     */
    @Override
    public void onTipDeleted() {
        ViewRecordFragment newFragment = new ViewRecordFragment();
        Bundle args = new Bundle();
        newFragment.setArguments(args);
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrameLayout, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
