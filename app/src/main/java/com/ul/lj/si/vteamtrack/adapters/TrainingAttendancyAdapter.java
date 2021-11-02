package com.ul.lj.si.vteamtrack.adapters;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import androidx.lifecycle.ViewModelProvider;

import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;


import java.util.ArrayList;
import java.util.List;

import entities.Training;
import entities.User;
import viewModels.TrainingModel;

public class TrainingAttendancyAdapter extends ArrayAdapter<User> {

    private List<User> users;
    private  Context context;
    private Training training;
    private TrainingModel trainingModel;


    public TrainingAttendancyAdapter( Context context, Training training, ArrayList<User> users) {
        super(context, 0, users);
        this.context= context;
        this.training = training;
        trainingModel = new ViewModelProvider((FragmentActivity) context).get(TrainingModel.class);

    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_training_attendance, parent, false);
        }
        // Lookup view for data population
        TextView userName = (TextView) convertView.findViewById(R.id.training_attendance_user_name);
        TextView userSurname = (TextView) convertView.findViewById(R.id.training_attendance_user_surname);
        CheckBox attendance = convertView.findViewById(R.id.checkBox_training_attendance);
        boolean checked = training.attendancy.contains(user.id);
        attendance.setChecked(checked);
        // Populate the data into the template view using the data object
        userName.setText(user.firstName);
        userSurname.setText(user.lastName);

        if(PreferenceData.getUserRole(getContext().getApplicationContext()).equals("trainer") ||
                PreferenceData.getUserRole(getContext().getApplicationContext()).equals("admin")){
            attendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checked){
                        training.attendancy.add(user.id);

                    }
                    else {
                        int index = training.attendancy.indexOf(user.id);
                        training.attendancy.remove(index);
                    }
                    trainingModel.updateTraining(training);
                }
            });
        }
        else {
            attendance.setEnabled(false);
        }

        // Return the completed view to render on screen
        return convertView;
    }

}
