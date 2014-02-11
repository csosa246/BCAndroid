package com.bluecast.interfaces;

import java.util.ArrayList;

import com.bluecast.models.ModelBusiness;


public interface DelegateAsyncGetBusiness {
    void didFinishGettingBussiness(ArrayList<ModelBusiness> businessArrayList);
}
