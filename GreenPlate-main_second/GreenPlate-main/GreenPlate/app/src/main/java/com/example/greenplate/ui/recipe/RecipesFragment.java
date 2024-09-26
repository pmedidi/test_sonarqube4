package com.example.greenplate.ui.recipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.greenplate.observer.Observer;
import com.example.greenplate.R;
import com.example.greenplate.database.CookBook;
import com.example.greenplate.database.Ingredient;
import com.example.greenplate.database.Pantry;
import com.example.greenplate.database.Recipe;
import com.example.greenplate.databinding.FragmentRecipesBinding;
import com.example.greenplate.sortingStrategy.SortByCaloriesAscending;
import com.example.greenplate.sortingStrategy.SortByCaloriesDescending;
import com.example.greenplate.sortingStrategy.SortByNameAscending;
import com.example.greenplate.sortingStrategy.SortByNameDescending;
import com.example.greenplate.sortingStrategy.SortingStrategy;

import java.util.ArrayList;

public class RecipesFragment extends Fragment implements Observer {

    private FragmentRecipesBinding binding;
    private ArrayList<Recipe> recipeItems; // A list to hold Recipe objects
    private ArrayAdapter<Recipe> adapter; // An adapter for Recipe objects
    private ArrayList<String> stringPantry = new ArrayList<String>();
    private ArrayList<Ingredient> pantryList = Pantry.getInstance().getPantryList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Toolbar for sorting features
        Toolbar toolbar = root.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_sort_name_ascending) {
                sortRecipes(new SortByNameAscending());
            } else if (item.getItemId() == R.id.action_sort_name_descending) {
                sortRecipes(new SortByNameDescending());
            } else if (item.getItemId() == R.id.action_sort_calories_ascending) {
                sortRecipes(new SortByCaloriesAscending());
            } else if (item.getItemId() == R.id.action_sort_calories_descending) {
                sortRecipes(new SortByCaloriesDescending());
            }
            return true;
        });

        // Button to create a recipe, go to new fragment
        TextView createRecipeButton = root.findViewById(R.id.createRecipeButton);
        createRecipeButton.setOnClickListener(v -> {
            Fragment newFragment = new InputRecipeFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Populating
        recipeItems = new ArrayList<>(CookBook.getInstance().getGlobalRecipeList());

        // Create and populate the list
        ListView recipesListView = binding.recipesListView;

        // Adapter for converting each data item from the data source into a view
        adapter = new ArrayAdapter<Recipe>(
                getActivity(),
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                recipeItems) { // Assume recipeItems is already populated with Recipe objects

            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext())
                            .inflate(android.R.layout.simple_list_item_2,
                                    parent, false);
                }

                Recipe recipe = getItem(position);

                TextView tvName = convertView.findViewById(android.R.id.text1);
                TextView tvCalories = convertView.findViewById(android.R.id.text2);

                Pantry.getInstance().addObserver(RecipesFragment.this);
                ArrayList<Ingredient> userPantry = Pantry.getInstance().getPantryList();
                String make = "Can make: True";
                for (Ingredient ingredient : recipe.getIngredients()) {
                    if (stringPantry.contains(ingredient.getName())) {
                        int quantity = userPantry.get(stringPantry.indexOf(ingredient.getName()))
                                .getQuantity();
                        if (quantity < ingredient.getQuantity()) {
                            make = "Can make: False";
                        }
                    } else {
                        make = "Can make: False";
                    }
                }
                Pantry.getInstance().removeObserver(RecipesFragment.this);

                tvName.setText(recipe.getName());
                tvCalories.setText(recipe.getCalories() + " Calories, " + make);

                return convertView;
            }
        };
        recipesListView.setAdapter(adapter);

        // On click listener to go to new fragment
        recipesListView.setOnItemClickListener((parent, view, position, id) -> {
            Recipe selectedRecipe = recipeItems.get(position);

            Fragment selectedFragment = new EachRecipeFragment();

            // Put the Recipe object into the Bundle
            Bundle args = new Bundle();
            args.putParcelable("SELECTED_RECIPE", selectedRecipe);
            selectedFragment.setArguments(args);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, selectedFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Default sorting by name ascending.
        sortRecipes(new SortByNameAscending());
        return root;
    }

    public void sortRecipes(SortingStrategy strategy) {
        if (strategy != null) {
            strategy.sort(recipeItems);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void update() {
        stringPantry.clear();
        for (Ingredient ingredient : pantryList) {
            stringPantry.add(ingredient.getName());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ArrayList<Ingredient> getPantryList() {
        return pantryList;
    }

    public ArrayList<String> getStringPantry() {
        return stringPantry;
    }

    public ArrayList<Recipe> getRecipeItems() {
        if (recipeItems == null) {
            recipeItems = new ArrayList<Recipe>();
        }
        return recipeItems;
    }
}
