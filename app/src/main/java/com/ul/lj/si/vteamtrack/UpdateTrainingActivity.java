package com.ul.lj.si.vteamtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.ul.lj.si.vteamtrack.fragments.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.Game;
import entities.Training;
import viewModels.GameModel;
import viewModels.TrainingModel;

public class UpdateTrainingActivity extends AppCompatActivity {
    private TrainingModel trainingModel;
    private Training training;
    private EditText trainingDate;
    private EditText trainingLocation;
    private EditText trainingTime;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_training);
        trainingModel = new ViewModelProvider(this).get(TrainingModel.class);
        int trainingId = getIntent().getIntExtra("training_id", 0);
        training = trainingModel.getTraining(trainingId);


        trainingDate=(EditText)findViewById(R.id.training_update_date);
        trainingTime=(EditText)findViewById(R.id.training_update_time);
        trainingLocation=(EditText)findViewById(R.id.training_update_location);

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date utilDate = new Date(training.date.getTime());
        trainingDate.setText(sdf.format(utilDate));

        trainingTime.setText(training.time);
        trainingLocation.setText(training.location);
    }
    public void updateTraining( View v) throws ParseException {

        int error = 1;

        if(!training.date.toString().equals(trainingDate.getText().toString())){
            error=0;

        }
        else if(!training.time.toString().equals(trainingTime.getText().toString())){
            error=0;
        }

        else if(!training.location.toString().equals(trainingLocation.getText().toString())){
            error=0;

        }

        if(trainingDate.getText().toString().trim().equals("")||
                trainingLocation.getText().toString().trim().equals("")||
                trainingTime.getText().toString().trim().equals("")){
            error=1;
        }

        if(error==0){
            training.date=sdf.parse(trainingDate.getText().toString());
            training.time=trainingTime.getText().toString();
            training.location=trainingLocation.getText().toString();
            trainingModel.updateTraining(training);

            Toast.makeText(getApplicationContext(), "Training successfully updated", Toast.LENGTH_LONG).show();

            Intent data = new Intent();
            setResult(RESULT_OK, data);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(), "Nothing updated. Fields can not be empty",
                    Toast.LENGTH_LONG).show();
            error=1;
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
    public void cancelUpdateTraining(View v){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
