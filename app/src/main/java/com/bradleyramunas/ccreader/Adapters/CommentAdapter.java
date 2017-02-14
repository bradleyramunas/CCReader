package com.bradleyramunas.ccreader.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bradleyramunas.ccreader.R;
import com.bradleyramunas.ccreader.Types.Comment;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Bradley on 2/13/2017.
 */

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private WeakReference<Activity> activity;
    private ArrayList<Comment> comments;
    private LayoutInflater layoutInflater;

    public CommentAdapter(Context context, Activity activity, ArrayList<Comment> comments) {
        this.context = context;
        this.activity = new WeakReference<Activity>(activity);
        this.comments = comments;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Comment currentComment = (Comment) getItem(i);
        View returner = layoutInflater.inflate(R.layout.comment_card, viewGroup, false);
        ImageView imageView = (ImageView) returner.findViewById(R.id.comment_card_image);
        Picasso.with(context).load(currentComment.getImageURL().toString()).into(imageView);

        TextView posterName = (TextView) returner.findViewById(R.id.comment_card_poster_name);
        posterName.setText(currentComment.getPosterName());

        TextView posterRole = (TextView) returner.findViewById(R.id.comment_card_poster_role);
        posterRole.setText(currentComment.getPosterRole());

        TextView posterPostCount = (TextView) returner.findViewById(R.id.comment_card_poster_post_count);
        posterPostCount.setText(currentComment.getPosterPostCount());

        TextView posterMemberType = (TextView) returner.findViewById(R.id.comment_card_poster_member_type);
        posterMemberType.setText(currentComment.getPosterMemberType());

        TextView replyDate = (TextView) returner.findViewById(R.id.comment_card_reply_date);
        replyDate.setText(currentComment.getReplyDate());

        TextView commentText = (TextView) returner.findViewById(R.id.comment_card_comment_text);
        commentText.setText(currentComment.getCommentText());

        return returner;
    }
}
