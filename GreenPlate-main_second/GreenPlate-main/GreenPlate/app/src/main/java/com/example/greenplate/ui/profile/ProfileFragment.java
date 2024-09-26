package com.example.greenplate.ui.profile;

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
import com.example.greenplate.database.User;
import com.example.greenplate.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        User user = User.getInstance();
        String userHeight = String.valueOf(user.getHeight());
        String userWeight = String.valueOf(user.getWeight());
        String userGender = user.getGender();

        binding.userHeight.setText("Height: " + userHeight + " centimeters");
        binding.userWeight.setText("Weight: " + userWeight + " kilograms");
        binding.userGender.setText("Gender: " + userGender);

        Button dataButton = binding.editButton;
        dataButton.setOnClickListener(v -> {
            EditStatsFragment editStatsFragment = new EditStatsFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, editStatsFragment);
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