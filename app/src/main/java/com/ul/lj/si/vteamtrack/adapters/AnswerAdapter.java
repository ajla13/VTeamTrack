package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import java.util.List;

import entities.Answer;
import entities.Survey;
import viewModels.AnswerModel;
import viewModels.CommentModel;
import viewModels.SurveyModel;
import viewModels.UserModel;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder>{
    private Activity activity;
    private List<Answer> answers;
    private AnswerModel answerModel;
    private SurveyModel surveyModel;
    public class ViewHolder extends RecyclerView.ViewHolder{

        public RadioButton content;
        public TextView votes;

        public ViewHolder(View itemView) {

            super(itemView);
            votes = (TextView) itemView.findViewById(R.id.item_answer_votes);
            content = (RadioButton) itemView.findViewById(R.id.item_answer_radioButton);

        }
    }

    public AnswerAdapter(List<Answer> answers, Activity activity){
        this.answers=answers;
        this.activity=activity;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        answerModel = new ViewModelProvider((FragmentActivity) activity).get(AnswerModel.class);
        surveyModel = new ViewModelProvider((FragmentActivity) activity).get(SurveyModel.class);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_answer, parent, false);

        // Return a new holder instance
        AnswerAdapter.ViewHolder viewHolder = new AnswerAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Answer answer=answers.get(position);
            RadioButton content = holder.content;
            TextView votes = holder.votes;
            votes.setText(String.valueOf(answer.getVotes().size()));
            Survey survey = surveyModel.getSurvey(answer.getSurveyId());
            int userId = PreferenceData.getLoggedInUser(activity.getApplication());
            content.setText(answer.getContent());
            boolean checked = answer.getVotes().contains(userId);
            content.setChecked(checked);

            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checked){
                        content.setChecked(true);
                        answer.getVotes().add(userId);
                        for (int counter = 0; counter < answers.size(); counter++) {
                            if(answers.get(counter).getId()!=answer.getId()){
                                if(answers.get(counter).getVotes().contains(userId)){
                                    int index = answers.get(counter).getVotes().indexOf(userId);
                                    answers.get(counter).getVotes().remove(index);
                                    answerModel.update(answers.get(counter));
                                }

                            }
                        }
                    }
                    else {
                        content.setChecked(false);
                        int index = answer.getVotes().indexOf(userId);
                        answer.getVotes().remove(index);
                    }
                    answerModel.update(answer);
                    notifyDataSetChanged();
                }
            });
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }
}
