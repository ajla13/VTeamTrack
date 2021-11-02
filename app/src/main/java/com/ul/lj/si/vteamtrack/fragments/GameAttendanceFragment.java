package com.ul.lj.si.vteamtrack.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.GameAttendanceAdapter;
import com.ul.lj.si.vteamtrack.adapters.TrainingAttendancyAdapter;

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
    private ListView listView;
    private GameAttendanceAdapter gameAttendanceAdapter;
    private Game game;

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

        ArrayList<User> arrayOfUsers = (ArrayList<User>) userModel.getPlayers().getValue();


        listView = (ListView)view.findViewById(R.id.lvUsers);
        listView.setAdapter(gameAttendanceAdapter);

        if (arrayOfUsers != null){

            gameAttendanceAdapter = new GameAttendanceAdapter(getActivity(), game, arrayOfUsers);
            listView.setAdapter(gameAttendanceAdapter);
        }else{
            Log.d("gwyd","user list was null");
            gameAttendanceAdapter = new GameAttendanceAdapter(getActivity(), game, new ArrayList<User>());
            listView.setAdapter(gameAttendanceAdapter);
        }
        userModel.getPlayers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                if (users != null) {
                    gameAttendanceAdapter.setUsers(users);
                    gameAttendanceAdapter.clear();
                    gameAttendanceAdapter.addAll(users);

                } else {
                    Log.d("gwyd", "no users found in db");
                    gameAttendanceAdapter.setUsers(new ArrayList<User>());
                }
            }
        });

        return view;
    }
}
