package com.androidapp.fidel.apiclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidapp.fidel.apiclient.Utils.CommentsHelper;
import com.androidapp.fidel.apiclient.Utils.PostHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView postResponseView;
    TextView commentsResponseView;
    ArrayList<Comments> commentsArray = new ArrayList<>();
    ArrayList<Posts> postArray = new ArrayList<>();
    CommentsAdapter oCommentsAdapter;
    PostAdapter oPostAdapter;
    String postId;

    final String commentsUrl ="https://jsonplaceholder.typicode.com/comments";
    final String postUrl ="https://jsonplaceholder.typicode.com/posts";

//    final PostHelper pHelper = new PostHelper(getApplicationContext());
//    final CommentsHelper cHelper = new CommentsHelper(getApplicationContext());

    PostHelper pHelper;
    CommentsHelper cHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pHelper = new PostHelper(getApplicationContext());
        cHelper = new CommentsHelper(getApplicationContext());
        setContentView(R.layout.activity_main);
        postResponseView = (TextView) findViewById(R.id.postTxtView);
        commentsResponseView = (TextView) findViewById(R.id.commentsTxtView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Button addPostButton = (Button) findViewById(R.id.addPostButton);
        Button viewPostButton = (Button) findViewById(R.id.viewPostButton);
        Button addCommentsButton = (Button) findViewById(R.id.commentsButton);
        Button viewCommentsButton = (Button) findViewById(R.id.viewCommentsButton);
        Button expandable_button = (Button) findViewById(R.id.expandableButton);
        Button postAsyncTask_button = (Button) findViewById(R.id.postAsyncTaskButton);
        Button commentAsyncTask_button = (Button) findViewById(R.id.commentsAsyncTaskButton);

        final RequestQueue queue = Volley.newRequestQueue(this);

        oPostAdapter = new PostAdapter(this);
        pHelper.open();
        postArray = pHelper.getAllPosts();
        pHelper.close();
        oPostAdapter.addAll(postArray);

        oCommentsAdapter = new CommentsAdapter(this);
        cHelper.open();
        commentsArray = cHelper.getAllComments();
        cHelper.close();
        oCommentsAdapter.addAll(commentsArray);

        final JsonArrayRequest postsArrayRequest = new JsonArrayRequest(Request.Method.GET, postUrl,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String result = "";
                Posts post = new Posts();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.getString(i));
                        String userId = jsonObject.getString("userId");
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        String body = jsonObject.getString("body");
                        pHelper.open();
                        post = pHelper.addPosts(id, userId, title, body);
                        pHelper.close();
                        postArray.add(post);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                commentsResponseView.setText(error.toString());
            }
        });

        final JsonArrayRequest commentsArrayRequest = new JsonArrayRequest(Request.Method.GET, commentsUrl,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String result = "";
                commentsArray = new ArrayList<Comments>();
                Comments comment = new Comments();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.getString(i));
                        String postId = jsonObject.getString("postId");
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String body = jsonObject.getString("body");
                        cHelper.open();
                        comment = cHelper.addComments(postId,id,name,email,body);
                        cHelper.close();
                        commentsArray.add(comment);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                commentsResponseView.setText(error.toString());
            }
        });

        addCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cHelper.open();
                cHelper.deleteComments();
                cHelper.close();
                queue.add(commentsArrayRequest);
                commentsResponseView.setText("Comments added succesfully! Click on the VIEW button to view the results");
            }
        });

        postAsyncTask_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpAsyncTask().execute("http://jsonplaceholder.typicode.com/posts/15");
            }
        });

        commentAsyncTask_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpAsyncTask().execute("http://jsonplaceholder.typicode.com/comments/15");
            }
        });

        addCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cHelper.open();
                cHelper.deleteComments();
                cHelper.close();
                queue.add(commentsArrayRequest);
                commentsResponseView.setText("Comments added succesfully! Click on the VIEW button to view the results");


            }
        });
        viewCommentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentsActivity.class);
                intent.putExtra("Array", commentsArray);
                startActivity(intent);
                commentsResponseView.setText("");
            }
        });

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pHelper.open();
                pHelper.deletePosts();
                pHelper.close();
                queue.add(postsArrayRequest);
                postResponseView.setText("Posts added succesfully! Click on the VIEW button to view the results");
            }
        });

        viewPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);

                intent.putExtra("Array", postArray);
                startActivity(intent);
                postResponseView.setText("");
            }
        });

                expandable_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ExpandableListActivity.class);

                intent.putExtra("PostArray", postArray);
                intent.putExtra("CommentArray", commentsArray);
                startActivity(intent);
            }
        });
    }

    public static String getHTTPRequest(String url) {

        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            } else {
                return "POST request did not work.";
            }
        } catch (IOException e) {
            return e.getMessage();
        }

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... urls) {
            return getHTTPRequest(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Received!" + result, Toast.LENGTH_LONG).show();
        }
    }
}
