package com.bradleyramunas.ccreader.Types;

/**
 * Created by Bradley on 2/9/2017.
 */

public class Post implements Page {
    private URL url;
    private boolean isPinned;
    private boolean isLocked;
    private boolean isFeatured;
    private String postName;
    private String replyCount;
    private String viewCount;
    private String startedBy;
    private String startedByDate;

    public Post(URL url, boolean isPinned, boolean isLocked, boolean isFeatured, String postName, String replyCount, String viewCount, String startedBy, String startedByDate) {
        this.url = url;
        this.isPinned = isPinned;
        this.isLocked = isLocked;
        this.isFeatured = isFeatured;
        this.postName = postName;
        this.replyCount = replyCount;
        this.viewCount = viewCount;
        this.startedBy = startedBy;
        this.startedByDate = startedByDate;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public String getPostName() {
        return postName;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public String getViewCount() {
        return viewCount;
    }

    public String getStartedBy() {
        return startedBy;
    }

    public String getStartedByDate() {
        return startedByDate;
    }

    @Override
    public URL getURL() {
        return url;
    }
}
