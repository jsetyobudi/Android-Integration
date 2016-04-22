
package com.example.android.fragments;

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


public class ContactDetail extends Fragment {

    onAddContactListListener mCallback;

    public interface onAddContactListListener {

        void addNewContact();
    }

    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    long id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        final View fragmentView = inflater.inflate(R.layout.userdetail_view, container, false);


        Button btnAddNew = (Button) fragmentView.findViewById(R.id.btn_add_contact);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.addNewContact();
            }
        });


        Button btnDelete = (Button) fragmentView.findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact(mCurrentPosition);
                Toast.makeText(getActivity(), "Contact deleted...", Toast.LENGTH_SHORT).show();
                mCallback.addNewContact();
            }
        });

        return fragmentView;

    }


    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {

            updateContactDetail(args.getInt(ARG_POSITION), id);
        } else if (mCurrentPosition != -1) {

            updateContactDetail(mCurrentPosition, id);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    public void updateContactDetail(int position, long id) {

        TextView contact_title = (TextView) getActivity().findViewById(R.id.name_title);
        TextView contact_time = (TextView) getActivity().findViewById(R.id.time);
//        TextView contact_phone = (TextView) getActivity().findViewById(R.id.phone);
//        TextView contact_email = (TextView) getActivity().findViewById(R.id.email_detail);
//        TextView contact_note = (TextView) getActivity().findViewById(R.id.note);

        DbAdapter dbHelper = new DbAdapter(getActivity());
        dbHelper.open();
        Cursor result = dbHelper.fetchContactByID(position);


        String date = result.getString(result.getColumnIndex(DbAdapter.KEY_DATE));
        String firstName = result.getString(result.getColumnIndex(DbAdapter.KEY_FIRSTNAME));
        String lastName = result.getString(result.getColumnIndex(DbAdapter.KEY_LASTNAME));
        String phone = result.getString(result.getColumnIndex(DbAdapter.KEY_PHONE));
        String email = result.getString(result.getColumnIndex(DbAdapter.KEY_EMAIL));
        String note = result.getString(result.getColumnIndex(DbAdapter.KEY_NOTE));


        contact_title.setText(firstName + " " + lastName);
//        contact_phone.setText(phone);
//        contact_email.setText(email);
        contact_time.setText(date);
//        contact_note.setText(note);

        mCurrentPosition = position;
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
