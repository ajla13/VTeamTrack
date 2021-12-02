package com.ul.lj.si.vteamtrack.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.FeeAdapter;
import com.ul.lj.si.vteamtrack.adapters.PublicTrainingsAdapter;
import com.ul.lj.si.vteamtrack.adapters.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import entities.Team;
import entities.Training;
import entities.User;
import viewModels.TeamModel;
import viewModels.TrainingModel;

public class PublicTrainingsFragment extends Fragment{

    private TrainingModel trainingModel;
    private TeamModel teamModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.view_trainings, container, false);
        teamModel = new ViewModelProvider((FragmentActivity)getActivity()).get(TeamModel.class);
        trainingModel = new ViewModelProvider((FragmentActivity) getActivity()).get(TrainingModel.class);
        RecyclerView trainingsView = view.findViewById(R.id.rvTrainingsView);

        Spinner spin = (Spinner) view.findViewById(R.id.trainings_spinner);
        ArrayList<Team> publicTeams = (ArrayList<Team>) teamModel.getPublicTeams();
        String[] teamNames=new String[publicTeams.size()];
        for( int i =0; i<publicTeams.size(); i++){
            teamNames[i] = publicTeams.get(i).name;
        }
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<Training> trainingsList = (ArrayList<Training>) trainingModel.getTeamTrainings(spin.getSelectedItem().toString());
                PublicTrainingsAdapter adapter = new PublicTrainingsAdapter(trainingsList,getActivity());
                trainingsView.setAdapter(adapter);
                trainingsView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, teamNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        return view;
    }


}
