package com.example.uburn2.ui.settings;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.uburn2.R;

import com.example.uburn2.Weight;
import com.example.uburn2.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;
    private Button btnEdit;
    private Button btnSave;
    private Button btnCancel;

    private EditText etName;
    //private EditText etGender;

    private EditText etGoalWeight;
    private EditText etHeight;

    private Spinner spinGender;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        btnEdit = (Button) root.findViewById(R.id.btnSettingsEdit);
        btnSave = (Button) root.findViewById(R.id.btnSettingsSave);
        btnCancel = (Button) root.findViewById(R.id.btnSettingsCancel);

        etName = (EditText) root.findViewById(R.id.nameSetting);
        //etGender = (EditText) root.findViewById(R.id.genderSetting);
        etGoalWeight = (EditText) root.findViewById(R.id.goalWeightSetting);
        etHeight = (EditText) root.findViewById(R.id.heightSetting);


        spinGender = (Spinner) root.findViewById(R.id.genderSetting);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.genders_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinGender.setAdapter(adapter);

        Refresh();
        DisableFields();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnableFields();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
                DisableFields();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Refresh();
                DisableFields();
            }
        });

        //final TextView textView = binding.textSettings;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }

    public void Refresh() {
        SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        etName.setText(settings.getString("Name", "").toString());
        //etGender.setText(settings.getString("Gender", "").toString());
        spinGender.setSelected(true);

        Log.d("Gender - Saved",settings.getString("Gender", "").toString());
        spinGender.setSelection(
                GetGenderPosition(settings.getString("Gender", "").toString())
        );
        etGoalWeight.setText(settings.getString("GoalWeight", "").toString());
        etHeight.setText(settings.getString("Height", "").toString());
    }

    public void Save() {
        SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Name",etName.getText().toString());
        //editor.putString("Gender",etGender.getText().toString());

        editor.putString("Gender",spinGender.getSelectedItem().toString());
        Log.d("Gender",spinGender.getSelectedItem().toString());
        editor.putString("GoalWeight",etGoalWeight.getText().toString());
        editor.putString("Height",etHeight.getText().toString());
        editor.commit();
    }

    public void PopulateData() {
        SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Name","Test Name");
        //editor.putString("Gender","Male");
        editor.putString("Gender", "Male");
        editor.putString("GoalWeight","250 lbs");
        editor.putString("Height","6'1");
        editor.commit();
    }

    public void EnableFields() {
        etName.setEnabled(true);
        spinGender.setEnabled(true);
        etGoalWeight.setEnabled(true);
        etHeight.setEnabled(true);

    }

    public void DisableFields() {
        etName.setEnabled(false);
        spinGender.setEnabled(false);
        etGoalWeight.setEnabled(false);
        etHeight.setEnabled(false);
    }

    public int GetGenderPosition(String value) {
        //Log.d("gender position", value);
        if (value.equals("Male")) {
            return 0;
        } else if (value.equals("Female")) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}