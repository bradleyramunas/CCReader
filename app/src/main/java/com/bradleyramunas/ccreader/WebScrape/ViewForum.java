package com.bradleyramunas.ccreader.WebScrape;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bradleyramunas.ccreader.Adapters.ForumAdapter;
import com.bradleyramunas.ccreader.MainActivity;
import com.bradleyramunas.ccreader.Types.Forum;
import com.bradleyramunas.ccreader.Types.Page;
import com.bradleyramunas.ccreader.Types.Post;
import com.bradleyramunas.ccreader.Types.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Bradley on 2/9/2017.
 */

public class ViewForum extends AsyncTask<URL, Void, ArrayList<Page>> {

    private WeakReference<Activity> weakReference;

    public ViewForum(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
    }

    @Override
    protected void onCancelled() {
        MainActivity mainActivity = (MainActivity) weakReference.get();
        mainActivity.stopProgressBar();
        mainActivity.errorToast();
        super.onCancelled();
    }

    @Override
    protected void onPreExecute() {
        MainActivity mainActivity = (MainActivity) weakReference.get();
        mainActivity.startProgressBar();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Page> pages) {
        MainActivity mainActivity = (MainActivity) weakReference.get();
        mainActivity.stopProgressBar();
        mainActivity.changeAdapter(pages);
        super.onPostExecute(pages);
    }

    public static Page generatePageFromElement(Element e){
        try{
            boolean isPinned = false;
            boolean isLocked = false;
            boolean isFeatured = false;

            Element pinned = e.select(".Tag-Announcement").first();
            if(pinned != null){
                isPinned = true;
            }
            Element locked = e.select(".Tag-Closed").first();
            if(locked != null){
                isLocked = true;
            }
            Element featured = e.select(".Tag-Featured").first();
            if(featured != null){
                isFeatured = true;
            }

            Element a = e.select(".Wrap").first().select("a").first();

            String postName = a.text();
            URL url = URL.createURLFromUnspecifiedSource(a.attr("href"));

            Element userLink = e.select(".FirstUser").first().select(".UserLink").first();

            String startedBy = userLink.text();

            Element postDate = e.select(".FirstUser").first().select("time").first();

            String startedOn = postDate.text();

            Element countComments = e.select(".CountComments").first().select("span").first();

            String commentCount = countComments.text();

            Element countViews = e.select(".CountViews").first().select("span").first();

            String viewCount = countViews.text();

            return new Post(url, isPinned, isLocked, isFeatured, postName, commentCount, viewCount, startedBy, startedOn);
        } catch (Exception x){
            return null;
        }
    }

    @Override
    protected ArrayList<Page> doInBackground(URL... url) {
        ArrayList<Page> returner = new ArrayList<>();
        try {
            Log.i("Connecting to...", url[0].toString() + "");
            Document document = Jsoup.connect(url[0].toString()).userAgent("Chrome").get();
            Element subForumHolder = document.select("#SubTopics").first();
            if(subForumHolder != null){
                Elements subForums = subForumHolder.select("li");
                for(Element e : subForums){
                    String forumURL = e.select("a").attr("abs:href");
                    String forumName = e.select("a").html().replaceAll("&amp;", "&");;
                    Element spans = e.select(".Subtree-Counts").first();
                    String threadCount = spans.child(0).text();
                    String replyCount = spans.child(1).text();
                    returner.add(new Forum(new URL(forumURL), forumName, threadCount, replyCount));
                }
            }
            Element discussionTable = document.select(".DiscussionsTable").get(1);
            if(discussionTable != null){
                Elements discussions = discussionTable.select("tr");
                for(Element x : discussions){
                    returner.add(generatePageFromElement(x));
                }
            }


        } catch (Exception e){
            cancel(true);
        }
        return returner;
    }
}
