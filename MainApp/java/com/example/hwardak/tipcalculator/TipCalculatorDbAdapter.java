package com.example.hwardak.tipcalculator;
/* Copyright © 2011-2013 mysamplecode.com, All rights reserved.
  This source code is provided to students of CST2335 for educational purposes only.
 */
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;
        import com.example.tgk.integrationwithfragment.R;


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


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    public TipCalculatorDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public TipCalculatorDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createCountry(String name, String tipP,
                              String tipD, String total, String note) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_TIPP, tipP);
        initialValues.put(KEY_TIPD, tipD);
        initialValues.put(KEY_TOTAL, total);
        initialValues.put(KEY_NOTE, note);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllCountries() {

        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public Cursor fetchCountriesByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);

        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_NAME,
                            KEY_TIPP, KEY_TIPD, KEY_TOTAL, KEY_NOTE},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_NAME,
                            KEY_TIPP, KEY_TIPD, KEY_TOTAL, KEY_NOTE},
                    KEY_TIPD + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllCountries() {

        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_NAME,
                        KEY_TIPP, KEY_TIPD, KEY_TOTAL, KEY_NOTE},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void insertSomeCountries() {

//        createCountry("AFG","Afghanistan","Asia","Southern and Central Asia");
//        createCountry("ALB","Albania","Europe","Southern Europe");
//        createCountry("DZA","Algeria","Africa","Northern Africa");
//        createCountry("ASM","American Samoa","Oceania","Polynesia");
//        createCountry("AND","Andorra","Europe","Southern Europe");
//        createCountry("AGO","Angola","Africa","Central Africa");
//        createCountry("AIA","Anguilla","North America","Caribbean");

    }

}
