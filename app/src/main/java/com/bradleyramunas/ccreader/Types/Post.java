package com.bradleyramunas.ccreader.Types;

/**
 * Created by Bradley on 2/9/2017.
 */

public class Post implements Page {
    private URL url;

    @Override
    public URL getURL() {
        return url;
    }
}
