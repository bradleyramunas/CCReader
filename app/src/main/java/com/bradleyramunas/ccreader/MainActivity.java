package com.bradleyramunas.ccreader;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bradleyramunas.ccreader.Adapters.AdapterInterface;
import com.bradleyramunas.ccreader.Adapters.CommentAdapter;
import com.bradleyramunas.ccreader.Adapters.ForumAdapter;
import com.bradleyramunas.ccreader.Types.Comment;
import com.bradleyramunas.ccreader.Types.Page;
import com.bradleyramunas.ccreader.Types.URL;
import com.bradleyramunas.ccreader.WebScrape.GetBoards;
import com.bradleyramunas.ccreader.WebScrape.GetComments;
import com.bradleyramunas.ccreader.WebScrape.ViewForum;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView contentViewer;
    private Stack<BaseAdapter> backStack;
    private boolean darkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        darkMode = false;
        backStack = new Stack<>();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        contentViewer = (ListView) findViewById(R.id.contentViewer);
        new GetBoards(this).execute();
    }

    public void updateActivityBackgroundColor(){
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity);
        if(darkMode){
            coordinatorLayout.setBackgroundColor(Color.BLACK);
        }else{
            coordinatorLayout.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            darkMode = !darkMode;
            backStack.clear();
            deleteAdapter();
            updateActivityBackgroundColor();
            new GetBoards(this).execute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!backStack.isEmpty()){
            AdapterInterface adapter = (AdapterInterface) backStack.pop();
            contentViewer.setAdapter((BaseAdapter) adapter);
            contentViewer.setSelection(adapter.getScroll());
        }else{
            super.onBackPressed();
        }

    }

    public void startProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        contentViewer.setVisibility(View.GONE);
    }

    public void stopProgressBar(){
        progressBar.setVisibility(View.GONE);
        contentViewer.setVisibility(View.VISIBLE);
    }


    public void changeAdapter(ArrayList<Page> pages){
        if(contentViewer.getAdapter() != null){
            ((AdapterInterface) contentViewer.getAdapter()).setScroll(contentViewer.getSelectedItemPosition());
            backStack.add((BaseAdapter)contentViewer.getAdapter());

        }
        contentViewer.setAdapter(new ForumAdapter(this, this, pages, darkMode));
    }

    public void changeAdapterC(ArrayList<Comment> comments){
        if(contentViewer.getAdapter() != null){
            ((AdapterInterface) contentViewer.getAdapter()).setScroll(contentViewer.getSelectedItemPosition());
            backStack.add((BaseAdapter)contentViewer.getAdapter());
        }
        contentViewer.setAdapter(new CommentAdapter(this, this, comments, darkMode));
    }

    public void deleteAdapter(){
        contentViewer.setAdapter(null);
    }

    public void viewBoard(URL url){
        new ViewForum(this).execute(url);
    }

    public void viewThread(URL url){
        new GetComments(this).execute(url);
    }

    public void errorToast(){
        Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
    }

}
