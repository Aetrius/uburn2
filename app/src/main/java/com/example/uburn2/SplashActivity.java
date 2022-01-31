package com.example.uburn2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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

public class SplashActivity extends AppCompatActivity {

    private static boolean splashLoaded = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!splashLoaded) {
            setContentView(R.layout.activity_splash);
            setTitleText();
            int secondsDelayed = 5;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, secondsDelayed * 500);

            splashLoaded = true;
        }
        else {
            DatabaseHandler db = new DatabaseHandler(this);

            Intent goToMainActivity = new Intent(SplashActivity.this, MainActivity.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        }
    }

    private void setTitleText() {
        TextView textView = (TextView) findViewById(R.id.title_text);
        Spannable word = new SpannableString("u");

        word.setSpan(new ForegroundColorSpan(Color.rgb(249,87,56)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(word);
        Spannable wordTwo = new SpannableString("Burn");

        wordTwo.setSpan(new ForegroundColorSpan(Color.WHITE), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(wordTwo);
    }

}