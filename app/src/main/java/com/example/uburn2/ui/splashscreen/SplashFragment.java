package com.example.uburn2.ui.splashscreen;

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

import com.example.uburn2.databinding.FragmentSplashBinding;
import com.example.uburn2.ui.splashscreen.SplashViewModel;

public class SplashFragment extends Fragment {

    private SplashViewModel splashViewModel;
    private FragmentSplashBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);

        binding = FragmentSplashBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSplash;

        splashViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("Test")
                ;
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