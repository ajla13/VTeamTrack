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

import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.TrainingAttendanceAdapter;

import java.util.ArrayList;
import java.util.List;

import entities.Training;
import entities.User;
import viewModels.TrainingModel;
import viewModels.UserModel;

public class TrainingAttendanceFragment extends Fragment {

    private TrainingModel trainingModel;
    private UserModel userModel;
    private TrainingAttendanceAdapter trainingAttendanceAdapter;
    private Training training;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.listview, container, false);

        int trainerId = this.getArguments().getInt("trainingId");

        userModel = new ViewModelProvider(this).get(UserModel.class);
        trainingModel = new ViewModelProvider(this).get(TrainingModel.class);

        training = trainingModel.getTraining(trainerId);

        ArrayList<User> arrayOfUsers = (ArrayList<User>) userModel.getPlayers().getValue();
        RecyclerView rvUsers = (RecyclerView) view.findViewById(R.id.rvUsers);

        if (arrayOfUsers != null){

            trainingAttendanceAdapter = new TrainingAttendanceAdapter(arrayOfUsers, getActivity(), training);
            rvUsers.setAdapter(trainingAttendanceAdapter);
            rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            Log.d("gwyd","user list was null");
            trainingAttendanceAdapter = new TrainingAttendanceAdapter(new ArrayList<User>(), getActivity(), training);
            rvUsers.setAdapter(trainingAttendanceAdapter);
            rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        userModel.getPlayers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                if (users != null) {
                    trainingAttendanceAdapter = new TrainingAttendanceAdapter(users, getActivity(), training);
                    rvUsers.setAdapter(trainingAttendanceAdapter);
                    rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));

                } else {
                    Log.d("gwyd", "no users found in db");
                    trainingAttendanceAdapter = new TrainingAttendanceAdapter(new ArrayList<User>(), getActivity(), training);
                    rvUsers.setAdapter(trainingAttendanceAdapter);
                    rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));                }
            }
        });

        return view;
    }
}
