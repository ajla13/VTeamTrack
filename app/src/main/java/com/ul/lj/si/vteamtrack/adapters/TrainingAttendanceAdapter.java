package com.ul.lj.si.vteamtrack.adapters;


import static java.time.LocalDate.now;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import entities.Training;
import entities.User;
import viewModels.TrainingModel;
import android.app.Activity;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


public class TrainingAttendanceAdapter extends RecyclerView.Adapter<TrainingAttendanceAdapter.ViewHolder> {

    private List<User> users;
    private Activity activity;
    private Training training;
    private TrainingModel trainingModel;


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView userSurname;
        private CheckBox attendance;

        public ViewHolder(View itemView) {

            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.training_attendance_user_name);
            userSurname = (TextView) itemView.findViewById(R.id.training_attendance_user_surname);
            attendance = itemView.findViewById(R.id.checkBox_training_attendance);

        }

    }
    public TrainingAttendanceAdapter( List<User> users, Activity activity, Training training) {

        this.activity = activity;
        this.users = users;
        this.training = training;
        trainingModel = new ViewModelProvider((FragmentActivity) activity).get(TrainingModel.class);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        trainingModel = new ViewModelProvider((FragmentActivity) activity).get(TrainingModel.class);


        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_training_attendance, parent, false);

        // Return a new holder instance
        TrainingAttendanceAdapter.ViewHolder viewHolder = new TrainingAttendanceAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = users.get(position);
        TextView userName = holder.userName;
        TextView userSurname = holder.userSurname;
        CheckBox attendance = holder.attendance;

        userName.setText(user.getFirstName());
        userSurname.setText(user.getLastName());
        boolean checked = training.getAttendancy().contains(user.getId());
        attendance.setChecked(checked);


        if(PreferenceData.getUserRole(activity.getApplicationContext()).equals("trainer") ||
                PreferenceData.getUserRole(activity.getApplicationContext()).equals("admin")){
            attendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checked){
                        training.getAttendancy().add(user.getId());

                    }
                    else {
                        int index = training.getAttendancy().indexOf(user.getId());
                        training.getAttendancy().remove(index);
                    }
                    trainingModel.updateTraining(training);
                }
            });
        }
        else {
            attendance.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

}
