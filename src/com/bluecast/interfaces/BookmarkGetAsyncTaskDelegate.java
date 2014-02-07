package com.bluecast.interfaces;

import java.util.ArrayList;

import com.bluecast.models.Person;


public interface BookmarkGetAsyncTaskDelegate {
    void didFinishAddingBookmarks(ArrayList<Person> personArrayList);
}
