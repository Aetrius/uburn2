package com.example.uburn2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class WeightAdapter extends ArrayAdapter<Weight> {

    public WeightAdapter(Context context, List<Weight> weights) {
        super(context, 0, weights);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Weight weight = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_layout,parent, false);
        }

        TextView tvWeight = (TextView) convertView.findViewById(R.id.tvWeight);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        tvWeight.setText(Double.toString(weight.getWeight()));
        tvDate.setText(sdf.format(weight.getWeightDate()));
        return convertView;
    }


}
