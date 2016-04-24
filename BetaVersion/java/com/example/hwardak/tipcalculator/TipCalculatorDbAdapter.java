package com.example.hwardak.tipcalculator;
/* Copyright © 2011-2013 mysamplecode.com, All rights reserved.
  This source code is provided to students of CST2335 for educational purposes only.
 */

/* IMPORTANT NOTE: Code in this file was based on the lecture
 * materials provided by Todd Kelly (2016).
 */
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class TipCalculatorDbAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_TIPP = "percent";
    public static final String KEY_TIPD = "dollar";
    public static final String KEY_TOTAL = "TOTAL";
    public static final String KEY_NOTE = "NOTE";

    private static final String TAG = "TipCalculatorDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "TipCalculator";
    private static final String SQLITE_TABLE = "TipCalculatorTable";
    private static final int DATABASE_VERSION = 3;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_NAME + "," +
                    KEY_TIPP + "," +
                    KEY_TIPD + "," +
                    KEY_TOTAL + "," +
                    KEY_NOTE + ");"
                    ;


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * Creates the databse.
         * @param db SQLiteDatabase object.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        /**
         * Update the database.
         * @param db SQLiteDatabse object
         * @param oldVersion the previous version of the database.
         * @param newVersion the new version of the database.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    /**
     * Tip calculator adapter setter.
     * @param ctx Context object.
     */
    public TipCalculatorDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * To open a connection to a database.
     * @return
     * @throws SQLException
     */
    public TipCalculatorDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    /**
     * To close the connection to a database.
     */
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    /**
     * To create the intaial values that will be added to the database.
     * @param name name of restaurant.
     * @param tipP tip percentage.
     * @param tipD tip dollar value.
     * @param total total dollar value.
     * @param note note.
     * @return SQLitedatabase object.
     */
    public long createRecord(String name, String tipP,
                             String tipD, String total, String note) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_TIPP, tipP);
        initialValues.put(KEY_TIPD, tipD);
        initialValues.put(KEY_TOTAL, total);
        initialValues.put(KEY_NOTE, note);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }


    /**
     * To delete specifc records from the database.
     * @param inputText String value of text to be passed.
     * @return SQLiteDatabse object.
     * @throws SQLException if it fails.
     */
    //TODO: EDIT THIS
    public boolean deleteCertainRecord(String inputText) throws SQLException {

        return mDb.delete(SQLITE_TABLE, KEY_ROWID + " like '%" + inputText + "%'", null ) > 0;



    }

    /**
     * To query the databse for specific record.
     * @param inputText String value of text to be passed.
     * @return Cursor object.
     * @throws SQLException
     */
    public Cursor fetchRecordsByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);

        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,KEY_NAME,
                            KEY_TIPP, KEY_TIPD, KEY_TOTAL, KEY_NOTE},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,KEY_NAME,
                            KEY_TIPP, KEY_TIPD, KEY_TOTAL, KEY_NOTE},
                    KEY_ROWID + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * Query the database for all records stored.
     * @return Cursor object.
     */
    public Cursor fetchAllRecords() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                        KEY_TIPP, KEY_TIPD, KEY_TOTAL, KEY_NOTE},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Double dataSummary() {
        Cursor cursor = mDb.rawQuery("SELECT SUM (TOTAL) FROM " + SQLITE_TABLE, null);
        cursor.moveToFirst();

        double total = 0;

        if (cursor.getCount() > 0) {
            total = cursor.getDouble(0);
        }
        return total;

    }
}
