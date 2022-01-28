package com.example.uburn2.ui.goals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.uburn2.databinding.FragmentGoalBinding;
import com.example.uburn2.ui.goals.GoalViewModel;

public class GoalFragment extends Fragment {

    private GoalViewModel goalViewModel;
    private FragmentGoalBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalViewModel =
                new ViewModelProvider(this).get(GoalViewModel.class);

        binding = FragmentGoalBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGoal;
        goalViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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