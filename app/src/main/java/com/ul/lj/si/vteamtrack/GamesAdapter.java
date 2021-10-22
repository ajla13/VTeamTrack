package com.ul.lj.si.vteamtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entities.Game;
import entities.User;

public class GamesAdapter extends ArrayAdapter<Game> {


    private List<Game> games;

    public GamesAdapter(Context context, ArrayList<Game> games) {
        super(context, 0, games);
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
        // Populate the data into the template view using the data object
        gameDate.setText(game.date);
        gameTime.setText(game.time);
        gameLocation.setText(game.location);
        // Return the completed view to render on screen
        return convertView;
    }
}
