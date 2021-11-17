package com.ul.lj.si.vteamtrack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ul.lj.si.vteamtrack.CreateTrainingActivity;
import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import com.ul.lj.si.vteamtrack.adapters.SurveyAdapter;

import java.util.ArrayList;

import java.util.List;


import entities.Survey;

import viewModels.SurveyModel;

public class SurveyListFragment extends Fragment {

    private SurveyModel surveyModel;
    private SurveyAdapter surveyAdapter;
    private ImageButton createSurvey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.survey_list, container, false);
        RecyclerView rvSurvey = (RecyclerView) view.findViewById(R.id.rvSurvey);

        surveyModel = new ViewModelProvider(this).get(SurveyModel.class);
        ArrayList<Survey> arrayOfSurveys = (ArrayList<Survey>) surveyModel.getAllSurveys().getValue();

        if (arrayOfSurveys!= null){

            surveyAdapter= new SurveyAdapter(arrayOfSurveys, getActivity());
            rvSurvey.setAdapter(surveyAdapter);
            rvSurvey.setLayoutManager(new LinearLayoutManager(getActivity()));

        }else{
            Log.d("gwyd","game list was null");
            surveyAdapter= new SurveyAdapter(new ArrayList<Survey>(), getActivity());
            rvSurvey.setAdapter(surveyAdapter);
            rvSurvey.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        surveyModel.getAllSurveys().observe(getViewLifecycleOwner(), new Observer<List<Survey>>() {
            @Override
            public void onChanged(@Nullable List<Survey> surveys) {
                if (surveys != null) {

                    surveyAdapter= new SurveyAdapter(surveys, getActivity());
                    rvSurvey.setAdapter(surveyAdapter);
                    rvSurvey.setLayoutManager(new LinearLayoutManager(getActivity()));
                } else {
                    surveyAdapter= new SurveyAdapter(new ArrayList<Survey>(), getActivity());
                    rvSurvey.setAdapter(surveyAdapter);
                    rvSurvey.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                if(surveys.isEmpty()){
                    TextView empty = view.findViewById(R.id.empty_view_survey);
                    rvSurvey.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
                else {
                    TextView empty = view.findViewById(R.id.empty_view_survey);
                    rvSurvey.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
            }
        });
        createSurvey = (ImageButton) view.findViewById(R.id.fab_survey);
        if(PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("admin")){
            createSurvey.setVisibility(View.VISIBLE);

        }
        createSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new CreateSurveyFragment();
                ft.replace(R.id.surveyFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });
        if(PreferenceData.getUserRole(getActivity().getApplicationContext()).equals("player")){
            createSurvey.setVisibility(View.GONE);
        }

       return view;

    }
}
