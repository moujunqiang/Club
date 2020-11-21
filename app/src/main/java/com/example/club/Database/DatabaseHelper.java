package com.example.club.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOError;
import java.io.IOException;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1; // Database Version
    private static final String DATABASE_NAME = "ClubSystem.db"; // Database Name

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_User_Table = "create table User(username TEXT NOT NULL, password TEXT NOT NULL, emailAddress TEXT NOT NULL" +
                ", year INTEGER, type INTEGER NOT NULL, gender TEXT,avatar BLOB, PRIMARY KEY(username), FOREIGN KEY(username) REFERENCES images(id))";
        String Insert_User_Table = "insert into User(username,password,emailAddress,year,type,gender) values(?,?,?,?,?,?)";


        String Create_ClubAccount_Table = "CREATE TABLE ClubAccount (clubid TEXT NOT NULL,password TEXT NOT NULL,emailaddress TEXT NOT NULL," +
                "type TEXT NOT NULL,FOREIGN KEY(clubid) REFERENCES FollowingClub(user_userid),PRIMARY KEY(clubid))";
        String Create_Activity_Table = "CREATE TABLE Activity (activityid TEXT,postByType INTEGER,postUserID TEXT,activityName TEXT," +
                "location TEXT,time TEXT,type TEXT,description TEXT,capacity INTEGER,img BLOB,likes INTEGER, detailsurl TEXT,FOREIGN KEY(img) REFERENCES Image(img)," +
                "FOREIGN KEY(activityid) REFERENCES ClubAccount(clubid),PRIMARY KEY(activityid))";

        String Create_FollowingClub_Table = "CREATE TABLE FollowingClub (user_userid TEXT,club_userid TEXT,FOREIGN KEY(club_userid) REFERENCES ClubAccount(clubid),FOREIGN KEY(user_userid) REFERENCES User(username),PRIMARY KEY(user_userid,club_userid))";
        String Create_Comment_Table = "CREATE TABLE Comment (commentid TEXT,belongactivityid TEXT,postuserid TEXT, content TEXT NOT NULL, FOREIGN KEY(belongactivityid) REFERENCES Activity(activityid),FOREIGN KEY(postuserid) REFERENCES User(username),PRIMARY KEY(commentid,postuserid,belongactivityid))";

        db.execSQL("create table images(id TEXT primary key, img blob not null)");
        db.execSQL(Create_Activity_Table);
        db.execSQL(Create_User_Table);
        db.execSQL(Create_ClubAccount_Table);

        db.execSQL(Create_FollowingClub_Table);
        db.execSQL(Create_Comment_Table);


        db.execSQL(Insert_User_Table, new Object[]{"Yuqi.Guo17", "1234", "Yuqi.Guo17@studentt.xjtlu.edu.cn", 4, 3, "male"});


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public Boolean insertImage(String x, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            FileInputStream fs = new FileInputStream(x);
            byte[] imgbyte = new byte[fs.available()];

            fs.read(imgbyte);

            ContentValues contentValues = new ContentValues();
            contentValues.put("id", id);
            contentValues.put("img", imgbyte);

            db.insert("images", null, contentValues);
            fs.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean insertDefultImage(String id, byte[] imgb) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("id", id);
            contentValues.put("img", imgb);

            db.insert("images", null, contentValues);

            return true;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

    }

    public boolean amendImage(String id, byte[] imgb) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {


            ContentValues contentValues = new ContentValues();
            contentValues.put("id", id);
            contentValues.put("img", imgb);

            db.update("images", contentValues, "id=?", new String[]{id});

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean amendImage(String x, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            FileInputStream fs = new FileInputStream(x);
            byte[] imgbyte = new byte[fs.available()];

            fs.read(imgbyte);

            ContentValues contentValues = new ContentValues();
            contentValues.put("id", id);
            contentValues.put("img", imgbyte);

            db.update("images", contentValues, "id=?", new String[]{id});
            fs.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public Bitmap getImage(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Bitmap bt = null;
        Cursor cursor = db.rawQuery("select * from images where id=?", new String[]{String.valueOf(id)});
        CursorWindow cw = new CursorWindow("test", 500000000);
        AbstractWindowedCursor ac = (AbstractWindowedCursor) cursor;
        ac.setWindow(cw);
        if (ac.moveToFirst()) {
            byte[] img = cursor.getBlob(1);
            bt = BitmapFactory.decodeByteArray(img, 0, img.length);
        }

        return bt;
    }


}
