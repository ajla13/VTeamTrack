package com.ul.lj.si.vteamtrack.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.GameAttendanceAdapter;

import java.util.ArrayList;
import java.util.List;

import entities.Game;
import entities.Training;
import entities.User;
import viewModels.GameModel;
import viewModels.TrainingModel;
import viewModels.UserModel;

public class GameAttendanceFragment extends Fragment {

    private GameModel gameModel;
    private UserModel userModel;
    private GameAttendanceAdapter gameAttendanceAdapter;
    private Game game;
    private ArrayList<User> arrayOfUsers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.listview, container, false);

        int gameId = this.getArguments().getInt("gameId");

        userModel = new ViewModelProvider(this).get(UserModel.class);
        gameModel = new ViewModelProvider(this).get(GameModel.class);

        game = gameModel.getGame(gameId);
        arrayOfUsers = new ArrayList<>();
        if(PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("supervisor")){
            User currentUser= userModel.getUser(PreferenceData.getLoggedInUser(getActivity().getApplicationContext()));
            User playerUser = userModel.checkUserCred(currentUser.getPlayerEmail(),currentUser.getTeamName());
            arrayOfUsers.add(playerUser);

        }
        else {
            ArrayList<User> arrayOfUsers = (ArrayList<User>) userModel.getPlayers().getValue();

        }
        RecyclerView rvUsers = (RecyclerView) view.findViewById(R.id.rvUsers);


        if (arrayOfUsers != null){

            gameAttendanceAdapter = new GameAttendanceAdapter(arrayOfUsers, getActivity(), game);
            rvUsers.setAdapter(gameAttendanceAdapter);
            rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            Log.d("gwyd","user list was null");
            gameAttendanceAdapter = new GameAttendanceAdapter(new ArrayList<User>(), getActivity(), game);
            rvUsers.setAdapter(gameAttendanceAdapter);
            rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        userModel.getPlayers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                if (users != null) {
                    if(PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("supervisor")){

                        User currentUser= userModel.getUser(PreferenceData.getLoggedInUser(getActivity().getApplicationContext()));
                        User playerUser = userModel.checkUserCred(currentUser.getPlayerEmail(),currentUser.getTeamName());
                        users.clear();
                        users.add(playerUser);
                    }
                    gameAttendanceAdapter = new GameAttendanceAdapter(users, getActivity(), game);
                    rvUsers.setAdapter(gameAttendanceAdapter);
                    rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));

                } else {
                    Log.d("gwyd", "no users found in db");
                    gameAttendanceAdapter = new GameAttendanceAdapter(new ArrayList<User>(), getActivity(), game);
                    rvUsers.setAdapter(gameAttendanceAdapter);
                    rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
            }
        });

        return view;
    }
}
