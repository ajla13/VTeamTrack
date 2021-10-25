package com.ul.lj.si.vteamtrack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent[] intent = new Intent[1];
        setContentView(R.layout.splash_screen);
        SharedPreferences preferences=PreferenceData.getSharedPreferences(getApplication());
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(3*1000);

                    if(PreferenceData.getUserLoggedInStatus(getApplicationContext())){
                        intent[0] = new Intent(getApplicationContext(), MainActivity.class);
                    }
                    else {
                        intent[0] = new Intent(getApplicationContext(), Login.class);
                    }

                } catch (Exception e) {
                }
                startActivity(intent[0]);
                finish();
            }
        };
        background.start();

    }
}
