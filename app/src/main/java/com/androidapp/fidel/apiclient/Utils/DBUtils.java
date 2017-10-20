package com.androidapp.fidel.apiclient.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fidel on 10/6/2017.
 */

public class DBUtils extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "JSONPlayHolder.db";
    public static final int DATABASE_VERSION = 13;

    public static final String POSTS_TABLE_NAME = "POSTS";
    public static final String P_ID = "_postsId";
    public static final String POSTS_USER_ID = "_userId";
    public static final String POSTS_ID = "_id";
    public static final String POSTS_TITLE = "_title";
    public static final String POSTS_BODY = "_body";

    public static final String COMMENTS_TABLE_NAME = "COMMENTS";
    public static final String C_ID = "_commentsId";
    public static final String FOREIGN_POST_ID = "_postId";
    public static final String COMMENTS_ID = "_id";
    public static final String COMMENTS_NAME = "_name";
    public static final String COMMENTS_EMAIL = "_email";
    public static final String COMMENTS_BODY = "_body";



    public static final String DB_POST_CREATE = "CREATE TABLE "+
            POSTS_TABLE_NAME +
            "(" +
            P_ID +
            " integer primary key autoincrement," +
            POSTS_ID  +
            " text NOT NULL," +
            POSTS_USER_ID +
            " text NOT NULL," +
            POSTS_TITLE +
            " text NOT NULL," +
            POSTS_BODY  +
            " text NOT NULL" +
            ")";

    public static final String DB_COMMENTS_CREATE = "CREATE TABLE "+
            COMMENTS_TABLE_NAME +
            " (" +
            C_ID +
            " integer primary key autoincrement," +
            FOREIGN_POST_ID  +
            " text NOT NULL," +
             COMMENTS_ID+
            " text NOT NULL," +
            COMMENTS_NAME +
            " text NOT NULL," +
            COMMENTS_EMAIL +
            " text NOT NULL," +
            COMMENTS_BODY  +
            " text NOT NULL" +
            ")";


    public DBUtils(Context context){
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_POST_CREATE);
        sqLiteDatabase.execSQL(DB_COMMENTS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS POSTS");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS COMMENTS");
        onCreate(sqLiteDatabase);


    }
}
