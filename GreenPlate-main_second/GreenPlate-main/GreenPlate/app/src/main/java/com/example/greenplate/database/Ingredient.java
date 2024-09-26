package com.example.greenplate.database;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private String name;
    private int quantity;
    private int caloriePerServing;
    private String expirationDate;

    // Constructor with all fields
    public Ingredient(String name, int quantity, int caloriePerServing, String expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.caloriePerServing = caloriePerServing;
        this.expirationDate = expirationDate;
    }

    // Constructor used by Parcelable.Creator
    protected Ingredient(Parcel in) {
        name = in.readString();
        quantity = in.readInt();
        caloriePerServing = in.readInt();
        expirationDate = in.readString();
    }

    // Implementation of Parcelable.Creator<Ingredient>
    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Ingredient(String ingredientName, int quantity, int caloriesPerServing) {
        this.name = ingredientName;
        this.quantity = quantity;
        this.caloriePerServing = caloriesPerServing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Write the ingredient's data to the Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(quantity);
        dest.writeInt(caloriePerServing);
        dest.writeString(expirationDate);
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCaloriePerServing() {
        return caloriePerServing;
    }

    public void setCaloriePerServing(int caloriePerServing) {
        this.caloriePerServing = caloriePerServing;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    // ToString method for debugging
    @Override
    public String toString() {
        return "Ingredient{"
                + "name='" + name + '\''
                + ", quantity=" + quantity
                + ", caloriePerServing=" + caloriePerServing
                + ", expirationDate='" + expirationDate + '\''
                + '}';
    }
}
