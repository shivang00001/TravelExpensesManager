package com.trip.colleaguesexpmanager.datasources.listeners;

public interface WebOperationListener<Data, Error> {
    public void onReady(Data data);
    public void onError(Error error);
}