package com.example.sonu.rateit.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sonu on 1/6/17.
 */



public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "loginData.db";
    private static final String DATABASE_TABLE = "loginTable";
    String notFound = "notFound";

    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "password";
    private static final String KEY_NAME = "name";
    private static final String KEY_DEPARTMENT = "department";
    private static final String KEY_PICTURE = "picture";

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT,"+ KEY_PASS + " TEXT," +KEY_NAME + " TEXT," +KEY_DEPARTMENT + " TEXT," +KEY_PICTURE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " +DATABASE_TABLE);
    }

    public void insert(String email , String password , String name , String department , String picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASS, password);
        values.put(KEY_NAME, name);
        values.put(KEY_DEPARTMENT, department);
        values.put(KEY_PICTURE, picture);
        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }
    public String getEmail(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID,  KEY_EMAIL, KEY_PASS }, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return notFound;
        return  cursor.getString(1);
    }
    public String getDataPass(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_EMAIL, KEY_PASS}, KEY_ID + "=?",  new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return notFound;
        return  cursor.getString(2);
    }
    public String getDataName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_PASS}, KEY_ID + "=?",  new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return notFound;
        return  cursor.getString(1);
    }
    public String getDataDepartment(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_DEPARTMENT, KEY_PASS}, KEY_ID + "=?",  new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return notFound;
        return  cursor.getString(1);
    }
    public String getDataPicture(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_PICTURE, KEY_PASS}, KEY_ID + "=?",  new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        else
            return notFound;
        return  cursor.getString(1);
    }

    public int getUser() {
        String countQuery = "SELECT  * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();

    }

}