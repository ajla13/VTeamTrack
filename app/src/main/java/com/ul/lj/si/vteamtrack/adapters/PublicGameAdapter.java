package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entities.Game;
import entities.Training;


public class PublicGameAdapter extends RecyclerView.Adapter<PublicGameAdapter.ViewHolder> {

    private Activity activity;
    private List<Game> games;

    public PublicGameAdapter(List<Game> games, Activity activity) {
        this.games=games;
        this.activity = activity;


    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView date;
        private TextView location;
        private TextView time;
        private TextView oponent;


        public ViewHolder(View itemView) {

            super(itemView);

            date = (TextView) itemView.findViewById(R.id.item_public_game_date);
            location = (TextView) itemView.findViewById(R.id.item_public_game_location);
            time = (TextView) itemView.findViewById(R.id.item_public_game_time);
            oponent = (TextView) itemView.findViewById(R.id.item_public_game_oponent);


        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.public_games_item, parent, false);

        // Return a new holder instance
        PublicGameAdapter.ViewHolder viewHolder = new PublicGameAdapter.ViewHolder((contactView));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = games.get(position);
        TextView gameDate = holder.date;
        TextView gameLocation = holder.location;
        TextView gameTime = holder.time;
        TextView gameOponent = holder.oponent;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(game.getDate().getTime());
        gameDate.setText(sdf.format(utilDate));

        SimpleDateFormat sdfTime= new SimpleDateFormat("HH:mm");
        Date utilTime = new Date(game.getTime().getTime());
        gameTime.setText(sdfTime.format(utilTime));

        gameLocation.setText(game.getLocation());
        gameOponent.setText(game.getOponent());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
