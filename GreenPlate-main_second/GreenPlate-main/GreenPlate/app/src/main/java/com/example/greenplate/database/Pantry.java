package com.example.greenplate.database;

import com.example.greenplate.observer.Observer;
import com.example.greenplate.observer.Subject;
import java.util.ArrayList;

public class Pantry implements Subject {

    private static Pantry pantry;
    private ArrayList<Ingredient> pantryList;
    private ArrayList<Observer> observers = new ArrayList<>();

    private Pantry() {
        pantryList = new ArrayList<>();
    }

    public static Pantry getInstance() {
        if (pantry == null) {
            pantry = new Pantry();
        }
        return pantry;
    }

    public static void resetInstance() {
        pantry = null;
    }

    public ArrayList<Ingredient> getPantryList() {
        notifyObservers();
        return pantryList;
    }

    public Ingredient getIngredient(String name) {
        Ingredient ret = null;
        for (int i = 0; i < pantryList.size(); i++) {
            Ingredient currIngredient = pantryList.get(i);
            if (currIngredient.getName().equals(name)) {
                ret = currIngredient;
            }
        }
        return ret;
    }

    public int getIngredientIndex(String name) {
        for (int i = 0; i < pantryList.size(); i++) {
            if (pantryList.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public void addIngredient(Ingredient ingredient) {
        pantryList.add(ingredient);
        notifyObservers();
    }

    public void removeIngredient(String name) {
        for (int i = 0; i < pantryList.size(); i++) {
            Ingredient currIngredient = pantryList.get(i);
            if (currIngredient.getName().equals(name)) {
                pantryList.remove(currIngredient);
            }
        }
        notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public ArrayList<Observer> getObservers() {
        return observers;
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        for (int i = 0; i < observers.size(); i++) {
            if (observers.get(i).equals(observer)) {
                observers.remove(i);
                break;
            }
        }
    }
}
