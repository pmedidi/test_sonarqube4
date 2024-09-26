package com.example.greenplate.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;


public class UserDatabase {

    private static UserDatabase userData;
    private FirebaseDatabase mDatabase;

    private UserDatabase() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    public static UserDatabase getInstance() {
        if (userData == null) {
            userData = new UserDatabase();
        }
        return userData;
    }

    // Writing User Data Functions

    public void writeNewUser(String first, String last, String email, String password) {
        User user = User.getInstance();
        user.setFirstName(first);
        user.setLastName(last);
        user.setEmail(email);
        user.setPassword(password);
        user.setHeight(0);
        user.setWeight(0);
        user.setGender("null");
        user.setDailyCalories(0);
        //user.setTotalCalories(0);
        //User user = new User(first, last, username, password);
        String username = email.replace(".", " ");
        user.setUsername(username);

        DatabaseReference database = mDatabase.getReference();

        database.child("Users").child(username).setValue(user);

        Calendar calendar = Calendar.getInstance();

        // Next create a new entry/tag inside the Meals Database itself so that we have a meals
        // dictionary personalized to a given current user
        database.child("Meals").child(username).child("EMPTY-MEAL").setValue(0);
        //database.child("Meals").child(username).child("EMPTY-MEAL").child("calories").setValue(0);

        System.out.println("Created EMPTY MEAL");

        // Create the Meal Calendar and initialize it with a -1 to prevent it counting as a meal day
        database.child("Users").child(username).child("mealCalendar")
                .child(calendar.getTime().toString().substring(0, calendar.getTime()
                        .toString().length() - 18)).child("-1").setValue("StartingDay");
        System.out.println("Created Meal Calendar");

    }

    public void writeHeightWeightGender(double height, double weight, String gender) {
        DatabaseReference database = mDatabase.getReference("Users");
        User user = User.getInstance();
        database.child(user.getUsername()).child("weight").setValue(weight);
        database.child(user.getUsername()).child("height").setValue(height);
        database.child(user.getUsername()).child("gender").setValue(gender);
    }

    // Writing and Tracking Meals

    public void writeNewMeal(String mealName, int calories) {

        DatabaseReference database = mDatabase.getReference();

        // Pull the user's username to make a user specific entry inside the meal database
        User curr = User.getInstance();

        database.child("Meals").child(curr.getUsername()).child(mealName);
        database.child("Meals").child(curr.getUsername())
                .child(mealName).child("calories").setValue(calories);
    }

    // Note: This function tracks a new meal into the User instance
    // Maybe we can move this into the User class to reduce coupling
    public void trackNewMeal(String currentMeal, int calories, String date) {
        // Track a new meal:
        // 1. Update our current user's meal log inside their mealCalendar in Firebase
        // 2. Update our local snapshot/copy
        // of the mealCalendar as well as the monthlyCalorie count

        DatabaseReference db = mDatabase.getReference();

        User currentUser = User.getInstance();
        int newMealNumber = currentUser.getMealCalendar().get(29).size();
        // The current meal index inside the meal log of a user, just use .size()

        db.child("Users").child(currentUser.getUsername())
                .child("mealCalendar").child(date).child(Integer.toString(newMealNumber))
                .setValue(currentMeal);

        currentUser.addMealToday(new Meal(currentMeal, calories));
    }

    // Cookbook Database Functions
    // 1. Write CookBook

    // This function should usually only be called on write
    // It also returns a recipe!
    public void writeRecipeInCookBook(String recipeName, ArrayList<Ingredient> requirements) {
        DatabaseReference database = mDatabase.getReference();

        // Pull the user's username to make a user specific entry inside the meal database
        User curr = User.getInstance();
        int totalCalories = 0;

        // Pull out the next possible id for global cook book recipes
        CookBook globalCookBook = CookBook.getInstance();
        String globalRecipeCount = String.valueOf(globalCookBook.getGlobalRecipeList().size());

        // Set the recipe names and calorie counts, also increment the global recipe count id
        int totalGlobalRecipeCount = globalCookBook.getGlobalRecipeList().size() + 1;
        database.child("CookBook")
                .child("totalGlobalRecipeCount").setValue(totalGlobalRecipeCount);
        database.child("CookBook")
                .child(globalRecipeCount).child("totalCalories").setValue(totalCalories);
        database.child("CookBook")
                .child(globalRecipeCount).child("recipeName").setValue(recipeName);

        // Use a for loop to write in all the required ingredients into the database
        for (int i = 0; i < requirements.size(); i++) {
            Ingredient currIngredient = requirements.get(i);

            database.child("CookBook").child(globalRecipeCount)
                    .child(String.valueOf(i)).child("ingredientName")
                    .setValue(currIngredient.getName());
            database.child("CookBook").child(globalRecipeCount)
                    .child(String.valueOf(i)).child("caloriesPerServing")
                    .setValue(currIngredient.getCaloriePerServing());
            database.child("CookBook").child(globalRecipeCount)
                    .child(String.valueOf(i)).child("requiredServing")
                    .setValue(currIngredient.getQuantity());

            // Sum up the calories for each ingredient:
            totalCalories += currIngredient.getCaloriePerServing() * currIngredient.getQuantity();
        }

        // Update the calorie count since it was initialized to be zero at the top
        database.child("CookBook").child(globalRecipeCount)
                .child("totalCalories").setValue(totalCalories);

        // Finally, add the new recipe to the globalRecipeList
        // stored on the local machine so that the local copy matches the database
        globalCookBook.addRecipe(new Recipe(recipeName, totalCalories, requirements));
    }



    // Pantry Database Functions:

    //Pantry.getInstance().getPantryList().size()

    public void writeNewIngredient(String ingredientName, int quantity, int caloriesPerServing) {

        writeNewIngredient(ingredientName, quantity, caloriesPerServing, "null");

    }
    public void writeNewIngredient(String ingredientName, int quantity, int caloriesPerServing,
                                   String expirationDate) {
        DatabaseReference database = mDatabase.getReference("Pantry");
        Pantry pantry = Pantry.getInstance();
        User user = User.getInstance();
        Ingredient duplicate = pantry.getIngredient(ingredientName);
        if (duplicate != null) {
            int q = duplicate.getQuantity() + quantity;
            duplicate.setQuantity(q);
            database = mDatabase.getReference("Pantry").child(user.getUsername())
                    .child(ingredientName).child("quantity");
            database.setValue(String.valueOf(q));
        } else {
            Ingredient ingredient = new Ingredient(ingredientName, quantity,
                    caloriesPerServing, expirationDate);
            pantry.getPantryList().add(ingredient);
            database.child(user.getUsername()).child(ingredientName);
            database.child(user.getUsername()).child(ingredientName).setValue(ingredient);
        }
        /*database.child(user.getUsername()).child(String.valueOf(pantry.getPantryList().size()));
        database.child(user.getUsername())
        .child(String.valueOf(pantry.getPantryList().size())).setValue(ingredient);*/
    }

    public void removeIngredient(String ingredientName) {
        User user = User.getInstance();
        DatabaseReference database = mDatabase.getReference("Pantry").child(user.getUsername())
                .child(ingredientName);

        database.removeValue();
    }

    public void changeQuantity(String ingredientName, int newQuantity) {
        User user = User.getInstance();
        DatabaseReference database = mDatabase.getReference("Pantry").child(user.getUsername())
                .child(ingredientName).child("quantity");

        database.setValue(String.valueOf(newQuantity));
    }


    // ShoppingList Database:

    public void writeNewShoppingListItem(String ingredientName, int quantity,
                                         int caloriesPerServing) {

        writeNewShoppingListItem(ingredientName, quantity, caloriesPerServing, "null");

    }

    public void writeNewShoppingListItem(String ingredientName,
                                         int quantity, int caloriesPerServing,
                                         String expirationDate) {
        DatabaseReference database = mDatabase.getReference("Shopping List");
        ShoppingList shpl = ShoppingList.getInstance();
        User user = User.getInstance();
        Ingredient duplicate = shpl.getIngredient(ingredientName);
        if (duplicate != null) {
            // If the ingredient
            int q = duplicate.getQuantity() + quantity;
            duplicate.setQuantity(q);
            database = mDatabase.getReference("Shopping List").child(user.getUsername())
                    .child(ingredientName).child("quantity");
            database.setValue(String.valueOf(q));
        } else {
            Ingredient ingredient = new Ingredient(ingredientName, quantity,
                    caloriesPerServing, expirationDate);
            shpl.getShoppingList().add(ingredient);
            database.child(user.getUsername()).child(ingredientName);
            database.child(user.getUsername()).child(ingredientName).setValue(ingredient);
        }
    }

    public void removeFromShoppinglist(String shoppingListItemName) {
        User user = User.getInstance();
        DatabaseReference database = mDatabase.getReference("Shopping List")
                .child(user.getUsername())
                .child(shoppingListItemName);
        database.removeValue();
    }

    public void changeEntryShoppingList(String shoppingListItemName,
                                        String variableToChange, String newValue) {
        User user = User.getInstance();
        DatabaseReference database = mDatabase.getReference("Shopping List")
                .child(user.getUsername())
                .child(shoppingListItemName).child(variableToChange);
        database.setValue(newValue);
    }

    public void changeShoppingListQuantity(String ingredientName, int newQuantity) {
        User user = User.getInstance();
        DatabaseReference database = mDatabase.getReference("Shopping List")
                .child(user.getUsername())
                .child(ingredientName).child("quantity");
        database.setValue(String.valueOf(newQuantity));
    }
}
