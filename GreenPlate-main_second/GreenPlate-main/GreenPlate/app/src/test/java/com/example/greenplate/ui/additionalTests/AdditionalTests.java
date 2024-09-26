package com.example.greenplate.ui.additionalTests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.example.greenplate.database.CookBook;
import com.example.greenplate.database.Ingredient;
import com.example.greenplate.database.Meal;
import com.example.greenplate.database.Pantry;
import com.example.greenplate.database.Recipe;
import com.example.greenplate.database.ShoppingList;
import com.example.greenplate.sortingStrategy.SortByCaloriesAscending;
import com.example.greenplate.sortingStrategy.SortByCaloriesDescending;
import com.example.greenplate.sortingStrategy.SortByNameAscending;
import com.example.greenplate.sortingStrategy.SortByNameDescending;
import com.example.greenplate.ui.recipe.EachRecipeFragment;
import com.example.greenplate.ui.recipe.RecipesFragment;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Observer;

public class AdditionalTests {


    /**
     * Test shoppinglist functionalities
     */
    @Test
    public void ShoppingList1() {
        ShoppingList test = ShoppingList.getInstance();

        String[] names = {"jesse", "jayden", "rachel", "henry", "jeff", "Ignatius"};
        Ingredient[] ingredient = new Ingredient[6];
        for (int i = 0; i < 6; i++){
            ingredient[i] = new Ingredient(names[i], i * 10, i * 30, "March " + i * 2);
            test.addIngredient(ingredient[i]);
        }

        for (int i = 0; i < 6; i++){
            assertEquals("Ingredient{" +
                    "name='" + names[i] + '\'' +
                    ", quantity=" + Integer.toString(i * 10) +
                    ", caloriePerServing=" + Integer.toString(i * 30) +
                    ", expirationDate='" + "March " + Integer.toString(i * 2) + '\'' +
                    '}', test.getShoppingList().get(i).toString());
        }

        for (int i = 0; i < 6; i++){
            assertEquals(test.getIngredient(names[i]), test.getShoppingList().get(i));
        }

        test.removeIngredient("henry");

        for (int i = 0; i < 3; i++){
            assertEquals("Ingredient{" +
                    "name='" + names[i] + '\'' +
                    ", quantity=" + Integer.toString(i * 10) +
                    ", caloriePerServing=" + Integer.toString(i * 30) +
                    ", expirationDate='" + "March " + Integer.toString(i * 2) + '\'' +
                    '}', test.getShoppingList().get(i).toString());
        }

        for (int i = 4; i < 6; i++){
            assertEquals("Ingredient{" +
                    "name='" + names[i] + '\'' +
                    ", quantity=" + Integer.toString(i * 10) +
                    ", caloriePerServing=" + Integer.toString(i * 30) +
                    ", expirationDate='" + "March " + Integer.toString(i * 2) + '\'' +
                    '}', test.getShoppingList().get(i - 1).toString());
        }
        ShoppingList.resetInstance();
    }

    /**
     * Test shoppinglist getfunctionalities, as well as removing all
     */
    @Test
    public void ShoppingList2() {
        ShoppingList test = ShoppingList.getInstance();

        test.addIngredient(new Ingredient("neeko", 10, 15, "Never"));
        test.addIngredient(new Ingredient("ksante", 20, 25, "Never"));
        test.addIngredient(new Ingredient("ornn", 30, 35, "Never"));
        test.addIngredient(new Ingredient("caitlyn", 40, 45, "Never"));
        test.addIngredient(new Ingredient("xerath", 50, 55, "Never"));

        assertEquals(null, test.getIngredient("masteryi"));
        assertEquals(20, test.getIngredient("ksante").getQuantity());

        test.removeIngredient("caitlyn");
        assertEquals(null, test.getIngredient("caitlyn"));

        test.removeIngredient("neeko");
        test.removeIngredient("ksante");
        test.removeIngredient("ornn");
        test.removeIngredient("xerath");

        assertEquals(0, test.getShoppingList().size());
    }

    /**
     * Test shoppinglist reset functionality
     */
    @Test
    public void ShoppingList3() {
        ShoppingList test = ShoppingList.getInstance();

        test.addIngredient(new Ingredient("lillia", 1, 2, "Tomorrow"));
        test.addIngredient(new Ingredient("lucian", 3, 4, "Tomorrow"));
        test.addIngredient(new Ingredient("aphelios", 5, 6, "Tomorrow"));
        test.addIngredient(new Ingredient("zeri", 7, 8, "Tomorrow"));

        assertEquals(3, test.getIngredient("lucian").getQuantity());

        ShoppingList.resetInstance();

        test = ShoppingList.getInstance();

        assertEquals(0, test.getShoppingList().size());
    }

    /**
     * Test stringpantry functionality from the fragment
     */
    @Test
    public void eachRecipeFragmentTest(){
        EachRecipeFragment test = new EachRecipeFragment();
        test.getUserPantry().add(new Ingredient("galio", 10, 10, "Never"));
        test.getUserPantry().add(new Ingredient("talon", 20, 20, "Never"));
        test.getUserPantry().add(new Ingredient("katarina", 30, 30, "Never"));
        test.getUserPantry().add(new Ingredient("ryze", 40, 40, "Never"));

        test.createStringPantry();

        assertEquals("galio", test.getStringPantry().get(0));
        assertEquals("talon", test.getStringPantry().get(1));
        assertEquals("katarina", test.getStringPantry().get(2));
        assertEquals("ryze", test.getStringPantry().get(3));
    }

    @Test
    public void pantryTest1(){
        Pantry test = Pantry.getInstance();
        RecipesFragment testFragment = new RecipesFragment();
        test.addObserver(testFragment);

        assertEquals(1, test.getObservers().size());

        test.removeObserver(testFragment);
        assertEquals(0, test.getObservers().size());
    }

    @Test
    public void pantryTest2(){
        Pantry test = Pantry.getInstance();
        RecipesFragment fragment1 = new RecipesFragment();
        RecipesFragment fragment2 = new RecipesFragment();
        test.addObserver(fragment1);
        test.addObserver(fragment2);
        assertEquals(2, test.getObservers().size());

        test.removeObserver(new RecipesFragment());

        assertEquals(2, test.getObservers().size());
        assertEquals(fragment2, test.getObservers().get(1));
    }

    @Test
    public void recipeFragmentTest(){
        RecipesFragment testFragment = new RecipesFragment();
        testFragment.getPantryList().add(new Ingredient("test1", 10, 20, "never"));
        testFragment.getPantryList().add(new Ingredient("test2", 30, 40, "never"));
        testFragment.getPantryList().add(new Ingredient("test3", 50, 60, "never"));

        testFragment.update();

        assertEquals("test1", testFragment.getStringPantry().get(0));
        assertEquals("test2", testFragment.getStringPantry().get(1));
        assertEquals("test3", testFragment.getStringPantry().get(2));
        testFragment.getPantryList().clear();
    }

    @Test
    public void recipeFragmentTest2(){
        RecipesFragment testFragment = new RecipesFragment();
        testFragment.getRecipeItems().add(new Recipe("hwei", 50));
        testFragment.getRecipeItems().add(new Recipe("yuumi", 10000));
        testFragment.getRecipeItems().add(new Recipe("reksai", 30000));

        SortByCaloriesDescending sortObj = new SortByCaloriesDescending();
        try {
            testFragment.sortRecipes(sortObj);
        }catch (Exception e){
            System.out.println("Something went wrong!");
        }

        assertEquals("reksai", testFragment.getRecipeItems().get(0).getName());
        assertEquals("yuumi", testFragment.getRecipeItems().get(1).getName());
        assertEquals("hwei", testFragment.getRecipeItems().get(2).getName());

    }



    @Test
    public void ascendingSortTest(){
        CookBook test = CookBook.getInstance();
        test.addRecipe(new Recipe("fizz", 32));
        test.addRecipe(new Recipe("kayn", 17));
        test.addRecipe(new Recipe("riven", 92));
        test.addRecipe(new Recipe("azir", 86));
        test.addRecipe(new Recipe("kindred", 73));
        test.addRecipe(new Recipe("nami", 26));

        SortByCaloriesAscending sortObj = new SortByCaloriesAscending();
        sortObj.sort(test.getGlobalRecipeList());

        assertEquals("kayn", test.getGlobalRecipeList().get(0).getName());
        assertEquals("nami", test.getGlobalRecipeList().get(1).getName());
        assertEquals("fizz", test.getGlobalRecipeList().get(2).getName());
        assertEquals("kindred", test.getGlobalRecipeList().get(3).getName());
        assertEquals("azir", test.getGlobalRecipeList().get(4).getName());
        assertEquals("riven", test.getGlobalRecipeList().get(5).getName());

        test.getGlobalRecipeList().clear();
        sortObj.sort(test.getGlobalRecipeList());
        assertEquals(0, test.getGlobalRecipeList().size());
    }

    @Test
    public void descendingSortTest(){
        CookBook test = CookBook.getInstance();
        test.addRecipe(new Recipe("test1", 33));
        test.addRecipe(new Recipe("test2", 18));
        test.addRecipe(new Recipe("test3", 93));
        test.addRecipe(new Recipe("test4", 85));
        test.addRecipe(new Recipe("test5", 72));
        test.addRecipe(new Recipe("test6", 25));

        SortByCaloriesDescending sortObj = new SortByCaloriesDescending();
        sortObj.sort(test.getGlobalRecipeList());

        assertEquals("test3", test.getGlobalRecipeList().get(0).getName());
        assertEquals("test4", test.getGlobalRecipeList().get(1).getName());
        assertEquals("test5", test.getGlobalRecipeList().get(2).getName());
        assertEquals("test1", test.getGlobalRecipeList().get(3).getName());
        assertEquals("test6", test.getGlobalRecipeList().get(4).getName());
        assertEquals("test2", test.getGlobalRecipeList().get(5).getName());

        test.getGlobalRecipeList().clear();
        sortObj.sort(test.getGlobalRecipeList());
        assertEquals(0, test.getGlobalRecipeList().size());
        test.getGlobalRecipeList().clear();
    }

    @Test
    public void nameAscendingTest(){
        CookBook test = CookBook.getInstance();
        test.addRecipe(new Recipe("garen", 1));
        test.addRecipe(new Recipe("fiora", 2));
        test.addRecipe(new Recipe("draven", 3));
        test.addRecipe(new Recipe("syndra", 4));
        test.addRecipe(new Recipe("orianna", 5));
        test.addRecipe(new Recipe("poppy", 6));

        SortByNameAscending sortObj = new SortByNameAscending();
        sortObj.sort(test.getGlobalRecipeList());

        assertEquals("draven", test.getGlobalRecipeList().get(0).getName());
        assertEquals("fiora", test.getGlobalRecipeList().get(1).getName());
        assertEquals("garen", test.getGlobalRecipeList().get(2).getName());
        assertEquals("orianna", test.getGlobalRecipeList().get(3).getName());
        assertEquals("poppy", test.getGlobalRecipeList().get(4).getName());
        assertEquals("syndra", test.getGlobalRecipeList().get(5).getName());

        test.getGlobalRecipeList().clear();
        sortObj.sort(test.getGlobalRecipeList());
        assertEquals(0, test.getGlobalRecipeList().size());
    }

    @Test
    public void nameDescendingTest(){
        CookBook test = CookBook.getInstance();
        test.addRecipe(new Recipe("gnar", 1));
        test.addRecipe(new Recipe("pantheon", 2));
        test.addRecipe(new Recipe("zed", 3));
        test.addRecipe(new Recipe("nocturne", 4));
        test.addRecipe(new Recipe("khazix", 5));
        test.addRecipe(new Recipe("seraphine", 6));

        SortByNameDescending sortObj = new SortByNameDescending();
        sortObj.sort(test.getGlobalRecipeList());

        assertEquals("zed", test.getGlobalRecipeList().get(0).getName());
        assertEquals("seraphine", test.getGlobalRecipeList().get(1).getName());
        assertEquals("pantheon", test.getGlobalRecipeList().get(2).getName());
        assertEquals("nocturne", test.getGlobalRecipeList().get(3).getName());
        assertEquals("khazix", test.getGlobalRecipeList().get(4).getName());
        assertEquals("gnar", test.getGlobalRecipeList().get(5).getName());

        test.getGlobalRecipeList().clear();
        sortObj.sort(test.getGlobalRecipeList());
        assertEquals(0, test.getGlobalRecipeList().size());
    }




}
