package com.ul.lj.si.vteamtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;


import com.ul.lj.si.vteamtrack.fragments.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import entities.Training;
import viewModels.TrainingModel;

public class CreateTrainingActivity extends AppCompatActivity {
    TrainingModel trainingModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_training);
        trainingModel = new ViewModelProvider(this).get(TrainingModel.class);

    }
    public void createTraining( View v) throws ParseException {

        Training training = new Training();
        int error = 0;

        EditText trainingDate=(EditText)findViewById(R.id.training_date);
        if(trainingDate.getText().toString().trim().equals("")){
            trainingDate.setError("Training date is required!");
            error=1;
        }


        EditText trainingTime=(EditText)findViewById(R.id.training_time);
        if(trainingTime.getText().toString().trim().equals("")){
            trainingTime.setError("Training time is required!");
            error=1;
        }


        EditText trainingLocation=(EditText)findViewById(R.id.training_location);
        if(trainingLocation.getText().toString().trim().equals("")){
            trainingLocation.setError("Training location is required!");
            error=1;
        }
        if(error==0){
            training.date=new SimpleDateFormat("dd/MM/yyyy").parse(trainingDate.getText().toString());
            training.time=trainingTime.getText().toString();
            training.location=trainingLocation.getText().toString();
            training.teamName=PreferenceData.getTeam(getApplicationContext());
            training.attendancy = new ArrayList<>();
            Training result = trainingModel.createTraining(training);

            if(result!= null){
                Toast.makeText(getApplicationContext(), "Training successfully created", Toast.LENGTH_LONG).show();
            }
            Intent data = new Intent();
            setResult(RESULT_OK, data);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Please fill out all required fields.", Toast.LENGTH_LONG).show();
            error=0;
        }

    }
    public void showDatePickerDialog(View v) {
        Bundle bundle = new Bundle();
        bundle.putInt("viewId",v.getId());
        bundle.putString("source", "editText");
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "datePicker");


    }
    public void cancel(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
