package com.ul.lj.si.vteamtrack;

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

import java.util.ArrayList;
import java.util.List;

import entities.Game;
import entities.Training;
import viewModels.GameModel;
import viewModels.TrainingModel;

public class TrainingsListFragment extends Fragment {
    TrainingModel trainingModel;
    ListView listView;
    TrainingAdapter trainingAdapter;
    FloatingActionButton createTraining;
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
        View view = inflater.inflate(R.layout.listview_training_schedule, container, false);

        trainingModel = new ViewModelProvider(this).get(TrainingModel.class);

        ArrayList<Training> arrayOfTrainings = (ArrayList<Training>) trainingModel.getTrainings().getValue();
        listView = (ListView)view.findViewById(R.id.lvTrainingSchedule);
        listView.setAdapter(trainingAdapter);

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

            trainingAdapter = new TrainingAdapter(getActivity(),getActivity(), arrayOfTrainings);
            listView.setAdapter(trainingAdapter);
        }else{
            Log.d("gwyd","training list was null");
            trainingAdapter = new TrainingAdapter(getActivity(),getActivity(),new ArrayList<Training>());
            listView.setAdapter(trainingAdapter);
        }

        trainingModel.getTrainings().observe(getViewLifecycleOwner(), new Observer<List<Training>>() {
            @Override
            public void onChanged(@Nullable List<Training> trainings) {
                if (trainings != null) {
                    trainingAdapter.setTrainings(trainings);
                    trainingAdapter.clear();
                    trainingAdapter.addAll(trainings);
                } else {
                    Log.d("gwyd", "no trainings found in db");
                    trainingAdapter.setTrainings(new ArrayList<Training>());
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
        if(PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("player")){
            createTraining.setVisibility(View.GONE);
        }
        return view;

    }

}
