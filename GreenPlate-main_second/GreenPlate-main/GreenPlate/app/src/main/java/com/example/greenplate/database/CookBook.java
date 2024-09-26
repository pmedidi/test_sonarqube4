package com.example.greenplate.database;

import java.util.ArrayList;

public class CookBook {


    // Cookbook Info:

    private ArrayList<Recipe> globalRecipeList;

    private static CookBook instance;

    private CookBook() {
        globalRecipeList = new ArrayList<Recipe>();
    }

    public static CookBook getInstance() {
        if (instance == null) {
            instance = new CookBook();
        }
        return instance;
    }

    public static void resetCookBook() {
        instance = null;
    }


    public ArrayList<Recipe> getGlobalRecipeList() {
        return globalRecipeList;
    }


    public void addRecipe(Recipe newRecipe) {
        globalRecipeList.add(newRecipe);
    }

    // This function reads from the database and uses a for loop to initialize all recipes
    public void copyGlobalRecipesToPhoneCookBook() {
        // NOTE THAT THIS FUNTION SHOULD ACTUALLY EXIST IN LOGIN
        // ACTIVITY SINCE ALL READS SHOULD BE DONE IN LOGIN
    }

    public void sortByName() {

    }

    public void sortByCalorie() {

    }


    public void printGlobalRecipeList() {
        for (int i = 0; i < globalRecipeList.size(); i++) {
            System.out.println("Recipe Number " + i + ": " + globalRecipeList.get(i).getName());
            System.out.println("Required Ingredients: " + globalRecipeList.get(i).getIngredients());
        }


    }

}
