package com.example.uburn2.ui.measurement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.graphics.Camera;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.uburn2.NumberPickerCustomDialog;
import com.example.uburn2.R;
import com.example.uburn2.databinding.FragmentMeasurementBinding;

import java.util.Calendar;
import java.util.List;

public class MeasurementFragment extends Fragment {

    private MeasurementViewModel measurementViewModel;
    private FragmentMeasurementBinding binding;
    private DatePickerDialog picker;
    private NumberPicker numPicker;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageButton btnCaptureImage;
    private ImageButton btnDeleteImage;
    private Button btnDatePicker;
    private Button btnWeightPicker;
    private View RootView;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView personImage;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        measurementViewModel =
                new ViewModelProvider(this).get(MeasurementViewModel.class);

        binding = FragmentMeasurementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //setDefaultImage();

        //RootView = inflater.inflate(R.layout.fragment_measurement, container, false);
        btnCaptureImage = (ImageButton) root.findViewById(R.id.capture_image);
        btnDeleteImage = (ImageButton) root.findViewById(R.id.delete_image);
        personImage = (ImageView) root.findViewById(R.id.personImage);
        btnDatePicker = (Button) root.findViewById(R.id.datePicker);
        btnWeightPicker = (Button) root.findViewById(R.id.weightPicker);

        setDefaultImage();
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "TEST", Toast.LENGTH_SHORT).show();
                Log.d("Button Press: ", "On Click Triggered");
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }
            }
        });

        btnDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultImage();
            }
        });

        btnWeightPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                picker = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                btnDatePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }

        });



        //final TextView textView = binding.textMeasurement;
        measurementViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        return root;
    }

    public void setDefaultImage() {
        personImage.setImageResource(R.drawable.ic_baseline_person_24);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        //Bitmap photo = (Bitmap) data.getExtras().get("data");
        //personImage.setImageBitmap(photo);
        if (resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            personImage.setImageBitmap(rotateImage(photo, 270));
            Log.d("Retreived image: ", "onactivity Result");
        } else {
            Log.d("Failure: ", "onactivity Result");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }



}