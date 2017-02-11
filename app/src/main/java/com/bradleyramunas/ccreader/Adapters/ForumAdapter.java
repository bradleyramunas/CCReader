package com.bradleyramunas.ccreader.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bradleyramunas.ccreader.Types.Page;

import java.util.ArrayList;

/**
 * Created by Bradley on 2/9/2017.
 */

public class ForumAdapter extends BaseAdapter{

    private ArrayList<Page> contentHolder;

    public ForumAdapter(ArrayList<Page> contentHolder) {
        this.contentHolder = contentHolder;
    }

    @Override
    public int getCount() {
        return contentHolder.size();
    }

    @Override
    public Object getItem(int i) {
        return contentHolder.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {



        return null;
    }
}
