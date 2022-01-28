package com.example.uburn2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;
//https://stackoverflow.com/questions/13522321/how-to-put-my-custom-number-picker-in-a-dialog
//https://developer.android.com/guide/topics/ui/dialogs


public class NumberPickerCustomDialog extends DialogFragment {

    Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get context
        context = getActivity().getApplicationContext();
        // make dialog object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // get the layout inflater
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate our custom layout for the dialog to a View
        View view = li.inflate(R.layout.number_picker, null);
        // inform the dialog it has a custom View
        builder.setView(view);
        // and if you need to call some method of the class
        NumberPicker myView = (NumberPicker) view.findViewById(R.id.numPicker);
        //myView.doSome("stuff");
        // create the dialog from the builder then show
        return builder.create();
    }

}
