package com.androidapp.fidel.apiclient;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fidel on 10/6/2017.
 */

public class Comments implements Parcelable {
    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    private Integer cId;
    private String postsId;
    private String id;
    private String name;
    private String email;
    private String body;

    public String getPostsId() {
        return postsId;
    }

    public void setPostsId(String postsId) {
        this.postsId = postsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Comments()
    {

    }

    public Comments(String postsId, String id, String name, String email, String body) {
        this.postsId = postsId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public Comments(Parcel in) {
        postsId=in.readString();
        id = in.readString();
        name=in.readString();
        email=in.readString();
        body=in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postsId);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(body);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Comments createFromParcel(Parcel in){
            return new Comments(in);
        }
        @Override
        public Comments[] newArray(int size){
            return new Comments[size];
        }
    };

}
