package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.internal.bind.DateTypeAdapter;
import com.ul.lj.si.vteamtrack.MainActivity;
import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.UpdateTrainingActivity;
import com.ul.lj.si.vteamtrack.fragments.ProfileFragment;
import com.ul.lj.si.vteamtrack.fragments.TrainingAttendancyFragment;
import com.ul.lj.si.vteamtrack.fragments.TrainingsListFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.Training;
import viewModels.TrainingModel;

public class TrainingAdapter extends ArrayAdapter<Training> {
    private Activity activity;

    private List<Training> trainings;

    private TrainingModel trainingModel;
    private Context context;

    public TrainingAdapter(Activity activity, Context context, ArrayList<Training> trainings) {

        super(context, 0, trainings);
        this.context = context;
        this.activity = activity;
        trainingModel = new ViewModelProvider((FragmentActivity) context).get(TrainingModel.class);
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Training training = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_training, parent, false);
        }

        TextView trainingDate = (TextView) convertView.findViewById(R.id.item_training_date);
        TextView trainingTime = (TextView) convertView.findViewById(R.id.item_training_time);
        TextView trainingLocation = (TextView) convertView.findViewById(R.id.item_training_location);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(training.date.getTime());
        trainingDate.setText(sdf.format(utilDate));
        trainingTime.setText(training.time);
        trainingLocation.setText(training.location);

        ImageButton edit = (ImageButton) convertView.findViewById(R.id.btn_edit_training);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.btn_delete_training);
        ImageButton expand = (ImageButton) convertView.findViewById(R.id.btn_expand_training);
        Button attendance = convertView.findViewById(R.id.btn_attendancy_training);

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("trainingId", training.id);
                FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new TrainingAttendancyFragment();
                fragment.setArguments(bundle);
                ft.replace(R.id.nav_fragment, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });

        LinearLayout toggleLayout = (LinearLayout) convertView.findViewById(R.id.item_training_secondlayout);


        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float deg = expand.getRotation() + 180F;
                expand.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());

                if(toggleLayout.getVisibility()==View.GONE){
                    toggleLayout.setVisibility(View.VISIBLE);
                    trainingLocation.setVisibility((trainingLocation.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    trainingTime.setVisibility((trainingTime.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);

                }
                else {
                    trainingLocation.setVisibility((trainingLocation.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    trainingTime.setVisibility((trainingTime.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);

                    toggleLayout.setVisibility(View.GONE);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trainingModel.deleteTraining(training);
                Toast.makeText(activity.getApplicationContext(), "Training successfully deleted",
                        Toast.LENGTH_LONG).show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity act = new MainActivity();
                TrainingsListFragment trainingListFragment = new TrainingsListFragment();
                Intent intent = new Intent( getContext(), UpdateTrainingActivity.class);
                intent.putExtra("training_id", training.id);
                activity.startActivity(intent);
            }
        });
        if(PreferenceData.getUserRole(activity.getApplicationContext()).equals("player")){
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }
        // Return the completed view to render on screen
        return convertView;
    }

}
