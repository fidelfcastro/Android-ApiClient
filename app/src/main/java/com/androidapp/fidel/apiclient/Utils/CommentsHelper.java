package com.androidapp.fidel.apiclient.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.androidapp.fidel.apiclient.Comments;

import java.util.ArrayList;

/**
 * Created by fidel on 10/6/2017.
 */

public class CommentsHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] COMMENTS_TABLE_COLUMNS=
            {
                    DBUtils.FOREIGN_POST_ID,
                    DBUtils.COMMENTS_ID,
                    DBUtils.COMMENTS_NAME,
                    DBUtils.COMMENTS_EMAIL,
                    DBUtils.COMMENTS_BODY
            };


    public CommentsHelper(Context context){
        dbHelper = new DBUtils(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Comments addComments(String postId, String id, String name, String email, String body) {
        ContentValues values = new ContentValues();
        values.put(DBUtils.FOREIGN_POST_ID,postId);
        values.put(DBUtils.COMMENTS_ID,id);
        values.put(DBUtils.COMMENTS_NAME,name);
        values.put(DBUtils.COMMENTS_EMAIL,email);
        values.put(DBUtils.COMMENTS_BODY,body);

        long lId = database.insert(DBUtils.COMMENTS_TABLE_NAME,null,values);

        Cursor cursor = database.query(DBUtils.COMMENTS_TABLE_NAME,
                COMMENTS_TABLE_COLUMNS,DBUtils.C_ID + " = " +  lId,
                null,null,null,null);
        Log.d("myTag", "ID" + lId);
        Log.d("myTag", "CURSOR" + cursor);

        cursor.moveToFirst();
        Comments oComments = parseComments(cursor);
        cursor.close();
        return oComments;
    }

    public int deleteComments(int commentsId){
        return database.delete(DBUtils.COMMENTS_TABLE_NAME, DBUtils.COMMENTS_ID + " = '" + commentsId + "'", null);
    }

    public ArrayList<Comments> getAllComments() {
        ArrayList<Comments> oComments = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.COMMENTS_TABLE_NAME, COMMENTS_TABLE_COLUMNS, null,null,null,null,null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            oComments.add(parseComments(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return oComments;
    }

    private Comments parseComments(Cursor cursor) {
        Comments oComments = new Comments();
        //oComments.setcId(cursor.getInt(cursor.getColumnIndex(DBUtils.C_ID)));
        oComments.setPostsId(cursor.getString(cursor.getColumnIndex(DBUtils.FOREIGN_POST_ID)));
        oComments.setId(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENTS_ID)));
        oComments.setName(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENTS_NAME)));
        oComments.setEmail(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENTS_EMAIL)));
        oComments.setBody(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENTS_BODY)));
        return oComments;
    }
}
