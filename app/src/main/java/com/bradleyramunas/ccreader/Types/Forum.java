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



    public boolean isShortForum(){
        return !hasSubTreeCounts;
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
        return threadCount + " | " + replyCount + "";
    }

    public URL getURL() {
        return url;
    }
}
