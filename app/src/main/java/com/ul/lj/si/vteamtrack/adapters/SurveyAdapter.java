package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.fragments.ProfileFragment;
import com.ul.lj.si.vteamtrack.fragments.SurveyFragment;

import java.util.List;

import entities.Game;
import entities.Survey;
import viewModels.GameModel;
import viewModels.SurveyModel;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.ViewHolder>{

    private List<Survey> surveys;
    private Activity activity;
    private SurveyModel surveyModel;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView content;
        private Button view;

        public ViewHolder(View itemView) {

            super(itemView);

            content = (TextView) itemView.findViewById(R.id.item_survey_content);
            view = (Button) itemView.findViewById(R.id.item_survey_view);

        }
    }

    public SurveyAdapter(List<Survey> surveys, Activity activity) {

        this.surveys = surveys;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        surveyModel = new ViewModelProvider((FragmentActivity) activity).get(SurveyModel.class);


        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_survey, parent, false);

        // Return a new holder instance
        SurveyAdapter.ViewHolder viewHolder = new SurveyAdapter.ViewHolder(contactView);
         return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Survey survey = surveys.get(position);
        TextView content = holder.content;
        Button view = holder.view;
        content.setText(survey.getContent());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("surveyId", survey.getId());
                FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new SurveyFragment();
                fragment.setArguments(bundle);
                ft.replace(R.id.surveyFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return surveys.size();
    }
}
