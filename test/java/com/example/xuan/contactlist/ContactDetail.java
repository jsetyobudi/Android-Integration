/**
 * Course Name:CST2335_010 Graphical Interface Programming
 Student Name: Xuan Li
 Student Number:040811628
 *
 * */
package com.example.xuan.contactlist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tgk.integrationwithfragment.R;


public class ContactDetail extends Fragment {

    onAddContactListListener mCallback;
    private TextView contact_title;
    private TextView contact_time;
    private TextView contact_phone;
    private TextView contact_email;
    private TextView contact_note;
    private String date;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String note;
    private Cursor result;

    public interface onAddContactListListener {

        void addNewContact();
    }

    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    long id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {

            id= args.getLong(ARG_POSITION);

        }

        DbAdapter dbHelper = new DbAdapter(getActivity());
        dbHelper.open();

        result = dbHelper.fetchContactByID(id);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        return inflater.inflate(R.layout.userdetail_view, container, false);



    }


    @Override
    public void onStart() {
        super.onStart();

        Button btnAddNew = (Button) getActivity().findViewById(R.id.btn_add_contact);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.addNewContact();
            }
        });


        Button btnDelete = (Button) getActivity().findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact(mCurrentPosition);
                Toast.makeText(getActivity(), "Contact deleted...", Toast.LENGTH_SHORT).show();
                mCallback.addNewContact();
            }
        });


        contact_title = (TextView) getActivity().findViewById(R.id.name_title);
        contact_time = (TextView) getActivity().findViewById(R.id.time);
        contact_phone = (TextView) getActivity().findViewById(R.id.phone);
        contact_email = (TextView) getActivity().findViewById(R.id.email);
        contact_note = (TextView) getActivity().findViewById(R.id.note);



        firstName = result.getString(result.getColumnIndex(DbAdapter.KEY_FIRSTNAME));
        lastName = result.getString(result.getColumnIndex(DbAdapter.KEY_LASTNAME));
        phone = result.getString(result.getColumnIndex(DbAdapter.KEY_PHONE));
        email = result.getString(result.getColumnIndex(DbAdapter.KEY_EMAIL));
        note = result.getString(result.getColumnIndex(DbAdapter.KEY_NOTE));
        date = result.getString(result.getColumnIndex(DbAdapter.KEY_DATE));



        contact_title.setText(firstName + " " + lastName);
        contact_phone.setText(phone);
        contact_email.setText(email);
        contact_time.setText(date);
        contact_note.setText(note);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }





    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (onAddContactListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onContactListListener");
        }
    }

    private void deleteContact(int id) {
        DbAdapter dbHelper = new DbAdapter(getActivity());
        dbHelper.open();
        dbHelper.deleteContact(id);
        dbHelper.close();
        this.getActivity().getSupportFragmentManager().popBackStack();
    }
}