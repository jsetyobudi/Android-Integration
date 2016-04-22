
package com.example.android.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class UserList extends Fragment {

    onContactListListener mCallback;

    public interface onContactListListener {

        void viewContactDetail(int position, long id);
    }

    private ListView listView;
    private EditText myFilter;

    DbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.userlist_view, container, false);

        listView = (ListView) fragmentView.findViewById(R.id.list_view);
        myFilter = (EditText) fragmentView.findViewById(R.id.myFilter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.viewContactDetail(position, id);
            }
        });

        dbHelper = new DbAdapter(getActivity());
        dbHelper.open();
        displayListView();

        return fragmentView;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void displayListView() {

        Cursor cursor = dbHelper.fetchAllTitles();

        String[] columns = new String[]{
                DbAdapter.KEY_FIRSTNAME, DbAdapter.KEY_PHONE
        };

        int[] to = new int[]{
                R.id.main_title, R.id.sub_title
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            dataAdapter = new SimpleCursorAdapter(getActivity(), R.layout.contact_title, cursor, columns, to, 0);
        }

        listView.setAdapter(dataAdapter);

        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper.fetchContactByName(constraint.toString());
            }
        });
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
            mCallback = (onContactListListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onContactListListener");
        }
    }


}
