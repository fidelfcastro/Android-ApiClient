package com.androidapp.fidel.apiclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fidel on 10/12/2017.
 */

public class CommentsActivity extends AppCompatActivity {
    CommentsAdapter oCommentsAdapter;
    ListView oListView;
    ArrayList<Comments> commentsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        oListView=(ListView) findViewById(R.id.lv_commentsList);
        oCommentsAdapter =new CommentsAdapter(this);
        oListView.setAdapter(oCommentsAdapter);

        commentsArray = this.getIntent().getParcelableArrayListExtra("Array");
        parceComments(commentsArray);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

        private void parceComments(ArrayList<Comments> lComments) {
            oCommentsAdapter.clear();

        for(Comments oComments : lComments) {
            oCommentsAdapter.add(oComments);

        }

            oCommentsAdapter.notifyDataSetChanged();
    }


}
