package com.example.uburn2.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.uburn2.DatabaseHandler;
import com.example.uburn2.R;
import com.example.uburn2.Weight;
import com.example.uburn2.WeightAdapter;
import com.example.uburn2.databinding.FragmentHistoryBinding;
import com.example.uburn2.ui.history.HistoryViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private FragmentHistoryBinding binding;
    private ListView historyView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        historyView = (ListView)root.findViewById(R.id.historyListView);

        List<Weight> weightList = new ArrayList<Weight>();
        DatabaseHandler db = new DatabaseHandler(getContext());

        // Inserting Contacts
        //Log.d("Insert: ", "Inserting ..");
        weightList = db.getAllWeights();
        //your_array_list.add("foo");
        //your_array_list.add("bar");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        WeightAdapter arrayAdapter = new WeightAdapter(getContext(),weightList);

        historyView.setAdapter(arrayAdapter);

        //final TextView textView = binding.textHistory;
        historyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
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