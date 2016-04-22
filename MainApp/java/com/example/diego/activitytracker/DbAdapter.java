package com.example.diego.activitytracker;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_HEADLINE = "headline";
    public static final String KEY_ARTICLE = "article";

    private static final String TAG = "ArticleDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "FragmentsLab";
    private static final String SQLITE_TABLE = "Articles";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_HEADLINE + " TEXT, " +
                    KEY_ARTICLE + " TEXT " +
                    ");";


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
    }//End of helper class

    public  DbAdapter (Context ctx){
        this.mCtx = ctx;
    }

    public DbAdapter open() throws SQLException{
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long createArticle (String headline, String article){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_HEADLINE, headline);
        initialValues.put(KEY_ARTICLE, article);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public boolean deleteAllArticles(){
        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null, null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
    }

    public Cursor fetchAllHeadlines(){

        Cursor cursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID, KEY_HEADLINE},
                null, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor fetchArticleByID(int ID){
        Cursor cursor = null;

        cursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ARTICLE},
                KEY_ROWID + " LIKE '%" + ID + "%'", null, null, null, null, null);


        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void insertSomeArticles(){

        createArticle("Article One","hhhhhhhhhExcepteur pour-over occaecat squid biodiesel umami gastropub, nulla laborum salvia dreamcatcher fanny pack. Ullamco culpa retro ea, trust fund excepteur eiusmod direct trade banksy nisi lo-fi cray messenger bag. Nesciunt esse carles selvage put a bird on it gluten-free, wes anderson ut trust fund twee occupy viral. Laboris small batch scenester pork belly, leggings ut farm-to-table aliquip yr nostrud iphone viral next level. Craft beer dreamcatcher pinterest truffaut ethnic, authentic brunch. Esse single-origin coffee banksy do next level tempor. Velit synth dreamcatcher, magna shoreditch in american apparel messenger bag narwhal PBR ennui farm-to-table.");
        createArticle("Article Two","hhhhhhhhhhVinyl williamsburg non velit, master cleanse four loko banh mi. Enim kogi keytar trust fund pop-up portland gentrify. Non ea typewriter dolore deserunt Austin. Ad magna ethical kogi mixtape next level. Aliqua pork belly thundercats, ut pop-up tattooed dreamcatcher kogi accusamus photo booth irony portland. Semiotics brunch ut locavore irure, enim etsy laborum stumptown carles gentrify post-ironic cray. Butcher 3 wolf moon blog synth, vegan carles odd future.");
    }

}