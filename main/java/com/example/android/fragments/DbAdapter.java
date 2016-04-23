/**
 * Course Name:CST2335_010 Graphical Interface Programming
 Student Name: Xuan Li
 Student Number:040811628
 *
 * */
package com.example.android.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_FIRSTNAME = "firstName";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NOTE = "note";
    public static final String KEY_DATE = "creationDate";

    private static final String TAG = "contactDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "contactDatabase";
    private static final String SQLITE_TABLE = "contactList";
    private static final int DATABASE_VERSION = 4;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_FIRSTNAME + " TEXT, " +
                    KEY_LASTNAME + " TEXT, " +
                    KEY_PHONE + " TEXT, " +
                    KEY_EMAIL + " TEXT, " +
                    KEY_NOTE + " TEXT, " +
                    KEY_DATE + " TEXT " +
                    ");";
    private Cursor mCursor;

    public Cursor getContactByInput(String inputText) {
        mCursor = mDb.query(true, SQLITE_TABLE, new String[]{KEY_ROWID,
                        KEY_DATE, KEY_FIRSTNAME, KEY_LASTNAME, KEY_PHONE, KEY_EMAIL, KEY_NOTE},
                KEY_LASTNAME + " like '%" + inputText + "%'", null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }





    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            Log.w(TAG, DATABASE_CREATE);
            sqLiteDatabase.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public DbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public DbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createNewContact(String firstName, String lastName, String phone, String email, String note, String date) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_FIRSTNAME, firstName);
        initialValues.put(KEY_LASTNAME, lastName);
        initialValues.put(KEY_PHONE, phone);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_NOTE, note);
        initialValues.put(KEY_DATE, date);


        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }



    public boolean deleteContact(int ID) {
        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, KEY_ROWID + " LIKE '%" + ID + "%'", null);
        return doneDelete > 0;
    }

    public Cursor fetchAllTitles() {

        Cursor cursor = mDb.query(SQLITE_TABLE, new String[]{KEY_ROWID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_PHONE},
                null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor fetchContactByID(long ID) {
        Cursor cursor = mDb.query(true, SQLITE_TABLE, new String[]{KEY_DATE, KEY_FIRSTNAME, KEY_LASTNAME, KEY_PHONE, KEY_EMAIL, KEY_NOTE},
                KEY_ROWID + "=" + ID, null, null, null, null, null);


        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }


    public Cursor fetchContactByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null || inputText.length() == 0) {
            mCursor = mDb.query(SQLITE_TABLE, new String[]{KEY_ROWID,
                            KEY_DATE, KEY_FIRSTNAME, KEY_LASTNAME, KEY_PHONE, KEY_EMAIL, KEY_NOTE},
                    null, null, null, null, null);

        } else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[]{KEY_ROWID,
                            KEY_DATE, KEY_FIRSTNAME, KEY_LASTNAME, KEY_PHONE, KEY_EMAIL, KEY_NOTE},
                    KEY_LASTNAME + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }


}