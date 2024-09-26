package com.example.greenplate.ui.shoppinglist;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.greenplate.R;
import com.example.greenplate.database.Ingredient;
import com.example.greenplate.database.Pantry;
import com.example.greenplate.database.ShoppingList;
import com.example.greenplate.database.UserDatabase;
import com.example.greenplate.databinding.FragmentShoppingListBinding;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    private FragmentShoppingListBinding binding;
    private ArrayList<Pair<String, Integer>> shopItems;
    private ArrayAdapter<Pair<String, Integer>> adapter;

    private ArrayList<Boolean> selectedToBuy = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShoppingListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button addShopButton = binding.addShopButton;
        addShopButton.setOnClickListener(v -> {
            AddShopFragment addShopFragment = new AddShopFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, addShopFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        ListView shopListView = binding.shopListView;
        ShoppingList shop = ShoppingList.getInstance();
        shopItems = new ArrayList<>();
        selectedToBuy = new ArrayList<Boolean>();
        for (Ingredient i: shop.getShoppingList()) {
            shopItems.add(new Pair<>(i.getName(), i.getQuantity()));
            selectedToBuy.add((Boolean) false);
        }
        adapter = new ArrayAdapter<Pair<String, Integer>>(
                getActivity(), android.R.layout.simple_list_item_2,
                android.R.id.text1, shopItems) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext())
                            .inflate(R.layout.item_shopping_list, parent, false);
                }
                TextView itemName = convertView.findViewById(R.id.item_name);
                TextView itemDetails = convertView.findViewById(R.id.item_details);
                CheckBox itemCheckbox = convertView.findViewById(R.id.item_checkbox);

                Pair<String, Integer> item = getItem(position);
                Ingredient ingredient = shop.getIngredient(item.first);
                if (item != null && ingredient != null) {
                    itemName.setText(item.first);
                    itemDetails.setText("Quantity: " + item.second);
                }


                itemCheckbox.setOnCheckedChangeListener(new CompoundButton
                        .OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        selectedToBuy.set(position, isChecked);
                    }
                }
                );

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //shopListView.setAdapter(adapter);

                        Fragment selectedFragment = new EditShoppingIngredientFragment();

                        // Pass data to the new fragment
                        Bundle args = new Bundle();
                        Pair<String, Integer> ingredient = shopItems.get(position);
                        args.putString("INGREDIENT", ingredient.first);
                        args.putInt("QUANTITY", ingredient.second);
                        selectedFragment.setArguments(args);

                        FragmentTransaction transaction = getParentFragmentManager()
                                .beginTransaction();
                        transaction.replace(R.id.fragment_container, selectedFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });

                return convertView;
            }
        };
        shopListView.setAdapter(adapter);

        ArrayList<Ingredient> shoppingListItems = ShoppingList.getInstance().getShoppingList();

        Button buyItems = binding.buyButton;
        UserDatabase udb = UserDatabase.getInstance();
        Pantry pantry = Pantry.getInstance();
        buyItems.setOnClickListener(v -> {
            System.out.println(selectedToBuy);
            for (int i = 0; i < shoppingListItems.size(); i++) {
                // Clear out each item in the shopping list:
                // 1. Remove it from the shopping list array singleton
                // 2. Remove it from the database shopping list
                // 3. Remove the flyweight boolean corresponding to it

                // If a given index i was marked for removal, then:
                if (selectedToBuy.get(i)) {
                    System.out.println("Removed: " + shoppingListItems.get(i).getName());

                    Ingredient curr = shoppingListItems.get(i);
                    int indexOfDupeIngredient = pantry.getIngredientIndex(curr.getName());

                    // First add the items into Pantry and database
                    if (indexOfDupeIngredient >= 0) {
                        System.out.println("Updated a current ingredient");
                        // If the index exists, then just need to update the ingredient data

                        // We can do this by pulling out the object
                        // and calling a setQuantity function
                        int oldIngredientQuantity = pantry.getPantryList().
                                get(indexOfDupeIngredient).getQuantity();

                        System.out.println(oldIngredientQuantity + " + " + curr.getQuantity());
                        udb.writeNewIngredient(curr.getName(), curr.getQuantity(),
                                curr.getCaloriePerServing());


                        pantry.getPantryList().set(indexOfDupeIngredient,
                                new Ingredient(curr.getName(),
                                        curr.getQuantity() + oldIngredientQuantity,
                                        curr.getCaloriePerServing()));
                        // Then update the database my going to that child node and set value

                    } else {
                        System.out.println(curr.getQuantity());
                        udb.writeNewIngredient(curr.getName(), curr.getQuantity(),
                                curr.getCaloriePerServing());
                        //pantry.addIngredient(shoppingListItems.get(i));
                        System.out.println(curr.getQuantity());

                    }

                    // Then remove them from our shopping lists and database
                    udb.removeFromShoppinglist(shoppingListItems.get(i).getName());
                    shoppingListItems.remove(i);
                    selectedToBuy.remove(i);
                    i--; // Subtract the index by one so that we don't skip an item
                }
            }

            ShoppingListFragment refresh = new ShoppingListFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, refresh);
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