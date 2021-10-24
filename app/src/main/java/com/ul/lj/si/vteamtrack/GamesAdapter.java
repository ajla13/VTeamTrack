package com.ul.lj.si.vteamtrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import entities.Game;
import viewModels.GameModel;


public class GamesAdapter extends ArrayAdapter<Game> {

    private Activity activity;

    private List<Game> games;

    private GameModel gameModel;

    public GamesAdapter(Activity activity,Context context, ArrayList<Game> games) {

        super(context, 0, games);
        this.activity = activity;
        gameModel = new ViewModelProvider((FragmentActivity) context).get(GameModel.class);
    }

    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
         Game game = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_game, parent, false);
        }
        // Lookup view for data population
        TextView gameDate = (TextView) convertView.findViewById(R.id.item_game_date);
        TextView gameTime = (TextView) convertView.findViewById(R.id.item_game_time);
        TextView gameLocation = (TextView) convertView.findViewById(R.id.item_game_location);
        TextView gameOponent = (TextView) convertView.findViewById(R.id.item_game_oponent);
        // Populate the data into the template view using the data object
        gameDate.setText(game.date);
        gameTime.setText(game.time);
        gameLocation.setText(game.location);
        gameOponent.setText(game.oponent);

        ImageButton edit = (ImageButton) convertView.findViewById(R.id.btn_edit);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.btn_delete);
        ImageButton expand = (ImageButton) convertView.findViewById(R.id.btn_expand);

        LinearLayout toggleLayout = (LinearLayout) convertView.findViewById(R.id.item_game_secondlayout);


        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float deg = expand.getRotation() + 180F;
                expand.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());

                if(toggleLayout.getVisibility()==View.GONE){
                    toggleLayout.setVisibility(View.VISIBLE);
                    gameLocation.setVisibility((gameLocation.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    gameDate.setVisibility((gameDate.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    gameTime.setVisibility((gameTime.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);

                }
                else {
                    gameLocation.setVisibility((gameLocation.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    gameDate.setVisibility((gameDate.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);

                    gameTime.setVisibility((gameTime.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);

                    toggleLayout.setVisibility(View.GONE);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameModel.deleteGame(game);
                Toast.makeText(activity.getApplicationContext(), "Game successfully deleted",
                        Toast.LENGTH_LONG).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity act = new MainActivity();
                GamesListFragment gamesListFragment = new GamesListFragment();
                Intent intent = new Intent( getContext(), UpdateGameActivity.class);
                intent.putExtra("game_id", game.id);
                activity.startActivity(intent);
            }
        });
        if(PreferenceData.getUserRole(activity.getApplicationContext()).equals("player")){
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }
        // Return the completed view to render on screen
        return convertView;
    }

}
