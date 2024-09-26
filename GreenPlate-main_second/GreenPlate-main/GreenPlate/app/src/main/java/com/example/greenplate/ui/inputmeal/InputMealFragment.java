package com.example.greenplate.ui.inputmeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.greenplate.R;
import com.example.greenplate.database.UserDatabase;
import com.example.greenplate.databinding.FragmentInputMealBinding;

import java.util.Calendar;

public class InputMealFragment extends Fragment {
    private FragmentInputMealBinding binding;
    private View root;

    private Button data;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InputMealViewModel inputMealViewModel =
                new ViewModelProvider(this).get(InputMealViewModel.class);

        binding = FragmentInputMealBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        TextView textView = binding.userInfo;
        inputMealViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Button inputMealButton = binding.trackMeal;
        inputMealButton.setOnClickListener(v -> {
            String meal = binding.meal.getText().toString();
            String calorieCount = binding.calorieCount.getText().toString();
            if (!meal.equals("") && !calorieCount.equals("")) {
                // When meal tracking button is pressed...
                int cCount = Integer.parseInt(calorieCount);
                binding.meal.setText("");
                binding.calorieCount.setText("");
                UserDatabase udb = UserDatabase.getInstance();

                // First check if that entry does not exist
                // (if the entry was null in the dictionary)
                // If it was null, we add a new entry on dictionary
                //String mealDictionaryEntry = String.valueOf
                // (dataSnapshot.child("mealCalendar").child
                // (currentDay).child(Integer.toString(0)).getValue());

                // First write a new meal in the meal
                // dictionary to store information about that specific meal
                // This is a function in the User
                // Database that takes in a meal name and calorie count
                udb.writeNewMeal(meal, Integer.parseInt(calorieCount));
                // Write a new meal in the meal dictionary

                // Next, we need to add that meal to the User 2D log of meals
                // And increment the total calorie
                // count for today (which is done inside track new meal)


                Calendar date = Calendar.getInstance();
                String currentDate = date.getTime().toString()
                        .substring(0, date.getTime().toString().length() - 18);
                // Pull the current date

                // Add the meal to the current day of the User's meal calendar
                udb.trackNewMeal(meal, Integer.parseInt(calorieCount), currentDate);

                // Refresh the fragment to update the
                InputMealFragment refresh = new InputMealFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, refresh);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

        //data = root.findViewById(R.id.dataButton);
        Button dataButton = binding.dataButton;
        dataButton.setOnClickListener(v -> {
            DataVisualFragment.setGraph(1);
            DataVisualFragment chartFragment = new DataVisualFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, chartFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        Button dataButton2 = binding.dataButton2;
        dataButton2.setOnClickListener(v -> {
            DataVisualFragment.setGraph(2);
            DataVisualFragment chartFragment = new DataVisualFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, chartFragment);
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