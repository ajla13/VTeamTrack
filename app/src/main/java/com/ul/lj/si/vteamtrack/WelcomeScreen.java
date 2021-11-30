package com.ul.lj.si.vteamtrack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import database.AppDatabase;
import entities.Fee;
import entities.FeeMonth;
import entities.Game;
import entities.Team;
import entities.Training;
import entities.User;
import viewModels.FeeModel;
import viewModels.FeeMonthModel;
import viewModels.GameModel;
import viewModels.TeamModel;
import viewModels.TrainingModel;
import viewModels.UserModel;

public class WelcomeScreen extends AppCompatActivity {

    private TrainingModel trainingModel;
    private GameModel gameModel;
    private FeeMonthModel feeMonthModel;
    private TeamModel teamModel;
    private UserModel userModel;
    private FeeModel feeModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDatabase db = AppDatabase.getInstance(getApplication());
        final Intent[] intent = new Intent[1];
        setContentView(R.layout.splash_screen);

        feeMonthModel = new ViewModelProvider(this).get(FeeMonthModel.class);
        teamModel = new ViewModelProvider(this).get(TeamModel.class);
        userModel = new ViewModelProvider(this).get(UserModel.class);
        feeModel = new ViewModelProvider(this).get(FeeModel.class);

        SharedPreferences preferences=PreferenceData.getSharedPreferences(getApplication());
        if(PreferenceData.getTrainingPref(getApplicationContext())){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            Date result = cal.getTime();

            trainingModel = new ViewModelProvider(this).get(TrainingModel.class);
            List<Training> expired= trainingModel.getExpiredTrainings(result);
            for (Training training : expired) {
                trainingModel.deleteTraining(training);
            }
        }
        if(PreferenceData.getGamePref(getApplicationContext())){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            Date result = cal.getTime();

            gameModel = new ViewModelProvider(this).get(GameModel.class);
            List<Game> expired= gameModel.getExpiredGames(result);
            for (Game game : expired) {
                gameModel.deleteGame(game);
            }
        }

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
