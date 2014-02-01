package com.bluecast.interfaces;

import java.util.ArrayList;

import com.bluecast.models.Person;

public interface MainFragmentDelegate {
    void shouldStartBeaconScan(int scanTime,String currentWorkingFragment);
    void setPersonArrayList(ArrayList<Person> personArrayList);
}
