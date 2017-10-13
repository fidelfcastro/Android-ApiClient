package com.androidapp.fidel.apiclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by fidel on 10/12/2017.
 */

public class CommentsAdapter extends ArrayAdapter<Comments> {
    public CommentsAdapter(Context context) {
        super(context, R.layout.comments_layout, R.id.txtComments_PostId);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View objectView=super.getView(position,convertView,parent);
        TextView txtPostId=(TextView) objectView.findViewById(R.id.txtComments_PostId);
        TextView txtId=(TextView) objectView.findViewById(R.id.txtCommentsId);
        TextView txtName = (TextView) objectView.findViewById(R.id.txtComments_Name);
        TextView txtEmail = (TextView) objectView.findViewById(R.id.txtComments_Email);
        TextView txtBody = (TextView) objectView.findViewById(R.id.txtComments_Body);

        Comments oComments = this.getItem(position);

        txtPostId.setText("Post Id:   " + oComments.getPostsId());
        txtId.setText("Id:   " + oComments.getId());
        txtName.setText("Name:   " + oComments.getName());
        txtEmail.setText("Email:   " + oComments.getEmail());
        txtBody.setText("Body:   " + oComments.getBody());
        return objectView;
    }
}
