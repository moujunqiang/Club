package com.example.club.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.club.Objects.Club;
import com.example.club.Objects.User;
import com.example.club.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class RegisterImplement implements RegisterDatabase{
    private DatabaseHelper helper;

    public RegisterImplement(Context context){
        helper = new DatabaseHelper(context);
    }

    @Override
    public User queryRegistered(String username) {
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

    @Override
    public boolean insertUser(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username",user.getUsername());
        values.put("password",user.getPassword());
        values.put("emailAddress",user.getEmailAddress());
        values.put("year",user.getYear());
        values.put("type",user.getType());
        values.put("gender",user.getGender());

        try {
            db.insert("User",null,values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean insertClub(Club club) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("clubid",club.getClubID());
        values.put("password",club.getPassword());
        values.put("emailaddress",club.getEmailAddress());
        values.put("type",club.getType());

        try {
            db.insert("ClubAccount",null,values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
