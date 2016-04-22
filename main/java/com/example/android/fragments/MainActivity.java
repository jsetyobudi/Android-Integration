/**
 * Course Name:CST2335_010 Graphical Interface Programming
 Student Name: Xuan Li
 Student Number:040811628
 *
 * */
package com.example.android.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends FragmentActivity
        implements addContact.onViewContactListener,
        UserList.onContactListListener,
        ContactDetail.onAddContactListListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial);
        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }



            addContact firstFragment =  addContact.createList();

            firstFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }

    }


    @Override
    public void onViewAllListener() {

        UserList newFragment = new UserList();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void viewContactDetail(int position, long id) {



            ContactDetail newFragment = new ContactDetail();
            Bundle args = new Bundle();
            args.putLong(ContactDetail.ARG_POSITION,id);
            newFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }



    @Override
    public void addNewContact() {

        addContact newFragment =  addContact.createList();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}