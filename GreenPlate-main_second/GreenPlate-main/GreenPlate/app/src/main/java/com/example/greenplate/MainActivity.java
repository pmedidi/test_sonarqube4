package com.example.greenplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.greenplate.database.CookBook;
import com.example.greenplate.database.ShoppingList;
import com.example.greenplate.database.User;
import com.example.greenplate.database.Pantry;
import com.example.greenplate.ui.home.HomeFragment;
import com.example.greenplate.ui.ingredient.IngredientsFragment;
import com.example.greenplate.ui.inputmeal.InputMealFragment;
import com.example.greenplate.ui.profile.ProfileFragment;
import com.example.greenplate.ui.recipe.RecipesFragment;
import com.example.greenplate.ui.shoppinglist.ShoppingListFragment;
import com.example.greenplate.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navigationDrawer.setNavigationItemSelectedListener(this);

        binding.bottomNavigation.setBackground(null);
        //Button dataVisualButton = findViewById(R.id.dataButtonTest);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            boolean fragmentSelected = false;
            Fragment fragment = null;

            if (itemId == R.id.navigation_home) {
                fragment = new HomeFragment();
                fragmentSelected = true;
            } else if (itemId == R.id.navigation_input_meal) {
                fragment = new InputMealFragment();
                fragmentSelected = true;
            } else if (itemId == R.id.navigation_ingredient) {
                fragment = new IngredientsFragment();
                fragmentSelected = true;
            } else if (itemId == R.id.navigation_recipe) {
                fragment = new RecipesFragment();
                fragmentSelected = true;
            } else if (itemId == R.id.navigation_shopping_list) {
                fragment = new ShoppingListFragment();
                fragmentSelected = true;
            }

            if (fragmentSelected) {
                openFragment(fragment);
                int size = binding.navigationDrawer.getMenu().size();
                for (int i = 0; i < size; i++) {
                    MenuItem menuItem = binding.navigationDrawer.getMenu().getItem(i);
                    if (menuItem.getItemId() == R.id.navigation_my_profile) {
                        menuItem.setChecked(false);
                        break;
                    }
                }
            }

            return fragmentSelected;
        });


        auth = FirebaseAuth.getInstance();

        // Open the default fragment
        if (savedInstanceState == null) {
            openFragment(new HomeFragment());
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navigation_my_profile) {
            openFragment(new ProfileFragment());
            binding.navigationDrawer.getMenu().getItem(0).setChecked(true);
            binding.bottomNavigation.getMenu().setGroupCheckable(0, true, false);
            for (int i = 0; i < binding.bottomNavigation.getMenu().size(); i++) {
                binding.bottomNavigation.getMenu().getItem(i).setChecked(false);
            }
            binding.bottomNavigation.getMenu().setGroupCheckable(0, true, true);
        } else if (id == R.id.navigation_sign_out) {
            Toast.makeText(this, "Signed Out", Toast.LENGTH_SHORT).show();
            //clears user
            //User.resetUser();
            System.out.println(User.getInstance().getFirstName());
            System.out.println(User.getInstance().getLastName());
            System.out.println(User.getInstance().getWeight());
            System.out.println(User.getInstance().getGender());
            System.out.println(User.getInstance().getPassword());
            System.out.println(User.getInstance().getMealCalendar().get(29).size());
            System.out.println(User.getInstance().getMealCalendar());
            System.out.println(User.getInstance().getMonthlyCalories());
            User.resetInstance();
            Pantry.resetInstance();
            ShoppingList.resetInstance();

            //System.out.println(User.getInstance().getMealCalendar().get(29).get(0));

            CookBook.getInstance().printGlobalRecipeList();
            CookBook.resetCookBook();




            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this,
                    com.example.greenplate.ui.login.LoginActivity.class);
            finish();
            startActivity(intent);
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}