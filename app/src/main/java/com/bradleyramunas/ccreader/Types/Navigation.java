package com.bradleyramunas.ccreader.Types;


import java.util.ArrayList;

/**
 * Created by Bradley on 2/20/2017.
 */

public class Navigation extends Comment implements Page{
    private ArrayList<URL> urls;

    public Navigation(ArrayList<URL> urls) {
        this.urls = urls;
    }

    public ArrayList<URL> getUrls() {
        return urls;
    }

}
