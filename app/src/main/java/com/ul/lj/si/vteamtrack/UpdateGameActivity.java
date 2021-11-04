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
        Date utilDate = new Date(game.date.getTime());
        gameDate.setText(sdf.format(utilDate));

        gameTime.setText(game.time);
        gameLocation.setText(game.location);
        gameOponent.setText(game.oponent);
    }
    public void updateGame( View v) throws ParseException {

        int error = 1;

         if(!game.date.toString().equals(gameDate.getText().toString())){
            error=0;

        }
        else if(!game.time.toString().equals(gameTime.getText().toString())){
                 error=0;
        }

        else if(!game.location.toString().equals(gameLocation.getText().toString())){
                 error=0;

        }
         else if(!game.oponent.toString().equals(gameOponent.getText().toString())){
             error=0;

         }

        if(gameDate.getText().toString().trim().equals("")||
                gameLocation.getText().toString().trim().equals("")||
                 gameTime.getText().toString().trim().equals("")||
                gameOponent.getText().toString().trim().equals("")){
            error=1;
        }

        if(error==0){
            game.date=sdf.parse(gameDate.getText().toString());
            game.time=gameTime.getText().toString();
            game.location=gameLocation.getText().toString();
            game.oponent=gameOponent.getText().toString();
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
    public void cancelUpdateGame(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
