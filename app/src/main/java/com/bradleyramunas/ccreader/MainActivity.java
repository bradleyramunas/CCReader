package com.bradleyramunas.ccreader;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bradleyramunas.ccreader.Adapters.ForumAdapter;
import com.bradleyramunas.ccreader.Types.Page;
import com.bradleyramunas.ccreader.Types.URL;
import com.bradleyramunas.ccreader.WebScrape.GetBoards;
import com.bradleyramunas.ccreader.WebScrape.ViewForum;

import java.util.ArrayList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView contentViewer;
    private Stack<ForumAdapter> backStack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backStack = new Stack<>();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        contentViewer = (ListView) findViewById(R.id.contentViewer);
        new GetBoards(this).execute();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!backStack.isEmpty()){
            contentViewer.setAdapter(backStack.pop());
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

    public void changeAdapter(ForumAdapter forumAdapter){
        if(contentViewer.getAdapter() != null){
            backStack.add((ForumAdapter)contentViewer.getAdapter());
        }
        contentViewer.setAdapter(forumAdapter);
    }

    public void changeAdapter(ArrayList<Page> pages){
        if(contentViewer.getAdapter() != null){
            backStack.add((ForumAdapter)contentViewer.getAdapter());
        }
        contentViewer.setAdapter(new ForumAdapter(this, this, pages));
    }

    public void deleteAdapter(){
        contentViewer.setAdapter(null);
    }

    public void viewBoard(URL url){
        new ViewForum(this).execute(url);
    }

    public void errorToast(){
        Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
    }

}
