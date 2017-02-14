package com.bradleyramunas.ccreader.WebScrape;

import android.app.Activity;
import android.os.AsyncTask;

import com.bradleyramunas.ccreader.MainActivity;
import com.bradleyramunas.ccreader.Types.Comment;
import com.bradleyramunas.ccreader.Types.Page;
import com.bradleyramunas.ccreader.Types.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Bradley on 2/13/2017.
 */

public class GetComments extends AsyncTask<URL, Void, ArrayList<Comment>> {

    private WeakReference<Activity> weakReference;

    public GetComments(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
    }

    @Override
    protected ArrayList<Comment> doInBackground(URL... urls) {
        ArrayList<Comment> returner = new ArrayList<>();
        try{
            Document document = Jsoup.connect(urls[0].toString()).userAgent("Chrome").get();
            Element authorDiv = document.select(".Discussion").first();
            if(authorDiv != null){
                String posterName = authorDiv.select("a.Username").first().text();
                String replyDate = authorDiv.select("time").first().text();
                URL posterImage = new URL(authorDiv.select("img").attr("abs:src"));
                String posterRole = authorDiv.select("span.RoleTitle").first().text();
                String posterPostCount = authorDiv.select("span.PostCount").first().text();
                String posterMemberType = authorDiv.select("span.Rank").first().text();
                String commentText = authorDiv.select("div.Message").first().text();
                returner.add(new Comment(posterImage, posterName, posterRole, posterPostCount, posterMemberType, replyDate, commentText));
            }

            Element commentList = document.select("ul.Comments").first();
            if(commentList != null){
                Elements comments = commentList.select("li");
                for(Element x : comments){
                    String posterName = x.select("a.Username").first().text();
                    String replyDate = x.select("time").first().text();
                    URL posterImage = new URL(x.select("img").attr("abs:src"));
                    String posterRole = x.select("span.RoleTitle").first().text();
                    String posterPostCount = x.select("span.PostCount").first().text();
                    String posterMemberType = x.select("span.Rank").first().text();
                    String commentText = x.select("div.Message").first().text();
                    returner.add(new Comment(posterImage, posterName, posterRole, posterPostCount, posterMemberType, replyDate, commentText));
                }
            }


        } catch (Exception e){
            cancel(true);
        }

        return returner;
    }


    @Override
    protected void onPreExecute() {
        MainActivity mainActivity = (MainActivity) weakReference.get();
        mainActivity.startProgressBar();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Comment> comments) {
        MainActivity mainActivity = (MainActivity) weakReference.get();
        mainActivity.stopProgressBar();
        mainActivity.changeAdapterC(comments);
        super.onPostExecute(comments);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
