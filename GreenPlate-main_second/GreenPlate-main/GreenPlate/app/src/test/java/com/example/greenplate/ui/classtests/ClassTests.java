package com.example.greenplate.ui.classtests;


import static org.junit.Assert.*;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

import com.example.greenplate.database.CookBook;
import com.example.greenplate.database.Ingredient;
import com.example.greenplate.database.Meal;
import com.example.greenplate.database.Pantry;
import com.example.greenplate.database.Recipe;

import org.junit.Test;

import java.util.ArrayList;

public class ClassTests {

    @Test
    public void PantryClassTest1() {
        Pantry testPantry = Pantry.getInstance();
        for (int i = 0; i < 50; i++){
            Ingredient temp = new Ingredient("test" + Integer.toString(i), i, i * 2, Integer.toString(i) + " March");
            testPantry.addIngredient(temp);
        }

        for (int i = 0; i < 50; i++){
            assertEquals("Ingredient{" +
                    "name='" + "test" + Integer.toString(i) + '\'' +
                    ", quantity=" + Integer.toString(i) +
                    ", caloriePerServing=" + Integer.toString(i * 2) +
                    ", expirationDate='" + Integer.toString(i) + " March" + '\'' +
                    '}', testPantry.getPantryList().get(i).toString());
        }
    }

    @Test
    public void PantryClassTest2() {
        Pantry testPantry = Pantry.getInstance();
        for (int i = 0; i < 10; i++){
            Ingredient temp = new Ingredient("test" + Integer.toString(i), i, i * 2, Integer.toString(i) + " March");
            testPantry.addIngredient(temp);
        }

        assertEquals("test5", testPantry.getPantryList().get(5).getName());
        testPantry.removeIngredient("test5");

        for (int i = 0; i < 5; i++){
            assertEquals("Ingredient{" +
                    "name='" + "test" + Integer.toString(i) + '\'' +
                    ", quantity=" + Integer.toString(i) +
                    ", caloriePerServing=" + Integer.toString(i * 2) +
                    ", expirationDate='" + Integer.toString(i) + " March" + '\'' +
                    '}', testPantry.getPantryList().get(i).toString());
        }
        for (int i = 5; i < 9; i++){
            assertEquals("Ingredient{" +
                    "name='" + "test" + Integer.toString(i + 1) + '\'' +
                    ", quantity=" + Integer.toString(i + 1) +
                    ", caloriePerServing=" + Integer.toString((i + 1) * 2) +
                    ", expirationDate='" + Integer.toString(i + 1) + " March" + '\'' +
                    '}', testPantry.getPantryList().get(i).toString());
        }
    }

    @Test
    public void RecipeTest1(){
        Recipe test = new Recipe("Test Recipe", 1000);

        ArrayList<Ingredient> tempIngredients = new ArrayList<Ingredient>();
        for(int i = 0; i < 10; i++){
            Ingredient temp = new Ingredient("jesse " + Integer.toString(i), i, i * 2, Integer.toString(i) + " jeff");
            tempIngredients.add(temp);
        }
        test.setIngredients(tempIngredients);

        for(int i = 0; i < 10; i++){
            assertEquals("Ingredient{" +
                    "name='" + "jesse " + Integer.toString(i) + '\'' +
                    ", quantity=" + Integer.toString(i) +
                    ", caloriePerServing=" + Integer.toString(i * 2) +
                    ", expirationDate='" + Integer.toString(i) + " jeff" + '\'' +
                    '}', test.getIngredients().get(i).toString());
        }
    }

    @Test
    public void RecipeTest2(){
        ArrayList<Ingredient> tempIngredients = new ArrayList<Ingredient>();
        for(int i = 0; i < 10; i++){
            Ingredient temp = new Ingredient("jayden" + Integer.toString(i), i, i * 2, Integer.toString(i) + " rachel");
            tempIngredients.add(temp);
        }
        Recipe test = new Recipe("Test Recipe", 1000, tempIngredients);

        for(int i = 0; i < 10; i++){
            assertEquals("Ingredient{" +
                    "name='" + "jayden" + Integer.toString(i) + '\'' +
                    ", quantity=" + Integer.toString(i) +
                    ", caloriePerServing=" + Integer.toString(i * 2) +
                    ", expirationDate='" + Integer.toString(i) + " rachel" + '\'' +
                    '}', test.getIngredients().get(i).toString());
        }
    }

    @Test
    public void RecipeTest3(){
        Recipe test = new Recipe("henry", 50);

        assertEquals("henry", test.getName());
        assertEquals(50, test.getCalories());
    }

    @Test
    public void RecipeTest4(){
        Recipe test = new Recipe("ignatius", 25);

        assertEquals("ignatius", test.getName());
        assertEquals(25, test.getCalories());
        assertEquals(new ArrayList<Ingredient>(), test.getIngredients());


        test.setName("jeffrey");
        test.setCalorieCount(2);
        ArrayList<Ingredient> testList = new ArrayList<Ingredient>();
        testList.add(new Ingredient("hop", 15, 54, "december"));
        test.setIngredients(testList);

        assertEquals("jeffrey", test.getName());
        assertEquals(2, test.getCalories());
        assertEquals("Ingredient{" +
                "name='" + "hop" + '\'' +
                ", quantity=" + "15" +
                ", caloriePerServing=" + "54" +
                ", expirationDate='" + "december" + '\'' +
                '}', test.getIngredients().get(0).toString());
    }

    @Test
    public void IngredientTest1(){
        Ingredient test = new Ingredient("mouse", 100, 400);

        assertEquals("mouse", test.getName());
        assertEquals(100, test.getQuantity());
        assertEquals(400, test.getCaloriePerServing());
    }

    @Test
    public void IngredientTest2(){
        Ingredient test = new Ingredient("jar", 1000, 5000, "december 15, 2024");

        assertEquals("jar", test.getName());
        assertEquals(1000, test.getQuantity());
        assertEquals(5000, test.getCaloriePerServing());
        assertEquals("december 15, 2024", test.getExpirationDate());

        test.setExpirationDate("november 13, 2024");
        assertEquals("november 13, 2024", test.getExpirationDate());
    }

    @Test
    public void MealTest1(){
        Meal test = new Meal("tail", 30);

        assertEquals("tail", test.getName());
        assertEquals(30, test.getCalories());
        assertEquals("tail: 30", test.toString());
    }

    @Test
    public void CookBookTest1(){
        CookBook test = CookBook.getInstance();

        Recipe testRecipe = new Recipe("monitor", 300);
        test.addRecipe(testRecipe);

        assertEquals("monitor: 300 calories", test.getGlobalRecipeList().get(0).toString());
        CookBook.resetCookBook();
    }

    @Test
    public void CookBookTest2(){
        CookBook test = CookBook.getInstance();

        ArrayList<Ingredient> listIngredients1 = new ArrayList<Ingredient>();
        ArrayList<Ingredient> listIngredients2 = new ArrayList<Ingredient>();

        for(int i = 0; i < 10; i++){
            listIngredients1.add(new Ingredient("t" + Integer.toString(i), i * 10, i * 10, "d" + Integer.toString(i)));
            listIngredients2.add(new Ingredient("h" + Integer.toString(i),i * 100, i * 100, "p" + Integer.toString(i)));
        }

        Recipe recipe1 = new Recipe("r1", 50, listIngredients1);
        Recipe recipe2 = new Recipe("r2", 300, listIngredients2);

        test.addRecipe(recipe1);
        test.addRecipe(recipe2);


        assertEquals("r1: 50 calories", test.getGlobalRecipeList().get(0).toString());
        assertEquals("r2: 300 calories", test.getGlobalRecipeList().get(1).toString());

        for(int i = 0; i < 10; i++){
            assertEquals("Ingredient{" +
                    "name='" + "t" + Integer.toString(i) + '\'' +
                    ", quantity=" + Integer.toString(i * 10) +
                    ", caloriePerServing=" + Integer.toString(i * 10) +
                    ", expirationDate='" + "d" + Integer.toString(i) + '\'' +
                    '}', test.getGlobalRecipeList().get(0).getIngredients().get(i).toString());

            assertEquals("Ingredient{" +
                    "name='" + "h" + Integer.toString(i) + '\'' +
                    ", quantity=" + Integer.toString(i * 100) +
                    ", caloriePerServing=" + Integer.toString(i * 100) +
                    ", expirationDate='" + "p" + Integer.toString(i) + '\'' +
                    '}', test.getGlobalRecipeList().get(1).getIngredients().get(i).toString());
        }
        CookBook.resetCookBook();
    }

    @Test
    public void CookBookTest3(){
        CookBook test = CookBook.getInstance();

        ArrayList<Ingredient> x = new ArrayList<Ingredient>();
        x.add(new Ingredient("x", 20, 30, "y"));
        x.add(new Ingredient("a", 40, 50, "b"));

        Recipe testRecipe = new Recipe("jjjirh", 1000, x);
        test.addRecipe(testRecipe);

        assertEquals("jjjirh: 1000 calories", test.getGlobalRecipeList().get(0).toString());
        assertEquals("Ingredient{" +
                "name='" + "x" + '\'' +
                ", quantity=" + "20" +
                ", caloriePerServing=" + "30" +
                ", expirationDate='" + "y" + '\'' +
                '}', test.getGlobalRecipeList().get(0).getIngredients().get(0).toString());

        assertEquals("Ingredient{" +
                "name='" + "a" + '\'' +
                ", quantity=" + "40" +
                ", caloriePerServing=" + "50" +
                ", expirationDate='" + "b" + '\'' +
                '}', test.getGlobalRecipeList().get(0).getIngredients().get(1).toString());

        CookBook.resetCookBook();

        CookBook test2 = CookBook.getInstance();

        assertEquals(new ArrayList<Ingredient>(), test2.getGlobalRecipeList());
        CookBook.resetCookBook();
    }


}
