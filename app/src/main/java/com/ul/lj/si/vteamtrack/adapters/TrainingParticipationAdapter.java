package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import java.util.List;

import entities.Training;
import entities.User;
import viewModels.TrainingModel;

public class TrainingParticipationAdapter extends RecyclerView.Adapter<TrainingParticipationAdapter.ViewHolder> {

    private List<User> users;
    private Activity activity;
    private Training training;
    private TrainingModel trainingModel;


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView userSurname;
        private CheckBox participation;

        public ViewHolder(View itemView) {

            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.training_participation_user_name);
            userSurname = (TextView) itemView.findViewById(R.id.training_participation_user_surname);
            participation = itemView.findViewById(R.id.checkBox_training_participation);

        }

    }
    public TrainingParticipationAdapter( List<User> users, Activity activity, Training training) {

        this.activity = activity;
        this.users = users;
        this.training = training;
        trainingModel = new ViewModelProvider((FragmentActivity) activity).get(TrainingModel.class);

    }
    @NonNull
    @Override
    public TrainingParticipationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        trainingModel = new ViewModelProvider((FragmentActivity) activity).get(TrainingModel.class);


        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_training_participation, parent, false);

        // Return a new holder instance
        TrainingParticipationAdapter.ViewHolder viewHolder = new TrainingParticipationAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingParticipationAdapter.ViewHolder holder, int position) {

        User user = users.get(position);
        TextView userName = holder.userName;
        TextView userSurname = holder.userSurname;
        CheckBox participation = holder.participation;

        userName.setText(user.getFirstName());
        userSurname.setText(user.getLastName());
        boolean checked = training.getParticipation().contains(user.getId());
        participation.setChecked(checked);


        if(PreferenceData.getLoggedInUser(activity.getApplication())==user.getId()){
            participation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checked){
                        training.getParticipation().add(user.getId());

                    }
                    else {
                        int index = training.getParticipation().indexOf(user.getId());
                        training.getParticipation().remove(index);
                    }
                    trainingModel.updateTraining(training);
                }
            });
        }
        else {
            participation.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
