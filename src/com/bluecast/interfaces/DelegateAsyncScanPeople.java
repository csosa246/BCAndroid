package com.bluecast.interfaces;

import java.util.ArrayList;

import com.bluecast.models.ModelPerson;

public interface DelegateAsyncScanPeople {
    void didFinishIdentifyingBeacons(ArrayList<ModelPerson> response);
}
