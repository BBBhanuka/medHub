package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    Context context;

    public Database(@Nullable Context context) {
        super(context, "healthcare_db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String userTableQry = "CREATE TABLE USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT UNIQUE, EMAIL TEXT,PASSWORD TEXT)";
        sqLiteDatabase.execSQL(userTableQry);

        String appointmentTableQry = "CREATE TABLE APPOINTMENT(APPID INTEGER PRIMARY KEY AUTOINCREMENT,FULLNAME TEXT,ADDRESS TEXT,CONTACTNUMBER TEXT,REPORTS TEXT,USERNAME TEXT, DATE TEXT, TIME TEXT)";
        sqLiteDatabase.execSQL(appointmentTableQry);

        String orderTableQry = "create table orders(FULLNAME text,ADDRESS text,CONTACTNO text,USERNAME text)";
        sqLiteDatabase.execSQL(orderTableQry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM users where username =? AND password =?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        String userName = null;
        if (cursor.moveToFirst()) {
            userName = cursor.getString(1);
        }
        cursor.close();

        if (userName != null) {
            SharedPreferences sharedpreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("username", userName);
            // to save our data with key and value.
            editor.apply();
            return true;
        } else {
            return false;
        }
    }


    public boolean checkUsername(String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.query("USERS", new String[]{"USERNAME"}, "USERNAME = ?", new String[]{username}, null, null, null);

        if (result.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean register(String username, String password, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("USERNAME", username);
        contentValues.put("EMAIL", email);
        contentValues.put("PASSWORD", password);

        long result = db.insert("USERS", null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }


//    private String getUsername() {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
//        String username = sharedPreferences.getString("username", "").toString();
//        return username;
//    }


   // CREATE TABLE APPOINTMENT(APPID INTEGER PRIMARY KEY AUTOINCREMENT,FULLNAME TEXT,ADDRESS TEXT,CONTACTNUMBER TEXT,REPORTS TEXT,USERNAME TEXT, DATE TEXT, TIME TEXT)
    public void bookAppointment(String fullName, String address, String contactNo, String reports, String date, String time) {
        ContentValues cv = new ContentValues();
        cv.put("USERNAME", AppGlobal.userName);
        cv.put("FULLNAME", fullName);
        cv.put("ADDRESS", address);
        cv.put("CONTACTNUMBER", contactNo);
        cv.put("REPORTS", reports);
        cv.put("DATE", date);
        cv.put("TIME", time);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("appointment", null, cv);
        db.close();
    }

//    public boolean insertUserData(String username, String email, String password, String birthday) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(T3COL2, username);
//        contentValues.put(T3COL3, email);
//        contentValues.put(T3COL4, password);
//        contentValues.put(T3COL5, birthday);
//
//
//        long result = db.insert(TABLE_NAME3, null, contentValues);
//
//        if (result == -1)
//            return false;
//        else
//            return true;
//    }


    public void addCart(String username, String product, float price, String otype) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("product", product);
        cv.put("price", price);
        cv.put("otype", otype);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("cart", null, cv);
        db.close();
    }

    public void removeCart(String username, String otype) {
        String str[] = new String[2];
        str[0] = username;
        str[1] = otype;
        SQLiteDatabase db = getWritableDatabase();
        db.delete("cart", "username=? and otype=?", str);
        db.close();
    }

    public ArrayList getCartData(String username, String otype) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str[] = new String[2];
        str[0] = username;
        str[1] = otype;
        Cursor c = db.rawQuery("select * from cart where username = ? and otype = ?", str);
        if (c.moveToFirst()) {
            do {
                String product = c.getString(1);
                String price = c.getString(2);
                arr.add(product + "$" + price);
            } while (c.moveToNext());
        }
        db.close();
        return arr;
    }

}
