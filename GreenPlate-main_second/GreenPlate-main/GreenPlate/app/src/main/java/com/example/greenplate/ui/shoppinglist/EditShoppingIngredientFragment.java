package com.example.greenplate.ui.shoppinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.greenplate.R;
import com.example.greenplate.database.ShoppingList;
import com.example.greenplate.database.UserDatabase;
import com.example.greenplate.databinding.FragmentEditShoppingIngredientBinding;


import java.util.Locale;

public class EditShoppingIngredientFragment extends Fragment {
    private FragmentEditShoppingIngredientBinding binding;
    private EditShoppingIngredientViewModel mViewModel;

    public static EditShoppingIngredientFragment newInstance() {
        return new EditShoppingIngredientFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEditShoppingIngredientBinding.inflate(inflater, container, false);
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
        ShoppingList shoppingList = ShoppingList.getInstance();
        Button change = binding.changeQuantity;
        change.setOnClickListener(v -> {

            String changeQuantityStr = binding.newQuantity.getText().toString();

            if (!changeQuantityStr.equals("")) {
                int changeQuantity = Integer.parseInt(changeQuantityStr);
                if (changeQuantity == 0) {
                    shoppingList.removeIngredient(ingredientName);
                    database.removeFromShoppinglist(ingredientName);
                } else {
                    shoppingList.getIngredient(ingredientName).setQuantity(changeQuantity);
                    database.changeShoppingListQuantity(ingredientName, changeQuantity);
                }
            }

            binding.newQuantity.setText("");

            ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, shoppingListFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            //  IngredientsFragment ingredientsFragment = new IngredientsFragment();
            //  FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            //  transaction.replace(R.id.fragment_container, ingredientsFragment);
            //  transaction.addToBackStack(null);
            //  transaction.commit();
        });

        Button remove = binding.removeIngredient;
        remove.setOnClickListener(v -> {

            shoppingList.removeIngredient(ingredientName);
            database.removeFromShoppinglist(ingredientName);

            binding.newQuantity.setText("");

            ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, shoppingListFragment);
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
