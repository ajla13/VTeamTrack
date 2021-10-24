package com.ul.lj.si.vteamtrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import entities.Game;
import entities.Training;
import viewModels.GameModel;
import viewModels.TrainingModel;

public class TrainingAdapter extends ArrayAdapter<Training> {
    private Activity activity;

    private List<Training> trainings;

    private TrainingModel trainingModel;

    public TrainingAdapter(Activity activity, Context context, ArrayList<Training> trainings) {

        super(context, 0, trainings);
        this.activity = activity;
        trainingModel = new ViewModelProvider((FragmentActivity) context).get(TrainingModel.class);
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Training training = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_training, parent, false);
        }
        // Lookup view for data population
        TextView trainingDate = (TextView) convertView.findViewById(R.id.item_training_date);
        TextView trainingTime = (TextView) convertView.findViewById(R.id.item_training_time);
        TextView trainingLocation = (TextView) convertView.findViewById(R.id.item_training_location);
        // Populate the data into the template view using the data object
        trainingDate.setText(training.date);
        trainingTime.setText(training.time);
        trainingLocation.setText(training.location);

        ImageButton edit = (ImageButton) convertView.findViewById(R.id.btn_edit_training);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.btn_delete_training);
        ImageButton expand = (ImageButton) convertView.findViewById(R.id.btn_expand_training);

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
