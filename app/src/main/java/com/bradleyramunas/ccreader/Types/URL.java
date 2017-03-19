package com.bradleyramunas.ccreader.Types;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bradley on 2/9/2017.
 */

public class URL implements Parcelable {

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



    protected URL(Parcel in) {
        url = in.readString();
        text = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(text);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<URL> CREATOR = new Parcelable.Creator<URL>() {
        @Override
        public URL createFromParcel(Parcel in) {
            return new URL(in);
        }

        @Override
        public URL[] newArray(int size) {
            return new URL[size];
        }
    };
}