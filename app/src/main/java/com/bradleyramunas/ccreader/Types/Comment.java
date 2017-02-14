package com.bradleyramunas.ccreader.Types;

/**
 * Created by Bradley on 2/13/2017.
 */

public class Comment {
    private URL imageURL;
    private String posterName;
    private String posterRole;
    private String posterPostCount;
    private String posterMemberType;
    private String replyDate;
    private String commentText;

    public Comment(URL imageURL, String posterName, String posterRole, String posterPostCount, String posterMemberType, String replyDate, String commentText) {
        this.imageURL = imageURL;
        this.posterName = posterName;
        this.posterRole = posterRole;
        this.posterPostCount = posterPostCount;
        this.posterMemberType = posterMemberType;
        this.replyDate = replyDate;
        this.commentText = commentText;
    }

    public URL getImageURL() {
        return imageURL;
    }

    public String getPosterName() {
        return posterName;
    }

    public String getPosterRole() {
        return posterRole;
    }

    public String getPosterPostCount() {
        return posterPostCount;
    }

    public String getPosterMemberType() {
        return posterMemberType;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public String getCommentText() {
        return commentText;
    }
}
