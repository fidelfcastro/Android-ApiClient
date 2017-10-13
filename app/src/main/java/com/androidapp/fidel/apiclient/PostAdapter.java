package com.androidapp.fidel.apiclient;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by fidel on 10/12/2017.
 */

public class PostAdapter extends ArrayAdapter<Posts> {
    public PostAdapter(Context context) {
        super(context, R.layout.posts_layout,  R.id.txtPost_userId);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View objectView=super.getView(position,convertView,parent);
        TextView txtPostUserId =(TextView) objectView.findViewById(R.id.txtPost_userId);
        TextView txtPostId=(TextView) objectView.findViewById(R.id.txtPostId);
        TextView txtPostTitle = (TextView) objectView.findViewById(R.id.txtPostTitle);
        TextView txtPostBody = (TextView) objectView.findViewById(R.id.txtPostBody);

        Posts oPost = this.getItem(position);

        txtPostUserId.setText("User Id:   " + oPost.getUserId());
        txtPostId.setText("Id:   " + oPost.getId());
        txtPostTitle.setText("Title:   " + oPost.getTitle());
        txtPostBody.setText("Body:   " + oPost.getBody());
        return objectView;
    }
}
