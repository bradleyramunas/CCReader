package com.bradleyramunas.ccreader.Types;

/**
 * Created by Bradley on 2/9/2017.
 */

public class URL {

    private String url;
    public final static String MAIN_FORUM_URL = "http://talk.collegeconfidential.com/";

    public URL(String url) {
        this.url = url;
    }

    public static URL createURLFromRelativeURL(String url){
        return new URL("http://talk.collegeconfidential.com" + url);
    }


    @Override
    public String toString() {
        return url;
    }
}