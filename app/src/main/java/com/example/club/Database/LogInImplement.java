package com.example.club.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.club.Objects.User;


public class LogInImplement implements LogInDatabase {
    private DatabaseHelper helper;

    public LogInImplement(Context context){
        helper = new DatabaseHelper(context);
    }

    @Override
    public User getUser(String username) {
        User result = new User("", "", 0, "", 0, "");

        SQLiteDatabase sdb = helper.getReadableDatabase();

        String sql = "select * from User where username=?";

        Cursor cursor = sdb.rawQuery(sql, new String[] {username});

        if (cursor.moveToFirst()){

            result.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            result.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            result.setEmailAddress(cursor.getString(cursor.getColumnIndex("emailAddress")));
            result.setGender(cursor.getString(cursor.getColumnIndex("gender")));
            result.setType(cursor.getInt(cursor.getColumnIndex("type")));
            result.setYear(cursor.getInt(cursor.getColumnIndex("year")));
        }

        cursor.close();

        return result;
    }
}
