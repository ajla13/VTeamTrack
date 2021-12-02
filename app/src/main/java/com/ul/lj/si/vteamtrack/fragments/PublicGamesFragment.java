package com.ul.lj.si.vteamtrack.fragments;

import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.PublicGameAdapter;
import com.ul.lj.si.vteamtrack.adapters.PublicTrainingsAdapter;

import java.util.ArrayList;

import entities.Game;
import entities.Team;
import entities.Training;
import viewModels.GameModel;
import viewModels.TeamModel;
import viewModels.TrainingModel;

public class PublicGamesFragment extends Fragment {
    private TeamModel teamModel;
    private GameModel gameModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.view_games, container, false);
        teamModel = new ViewModelProvider((FragmentActivity)getActivity()).get(TeamModel.class);
        gameModel = new ViewModelProvider((FragmentActivity) getActivity()).get(GameModel.class);
        RecyclerView gamesView = view.findViewById(R.id.rvGamesView);

        Spinner spin = (Spinner) view.findViewById(R.id.games_spinner);
        ArrayList<Team> publicTeams = (ArrayList<Team>) teamModel.getPublicTeams();
        String[] teamNames=new String[publicTeams.size()];
        for( int i =0; i<publicTeams.size(); i++){
            teamNames[i] = publicTeams.get(i).name;
        }
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<Game> gamesList = (ArrayList<Game>) gameModel.getPublicGames(spin.getSelectedItem().toString());
                PublicGameAdapter adapter = new PublicGameAdapter(gamesList,getActivity());
                gamesView.setAdapter(adapter);
                gamesView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, teamNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        return view;

    }
}
