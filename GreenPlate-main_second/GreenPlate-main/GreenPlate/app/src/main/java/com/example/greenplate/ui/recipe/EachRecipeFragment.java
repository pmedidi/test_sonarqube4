package com.example.greenplate.ui.recipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.greenplate.R;
import com.example.greenplate.database.Ingredient;
import com.example.greenplate.database.Pantry;
import com.example.greenplate.database.Recipe;
import com.example.greenplate.database.UserDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EachRecipeFragment extends Fragment {
    //private FragmentEachRecipeBinding binding;

    private EachRecipeViewModel mViewModel;

    private ArrayList<Ingredient> userPantry = Pantry.getInstance().getPantryList();

    private ArrayList<String> stringPantry = new ArrayList<String>();

    public static EachRecipeFragment newInstance() {
        return new EachRecipeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_each_recipe, container, false);
        //binding = FragmentEachRecipeBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            Recipe recipe = getArguments().getParcelable("SELECTED_RECIPE");
            if (recipe != null) {
                createStringPantry();
                TextView recipeNameTextView = view.findViewById(R.id.recipe_name_text_view);
                TextView caloriesTextView = view.findViewById(R.id.calories_text_view);
                TableLayout ingredientsTable = view.findViewById(R.id.ingredients_table);

                recipeNameTextView.setText(recipe.getName());
                caloriesTextView.setText(String.format(Locale.getDefault(),
                        "%d Calories", recipe.getCalories()));

                for (Ingredient ingredient : recipe.getIngredients()) {
                    TableRow tableRow = new TableRow(getContext());
                    tableRow.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));

                    TextView ingredientName = new TextView(getContext());
                    ingredientName.setText(ingredient.getName());
                    ingredientName.setLayoutParams(new TableRow.LayoutParams(
                            0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tableRow.addView(ingredientName);

                    TextView ingredientQuantity = new TextView(getContext());
                    ingredientQuantity.setText(String.format(Locale.getDefault(),
                            "%d", ingredient.getQuantity()));
                    ingredientQuantity.setLayoutParams(new TableRow.LayoutParams(
                            0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tableRow.addView(ingredientQuantity);

                    TextView pantryIngredientName = new TextView(getContext());
                    pantryIngredientName.setText(ingredient.getName());
                    pantryIngredientName.setLayoutParams(new TableRow.LayoutParams(
                            0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tableRow.addView(pantryIngredientName);

                    TextView pantryIngredientQuantity = new TextView(getContext());
                    if (stringPantry.contains(ingredient.getName())) {
                        pantryIngredientQuantity.setText(String.format(Locale.getDefault(),
                                "%d", userPantry.get(stringPantry.indexOf(
                                        ingredient.getName())).getQuantity()));
                        pantryIngredientQuantity.setLayoutParams(new TableRow.LayoutParams(
                                0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                        tableRow.addView(pantryIngredientQuantity);
                    } else {
                        pantryIngredientQuantity.setText(String.format(Locale.getDefault(),
                                "%d", 0));
                        pantryIngredientQuantity.setLayoutParams(new TableRow.LayoutParams(0,
                                TableRow.LayoutParams.WRAP_CONTENT, 1f));
                        tableRow.addView(pantryIngredientQuantity);
                    }
                    //IngredientQuantity.setText(String.format(Locale.getDefault(),
                    // "%d", ingredient.getQuantity()));
                    //IngredientQuantity.setLayoutParams(new TableRow.LayoutParams(0,
                    // TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    //tableRow.addView(ingredientQuantity);


                    ingredientsTable.addView(tableRow);
                }
                ArrayList<Ingredient> shop = new ArrayList<Ingredient>();
                boolean make = true;
                for (Ingredient ingredient : recipe.getIngredients()) {
                    if (stringPantry.contains(ingredient.getName())) {
                        int quantity = userPantry.get(stringPantry.indexOf(ingredient.getName()))
                                .getQuantity();
                        if (quantity < ingredient.getQuantity()) {
                            make = false;
                            shop.add(new Ingredient(ingredient.getName(),
                                    ingredient.getQuantity() - quantity,
                                    ingredient.getCaloriePerServing(),
                                    ingredient.getExpirationDate()));
                        }
                    } else {
                        make = false;
                        shop.add(ingredient);
                    }
                }
                Button cook = view.findViewById(R.id.cook);
                if (!make) {
                    cook.setText("Shop");
                }
                boolean finalMake = make;
                cook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserDatabase database = UserDatabase.getInstance();
                        if (finalMake) {
                            Pantry pantry = Pantry.getInstance();
                            database.writeNewMeal(recipe.getName(), recipe.getCalories());
                            Calendar date = Calendar.getInstance();
                            String currentDate = date.getTime().toString()
                                    .substring(0, date.getTime().toString().length() - 18);
                            database.trackNewMeal(recipe.getName(), recipe.getCalories(),
                                    currentDate);
                            for (Ingredient i : recipe.getIngredients()) {
                                Ingredient ingredient = pantry.getIngredient(i.getName());
                                int quantity = ingredient.getQuantity() - i.getQuantity();
                                if (quantity != 0) {
                                    ingredient.setQuantity(quantity);
                                    database.changeQuantity(i.getName(), quantity);
                                } else {
                                    pantry.removeIngredient(i.getName());
                                    database.removeIngredient(i.getName());
                                }
                            }
                        } else {
                            for (Ingredient i: shop) {
                                database.writeNewShoppingListItem(i.getName(), i.getQuantity(),
                                        i.getCaloriePerServing(), i.getExpirationDate());
                            }
                        }
                        RecipesFragment recipesFragment = new RecipesFragment();
                        FragmentTransaction transaction = getParentFragmentManager()
                                .beginTransaction();
                        transaction.replace(R.id.fragment_container, recipesFragment);

                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }
        }

        return view;
    }

    // This function converts the user's pantry of ingredients into
    // names of each ingredient so comparison is possible
    public void createStringPantry() {
        for (int i = 0; i < userPantry.size(); i++) {
            stringPantry.add(userPantry.get(i).getName());
        }
    }

    public ArrayList<String> getStringPantry() {
        return stringPantry;
    }

    public ArrayList<Ingredient> getUserPantry() {
        return userPantry;
    }

}