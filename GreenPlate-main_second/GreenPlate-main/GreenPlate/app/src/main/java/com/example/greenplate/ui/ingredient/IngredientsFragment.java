package com.example.greenplate.ui.ingredient;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenplate.R;
import com.example.greenplate.database.Ingredient;
import com.example.greenplate.database.Pantry;
import com.example.greenplate.databinding.FragmentIngredientsBinding;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {

    private FragmentIngredientsBinding binding;
    private ArrayList<Pair<String, Integer>> pantryItems; // Changed to Integer for calories
    private ArrayAdapter<Pair<String, Integer>> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        IngredientsViewModel ingredientViewModel =
                new ViewModelProvider(this).get(IngredientsViewModel.class);

        binding = FragmentIngredientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        Button inputIngredientButton = binding.inputIngredientButton;
        inputIngredientButton.setOnClickListener(v -> {
            InputIngredientFragment inputIngredientFragment = new InputIngredientFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, inputIngredientFragment);
            transaction.addToBackStack(null);
            transaction.commit();


        });


        ListView pantryListView = binding.pantryListView;
        Pantry pantry = Pantry.getInstance();
        pantryItems = new ArrayList<>();
        // CookBook.getInstance().getGlobalRecipeList()
        // .size() --> this returns total number of global recipes
        for (Ingredient i: pantry.getPantryList()) {
            pantryItems.add(new Pair<>(i.getName(), i.getQuantity()));
            // Try populating array just for example
        }

        adapter = new ArrayAdapter<Pair<String, Integer>>(
                getActivity(), android.R.layout.simple_list_item_2,
                android.R.id.text1, pantryItems) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Pair<String, Integer> item = getItem(position);
                Ingredient ingredient = pantry.getIngredient(item.first);
                if (item != null) {
                    ((TextView) view.findViewById(android.R.id.text1)).setText(item.first);
                    ((TextView) view.findViewById(android.R.id.text2)).setText("Quantity: "
                            + item.second + ", Calories: "
                            + ingredient.getCaloriePerServing()
                            + ", Expiration Date: "
                            + ingredient.getExpirationDate());
                }
                return view;
            }
        };

        pantryListView.setAdapter(adapter);

        pantryListView.setOnItemClickListener((parent, view, position, id) -> {
            Fragment selectedFragment = new EditIngredientFragment();

            // Pass data to the new fragment
            Bundle args = new Bundle();
            Pair<String, Integer> ingredient = pantryItems.get(position);
            args.putString("INGREDIENT", ingredient.first);
            args.putInt("QUANTITY", ingredient.second);
            selectedFragment.setArguments(args);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, selectedFragment);
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