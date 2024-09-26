package com.example.greenplate.database;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Recipe implements Parcelable {
    private String name;
    private int calorieCount;
    private ArrayList<Ingredient> ingredients;

    public Recipe(String name, int calorieCount) {
        this.name = name;
        this.calorieCount = calorieCount;
        this.ingredients = new ArrayList<>();
    }

    public Recipe(String name, int calorieCount, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.calorieCount = calorieCount;
        this.ingredients = ingredients;
    }

    protected Recipe(Parcel in) {
        name = in.readString();
        calorieCount = in.readInt();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(calorieCount);
        dest.writeTypedList(ingredients);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    // Getters and Setters

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calorieCount;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCalorieCount(int calorieCount) {
        this.calorieCount = calorieCount;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return name + ": " + calorieCount + " calories";
    }
}
