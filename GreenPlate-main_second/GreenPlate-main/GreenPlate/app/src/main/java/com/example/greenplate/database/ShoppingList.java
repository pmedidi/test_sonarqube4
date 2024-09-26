package com.example.greenplate.database;

import java.util.ArrayList;

public class ShoppingList {

    private static ShoppingList shopping;
    private ArrayList<Ingredient> shoppingList;

    private ShoppingList() {
        shoppingList = new ArrayList<>();
    }

    public static ShoppingList getInstance() {
        if (shopping == null) {
            shopping = new ShoppingList();
        }
        return shopping;
    }

    public static void resetInstance() {
        shopping = null;
    }

    public ArrayList<Ingredient> getShoppingList() {
        return shoppingList;
    }

    // This function returns the ingredient if found
    public Ingredient getIngredient(String name) {
        Ingredient ret = null;
        for (int i = 0; i < shoppingList.size(); i++) {
            Ingredient currIngredient = shoppingList.get(i);
            if (currIngredient.getName().equals(name)) {
                ret = currIngredient;
            }
        }
        return ret;
    }

    public void addIngredient(Ingredient ingredient) {
        shoppingList.add(ingredient);
    }

    public void removeIngredient(String name) {
        for (int i = 0; i < shoppingList.size(); i++) {
            Ingredient currIngredient = shoppingList.get(i);
            if (currIngredient.getName().equals(name)) {
                shoppingList.remove(currIngredient);
            }
        }
    }
}
