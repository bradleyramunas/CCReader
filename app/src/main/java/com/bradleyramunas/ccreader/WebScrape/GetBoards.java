package com.bradleyramunas.ccreader.WebScrape;

import android.app.Activity;
import android.os.AsyncTask;

import com.bradleyramunas.ccreader.MainActivity;
import com.bradleyramunas.ccreader.Types.Forum;
import com.bradleyramunas.ccreader.Types.Page;
import com.bradleyramunas.ccreader.Types.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Bradley on 2/10/2017.
 */

public class GetBoards extends AsyncTask<Void, Void, ArrayList<Page>> {

    private WeakReference<Activity> weakReference;

    public GetBoards(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
    }

    @Override
    protected ArrayList<Page> doInBackground(Void... voids) {
        ArrayList<Page> returner = new ArrayList<>();
        try{
            Document document = Jsoup.connect(URL.MAIN_FORUM_URL).userAgent("Chrome").get();
            Element holder = document.select(".ContentColumn").first();
            //"#CategoryGroup-college-admissions-search"
            //"#CategoryGroup-professional-graduate-school"
            //"#CategoryGroup-pre-college-issues"
            //"#CategoryGroup-college-confidential-community"
            Elements firstGroup 	= holder.select("#CategoryGroup-college-admissions-search");
            Elements secondGroup 	= holder.select("#CategoryGroup-professional-graduate-school");
            Elements thirdGroup 	= holder.select("#CategoryGroup-pre-college-issues");
            Elements fourthGroup 	= holder.select("#CategoryGroup-college-confidential-community");

            ArrayList<Elements> elementHolder = new ArrayList<Elements>();
            elementHolder.add(firstGroup);
            elementHolder.add(secondGroup);
            elementHolder.add(thirdGroup);
            elementHolder.add(fourthGroup);

            for(Elements z : elementHolder){
                Element dataSet = z.select(".DataList").first();
                Elements linksInDataSet = dataSet.select(".CategoryName").select("a");
                for(Element e : linksInDataSet){
                    String relativeURL = e.attr("abs:href");
                    String boardName = e.html().replaceAll("&amp;", "&");
                    Element threadCount = e.select(".ThreadsCount").first();
                    Element replyCount = e.select(".RepliesCount").first();
                    if(threadCount != null && replyCount != null){
                        String threadCounts = threadCount.text();
                        String replyCounts = replyCount.text();
                        returner.add(new Forum(new URL(relativeURL), boardName, threadCounts, replyCounts));
                    }else{
                        returner.add(new Forum(new URL(relativeURL), boardName));
                    }
                }
            }


        }catch (Exception e){
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
    protected void onPostExecute(ArrayList<Page> pages) {
        MainActivity mainActivity = (MainActivity) weakReference.get();
        mainActivity.stopProgressBar();
        mainActivity.changeAdapter(pages);
        super.onPostExecute(pages);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
