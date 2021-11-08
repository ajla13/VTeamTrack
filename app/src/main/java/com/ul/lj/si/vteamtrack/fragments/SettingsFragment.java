package com.ul.lj.si.vteamtrack.fragments;

import static java.time.ZonedDateTime.*;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import entities.Game;
import entities.Training;
import viewModels.GameModel;
import viewModels.TrainingModel;


public class SettingsFragment extends PreferenceFragmentCompat {

    private TrainingModel trainingModel;
    private GameModel gameModel;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
        SwitchPreferenceCompat toggleGameExpired = findPreference("gameExpiration");
        SwitchPreferenceCompat toggleTrainingExpired = findPreference("trainingExpiration");
        toggleTrainingExpired.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                PreferenceData.setTrainingEx(getActivity().getApplicationContext(), (Boolean) newValue);
                if((Boolean) newValue){

                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MONTH, -1);
                    Date result = cal.getTime();

                    trainingModel = new ViewModelProvider(getActivity()).get(TrainingModel.class);
                    List<Training> expired= trainingModel.getExpiredTrainings(result);
                    for (Training training : expired) {
                        trainingModel.deleteTraining(training);
                    }

                }
                return true;
            }

        });
        toggleGameExpired.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                PreferenceData.setGameEx(getActivity().getApplicationContext(), (Boolean) newValue);

                if(PreferenceData.getGamePref(getActivity().getApplicationContext())){
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MONTH, -1);
                    Date result = cal.getTime();

                    gameModel = new ViewModelProvider(getActivity()).get(GameModel.class);
                    List<Game> expired= gameModel.getExpiredGames(result);
                    for (Game game : expired) {
                        gameModel.deleteGame(game);
                    }
                }
                return true;
            }
        });
    }
}
