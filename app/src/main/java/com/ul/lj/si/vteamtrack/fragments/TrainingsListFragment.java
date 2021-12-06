package com.ul.lj.si.vteamtrack.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ul.lj.si.vteamtrack.CreateTrainingActivity;
import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.TrainingAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import entities.Training;
import viewModels.TrainingModel;

public class TrainingsListFragment extends Fragment {
    TrainingModel trainingModel;
    TrainingAdapter trainingAdapter;
    FloatingActionButton createTraining;
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
        View view = inflater.inflate(R.layout.listview_training_schedule, container, false);

        trainingModel = new ViewModelProvider(this).get(TrainingModel.class);

        ArrayList<Training> arrayOfTrainings = (ArrayList<Training>) trainingModel.getTrainings().getValue();


        RecyclerView rvTrainings = (RecyclerView) view.findViewById(R.id.rvTrainingSchedule);

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

        if (arrayOfTrainings!= null){

            Collections.sort(arrayOfTrainings, new Comparator<Training>() {
                public int compare(Training training1, Training training2) {
                    if (training1.getDate() == null || training2.getDate() == null){
                        return  0;
                    }
                    else if(training1.getDate()==training2.getDate()) {
                        return training1.getTime().compareTo(training2.getTime());
                    }
                    return training1.getDate().compareTo(training2.getDate());
                }
            });
            trainingAdapter = new TrainingAdapter(arrayOfTrainings, getActivity());
            rvTrainings.setAdapter(trainingAdapter);
            rvTrainings.setLayoutManager((new LinearLayoutManager(getActivity())));

        }else{

            Log.d("gwyd","training list was null");
            trainingAdapter = new TrainingAdapter(new ArrayList<Training>(), getActivity());
            rvTrainings.setAdapter(trainingAdapter);
            rvTrainings.setLayoutManager((new LinearLayoutManager(getActivity())));
        }

        trainingModel.getTrainings().observe(getViewLifecycleOwner(), new Observer<List<Training>>() {
            @Override
            public void onChanged(@Nullable List<Training> trainings) {
                if (trainings != null) {
                    Collections.sort(trainings, new Comparator<Training>() {
                        public int compare(Training training1, Training training2) {
                            if (training1.getDate() == null || training2.getDate() == null){
                                return  0;
                            }
                            else if(training1.getDate()==training2.getDate()) {
                                return training1.getTime().compareTo(training2.getTime());
                            }
                            return training1.getDate().compareTo(training2.getDate());
                        }
                    });
                    if(PreferenceData.getTrainingPref(getActivity().getApplicationContext())){
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.MONTH, -1);
                        Date result = cal.getTime();

                        List<Training> expired= trainingModel.getExpiredTrainings(result);
                        for (Training training : expired) {
                            trainingModel.deleteTraining(training);
                        }
                    }

                    trainingAdapter = new TrainingAdapter(trainings, getActivity());
                    rvTrainings.setAdapter(trainingAdapter);
                    rvTrainings.setLayoutManager((new LinearLayoutManager(getActivity())));


                } else {
                    Log.d("gwyd", "no trainings found in db");

                    trainingAdapter = new TrainingAdapter(new ArrayList<Training>(), getActivity());
                    rvTrainings.setAdapter(trainingAdapter);
                    rvTrainings.setLayoutManager((new LinearLayoutManager(getActivity())));
                }
                if(trainings.isEmpty()){
                    TextView empty = view.findViewById(R.id.empty_view_training);
                    rvTrainings.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
                else {
                    TextView empty = view.findViewById(R.id.empty_view_training);
                    rvTrainings.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
            }
        });

        createTraining=view.findViewById(R.id.fab_training);
        createTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateTrainingActivity.class);
                launchActivity.launch(intent);
            }
        });
        if(PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("player")||
                PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("supervisor")){
            createTraining.setVisibility(View.GONE);
        }
        return view;

    }

}
