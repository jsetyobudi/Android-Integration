/**
 * Course Name:CST2335_010 Graphical Interface Programming
 Student Name: Xuan Li
 Student Number:040811628
 *
 * */

package com.example.android.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class addContact extends Fragment {

    onViewContactListener mCallback;


    public static addContact createList() {
        return new addContact();
    }

    public interface onViewContactListener {
        void onViewAllListener();
    }

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText phoneText;
    private EditText emailText;
    private EditText noteText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.adduser_view, container, false);

        firstNameText = (EditText) fragmentView.findViewById(R.id.first_name);
        lastNameText = (EditText) fragmentView.findViewById(R.id.last_name);
        phoneText = (EditText) fragmentView.findViewById(R.id.phone);
        emailText = (EditText) fragmentView.findViewById(R.id.email);
        noteText = (EditText) fragmentView.findViewById(R.id.note);

        Button btnAddTips = (Button) fragmentView.findViewById(R.id.btn_add_contact);
        btnAddTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
                Toast.makeText(getActivity(), "Contact added...", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnViewAll = (Button) fragmentView.findViewById(R.id.btn_view_all);
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onViewAllListener();
            }
        });

        return fragmentView;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (onViewContactListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onAddContactListener");
        }
    }


    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String note;
    private String date;



    private void addContact() {

        firstName = firstNameText.getText().toString();
        lastName = lastNameText.getText().toString();
        phone = phoneText.getText().toString();
        email = emailText.getText().toString();
        note = noteText.getText().toString();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Calendar time = Calendar.getInstance();
        date = simpleDateFormat.format(time.getTime());

            DbAdapter dbHelper = new DbAdapter(getActivity());
            dbHelper.open();
            dbHelper.createNewContact(firstName, lastName, phone, email, note, date);
            dbHelper.close();
            reSet();
    }


    private void reSet() {
        firstNameText.setText("");
        lastNameText.setText("");
        phoneText.setText("");
        emailText.setText("");
        noteText.setText("");
    }


}
