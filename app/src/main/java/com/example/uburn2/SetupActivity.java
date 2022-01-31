package com.example.uburn2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uburn2.DatabaseHandler;
import com.example.uburn2.MainActivity;
import com.example.uburn2.R;
import com.example.uburn2.SplashActivity;

public class SetupActivity extends AppCompatActivity {

    private static boolean settingsSetup = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences settings = this.getSharedPreferences("UserInfo", 0);
        //oncreate check if setting exists, if not, create it and save it
        //settingsSetup = settings.getString("Name", "").toString();

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (mPrefs.getAll().containsKey("settingsSetup")) {
            DatabaseHandler db = new DatabaseHandler(this);

            Intent goToMainActivity = new Intent(SetupActivity.this, MainActivity.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        } else {
            Intent goToSetupActivity = new Intent(SetupActivity.this, SetupActivity.class);
            goToSetupActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToSetupActivity);
            finish();
        }

    }

    public void Save() {
        SharedPreferences settings = this.getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        /*editor.putString("Name",etName.getText().toString());
        //editor.putString("Gender",etGender.getText().toString());

        editor.putString("Gender",spinGender.getSelectedItem().toString());
        Log.d("Gender",spinGender.getSelectedItem().toString());
        editor.putString("GoalWeight",etGoalWeight.getText().toString());
        editor.putString("Height",etHeight.getText().toString());*/
        editor.commit();
    }
}
