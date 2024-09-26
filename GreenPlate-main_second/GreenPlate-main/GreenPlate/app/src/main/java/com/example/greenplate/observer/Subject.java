package com.example.greenplate.observer;

import java.util.ArrayList;

public interface Subject {
    public ArrayList<Observer> OBSERVERS = new ArrayList<>();

    public void addObserver(Observer observer);

    public void notifyObservers();

    public void removeObserver(Observer observer);
}
