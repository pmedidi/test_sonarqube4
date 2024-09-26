package com.example.greenplate.database;

public class Meal {
    private String name;
    private int calorieCount;

    public Meal(String name, int calorieCount) {
        this.name = name;
        this.calorieCount = calorieCount;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calorieCount;
    }
    public String toString() {
        return name + ": " + calorieCount;
    }
}
