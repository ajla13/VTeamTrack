package com.ul.lj.si.vteamtrack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.AnswerAdapter;
import com.ul.lj.si.vteamtrack.adapters.CreateAnswerAdapter;

import java.util.ArrayList;
import java.util.List;

import entities.Answer;
import entities.Survey;
import entities.Team;
import viewModels.AnswerModel;
import viewModels.SurveyModel;
import viewModels.TeamModel;

public class CreateSurveyFragment extends Fragment {
    private SurveyModel surveyModel;
    private AnswerModel answerModel;
    private TeamModel teamModel;
    private CreateAnswerAdapter answerAdapter;
    private EditText answerContent;
    private EditText surveyContent;
    private Survey survey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_survey, container, false);
        RecyclerView rvAnswersCreate = (RecyclerView) view.findViewById(R.id.rvAnswersCreate);

        surveyModel = new ViewModelProvider(this).get(SurveyModel.class);
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);
        teamModel = new ViewModelProvider(this).get(TeamModel.class);
        surveyContent = view.findViewById(R.id.create_survey_content);
        answerContent = view.findViewById(R.id.new_answer);

        Button createAnswer = view.findViewById(R.id.btn_add_answer);
        Button createSurvey = view.findViewById(R.id.btn_create_survey);
        Button done = view.findViewById(R.id.btn_create_survey_done);

        answerAdapter = new CreateAnswerAdapter(new ArrayList<Answer>(), getActivity());
        rvAnswersCreate.setAdapter(answerAdapter);
        rvAnswersCreate.setLayoutManager(new LinearLayoutManager(getActivity()));


        createSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(surveyContent.getText().toString().isEmpty()){
                    surveyContent.setError("Survey has to contain a question");
                }

                else {
                    String content = surveyContent.getText().toString();
                    Team team = teamModel.getTeam(PreferenceData.getTeam(getActivity().getApplicationContext()));
                    Survey surveyCreate = new Survey(team.getId(),100, team.getName(),content);
                    survey = surveyModel.createSurvey(surveyCreate);
                    answerContent.setEnabled(true);
                    createAnswer.setEnabled(true);
                }
            }
        });
        createAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                survey.setCustomIdentifier(100);
                String contentAnswer = answerContent.getText().toString();
                Survey surveyTemp = surveyModel.getSurveyByCI(survey.getCustomIdentifier());
                Answer answer = new Answer(surveyTemp.getId(),contentAnswer,new ArrayList<Integer>());
                answerModel.create(answer);
                ArrayList<Answer> answers = (ArrayList<Answer>) answerModel.getAllAnswers(surveyTemp.getId());
                answerAdapter = new CreateAnswerAdapter(answers, getActivity());
                rvAnswersCreate.setAdapter(answerAdapter);
                rvAnswersCreate.setLayoutManager(new LinearLayoutManager(getActivity()));
                answerContent.setText("");
                survey.setCustomIdentifier(0);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                survey.setCustomIdentifier(0);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new SurveyListFragment();
                ft.replace(R.id.surveyFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        return view;
    }
}
