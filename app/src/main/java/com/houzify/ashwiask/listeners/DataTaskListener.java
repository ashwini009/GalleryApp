package com.houzify.ashwiask.listeners;

import java.util.ArrayList;

/**
 * Created by ashwiask on 8/18/2015.
 * <p/>
 * DataTaskListener that listens when the data task processing has been completed
 */
public interface DataTaskListener {

    void onDataTaskComplete(ArrayList<String> imageList);
}
