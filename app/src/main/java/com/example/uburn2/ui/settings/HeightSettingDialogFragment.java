package com.example.uburn2.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.R.*;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.uburn2.R;

import java.util.ArrayList;
import java.util.List;

public class HeightSettingDialogFragment extends DialogFragment {

    private Spinner spinnerHeight;
    private Button selectionBtn;



    public void recordHeight() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        //int storedPreference = preferences.getInt("height", 0);
        int value = Integer.parseInt(spinnerHeight.getSelectedItem().toString());

        editor.putInt("height", value); // value to store
        editor.commit();

        dismiss();
        //FragmentManager fm = getParentFragmentManager();
        //fm.popBackStackImmediate();
        //Fragment frag = manager.findFragmentByTag("fragment")
    }

    public HeightSettingDialogFragment() {}

    public static HeightSettingDialogFragment newInstance(String title) {
        HeightSettingDialogFragment frag = new HeightSettingDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        return inflater.inflate(R.layout.height_setting_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerHeight = (Spinner) view.findViewById(R.id.spinnerHeight);
        selectionBtn = (Button) view.findViewById(R.id.btnOk);

        List<String> list = new ArrayList<String>();

        for (int i = 32; i <= 100; i++) {
            list.add("" + i);
        }

        selectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordHeight();
            }
        });


        ArrayAdapter<String> h1 = new ArrayAdapter<String>(getContext(), layout.simple_list_item_1, list);
        spinnerHeight.setAdapter(h1);

        String title = getArguments().getString("title", "Enter a height");
        getDialog().setTitle(title);
        spinnerHeight.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

}
