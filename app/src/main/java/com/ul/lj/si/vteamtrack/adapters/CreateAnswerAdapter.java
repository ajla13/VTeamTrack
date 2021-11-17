package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.R;

import java.util.List;

import entities.Answer;

import viewModels.AnswerModel;
import viewModels.SurveyModel;

public class CreateAnswerAdapter extends RecyclerView.Adapter<CreateAnswerAdapter.ViewHolder>{
    private Activity activity;
    private List<Answer> answers;
    private AnswerModel answerModel;
    private SurveyModel surveyModel;

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView content;

        public ViewHolder(View itemView) {

            super(itemView);
            content = (TextView) itemView.findViewById(R.id.item_create_answer_content);

        }
    }

    public CreateAnswerAdapter(List<Answer> answers, Activity activity){
        this.answers=answers;
        this.activity=activity;

    }
    @NonNull
    @Override
    public CreateAnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        answerModel = new ViewModelProvider((FragmentActivity) activity).get(AnswerModel.class);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_create_answer, parent, false);

        // Return a new holder instance
        CreateAnswerAdapter.ViewHolder viewHolder = new CreateAnswerAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CreateAnswerAdapter.ViewHolder holder, int position) {
        Answer answer=answers.get(position);
        TextView content = holder.content;
        content.setText(answer.getContent());

    }

    @Override
    public int getItemCount() {
        return answers.size();
    }
}
