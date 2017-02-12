package com.bradleyramunas.ccreader.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bradleyramunas.ccreader.MainActivity;
import com.bradleyramunas.ccreader.R;
import com.bradleyramunas.ccreader.Types.Forum;
import com.bradleyramunas.ccreader.Types.Page;
import com.bradleyramunas.ccreader.Types.Post;
import com.bradleyramunas.ccreader.WebScrape.ViewForum;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Bradley on 2/9/2017.
 */

public class ForumAdapter extends BaseAdapter{

    private Context context;
    private WeakReference<Activity> activity;
    private LayoutInflater layoutInflater;
    private ArrayList<Page> contentHolder;

    public ForumAdapter(Context context, Activity activity, ArrayList<Page> contentHolder) {
        this.context = context;
        this.activity = new WeakReference<Activity>(activity);
        this.contentHolder = contentHolder;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                result = layoutInflater.inflate(R.layout.board_card_short, viewGroup, false);
                TextView textView = (TextView) result.findViewById(R.id.boardCardBoardNameText);
                textView.setText(forum.getBoardName());
            }else{
                result = layoutInflater.inflate(R.layout.board_card, viewGroup, false);
                TextView textView = (TextView) result.findViewById(R.id.boardCardBoardNameText);
                textView.setText(forum.getBoardName());
                TextView textView1 = (TextView) result.findViewById(R.id.boardCardBoardThreadCountText);
                textView1.setText(forum.formattedReplyView());
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
            result = layoutInflater.inflate(R.layout.post_card, viewGroup, false);
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
                    Toast.makeText(context, post.getURL().toString() + " WOW ", Toast.LENGTH_LONG).show();
                }
            });
        }else if(currentItem == null){
            result = layoutInflater.inflate(R.layout.board_card_short, viewGroup, false);
            TextView textView = (TextView) result.findViewById(R.id.boardCardBoardNameText);
            textView.setText("ERROR");
        }

        return result;
    }
}
