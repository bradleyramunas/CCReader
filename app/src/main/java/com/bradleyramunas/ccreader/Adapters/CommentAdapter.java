package com.bradleyramunas.ccreader.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bradleyramunas.ccreader.MainActivity;
import com.bradleyramunas.ccreader.R;
import com.bradleyramunas.ccreader.Types.Comment;
import com.bradleyramunas.ccreader.Types.Navigation;
import com.bradleyramunas.ccreader.Types.URL;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Bradley on 2/13/2017.
 */

public class CommentAdapter extends BaseAdapter implements AdapterInterface{

    private Context context;
    private WeakReference<Activity> activity;
    private ArrayList<Comment> comments;
    private LayoutInflater layoutInflater;
    private int scroll = 0;

    public CommentAdapter(Context context, Activity activity, ArrayList<Comment> comments) {
        this.context = context;
        this.activity = new WeakReference<Activity>(activity);
        this.comments = comments;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getScroll() {
        return scroll;
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
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
        Object currentItem = getItem(i);
        View returner = null;
        if(currentItem instanceof Navigation){
            Navigation navigation = (Navigation) currentItem;
            returner = layoutInflater.inflate(R.layout.navigation_card, viewGroup, false);
            ArrayList<URL> urls = navigation.getUrls();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            LinearLayout container = (LinearLayout) returner.findViewById(R.id.buttonContainer);
            for(final URL u : urls){
                Button button = new Button(context);
                button.setLayoutParams(layoutParams);
                button.setText(u.getText());
                button.setTextSize(8);
                button.setBackground(null);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity mainActivity = (MainActivity) activity.get();
                        mainActivity.viewThread(u);
                    }
                });
                container.addView(button);

            }
        }else{
            Comment currentComment = (Comment) currentItem;
            returner = layoutInflater.inflate(R.layout.comment_card, viewGroup, false);
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

        }
        return returner;
    }
}
