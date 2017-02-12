package com.bradleyramunas.ccreader.Types;


/**
 * Created by Bradley on 2/9/2017.
 */

public class Forum implements Page {
    private URL url;
    private String boardName;
    private boolean hasSubTreeCounts;
    private String threadCount;
    private String replyCount;

    public Forum(URL url, String boardName) {
        this.url = url;
        this.boardName = boardName;
        this.hasSubTreeCounts = false;
        threadCount = "";
        replyCount = "";
    }

    public Forum(URL url, String boardName, String threadCount, String replyCount) {
        this.url = url;
        this.boardName = boardName;
        this.threadCount = threadCount;
        this.replyCount = replyCount;
        this.hasSubTreeCounts = true;
    }

    public static Forum createForumFromRelativeURL(String url, String boardName){
        return new Forum(URL.createURLFromUnspecifiedSource(url), boardName);
    }

    public static Forum createForumFromRelativeURL(String url, String boardName, String threadCount, String replyCount){
        return new Forum(URL.createURLFromUnspecifiedSource(url), boardName, threadCount, replyCount);
    }

    public boolean isShortForum(){
        return hasSubTreeCounts;
    }

    public String getBoardName() {
        return boardName;
    }

    public boolean isHasSubTreeCounts() {
        return hasSubTreeCounts;
    }

    public String getThreadCount() {
        return threadCount;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public String formattedReplyView(){
        return threadCount + " threads | " + replyCount + " replies";
    }

    @Override
    public URL getURL() {
        return url;
    }
}
