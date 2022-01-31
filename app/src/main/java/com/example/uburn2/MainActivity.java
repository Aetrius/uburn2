package com.example.uburn2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.uburn2.ui.splashscreen.SplashFragment;
import com.example.uburn2.ui.splashscreen.SplashViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.uburn2.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int DATABASE_VERSION = 1;
    //SplashViewModel mViewModel = new ViewModelProvider(this).get(SplashViewModel.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_history, R.id.navigation_notifications, R.id.navigation_settings, R.id.navigation_measurement, R.id.navigation_goals
        ).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        DatabaseHandler db = new DatabaseHandler(this);
        //dbh.deleteDatabase();

        //DatabaseHandler dbh = new DatabaseHandler(this);
        // Inserting Contacts
        //Log.d("Insert: ", "Inserting ..");
        //db.addGoal(new Goal(200, 150, "1/24/2022"));
        //db.addGoal(new Goal(180, 150, "2/30/2022"));

        //db.addWeight(new Weight(150, "1/24/2022"));
        //db.addWeight(new Weight(155, "1/25/2022"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Goal> goals = db.getAllGoals();



        for (Goal gl : goals) {
            String log = "Id: " + gl.getID() + " ,Weight: " + gl.getWeight() + " ,Goal Weight: " +
                    gl.getGoalWeight() + " , Goal Date: " + gl.getGoalDate();
            // Writing Goals to log
            Log.d("Weight: ", log);
        }

        Log.d("Reading: ", "Reading all weight history..");
        List<Weight> weights = db.getAllWeights();

        for (Weight weight : weights) {
            String log = "Id: " + weight.getID() + " ,Weight: " + weight.getWeight() + " ,Weight Date: " +
                    weight.getWeightDate();
            // Writing Goals to log
            Log.d("Weight: ", log);
        }
    }
}