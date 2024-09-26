package com.example.greenplate.ui.recipe;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.greenplate.database.Ingredient;
import com.example.greenplate.R;
import com.example.greenplate.database.UserDatabase;
import com.example.greenplate.databinding.FragmentInputRecipeBinding;

import java.util.ArrayList;

public class InputRecipeFragment extends Fragment {

    private FragmentInputRecipeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InputRecipeViewModel inputRecipeViewModel =
                new ViewModelProvider(this).get(InputRecipeViewModel.class);

        binding = FragmentInputRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<Ingredient> list = new ArrayList<Ingredient>();

        //final TextView textView = binding.textInputIngredient;
        //inputIngredientViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button submitIngredient = binding.submitIngredient;
        submitIngredient.setOnClickListener(v -> {
            String ingredientName = binding.recipeIngredientName.getText().toString();
            String quantityStr = binding.recipeQuantity.getText().toString();
            String caloriesPerServingStr = binding.recipeCaloriesPerServing.getText().toString();
            String expirationDate = binding.recipeExpirationDate.getText().toString();

            if (!ingredientName.equals("") && !quantityStr.equals("")
                    && !caloriesPerServingStr.equals("")) {

                int quantity = Integer.parseInt(quantityStr);
                int caloriesPerServing = Integer.parseInt(caloriesPerServingStr);

                Ingredient ingredient = null;
                if (expirationDate.equals("")) {
                    ingredient = new Ingredient(ingredientName, quantity, caloriesPerServing);
                } else {
                    ingredient = new Ingredient(ingredientName, quantity, caloriesPerServing,
                            expirationDate);
                }
                list.add(ingredient);

                binding.recipeIngredientName.setText("");
                binding.recipeQuantity.setText("");
                binding.recipeCaloriesPerServing.setText("");
                binding.recipeExpirationDate.setText("");

            }
        });
        Button submit = binding.submitRecipeButton;
        submit.setOnClickListener(v -> {
            String recipeName = binding.recipeName.getText().toString();
            if (!list.isEmpty() && !recipeName.equals("")) {
                UserDatabase userDatabase = UserDatabase.getInstance();

                userDatabase.writeRecipeInCookBook(recipeName, list);

                binding.recipeIngredientName.setText("");
                binding.recipeQuantity.setText("");
                binding.recipeCaloriesPerServing.setText("");
                binding.recipeExpirationDate.setText("");

                RecipesFragment recipesFragment = new RecipesFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, recipesFragment);

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}