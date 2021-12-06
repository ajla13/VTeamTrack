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
import com.ul.lj.si.vteamtrack.adapters.TrainingAttendanceAdapter;
import com.ul.lj.si.vteamtrack.adapters.TrainingParticipationAdapter;

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
    private TrainingParticipationAdapter trainingParticipationAdapter;
    private Training training;
    private ArrayList<User> arrayOfUsers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View view = inflater.inflate(R.layout.training_list, container, false);

        int trainerId = this.getArguments().getInt("trainingId");

        userModel = new ViewModelProvider(this).get(UserModel.class);
        trainingModel = new ViewModelProvider(this).get(TrainingModel.class);

        training = trainingModel.getTraining(trainerId);
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
        RecyclerView rvUsersParticipation = (RecyclerView) view.findViewById(R.id.rvUsersParticipation);


        if (arrayOfUsers != null){

            trainingParticipationAdapter=new TrainingParticipationAdapter(arrayOfUsers, getActivity(), training);
            rvUsersParticipation.setAdapter(trainingParticipationAdapter);
            rvUsersParticipation.setLayoutManager(new LinearLayoutManager(getActivity()));

            trainingAttendanceAdapter = new TrainingAttendanceAdapter(arrayOfUsers, getActivity(), training);
            rvUsers.setAdapter(trainingAttendanceAdapter);
            rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            Log.d("gwyd","user list was null");
            trainingParticipationAdapter=new TrainingParticipationAdapter(new ArrayList<User>(), getActivity(), training);
            rvUsersParticipation.setAdapter(trainingParticipationAdapter);
            rvUsersParticipation.setLayoutManager(new LinearLayoutManager(getActivity()));

            trainingAttendanceAdapter = new TrainingAttendanceAdapter(new ArrayList<User>(), getActivity(), training);
            rvUsers.setAdapter(trainingAttendanceAdapter);
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
                    trainingParticipationAdapter=new TrainingParticipationAdapter(users, getActivity(), training);
                    rvUsersParticipation.setAdapter(trainingParticipationAdapter);
                    rvUsersParticipation.setLayoutManager(new LinearLayoutManager(getActivity()));


                    trainingAttendanceAdapter = new TrainingAttendanceAdapter(users, getActivity(), training);
                    rvUsers.setAdapter(trainingAttendanceAdapter);
                    rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));

                } else {
                    Log.d("gwyd", "no users found in db");
                    trainingParticipationAdapter=new TrainingParticipationAdapter(new ArrayList<User>(), getActivity(), training);
                    rvUsersParticipation.setAdapter(trainingParticipationAdapter);
                    rvUsersParticipation.setLayoutManager(new LinearLayoutManager(getActivity()));

                    trainingAttendanceAdapter = new TrainingAttendanceAdapter(new ArrayList<User>(), getActivity(), training);
                    rvUsers.setAdapter(trainingAttendanceAdapter);
                    rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));                }
            }
        });

        return view;
    }
}
