package com.example.club.Objects;

import android.graphics.Bitmap;

public class Activity {
    private String activityID;
    private int postByType;
    private String postUserID;
    private String activityName;
    private String location;
    private String Date; //YYYY-MM-DD
    private String Time;
    private String type;
    private String description;
    private int capacity;
    private Bitmap image;
    private int likes;
    private String detailsURL;

    public Activity(String activityID, int postByType, String postUserID, String activityName,
                    String location, String Date, String time,
                    String type, String description, int capacity, Bitmap image, int likes, String detailsURL){
        this.activityID = activityID;
        this.postByType = postByType;
        this.postUserID = postUserID;
        this.activityName = activityName;
        this.location = location;
        this.Date = Date;
        this.Time = time;
        this.type = type;
        this.description = description;
        this.capacity = capacity;
        this.image = image;
        this.likes = likes;
        this.detailsURL = detailsURL;
    }

    public String getActivityID() {
        return activityID;
    }

    public int getPostByType() {
        return postByType;
    }

    public String getPostUserID() {
        return postUserID;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getLikes() {
        return likes;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getDetailsURL() {
        return detailsURL;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPostByType(int postByType) {
        this.postByType = postByType;
    }

    public void setPostUserID(String postUserID) {
        this.postUserID = postUserID;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDetailsURL(String detailsURL) {
        this.detailsURL = detailsURL;
    }
}
