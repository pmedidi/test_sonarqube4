package com.example.greenplate.ui.shoppinglist;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.greenplate.R;
import com.example.greenplate.database.UserDatabase;
import com.example.greenplate.databinding.FragmentAddShopBinding;

public class AddShopFragment extends Fragment {

    private FragmentAddShopBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddShopViewModel addShopViewModel =
                new ViewModelProvider(this).get(AddShopViewModel.class);

        binding = FragmentAddShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button submit = binding.addShopListButton;
        submit.setOnClickListener(v -> {
            String ingredientName = binding.ingredientNameShop.getText().toString();
            String quantityStr = binding.quantityShop.getText().toString();
            String caloriesPerServingStr = binding.caloriesPerServingShop.getText().toString();
            String expirationDate = binding.expirationDateShop.getText().toString();

            if (!ingredientName.equals("") && !quantityStr.equals("")
                    && !caloriesPerServingStr.equals("")) {

                int quantity = Integer.parseInt(quantityStr);
                int caloriesPerServing = Integer.parseInt(caloriesPerServingStr);

                binding.ingredientNameShop.setText("");
                binding.quantityShop.setText("");
                binding.caloriesPerServingShop.setText("");
                binding.expirationDateShop.setText("");

                UserDatabase userDatabase = UserDatabase.getInstance();

                if (expirationDate.equals("")) {
                    userDatabase.writeNewShoppingListItem(ingredientName, quantity,
                            caloriesPerServing);
                } else {
                    userDatabase.writeNewShoppingListItem(ingredientName, quantity,
                            caloriesPerServing, expirationDate);
                }

                ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, shoppingListFragment);

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