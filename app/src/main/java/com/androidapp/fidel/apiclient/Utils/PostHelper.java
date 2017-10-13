package com.androidapp.fidel.apiclient.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.androidapp.fidel.apiclient.Posts;

import java.util.ArrayList;

/**
 * Created by fidel on 10/6/2017.
 */

public class PostHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] POSTS_TABLE_COLUMNS=
            {
                    DBUtils.POSTS_USER_ID,
                    DBUtils.POSTS_ID,
                    DBUtils.POSTS_TITLE,
                    DBUtils.POSTS_BODY
            };


    public PostHelper(Context context){
        dbHelper = new DBUtils(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Posts addPosts(String userId, String id, String title, String body) {
        ContentValues values = new ContentValues();
        values.put(DBUtils.POSTS_USER_ID,userId);
        values.put(DBUtils.POSTS_ID,id);
        values.put(DBUtils.POSTS_TITLE,title);
        values.put(DBUtils.POSTS_BODY,body);

        long lId = database.insert(DBUtils.POSTS_TABLE_NAME,null,values);

        Cursor cursor = database.query(DBUtils.POSTS_TABLE_NAME,
                POSTS_TABLE_COLUMNS,DBUtils.P_ID+ " = " + lId,
                null,null,null,null);
        cursor.moveToFirst();
        Posts oPost = parsePosts(cursor);
        cursor.close();
        return oPost;
    }

    public int deletePosts(int postId){
        return database.delete(DBUtils.POSTS_TABLE_NAME, DBUtils.POSTS_ID + " = '" + postId + "'", null);
    }

    public ArrayList<Posts> getAllPosts() {
        ArrayList<Posts> oPosts = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.POSTS_TABLE_NAME, POSTS_TABLE_COLUMNS, null,null,null,null,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            oPosts.add(parsePosts(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return oPosts;
    }

    private Posts parsePosts(Cursor cursor) {
        Posts oPost = new Posts();
        oPost.setUserId(cursor.getString(cursor.getColumnIndex(DBUtils.POSTS_USER_ID)));
        oPost.setId(cursor.getString(cursor.getColumnIndex(DBUtils.POSTS_ID)));
        oPost.setId(cursor.getString(cursor.getColumnIndex(DBUtils.POSTS_ID)));
        oPost.setTitle(cursor.getString(cursor.getColumnIndex(DBUtils.POSTS_TITLE)));
        oPost.setBody(cursor.getString(cursor.getColumnIndex(DBUtils.POSTS_BODY)));
        return oPost;
    }


}
