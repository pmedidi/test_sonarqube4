package com.example.greenplate.ui.ingredient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenplate.R;
import com.example.greenplate.database.UserDatabase;
import com.example.greenplate.databinding.FragmentInputIngredientBinding;

public class InputIngredientFragment extends Fragment {

    private FragmentInputIngredientBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InputIngredientViewModel inputIngredientViewModel =
                new ViewModelProvider(this).get(InputIngredientViewModel.class);

        binding = FragmentInputIngredientBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textInputIngredient;
        //inputIngredientViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button submit = binding.submitIngredientButton;
        submit.setOnClickListener(v -> {
            String ingredientName = binding.ingredientName.getText().toString();
            String quantityStr = binding.quantity.getText().toString();
            String caloriesPerServingStr = binding.caloriesPerServing.getText().toString();
            String expirationDate = binding.expirationDate.getText().toString();

            if (!ingredientName.equals("") && !quantityStr.equals("")
                    && !caloriesPerServingStr.equals("")) {

                int quantity = Integer.parseInt(quantityStr);
                int caloriesPerServing = Integer.parseInt(caloriesPerServingStr);

                binding.ingredientName.setText("");
                binding.quantity.setText("");
                binding.caloriesPerServing.setText("");
                binding.expirationDate.setText("");

                UserDatabase userDatabase = UserDatabase.getInstance();

                if (expirationDate.equals("")) {
                    userDatabase.writeNewIngredient(ingredientName, quantity, caloriesPerServing);
                } else {
                    userDatabase.writeNewIngredient(ingredientName, quantity, caloriesPerServing,
                            expirationDate);
                }

                //user.setHeight(Double.parseDouble(userHeight));
                //user.setWeight(Double.parseDouble(userWeight));
                //user.setGender(userGender);
                //
                //UserDatabase database = UserDatabase.getInstance();
                //database.writeHeightWeightGender(Double.parseDouble(userHeight),
                //Double.parseDouble(userWeight), userGender);

                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, ingredientsFragment);

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