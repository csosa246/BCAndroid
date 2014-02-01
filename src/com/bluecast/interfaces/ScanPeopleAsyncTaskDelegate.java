package com.bluecast.interfaces;

import java.util.ArrayList;

import com.bluecast.models.Person;

public interface ScanPeopleAsyncTaskDelegate {
    void didFinishIdentifyingBeacons(ArrayList<Person> response);
}
