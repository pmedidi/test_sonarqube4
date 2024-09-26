package com.example.greenplate.database;

import java.util.ArrayList;

public class User {

    private static User user;
    private String firstName;
    private String lastName;

    private String username;
    private String email;
    private String password;
    private double height;
    private double weight;
    private String gender;

    private ArrayList<Integer> monthlyCalories;

    private int dailyCalories;
    private double calorieGoal;

    //private int totalCalories;

    //private int[] calorieCalendar;
    private ArrayList<ArrayList<Meal>> mealCalendar;

    private User() {
        mealCalendar = new ArrayList<ArrayList<Meal>>(30);
        monthlyCalories = new ArrayList<Integer>(30);
        for (int i = 0; i < 30; i++) {
            mealCalendar.add(new ArrayList<Meal>());
        }
        for (int i = 0; i < 30; i++) {
            monthlyCalories.add(0);
        }
        //monthlyCalories = new ArrayList<Integer>(30);
    }

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public static void resetInstance() {
        user = null;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<ArrayList<Meal>> getMealCalendar() {
        return mealCalendar;
    }

    public void addMealToday(Meal meal) {
        mealCalendar.get(29).add(meal);
        addCaloriesToday(meal.getCalories(), 29);
        // also add a check so that if it is the first day, we write into the database
    }

    //public void initializeAddMeal(Meal meal)
    public ArrayList<Integer> getMonthlyCalories() {
        return monthlyCalories;
    }

    public void addCaloriesToday(int calories, int day) {
        monthlyCalories.set(day, calories + monthlyCalories.get(day));
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDailyCalories() {
        return dailyCalories;
    }

    public void setDailyCalories(int dailyCalories) {
        this.dailyCalories = dailyCalories;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(double calorieGoal) {
        this.calorieGoal = calorieGoal;
    }
}
