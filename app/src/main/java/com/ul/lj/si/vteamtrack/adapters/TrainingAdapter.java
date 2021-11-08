package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.MainActivity;
import com.ul.lj.si.vteamtrack.PreferenceData;
import com.ul.lj.si.vteamtrack.R;
import com.ul.lj.si.vteamtrack.UpdateTrainingActivity;
import com.ul.lj.si.vteamtrack.fragments.TrainingAttendanceFragment;
import com.ul.lj.si.vteamtrack.fragments.TrainingsListFragment;

import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entities.Training;
import viewModels.TrainingModel;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.ViewHolder>{


    TrainingModel trainingModel;
    SimpleDateFormat sdf;
    private Activity activity;
    private List<Training> trainings;


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView trainingDate;
        public TextView trainingTime;
        public TextView trainingLocation;
        public ImageButton edit;
        public ImageButton delete;
        public ImageButton expand;
        public Button attendance;
        public LinearLayout toggleLayout;

        public ViewHolder(View itemView) {

            super(itemView);

            trainingDate = (TextView) itemView.findViewById(R.id.item_training_date);
            trainingTime = (TextView) itemView.findViewById(R.id.item_training_time);
            trainingLocation = (TextView) itemView.findViewById(R.id.item_training_location);
            edit = (ImageButton) itemView.findViewById(R.id.btn_edit_training);
            delete = (ImageButton) itemView.findViewById(R.id.btn_delete_training);
            expand = (ImageButton) itemView.findViewById(R.id.btn_expand_training);
            attendance = itemView.findViewById(R.id.btn_attendancy_training);
            toggleLayout = (LinearLayout) itemView.findViewById(R.id.item_training_secondlayout);

        }
    }

    public TrainingAdapter(List<Training> trainings, Activity activity) {

        this.trainings = trainings;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        trainingModel = new ViewModelProvider((FragmentActivity) activity).get(TrainingModel.class);


        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_training, parent, false);

        // Return a new holder instance
        TrainingAdapter.ViewHolder viewHolder = new TrainingAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Training training = trainings.get(position);
        TextView location = holder.trainingLocation;
        TextView time = holder.trainingTime;
        TextView date = holder.trainingDate;
        ImageButton edit = holder.edit;
        ImageButton delete = holder.delete;
        ImageButton expand = holder.expand;
        Button attendance = holder.attendance;
        LinearLayout toggleLayout = holder.toggleLayout;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(training.getDate().getTime());
        date.setText(sdf.format(utilDate));

        SimpleDateFormat sdfTime= new SimpleDateFormat("HH:mm");
        Date utilTime = new Date(training.getTime().getTime());
        time.setText(sdfTime.format(utilTime));

        location.setText(training.getLocation());

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("trainingId", training.getId());
                FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = new TrainingAttendanceFragment();
                fragment.setArguments(bundle);
                ft.replace(R.id.nav_fragment, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }
        });
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float deg = expand.getRotation() + 180F;
                expand.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());

                if(toggleLayout.getVisibility()==View.GONE){
                    toggleLayout.setVisibility(View.VISIBLE);
                    location.setVisibility((location.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    time.setVisibility((time.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);

                }
                else {
                    location.setVisibility((location.getVisibility() == View.VISIBLE)
                            ? View.INVISIBLE
                            : View.VISIBLE);
                    time.setVisibility((time.getVisibility() == View.VISIBLE)
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
                Intent intent = new Intent( activity.getApplicationContext(), UpdateTrainingActivity.class);
                intent.putExtra("training_id", training.getId());
                activity.startActivity(intent);
            }
        });
        if(PreferenceData.getUserRole(activity.getApplicationContext()).equals("player")){
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }


}
