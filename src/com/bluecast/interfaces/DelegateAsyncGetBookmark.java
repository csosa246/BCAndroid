package com.bluecast.interfaces;

import java.util.ArrayList;

import com.bluecast.models.ModelPerson;


public interface DelegateAsyncGetBookmark {
    void didFinishAddingBookmarks(ArrayList<ModelPerson> personArrayList);
}
