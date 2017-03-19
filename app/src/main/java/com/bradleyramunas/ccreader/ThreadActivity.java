package com.bradleyramunas.ccreader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bradleyramunas.ccreader.Adapters.CommentAdapter;
import com.bradleyramunas.ccreader.Types.Comment;
import com.bradleyramunas.ccreader.Types.URL;
import com.bradleyramunas.ccreader.WebScrape.GetComments;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import java.util.ArrayList;

public class ThreadActivity extends SwipeBackActivity{

    private boolean darkMode;
    private URL url;
    private ListView contentViewer;
    private ProgressBar progressBar;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        darkMode = (boolean) i.getBooleanExtra("darkMode", false);
        url =  (URL) i.getParcelableExtra("URL");
        title = i.getStringExtra("Title");

        toolbar.setTitle(title);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        contentViewer = (ListView) findViewById(R.id.contentViewer);
        if(darkMode){
            coordinatorLayout.setBackgroundColor(Color.BLACK);
        }else{
            coordinatorLayout.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
        new GetComments(this).execute(url);
    }

    public void viewThread(URL url){
        new GetComments(this).execute(url);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void startProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        contentViewer.setVisibility(View.GONE);
    }

    public void stopProgressBar(){
        progressBar.setVisibility(View.GONE);
        contentViewer.setVisibility(View.VISIBLE);
    }

    public void changeAdapterC(ArrayList<Comment> comments){
        contentViewer.setAdapter(new CommentAdapter(this, this, comments, darkMode));
    }
}
