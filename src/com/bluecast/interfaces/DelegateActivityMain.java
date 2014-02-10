package com.bluecast.interfaces;

import java.util.ArrayList;

import com.bluecast.models.ModelPerson;

public interface DelegateActivityMain {
    void shouldStartBeaconScan(int scanTime,String currentWorkingFragment);
    void setPersonArrayList(ArrayList<ModelPerson> personArrayList);
    void shouldLoadPublicProfile(String URL);
}
