package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.MainActivity;
import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.UpdateGameActivity;
import com.ul.lj.si.vteamtrack.fragments.GameAttendanceFragment;
import com.ul.lj.si.vteamtrack.fragments.GamesListFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entities.Game;
import viewModels.GameModel;


public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder>{

    private Activity activity;

    private List<Game> games;

    private GameModel gameModel;
    SimpleDateFormat sdf;
    SimpleDateFormat sdfTime;

    public class ViewHolder extends RecyclerView.ViewHolder{

    private TextView gameDate;
    private TextView gameTime;
    private TextView gameLocation;
    private  TextView gameOponent;
    private  ImageButton edit;
    private ImageButton delete;
    private ImageButton expand;
    private Button attendance;
    private LinearLayout toggleLayout;

        public ViewHolder(View itemView) {

        super(itemView);

        gameDate = (TextView) itemView.findViewById(R.id.item_game_date);
        gameTime = (TextView) itemView.findViewById(R.id.item_game_time);
        gameLocation = (TextView) itemView.findViewById(R.id.item_game_location);
        gameOponent = (TextView) itemView.findViewById(R.id.item_game_oponent);
        edit = (ImageButton) itemView.findViewById(R.id.btn_edit);
        delete = (ImageButton) itemView.findViewById(R.id.btn_delete);
        expand = (ImageButton) itemView.findViewById(R.id.btn_expand);
        attendance = itemView.findViewById(R.id.btn_attendance_game);
        toggleLayout = itemView.findViewById(R.id.item_game_secondlayout);
       }
    }

    public GamesAdapter(List<Game> games, Activity activity) {

        this.games = games;
        this.activity = activity;
    }

    @NonNull
    @Override
    public GamesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        gameModel = new ViewModelProvider((FragmentActivity) activity).get(GameModel.class);


        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_game, parent, false);

        // Return a new holder instance
        GamesAdapter.ViewHolder viewHolder = new GamesAdapter.ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull GamesAdapter.ViewHolder holder, int position) {
        Game game = games.get(position);
        TextView gameDate = holder.gameDate;
        TextView gameOponent = holder.gameOponent;
        TextView gameTime = holder.gameTime;
        TextView gameLocation = holder.gameLocation;
        ImageButton edit = holder.edit;
        Button attendance= holder.attendance;
        ImageButton expand = holder.expand;
        ImageButton delete = holder.delete;
        LinearLayout toggleLayout = holder.toggleLayout;

        gameOponent.setText(game.getOponent());
        gameLocation.setText(game.getLocation());

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(game.getDate().getTime());
        gameDate.setText(sdf.format(utilDate));

        sdfTime= new SimpleDateFormat("HH:mm");
        Date utilTime = new Date(game.getTime().getTime());
        gameTime.setText(sdfTime.format(utilTime));

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("gameId", game.getId());
                FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new GameAttendanceFragment();
                fragment.setArguments(bundle);
                ft.replace(R.id.nav_fragment, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });
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
                Intent intent = new Intent( activity.getApplicationContext(), UpdateGameActivity.class);
                intent.putExtra("game_id", game.getId());
                activity.startActivity(intent);
            }
        });
        if(PreferenceData.getUserRole(activity.getApplicationContext()).equals("player")){
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }



    }
    @Override
    public int getItemCount() {
        return games.size();
    }

}
