package com.ul.lj.si.vteamtrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import entities.Game;
import viewModels.GameModel;

public class CreateGameActivity extends AppCompatActivity {
    GameModel gameModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game);
        gameModel = new ViewModelProvider(this).get(GameModel.class);

    }
    public void createGame( View v){

         Game game = new Game();
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
             game.date=gameDate.getText().toString();
             game.time=gameTime.getText().toString();
             game.location=gameLocation.getText().toString();
             game.oponent =gameOponent.getText().toString();
             game.teamName=PreferenceData.getTeam(getApplicationContext());
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

    public void cancel(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
