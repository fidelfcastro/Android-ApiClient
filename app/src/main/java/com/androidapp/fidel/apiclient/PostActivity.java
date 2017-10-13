package com.androidapp.fidel.apiclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by fidel on 10/13/2017.
 */

public class PostActivity extends AppCompatActivity {
    PostAdapter oPostAdapter;
    ListView oListView;
    ArrayList<Posts> postsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        oListView=(ListView) findViewById(R.id.lv_postsList);
        oPostAdapter =new PostAdapter(this);
        oListView.setAdapter(oPostAdapter);

        postsArray = this.getIntent().getParcelableArrayListExtra("Array");
        parceComments(postsArray);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void parceComments(ArrayList<Posts> lPosts) {
        oPostAdapter.clear();

        for(Posts oPost : lPosts) {
            oPostAdapter.add(oPost);

        }

        oPostAdapter.notifyDataSetChanged();
    }
}
