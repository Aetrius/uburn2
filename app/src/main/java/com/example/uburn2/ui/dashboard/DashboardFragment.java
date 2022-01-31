package com.example.uburn2.ui.dashboard;

import android.graphics.Color;
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

import com.example.uburn2.DatabaseHandler;
import com.example.uburn2.R;
import com.example.uburn2.Weight;
import com.example.uburn2.databinding.FragmentDashboardBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Date;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private LineChart lineChart;
    private LineData lineData;
    private LineDataSet lineDataSet;
    private ArrayList lineEntries;
    private TextView weightCardData;
    private TextView bmiCardData;
    private TextView avgWeeklyLossCardData;
    private TextView weightLossToDateCardData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lineChart = root.findViewById(R.id.lineChart);

        weightCardData = (TextView) root.findViewById(R.id.weightCardData);
        bmiCardData = (TextView) root.findViewById(R.id.bmiCardData);
        avgWeeklyLossCardData = (TextView) root.findViewById(R.id.avgWeeklyLossCardData);
        weightLossToDateCardData = (TextView) root.findViewById(R.id.weightLossToDateCardData);

        getEntries();
        lineDataSet = new LineDataSet(lineEntries, "");
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineDataSet.setColors(Color.CYAN);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueTextSize(18f);
        lineChart.setTouchEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        //lineChart.enableScroll();
       //updateUIMetrics();

        //final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                updateUIMetrics();
                //textView.setText(s);
            }
        });
        return root;
    }

    private void getEntries() {
        lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(1f, 250));
        lineEntries.add(new Entry(2f, 255));
        lineEntries.add(new Entry(3f, 254));
        lineEntries.add(new Entry(4f, 255));
        //lineEntries.add(new Entry(""))
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateUIMetrics() {
        DatabaseHandler db = new DatabaseHandler(getContext());

        // Inserting Contacts
        //Log.d("Insert: ", "Inserting ..");
        //db.addWeight(new Weight(Double.parseDouble(etWeight.getText().toString()), btnDatePicker.getText().toString()));
        db.addWeight(new Weight(250, new Date("1/30/2022")));
        //db.addWeight(new Weight(251, new Date("1/29/2022")));
        //db.addWeight(new Weight(252, new Date("1/28/2022")));
        //db.addWeight(new Weight(253, new Date("1/27/2022")));

        weightCardData.setText(db.getWeight() + " lbs");
        bmiCardData.setText(Double.toString(db.getBMI(getContext())));
        avgWeeklyLossCardData.setText(Double.toString(db.getAvgWeeklyWeightLoss()) + " lbs");
        weightLossToDateCardData.setText(Double.toString(db.getWeightLossToDate()) + " lbs");
    }
}