package com.androidapp.fidel.apiclient;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fidel on 10/6/2017.
 */

public class Posts implements Parcelable {

    private String userId;
    private String id;
    private String title;
    private String body;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Posts()
    {

    }

    public Posts(String userId, String id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Posts(Parcel in) {
        userId=in.readString();
        id = in.readString();
        title=in.readString();
        body=in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(body);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Posts createFromParcel(Parcel in){
            return new Posts(in);
        }
        @Override
        public Posts[] newArray(int size){
            return new Posts[size];
        }
    };


}
