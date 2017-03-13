package com.bradleyramunas.ccreader.Adapters;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bradleyramunas.ccreader.MainActivity;
import com.bradleyramunas.ccreader.R;
import com.bradleyramunas.ccreader.Types.Forum;
import com.bradleyramunas.ccreader.Types.Navigation;
import com.bradleyramunas.ccreader.Types.Page;
import com.bradleyramunas.ccreader.Types.Post;
import com.bradleyramunas.ccreader.Types.URL;
import com.bradleyramunas.ccreader.WebScrape.ViewForum;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Bradley on 2/9/2017.
 */

public class ForumAdapter extends BaseAdapter implements AdapterInterface{

    private Context context;
    private WeakReference<Activity> activity;
    private LayoutInflater layoutInflater;
    private ArrayList<Page> contentHolder;
    private int scroll;
    private boolean darkMode;

    public ForumAdapter(Context context, Activity activity, ArrayList<Page> contentHolder, boolean darkMode) {
        this.context = context;
        this.activity = new WeakReference<Activity>(activity);
        this.contentHolder = contentHolder;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.darkMode = darkMode;
    }

    public int getScroll() {
        return scroll;
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
    }

    public ArrayList<Page> getContentHolder(){
        return contentHolder;
    }

    @Override
    public int getCount() {
        return contentHolder.size();
    }

    @Override
    public Object getItem(int i) {
        return contentHolder.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Page currentItem = (Page) getItem(i);
        View result = null;
        if(currentItem instanceof Forum){
            final Forum forum = (Forum) currentItem;
            if(forum.isShortForum()){
                if(darkMode){
                    result = layoutInflater.inflate(R.layout.board_card_short_night, viewGroup, false);
                }else{
                    result = layoutInflater.inflate(R.layout.board_card_short, viewGroup, false);
                }

                TextView textView = (TextView) result.findViewById(R.id.boardCardBoardNameText);
                textView.setText(forum.getBoardName());
            }else{
                if(darkMode){
                    result = layoutInflater.inflate(R.layout.board_card_night, viewGroup, false);
                }else{
                    result = layoutInflater.inflate(R.layout.board_card, viewGroup, false);
                }

                TextView textView = (TextView) result.findViewById(R.id.boardCardBoardNameText);
                textView.setText(forum.getBoardName());
                TextView textView1 = (TextView) result.findViewById(R.id.boardCardBoardThreadCountText);
                textView1.setText(forum.formattedReplyView());
                ImageView imageView = (ImageView) result.findViewById(R.id.boardCardBoardImage);
                Picasso.with(context).load("https://us.v-cdn.net/5020364/uploads/defaultavatar/28AAP7EH8UX2.jpg").into(imageView);
            }
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity mainActivity = (MainActivity) activity.get();
                    mainActivity.viewBoard(forum.getURL());
                }
            });
        }else if(currentItem instanceof Post){
            final Post post = (Post) currentItem;
            if(darkMode){
                result = layoutInflater.inflate(R.layout.post_card_night, viewGroup, false);
            }else{
                result = layoutInflater.inflate(R.layout.post_card, viewGroup, false);
            }

            ImageView imageView1 = (ImageView) result.findViewById(R.id.postCardPinned);
            ImageView imageView2 = (ImageView) result.findViewById(R.id.postCardLocked);
            ImageView imageView3 = (ImageView) result.findViewById(R.id.postCardFeatured);

            if(post.isPinned()){
                imageView1.setVisibility(View.VISIBLE);
            }

            if(post.isLocked()){
                imageView2.setVisibility(View.VISIBLE);
            }

            if(post.isFeatured()){
                imageView3.setVisibility(View.VISIBLE);
            }

            TextView textView1 = (TextView) result.findViewById(R.id.postCardThreadName);
            textView1.setText(post.getPostName());
            TextView textView2 = (TextView) result.findViewById(R.id.postCardAuthorText);
            textView2.setText(post.getStartedBy());
            TextView textView3 = (TextView) result.findViewById(R.id.postCardPostDate);
            textView3.setText(post.getStartedByDate());
            TextView textView4 = (TextView) result.findViewById(R.id.postCardReplyCount);
            textView4.setText(post.getReplyCount() + " replies");
            TextView textView5 = (TextView) result.findViewById(R.id.postCardViewCount);
            textView5.setText(post.getViewCount() + " views");
            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity mainActivity = (MainActivity) activity.get();
                    mainActivity.viewThread(post.getURL());
                    //Toast.makeText(context, post.getURL().toString() + " WOW ", Toast.LENGTH_LONG).show();
                }
            });
        }else if(currentItem instanceof Navigation){
            Navigation navigation = (Navigation) currentItem;
            if(darkMode){
                result = layoutInflater.inflate(R.layout.navigation_card_night, viewGroup, false);
            }else{
                result = layoutInflater.inflate(R.layout.navigation_card, viewGroup, false);
            }

            ArrayList<URL> urls = navigation.getUrls();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            LinearLayout container = (LinearLayout) result.findViewById(R.id.buttonContainer);
            for(final URL u : urls) {
                Button button = new Button(context);
                button.setLayoutParams(layoutParams);
                button.setText(u.getText());
                button.setTextSize(10);
                button.setBackground(null);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity mainActivity = (MainActivity) activity.get();
                        mainActivity.viewBoard(u);
                    }
                });
                container.addView(button);
            }

        }else if(currentItem == null){
            if(darkMode){
                result = layoutInflater.inflate(R.layout.board_card_short_night, viewGroup, false);
            }else{
                result = layoutInflater.inflate(R.layout.board_card_short, viewGroup, false);
            }

            TextView textView = (TextView) result.findViewById(R.id.boardCardBoardNameText);
            textView.setText("ERROR");
        }


        return result;
    }
}
