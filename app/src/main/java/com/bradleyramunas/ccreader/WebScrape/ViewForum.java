package com.bradleyramunas.ccreader.WebScrape;

import android.os.AsyncTask;

import com.bradleyramunas.ccreader.Adapters.ForumAdapter;
import com.bradleyramunas.ccreader.Types.Forum;
import com.bradleyramunas.ccreader.Types.Page;
import com.bradleyramunas.ccreader.Types.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by Bradley on 2/9/2017.
 */

public class ViewForum extends AsyncTask<URL, Void, ArrayList<Page>> {
    @Override
    protected ArrayList<Page> doInBackground(URL... url) {
        ArrayList<Page> returner = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url[0].toString()).userAgent("Chrome").get();
            Element subForumHolder = document.select("#SubTopics").first();
            if(subForumHolder != null){
                Elements subForums = subForumHolder.select("li");
                for(Element e : subForums){
                    String forumURL = e.select("a").attr("href");
                    String forumName = e.select("a").html().replaceAll("&amp;", "&");;
                    Element spans = e.select(".Subtree-Counts").first();
                    String threadCount = spans.child(0).text();
                    String replyCount = spans.child(1).text();
                    returner.add(Forum.createForumFromRelativeURL(forumURL, forumName, threadCount, replyCount));
                }
            }


        } catch (Exception e){
            cancel(true);
        }
        return returner;
    }
}
