package com.androidapp.fidel.apiclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fidel on 10/18/2017.
 */

public class ExpandableListActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    ArrayList<Comments> commentsArray;
    ArrayList<Posts> postsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list);

        postsArray = this.getIntent().getParcelableArrayListExtra("PostArray");
        commentsArray = this.getIntent().getParcelableArrayListExtra("CommentArray");

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = getData(postsArray, commentsArray);

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
    }

    public static HashMap<String, List<String>> getData(ArrayList<Posts> pArray, ArrayList<Comments> cArray) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        List<String> commentsList;
        String comments = "";
        String posts = "";

        for(Posts post : pArray){
            posts = post.getTitle();
            commentsList = new ArrayList<>();
            for(Comments comment : cArray){
                if(comment.getPostsId().equals(post.getId())){
                    comments = comment.getName();
                    commentsList.add(comments);
                }
            }
            expandableListDetail.put(posts, commentsList);
        }

        return expandableListDetail;
    }
}
