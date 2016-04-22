package com.example.johan.carbonfootprint;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * database class for carbon footprint
 * Created by Johan on 16-Apr-2016.
 */
public class CarbonFootprintDBAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_VEHICLE = "vehicle";
    public static final String KEY_DISTANCE = "distance";
    public static final String KEY_DATE = "date";
    public static final String KEY_NOTE = "note";
    public static final String KEY_FOOTPRINT = "footprint";

    private static final String TAG = "FootprintDBAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "CarbonFootprint";
    private static final String SQLITE_TABLE = "VehicleFootprint";
    private static final int DATABASE_VERSION = 1;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_CATEGORY + "," +
                    KEY_VEHICLE + "," +
                    KEY_DISTANCE + "," +
                    KEY_DATE + "," +
                    KEY_NOTE + "," +
                    KEY_FOOTPRINT +");";

    /**
     * database helper class
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        /**
         * on create
         * @param db
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }


        /**
         * if we need to upgrade, we call this class
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ",which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    /**
     * constructor
     * @param ctx
     */
    public CarbonFootprintDBAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * open the database
     * @return
     * @throws SQLException
     */
    public CarbonFootprintDBAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    /**
     * close the database
     */
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    /**
     * create a carbon footprint record and store in database
     * @param category
     * @param vehicle
     * @param distance
     * @param date
     * @param note
     * @param footprint
     * @return
     */
    public long createFootprint(String category, String vehicle,
                              String distance, String date, String note, String footprint) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_CATEGORY, category);
        initialValues.put(KEY_VEHICLE, vehicle);
        initialValues.put(KEY_DISTANCE, distance);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_NOTE, note);
        initialValues.put(KEY_FOOTPRINT, footprint);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    /**
     * delete all records
     * @return
     */
    public boolean deleteAllFootprint() {
        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    /**
     * delete one specific record
     * @param inputText
     * @return
     * @throws SQLException
     */
    public boolean deleteCertainFootprint(String inputText) throws SQLException {
//        Log.w(TAG, inputText);
//        Cursor mCursor = null;
//        if (inputText == null  ||  inputText.length () == 0)  {
//            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
//                            KEY_CATEGORY, KEY_VEHICLE, KEY_DISTANCE, KEY_DATE, KEY_NOTE, KEY_FOOTPRINT},
//                    null, null, null, null, null);
//
//        }
//        else {
//            mCursor = mDb.delete(true, SQLITE_TABLE, new String[] {KEY_ROWID,
//                            KEY_CATEGORY, KEY_VEHICLE, KEY_DISTANCE, KEY_DATE, KEY_NOTE, KEY_FOOTPRINT},
//                    KEY_ROWID + " like '%" + inputText + "%'", null,
//                    null, null, null, null);

            return mDb.delete(SQLITE_TABLE, KEY_ROWID + " like '%" + inputText + "%'", null ) > 0;

        //}

    }

    /**
     * get specific row
     * @param inputText
     * @return
     * @throws SQLException
     */
    public Cursor fetchFootprintByRowID(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                            KEY_CATEGORY, KEY_VEHICLE, KEY_DISTANCE, KEY_DATE, KEY_NOTE, KEY_FOOTPRINT},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
                    KEY_CATEGORY, KEY_VEHICLE, KEY_DISTANCE, KEY_DATE, KEY_NOTE, KEY_FOOTPRINT},
                    KEY_ROWID + " like '%" + inputText + "%'", null,
                   null, null, null, null);

        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * get all rows
     * @return
     */
    public Cursor fetchAllFootprint() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                        KEY_CATEGORY, KEY_VEHICLE, KEY_DISTANCE, KEY_DATE, KEY_NOTE, KEY_FOOTPRINT},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}
