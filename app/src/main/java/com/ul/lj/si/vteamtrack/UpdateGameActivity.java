package com.ul.lj.si.vteamtrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.ul.lj.si.vteamtrack.fragments.DatePickerFragment;
import com.ul.lj.si.vteamtrack.fragments.TimePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.Game;
import viewModels.GameModel;

public class UpdateGameActivity extends AppCompatActivity {
    private GameModel gameModel;
    private Game game;
    private EditText gameDate;
    private EditText gameLocation;
    private EditText gameTime;
    private EditText gameOponent;
    SimpleDateFormat sdf;
    SimpleDateFormat sdfTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_game);
        gameModel = new ViewModelProvider(this).get(GameModel.class);
        int gameId = getIntent().getIntExtra("game_id", 0);
        game = gameModel.getGame(gameId);

        gameDate=(EditText)findViewById(R.id.game_update_date);
        gameTime=(EditText)findViewById(R.id.game_update_time);
        gameLocation=(EditText)findViewById(R.id.game_update_location);
        gameOponent=(EditText)findViewById(R.id.game_update_oponent);

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdfTime = new SimpleDateFormat("HH:mm");
        Date utilDate = new Date(game.getDate().getTime());
        gameDate.setText(sdf.format(utilDate));

        Date utilTime = new Date(game.getTime().getTime());
        gameTime.setText(sdfTime.format(utilTime));

        gameLocation.setText(game.getLocation());
        gameOponent.setText(game.getOponent());
    }
    public void updateGame( View v) throws ParseException {

        int error = 1;

         if(!game.getDate().toString().equals(gameDate.getText().toString())){
            error=0;

        }
        else if(!game.getTime().toString().equals(gameTime.getText().toString())){
                 error=0;
        }

        else if(!game.getLocation().toString().equals(gameLocation.getText().toString())){
                 error=0;

        }
         else if(!game.getOponent().toString().equals(gameOponent.getText().toString())){
             error=0;

         }

        if(gameDate.getText().toString().trim().equals("")||
                gameLocation.getText().toString().trim().equals("")||
                 gameTime.getText().toString().trim().equals("")||
                gameOponent.getText().toString().trim().equals("")){
            error=1;
        }

        if(error==0){
            game.setDate(sdf.parse(gameDate.getText().toString()));
            game.setTime(sdfTime.parse(gameTime.getText().toString()));
            game.setLocation(gameLocation.getText().toString());
            game.setOponent(gameOponent.getText().toString());
            gameModel.updateGame(game);

            Toast.makeText(getApplicationContext(), "Game successfully updated", Toast.LENGTH_LONG).show();

            Intent data = new Intent();
            setResult(RESULT_OK, data);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Nothing updated. Fields can not be empty",
                    Toast.LENGTH_LONG).show();
            error=1;
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
    public void cancelUpdateGame(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
