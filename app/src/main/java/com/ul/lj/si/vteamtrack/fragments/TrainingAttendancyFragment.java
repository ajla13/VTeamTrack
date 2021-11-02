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
import com.ul.lj.si.vteamtrack.adapters.TrainingAttendancyAdapter;
import com.ul.lj.si.vteamtrack.adapters.UsersAdapter;

import java.util.ArrayList;
import java.util.List;

import entities.Training;
import entities.User;
import viewModels.TrainingModel;
import viewModels.UserModel;

public class TrainingAttendancyFragment extends Fragment {

    private TrainingModel trainingModel;
    private UserModel userModel;
    private ListView listView;
    private TrainingAttendancyAdapter trainingAttendancyAdapter;
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


        listView = (ListView)view.findViewById(R.id.lvUsers);
        listView.setAdapter(trainingAttendancyAdapter);

        if (arrayOfUsers != null){

            trainingAttendancyAdapter = new TrainingAttendancyAdapter(getActivity(), training, arrayOfUsers);
            listView.setAdapter(trainingAttendancyAdapter);
        }else{
            Log.d("gwyd","user list was null");
            trainingAttendancyAdapter = new TrainingAttendancyAdapter(getActivity(), training, new ArrayList<User>());
            listView.setAdapter(trainingAttendancyAdapter);
        }
        userModel.getPlayers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                if (users != null) {
                    trainingAttendancyAdapter.setUsers(users);
                    trainingAttendancyAdapter.clear();
                    trainingAttendancyAdapter.addAll(users);

                } else {
                    Log.d("gwyd", "no users found in db");
                    trainingAttendancyAdapter.setUsers(new ArrayList<User>());
                }
            }
        });

        return view;
    }
}
