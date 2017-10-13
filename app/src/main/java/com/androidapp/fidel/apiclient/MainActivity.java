package com.androidapp.fidel.apiclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidapp.fidel.apiclient.Utils.CommentsHelper;
import com.androidapp.fidel.apiclient.Utils.PostHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView postResponseView;
    TextView commentsResponseView;
    ArrayList<Comments> commentsArray = new ArrayList<>();
    ArrayList<Posts> postArray = new ArrayList<>();
    CommentsAdapter oCommentsAdapter;
    PostAdapter oPostAdapter;
    String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postResponseView = (TextView) findViewById(R.id.postTxtView);
        commentsResponseView = (TextView) findViewById(R.id.commentsTxtView);

        Button addPostButton = (Button) findViewById(R.id.addPostButton);
        Button viewPostButton = (Button) findViewById(R.id.viewPostButton);
        Button addCommentsButton = (Button) findViewById(R.id.commentsButton);
        Button viewCommentsButton = (Button) findViewById(R.id.viewCommentsButton);

        final EditText txtPostId = (EditText) findViewById(R.id.postId);
        final EditText txtCommentsId = (EditText) findViewById(R.id.commentsId);

        final RequestQueue queue = Volley.newRequestQueue(this);

        oPostAdapter = new PostAdapter(this);
        final PostHelper pHelper = new PostHelper(getApplicationContext());
        pHelper.open();
        postArray = pHelper.getAllPosts();
        pHelper.close();
        oPostAdapter.addAll(postArray);

        oCommentsAdapter = new CommentsAdapter(this);
        final CommentsHelper cHelper = new CommentsHelper(getApplicationContext());
        cHelper.open();
        commentsArray = cHelper.getAllComments();
        cHelper.close();
        oCommentsAdapter.addAll(commentsArray);



//        final JsonObjectRequest jsonRequest = new JsonObjectRequest
//                (Request.Method.GET, postUrl, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        txtResponseView.setText(response.toString());
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        txtResponseView.setText(error.toString());
//                    }
//                });
//
//        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
//                (Request.Method.GET, postUrl, null, new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        for (int i = 0; i < response.length(); i++) {
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        txtResponseView.setText(error.toString());
//                    }
//
//                });



        addCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtCommentsId.getText().toString();
                if(id.isEmpty()){
                    commentsResponseView.setText("Must select an ID");
                }
                else{
                    String commentsUrl ="https://jsonplaceholder.typicode.com/comments/" + id;
                    final StringRequest commentsStringRequest = new StringRequest(Request.Method.GET, commentsUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String comments_postId = jsonObject.getString("postId");
                                        String commentsId = jsonObject.getString("id");
                                        String commentsName = jsonObject.getString("name");
                                        String commentsEmail = jsonObject.getString("email");
                                        String commentsBody = jsonObject.getString("body");

                                        cHelper.open();
                                        Comments oComments = cHelper.addComments(comments_postId, commentsId, commentsName,commentsEmail,commentsBody);
                                        cHelper.close();
                                        commentsArray.add(oComments);
                                        oCommentsAdapter.add(oComments);
                                        oCommentsAdapter.notifyDataSetChanged();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    commentsResponseView.setText(response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            commentsResponseView.setText(error.toString());
                        }
                    });
                queue.add(commentsStringRequest);
                }

            }
        });
        viewCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentsActivity.class);

                intent.putExtra("Array", commentsArray);
                startActivity(intent);
            }
        });

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtPostId.getText().toString();
                if(id.isEmpty()){
                    postResponseView.setText("Must select an ID");
                }
                else{
                    String postUrl ="https://jsonplaceholder.typicode.com/posts/"+id;
                    final StringRequest postStringRequest = new StringRequest(Request.Method.GET, postUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String userId = jsonObject.getString("userId");
                                        String id = jsonObject.getString("id");
                                        String title = jsonObject.getString("title");
                                        String body = jsonObject.getString("body");

                                        pHelper.open();
                                        Posts oPosts = pHelper.addPosts(userId, id, title,body);
                                        pHelper.close();
                                        postArray.add(oPosts);
                                        oPostAdapter.add(oPosts);
                                        oPostAdapter.notifyDataSetChanged();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    postResponseView.setText(response);
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            postResponseView.setText(error.toString());
                        }
                    });
                    queue.add(postStringRequest);
                }


            }
            });


        viewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);

                intent.putExtra("Array", postArray);
                startActivity(intent);
            }
        });



    }
}