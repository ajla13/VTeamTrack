package com.ul.lj.si.vteamtrack.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ul.lj.si.vteamtrack.CreateGameActivity;
import com.ul.lj.si.vteamtrack.adapters.GamesAdapter;
import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.TrainingAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import entities.Game;
import viewModels.GameModel;

public class GamesListFragment extends Fragment {
    private GameModel gameModel;
    private GamesAdapter gameAdapter;
    private FloatingActionButton createGame;
    private ActivityResultLauncher<Intent> launchActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.listview_schedule, container, false);
        RecyclerView rvGames = (RecyclerView) view.findViewById(R.id.rvSchedule);

        gameModel = new ViewModelProvider(this).get(GameModel.class);

        ArrayList<Game> arrayOfGames = (ArrayList<Game>) gameModel.getGames().getValue();



        launchActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();

                        }
                    }
                });

        if (arrayOfGames!= null){
            Collections.sort(arrayOfGames, new Comparator<Game>() {
                public int compare(Game game1, Game game2) {
                    if (game1.getDate() == null || game2.getDate() == null)
                        return 0;
                    return game1.getDate().compareTo(game2.getDate());
                }
            });
            gameAdapter = new GamesAdapter(arrayOfGames, getActivity());
            rvGames.setAdapter(gameAdapter);
            rvGames.setLayoutManager(new LinearLayoutManager(getActivity()));

        }else{
            Log.d("gwyd","game list was null");
            gameAdapter = new GamesAdapter(new ArrayList<Game>(), getActivity());
            rvGames.setAdapter(gameAdapter);
            rvGames.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        gameModel.getGames().observe(getViewLifecycleOwner(), new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> games) {
                if (games != null) {
                    Collections.sort(games, new Comparator<Game>() {
                        public int compare(Game game1, Game game2) {
                            if (game1.getDate() == null || game2.getDate() == null)
                                return 0;
                            return game1.getDate().compareTo(game2.getDate());
                        }
                    });
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
                    gameAdapter = new GamesAdapter(games, getActivity());
                    rvGames.setAdapter(gameAdapter);
                    rvGames.setLayoutManager(new LinearLayoutManager(getActivity()));
                } else {
                    Log.d("gwyd", "no games found in db");
                    gameAdapter = new GamesAdapter(new ArrayList<Game>(), getActivity());
                    rvGames.setAdapter(gameAdapter);
                    rvGames.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                if(games.isEmpty()){
                    TextView empty = view.findViewById(R.id.empty_view_game);
                    rvGames.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
                else {
                    TextView empty = view.findViewById(R.id.empty_view_game);
                    rvGames.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
            }
        });

        createGame=view.findViewById(R.id.fab);
        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateGameActivity.class);
                launchActivity.launch(intent);
            }
        });
        if(PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("player")){
            createGame.setVisibility(View.GONE);
        }
        return view;

    }



}
