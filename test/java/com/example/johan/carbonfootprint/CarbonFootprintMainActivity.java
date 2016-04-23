package com.example.johan.carbonfootprint;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diego.activitytracker.MainActivity;
import com.example.tgk.integrationwithfragment.R;

import java.text.NumberFormat;
import java.util.Calendar;

/**
 * Main activity for the Carbon Footprint Class
 * Created by Johan on 16-Apr-2016.
 */
public class CarbonFootprintMainActivity extends AppCompatActivity
        implements CarbonFootprintViewAllRecords.OnFootprintSelectedListener, CarbonFootprintViewDetailsFragment.OnFootprintListener
        , CarbonFootprintAdd.OnViewAllRecordsSelectedListener, CarbonFootprintAdd.OnHelpSelectedListener        {


    private Spinner vehicleSpinner;
    private Spinner distanceSpinner;
    private Spinner categorySpinner;
    private CarbonFootprintDBAdapter footprint;
    private static final String[]category = {"Vacation", "Work", "Grocery", "Miscellaneous"};
    private static final String[]vehicle = {"Motorcycle", "Car","Diesel Car", "Truck", "Diesel Truck","Van","SUV", "Airplane", "Bus", "Train"};
    private static final String[]distanceType = {"Km", "Miles"};
    private String categoryChoice;
    private String vehicleChoice;
    private String distanceTypeChoice;
    private String date;
    private String note;
    private String distance;
//    private int tripYear;
//    private int tripMonth;
//    private int tripDay;
    private TextView tripDateDisplay;
    private TextView carbonFootprint;
    private EditText distanceDisplay;
    private EditText noteDisplay;
    private Button tripPickDate;
    private Button submitButton;
    private Button viewAllRecordsButton;
    private Button helpButton;
    static final int DATE_DIALOG_ID = 0;

    /**
     * UPDATE: updated code so a lot of commented out
     * onCreate, my mistake...i did not make the add a fragment, so everything is in here. it is very
     * messy and i realized too late that i had to change it. so all the action listeners, spinner
     * listeners, button listeners, everything is in this method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carbonfootprint_articles);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
// Check whether the activity is using the layout version with
        // the fragment_container FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.footprint_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            CarbonFootprintAdd firstFragment = new CarbonFootprintAdd();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.footprint_container, firstFragment).commit();
            //everything below here is my old code, please ignore
//        CarbonFootprintAdd firstFragment = new CarbonFootprintAdd();
//        setContentView(R.layout.carbonfootprint_add);
//        firstFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.footprint_container, firstFragment).commit();


//
//        vehicleSpinner = (Spinner)findViewById(R.id.vehicleSpinner);
//        distanceSpinner = (Spinner)findViewById(R.id.distanceSpinner);
//        categorySpinner = (Spinner)findViewById(R.id.categorySpinner);
//
//        final ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<String>(CarbonFootprintMainActivity.this,
//                android.R.layout.simple_spinner_item,vehicle);
//        final  ArrayAdapter<String> distanceAdapter = new ArrayAdapter<String>(CarbonFootprintMainActivity.this,
//                android.R.layout.simple_spinner_item,distanceType);
//        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(CarbonFootprintMainActivity.this,
//                android.R.layout.simple_spinner_item,category);
//
//        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        vehicleSpinner.setAdapter(vehicleAdapter);
//        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                vehicleChoice = vehicleSpinner.getSelectedItem().toString();
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        distanceSpinner.setAdapter(distanceAdapter);
//        distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                distanceTypeChoice = distanceSpinner.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        categorySpinner.setAdapter(categoryAdapter);
//        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                categoryChoice = categorySpinner.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        tripDateDisplay = (TextView) findViewById(R.id.showMyDate);
//        tripPickDate = (Button) findViewById(R.id.myDatePickerButton);
//
//        tripPickDate.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                showDialog(DATE_DIALOG_ID);
//            }
//        });
//        // get the current date
//        final Calendar c = Calendar.getInstance();
//        tripYear = c.get(Calendar.YEAR);
//        tripMonth = c.get(Calendar.MONTH);
//        tripDay = c.get(Calendar.DAY_OF_MONTH);
//        // display the current date
//        updateDisplay();
//        distanceDisplay = (EditText) findViewById(R.id.distanceNumber);
//        noteDisplay = (EditText) findViewById(R.id.noteText);
//        carbonFootprint = (TextView) findViewById(R.id.footprintInTonnes);
//        submitButton = (Button) findViewById(R.id.submitButton);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                distance = distanceDisplay.getText().toString();
//                if(distance.isEmpty() || distance == null) {
//                    Toast.makeText(CarbonFootprintMainActivity.this,"Distance cannot be empty. Please try again.",Toast.LENGTH_LONG).show();
//                } else {
//                    note = noteDisplay.getText().toString();
//                    date = tripDateDisplay.getText().toString();
//                    double total;
//                    double distanceKm = Double.parseDouble(distance);
//
//                   total = calculateFootprint(vehicleChoice, distance, distanceTypeChoice);
//                    if (distanceTypeChoice.equalsIgnoreCase("miles")) {
//                        distanceKm = distanceKm * 1.60;
//                    }
//                    NumberFormat format = NumberFormat.getInstance();
//                    // test.format(total);
//                    carbonFootprint.setText(format.format(total) + " Tonnes");
//                    footprint = new CarbonFootprintDBAdapter(CarbonFootprintMainActivity.this);
//                    footprint.open();
//                    footprint.createFootprint(categoryChoice, vehicleChoice,
//                            Double.toString(distanceKm), date, note, Double.toString(total));
//                    footprint.close();
//                }
//            }
//        });
//        viewAllRecordsButton = (Button) findViewById(R.id.viewAllRecords);
//        viewAllRecordsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // ListView listView = (ListView) findViewById(R.id.listView2);
//
//                CarbonFootprintViewAllRecords firstFragment = new CarbonFootprintViewAllRecords();
//                setContentView(R.layout.carbonfootprint_articles);
//                firstFragment.setArguments(getIntent().getExtras());
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.footprint_container, firstFragment).commit();
//
//            }
//        });
//        helpButton = (Button) findViewById(R.id.help);
//        helpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CarbonFootprintHelp firstFragment = new CarbonFootprintHelp();
//                setContentView(R.layout.carbonfootprint_articles);
//                firstFragment.setArguments(getIntent().getExtras());
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.footprint_container, firstFragment).commit();
//            }
//        });
            setSupportActionBar(myToolbar);
        }
    }


//    /**
//     * calculating carbon footprint, and returning the number in tonnes
//     * @param vehicle
//     * @param distance
//     * @param distanceType
//     * @return
//     */
//    private double calculateFootprint( String vehicle, String distance, String distanceType) {
//        Double distanceKm;
//        Double vehicleFootprintPerThousandKmPerTonne = 0.0;
//        Double total;
//            switch (vehicle) {
//                case "Motorcycle":
//                    vehicleFootprintPerThousandKmPerTonne = 0.12;
//                    break;
//                case "Car":
//                    vehicleFootprintPerThousandKmPerTonne = 0.19;
//                    break;
//                case "Diesel Car":
//                    vehicleFootprintPerThousandKmPerTonne = 0.18;
//                    break;
//                case "Truck":
//                    vehicleFootprintPerThousandKmPerTonne = 0.29;
//                    break;
//                case "Diesel Truck":
//                    vehicleFootprintPerThousandKmPerTonne = 0.23;
//                    break;
//                case "Van":
//                    vehicleFootprintPerThousandKmPerTonne = 0.25;
//                    break;
//                case "SUV":
//                    vehicleFootprintPerThousandKmPerTonne = 0.29;
//                    break;
//                case "Airplane":
//                    vehicleFootprintPerThousandKmPerTonne = 0.05;
//                    break;
//                case "Bus":
//                    vehicleFootprintPerThousandKmPerTonne = 0.11;
//                    break;
//                case "Train":
//                    vehicleFootprintPerThousandKmPerTonne = 0.05;
//                    break;
//                default:
//                    break;
//            }
//            distanceKm = Double.parseDouble(distance);
//            if (distanceType.equalsIgnoreCase("miles")) {
//                distanceKm = distanceKm * 1.60;
//            }
//            total = vehicleFootprintPerThousandKmPerTonne * (distanceKm / 1000);
//        return total;
//    }

//    /**
//     * updating date
//     */
//    private void updateDisplay() {
//        this.tripDateDisplay.setText(
//                new StringBuilder()
//                        .append(tripMonth + 1).append("-") //adding 1 since month starts from 0
//                        .append(tripDay).append("-")
//                        .append(tripYear).append(" "));
//    }
//    private DatePickerDialog.OnDateSetListener mDateSetListener =
//            new DatePickerDialog.OnDateSetListener() {
//                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                    tripYear = year;
//                    tripMonth = monthOfYear;
//                    tripDay = dayOfMonth;
//                    updateDisplay();
//                }
//            };

//    /**
//     * getting date from user
//     * @param id
//     * @return
//     */
//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case DATE_DIALOG_ID: return new DatePickerDialog(this,
//                                 mDateSetListener, tripYear, tripMonth, tripDay);
//        }
//        return null;
//    }


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
                go(com.example.hwardak.tipcalculator.MainActivity.class);
                break;
            case R.id.action_two:
                go(com.example.diego.activitytracker.MainActivity.class);
                break;
            case R.id.action_three:
                //go(com.example.johan.carbonfootprint.CarbonFootprintMainActivity.class);
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
     * changing to detail view based on selection
     * @param position
     * @param id
     */
    @Override
    public void onFootprintSelected(int position, long id) {// The user selected the headline of an article from the HeadlinesFragment

// The user selected the headline of an article from the HeadlinesFragment

//        // Capture the article fragment from the activity layout
//        CarbonFootprintViewDetailsFragment articleFrag = (CarbonFootprintViewDetailsFragment)
//                getSupportFragmentManager().findFragmentById(R.id.article_fragment);
//
//        if (articleFrag != null) {
//            // If article frag is available, we're in two-pane layout...
//
//            // Call a method in the ArticleFragment to update its content
//            articleFrag.updateFootprintView(position, id);
//
//        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...

            // Create fragment and give it an argument for the selected article
            CarbonFootprintViewDetailsFragment newFragment = new CarbonFootprintViewDetailsFragment();
            Bundle args = new Bundle();
            args.putInt(CarbonFootprintViewDetailsFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.footprint_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
       // }
}


    /**
     * Go back to the main activity
     */
    @Override
    public void onFootprintDeleted() {
      //go(CarbonFootprintMainActivity.class);
        CarbonFootprintViewAllRecords newFragment = new CarbonFootprintViewAllRecords();
        Bundle args = new Bundle();
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.footprint_container, newFragment);

        transaction.addToBackStack(null);
        //transaction.disallowAddToBackStack();
        // Commit the transaction
        transaction.commit();
    }


    @Override
    public void onHelpSelected() {
//        CarbonFootprintHelp firstFragment = new CarbonFootprintHelp();
//        setContentView(R.layout.carbonfootprint_articles);
//        firstFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.footprint_container, firstFragment).commit();
        CarbonFootprintHelp newFragment = new CarbonFootprintHelp();
        Bundle args = new Bundle();
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.footprint_container, newFragment);

        transaction.addToBackStack(null);
        //transaction.disallowAddToBackStack();
        // Commit the transaction
        transaction.commit();

    }

    @Override
    public void onViewAllRecordsSelected() {
//        CarbonFootprintViewAllRecords firstFragment = new CarbonFootprintViewAllRecords();
//        setContentView(R.layout.carbonfootprint_articles);
//        firstFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.footprint_container, firstFragment).commit();
        CarbonFootprintViewAllRecords newFragment = new CarbonFootprintViewAllRecords();
        Bundle args = new Bundle();
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.footprint_container, newFragment);

        transaction.addToBackStack(null);
        //transaction.disallowAddToBackStack();
        // Commit the transaction
        transaction.commit();

    }
}

