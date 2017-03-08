package com.bradleyramunas.ccreader.Types;

/**
 * Created by Bradley on 2/9/2017.
 */

public class URL {

    private String url;
    private String text;
    public final static String MAIN_FORUM_URL = "http://talk.collegeconfidential.com/";
    private boolean selected = false;

    public URL(String url) {
        this.url = url;
    }

    public URL(String url, String text, boolean selected) {
        this.url = url;
        this.text = text;
        this.selected = selected;
    }

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return url;
    }

    public boolean isSelected() {
        return selected;
    }


}
