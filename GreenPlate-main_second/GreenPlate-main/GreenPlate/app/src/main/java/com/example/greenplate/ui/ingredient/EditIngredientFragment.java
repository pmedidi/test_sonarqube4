package com.example.greenplate.ui.ingredient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.greenplate.R;
import com.example.greenplate.database.Pantry;
import com.example.greenplate.database.UserDatabase;
import com.example.greenplate.databinding.FragmentEditIngredientBinding;


import java.util.Locale;

public class EditIngredientFragment extends Fragment {

    private FragmentEditIngredientBinding binding;
    private EditIngredientViewModel mViewModel;

    public static EditIngredientFragment newInstance() {
        return new EditIngredientFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEditIngredientBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String ingredientName;
        if (getArguments() != null) {
            ingredientName = getArguments().getString("INGREDIENT");
            int quantity = getArguments().getInt("QUANTITY");

            binding.ingredientNameTextView.setText(ingredientName);
            binding.quantityTextView.setText(String.format(Locale.getDefault(), "%d", quantity));
        } else {
            ingredientName = "";
        }
        UserDatabase database = UserDatabase.getInstance();
        Pantry pantry = Pantry.getInstance();
        Button change = binding.changeQuantity;
        change.setOnClickListener(v -> {

            String changeQuantityStr = binding.newQuantity.getText().toString();

            if (!changeQuantityStr.equals("")) {
                int changeQuantity = Integer.parseInt(changeQuantityStr);
                if (changeQuantity == 0) {
                    pantry.removeIngredient(ingredientName);
                    database.removeIngredient(ingredientName);
                } else {
                    pantry.getIngredient(ingredientName).setQuantity(changeQuantity);
                    database.changeQuantity(ingredientName, changeQuantity);
                }
            }

            binding.newQuantity.setText("");

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, ingredientsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        Button remove = binding.removeIngredient;
        remove.setOnClickListener(v -> {

            pantry.removeIngredient(ingredientName);
            database.removeIngredient(ingredientName);

            binding.newQuantity.setText("");

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, ingredientsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}