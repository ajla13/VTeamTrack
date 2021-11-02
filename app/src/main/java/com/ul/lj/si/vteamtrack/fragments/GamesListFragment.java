package com.ul.lj.si.vteamtrack.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ul.lj.si.vteamtrack.CreateGameActivity;
import com.ul.lj.si.vteamtrack.adapters.GamesAdapter;
import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import java.util.ArrayList;
import java.util.List;

import entities.Game;
import viewModels.GameModel;

public class GamesListFragment extends Fragment {
    GameModel gameModel;
    ListView listView;
    GamesAdapter gameAdapter;
    FloatingActionButton createGame;
    public ActivityResultLauncher<Intent> launchActivity;
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

        gameModel = new ViewModelProvider(this).get(GameModel.class);

        ArrayList<Game> arrayOfGames = (ArrayList<Game>) gameModel.getGames().getValue();
        listView = (ListView)view.findViewById(R.id.lvSchedule);
        listView.setAdapter(gameAdapter);

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

            gameAdapter = new GamesAdapter(getActivity(),getActivity(), arrayOfGames);
            listView.setAdapter(gameAdapter);
        }else{
            Log.d("gwyd","game list was null");
            gameAdapter = new GamesAdapter(getActivity(),getActivity(),new ArrayList<Game>());
            listView.setAdapter(gameAdapter);
        }

        gameModel.getGames().observe(getViewLifecycleOwner(), new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> games) {
                if (games != null) {
                    gameAdapter.setGames(games);
                    gameAdapter.clear();
                    gameAdapter.addAll(games);
                } else {
                    Log.d("gwyd", "no games found in db");
                    gameAdapter.setGames(new ArrayList<Game>());
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
