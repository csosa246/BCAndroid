package com.bluecast.interfaces;

import java.util.ArrayList;

import com.bluecast.models.Business;


public interface DelegateAsyncGetBusiness {
    void didFinishGettingBussiness(ArrayList<Business> businessArrayList);
}
