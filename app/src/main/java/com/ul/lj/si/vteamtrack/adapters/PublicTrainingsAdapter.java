package com.ul.lj.si.vteamtrack.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.ul.lj.si.vteamtrack.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entities.Training;


public class PublicTrainingsAdapter extends RecyclerView.Adapter<PublicTrainingsAdapter.ViewHolder> {

    private Activity activity;
    private List<Training> trainings;

    public PublicTrainingsAdapter(List<Training> trainings, Activity activity) {
        this.trainings = trainings;
        this.activity = activity;


    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView date;
        private TextView location;
        private TextView time;


        public ViewHolder(View itemView) {

            super(itemView);

            date = (TextView) itemView.findViewById(R.id.item_public_training_date);
            location = (TextView) itemView.findViewById(R.id.item_public_training_location);
            time = (TextView) itemView.findViewById(R.id.item_public_training_time);


        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.public_trainings_item, parent, false);

        // Return a new holder instance
        PublicTrainingsAdapter.ViewHolder viewHolder = new PublicTrainingsAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Training training = trainings.get(position);
        TextView dateTraining = holder.date;
        TextView locationTraining = holder.location;
        TextView timeTraining = holder.time;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(training.getDate().getTime());
        dateTraining.setText(sdf.format(utilDate));

        SimpleDateFormat sdfTime= new SimpleDateFormat("HH:mm");
        Date utilTime = new Date(training.getTime().getTime());
        timeTraining.setText(sdfTime.format(utilTime));

        locationTraining.setText(training.getLocation());

    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }
}
