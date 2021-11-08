package com.ul.lj.si.vteamtrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.ul.lj.si.vteamtrack.fragments.DatePickerFragment;
import com.ul.lj.si.vteamtrack.fragments.TimePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entities.Game;
import entities.Team;
import viewModels.GameModel;
import viewModels.TeamModel;

public class CreateGameActivity extends AppCompatActivity {
    GameModel gameModel;
    TeamModel teamModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game);
        gameModel = new ViewModelProvider(this).get(GameModel.class);
        teamModel = new ViewModelProvider(this).get(TeamModel.class);

    }
    public void createGame( View v) throws ParseException {


         int error = 0;
        EditText gameOponent=(EditText)findViewById(R.id.game_oponent);
        if(gameOponent.getText().toString().trim().equals("")){
            gameOponent.setError("Game oponent is required!");
            error=1;
        }

         EditText gameDate=(EditText)findViewById(R.id.game_date);
         if(gameDate.getText().toString().trim().equals("")){
             gameDate.setError("Game date is required!");
             error=1;
         }


         EditText gameTime=(EditText)findViewById(R.id.game_time);
         if(gameTime.getText().toString().trim().equals("")){
             gameTime.setError("Game time is required!");
             error=1;
         }


         EditText gameLocation=(EditText)findViewById(R.id.game_location);
         if(gameLocation.getText().toString().trim().equals("")){
            gameLocation.setError("Game location is required!");
            error=1;
         }
         if(error==0){
             Date date=new SimpleDateFormat("dd/MM/yyyy").parse(gameDate.getText().toString());
             Date time=new SimpleDateFormat("HH:mm").parse(gameTime.getText().toString());
             String location=gameLocation.getText().toString();
             String oponent =gameOponent.getText().toString();
             ArrayList attendance = new ArrayList<>();
             String teamName=PreferenceData.getTeam(getApplicationContext());
             Team teamGame = teamModel.getTeam(teamName);
             Game game = new Game(teamGame.getId(),date, time, location, teamName, attendance, oponent);
             Game result = gameModel.createGame(game);

             if(result!= null){
                 Toast.makeText(getApplicationContext(), "Game successfully created", Toast.LENGTH_LONG).show();
             }
             Intent data = new Intent();
             setResult(RESULT_OK, data);
             finish();
         }
         else {
             Toast.makeText(getApplicationContext(), "Please fill out all required fields.", Toast.LENGTH_LONG).show();
             error=0;
         }

    }
    public void showDatePickerDialog(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("viewId",v.getId());
        bundle.putString("source", "editText");
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "datePicker");


    }
    public void showTimePickerDialog(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("viewId",v.getId());
        bundle.putString("source", "editText");
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public void cancel(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
