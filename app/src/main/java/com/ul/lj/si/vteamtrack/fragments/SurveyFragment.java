package com.ul.lj.si.vteamtrack.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.adapters.AnswerAdapter;
import com.ul.lj.si.vteamtrack.adapters.SurveyAdapter;

import java.util.ArrayList;

import entities.Answer;
import entities.Survey;
import viewModels.AnswerModel;
import viewModels.SurveyModel;

public class SurveyFragment extends Fragment {

    private AnswerModel answerModel;
    private int surveyId;
    private AnswerAdapter answerAdapter;
    private SurveyModel surveyModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.survey, container, false);
        RecyclerView rvAnswer = (RecyclerView) view.findViewById(R.id.rvAnswer);
        surveyId = (int) getArguments().get("surveyId");
        answerModel = new ViewModelProvider(this).get(AnswerModel.class);
        surveyModel = new ViewModelProvider(this).get(SurveyModel.class);
        Survey survey = surveyModel.getSurvey(surveyId);
        TextView content = view.findViewById(R.id.survey_content);
        content.setText(survey.getContent());
        ArrayList<Answer> arrayOfAnswers = (ArrayList<Answer>) answerModel.getAllAnswers(surveyId);

        if (arrayOfAnswers!= null){

            answerAdapter=new AnswerAdapter(arrayOfAnswers,getActivity());
            rvAnswer.setAdapter(answerAdapter);
            rvAnswer.setLayoutManager(new LinearLayoutManager(getActivity()));
        }else{
            Log.d("gwyd","answer list was null");
            answerAdapter=new AnswerAdapter(new ArrayList<Answer>(),getActivity());
            rvAnswer.setAdapter(answerAdapter);
            rvAnswer.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        return view;
    }
}
