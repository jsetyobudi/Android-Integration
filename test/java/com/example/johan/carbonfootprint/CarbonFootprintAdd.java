package com.example.johan.carbonfootprint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tgk.integrationwithfragment.R;

import java.text.NumberFormat;
import java.util.Calendar;

/**
 * Created by Johan on 23-Apr-2016.
 */
public class CarbonFootprintAdd extends Fragment{

    View view;
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
    private int tripYear;
    private int tripMonth;
    private int tripDay;
    private TextView tripDateDisplay;
    private TextView carbonFootprint;
    private EditText distanceDisplay;
    private EditText noteDisplay;
    private Button tripPickDate;
    private Button submitButton;
    private Button viewAllRecordsButton;
    private Button helpButton;
    static final int DATE_DIALOG_ID = 0;
    OnHelpSelectedListener mCallback;
    OnViewAllRecordsSelectedListener mCallback2;

    /**
     * interface that has to be implemented
     */
    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnViewAllRecordsSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onViewAllRecordsSelected();
    }
    /**
     * interface that has to be implemented
     */
    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnHelpSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onHelpSelected();
    }


    /**
     * oncreate method, sets up all spinners and fancy stuff
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.carbonfootprint_add, container, false);
        Toolbar myToolbar = (Toolbar)  getActivity().findViewById(R.id.my_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);
        setHasOptionsMenu(true);
        vehicleSpinner = (Spinner) view.findViewById(R.id.vehicleSpinner);
        distanceSpinner = (Spinner)view.findViewById(R.id.distanceSpinner);
        categorySpinner = (Spinner)view.findViewById(R.id.categorySpinner);

        final ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item,vehicle);
        final  ArrayAdapter<String> distanceAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item,distanceType);
        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item,category);

        distanceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        vehicleSpinner.setAdapter(vehicleAdapter);
        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                vehicleChoice = vehicleSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        distanceSpinner.setAdapter(distanceAdapter);
        distanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                distanceTypeChoice = distanceSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                categoryChoice = categorySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tripDateDisplay = (TextView) view.findViewById(R.id.showMyDate);
        tripPickDate = (Button) view.findViewById(R.id.myDatePickerButton);

        tripPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // getActivity().showDialog(DATE_DIALOG_ID);
                CarbonFootprintDate newFragment = new CarbonFootprintDate();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
        // get the current date
        final Calendar c = Calendar.getInstance();
        tripYear = c.get(Calendar.YEAR);
        tripMonth = c.get(Calendar.MONTH);
        tripDay = c.get(Calendar.DAY_OF_MONTH);
        // display the current date
        updateDisplay(tripYear,tripMonth,tripDay);
        distanceDisplay = (EditText) view.findViewById(R.id.distanceNumber);
        noteDisplay = (EditText) view.findViewById(R.id.noteText);
        carbonFootprint = (TextView) view.findViewById(R.id.footprintInTonnes);
        submitButton = (Button) view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance = distanceDisplay.getText().toString();
                if(distance.isEmpty() || distance == null) {
                    Toast.makeText(getActivity(), "Distance cannot be empty. Please try again.", Toast.LENGTH_LONG).show();
                } else {
                    note = noteDisplay.getText().toString();
                    date = tripDateDisplay.getText().toString();
                    double total;
                    double distanceKm = Double.parseDouble(distance);

                    total = calculateFootprint(vehicleChoice, distance, distanceTypeChoice);
                    if (distanceTypeChoice.equalsIgnoreCase("miles")) {
                        distanceKm = distanceKm * 1.60;
                    }
                    NumberFormat format = NumberFormat.getInstance();
                    // test.format(total);
                    carbonFootprint.setText(format.format(total) + " Tonnes");
                    footprint = new CarbonFootprintDBAdapter(getActivity());
                    footprint.open();
                    footprint.createFootprint(categoryChoice, vehicleChoice,
                            Double.toString(distanceKm), date, note, Double.toString(total));
                    footprint.close();
                }
            }
        });
        viewAllRecordsButton = (Button) view.findViewById(R.id.viewAllRecords);
        viewAllRecordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ListView listView = (ListView) findViewById(R.id.listView2);
                mCallback2.onViewAllRecordsSelected();
//                CarbonFootprintViewAllRecords firstFragment = new CarbonFootprintViewAllRecords();
//                setContentView(R.layout.carbonfootprint_articles);
//                firstFragment.setArguments(getActivity().getIntent().getExtras());
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .add(R.id.footprint_container, firstFragment).commit();

            }
        });
        helpButton = (Button) view.findViewById(R.id.help);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onHelpSelected();
//                CarbonFootprintHelp firstFragment = new CarbonFootprintHelp();
//                setContentView(R.layout.carbonfootprint_articles);
//                firstFragment.setArguments(getActivity().getIntent().getExtras());
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .add(R.id.footprint_container, firstFragment).commit();
            }
        });

        return view;

    }
    /**
     * calculating carbon footprint, and returning the number in tonnes
     * @param vehicle
     * @param distance
     * @param distanceType
     * @return
     */
    private double calculateFootprint( String vehicle, String distance, String distanceType) {
        Double distanceKm;
        Double vehicleFootprintPerThousandKmPerTonne = 0.0;
        Double total;
        switch (vehicle) {
            case "Motorcycle":
                vehicleFootprintPerThousandKmPerTonne = 0.12;
                break;
            case "Car":
                vehicleFootprintPerThousandKmPerTonne = 0.19;
                break;
            case "Diesel Car":
                vehicleFootprintPerThousandKmPerTonne = 0.18;
                break;
            case "Truck":
                vehicleFootprintPerThousandKmPerTonne = 0.29;
                break;
            case "Diesel Truck":
                vehicleFootprintPerThousandKmPerTonne = 0.23;
                break;
            case "Van":
                vehicleFootprintPerThousandKmPerTonne = 0.25;
                break;
            case "SUV":
                vehicleFootprintPerThousandKmPerTonne = 0.29;
                break;
            case "Airplane":
                vehicleFootprintPerThousandKmPerTonne = 0.05;
                break;
            case "Bus":
                vehicleFootprintPerThousandKmPerTonne = 0.11;
                break;
            case "Train":
                vehicleFootprintPerThousandKmPerTonne = 0.05;
                break;
            default:
                break;
        }
        distanceKm = Double.parseDouble(distance);
        if (distanceType.equalsIgnoreCase("miles")) {
            distanceKm = distanceKm * 1.60;
        }
        total = vehicleFootprintPerThousandKmPerTonne * (distanceKm / 1000);
        return total;
    }

    /**
     * updating date
     */
    private void updateDisplay(int year, int month, int day) {
        this.tripDateDisplay.setText(
                new StringBuilder()
                        .append(month + 1).append("-") //adding 1 since month starts from 0
                        .append(day).append("-")
                        .append(year).append(" "));
    }
    /**
     * Creating the fragment menu
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    /**
     * Switching to different activities
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
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
    }


    /**
     * onStart
     */
    @Override
    public void onStart() {

        super.onStart();
    }
    /**
     * attaching interface
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnHelpSelectedListener) activity;
            mCallback2 = (OnViewAllRecordsSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHelpSelectedListener and OnViewAllRecordsSelectedListener");
        }
    }
}
