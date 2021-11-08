package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import entities.Training;
import repository.TrainingRepo;


public class TrainingModel extends AndroidViewModel {

    private TrainingRepo trainingRepo;
    private LiveData<List<Training>> trainings;


    public TrainingModel(@NonNull Application application) {
        super(application);
        trainingRepo = new TrainingRepo(application);
        trainings = trainingRepo.getAllTrainings();
    }

    public LiveData<List<Training>> getTrainings() {
        if (trainings == null) {
            trainings = trainingRepo.getAllTrainings();
        }
        return trainings;
    }
    public List<Training> getExpiredTrainings(Date date){
        return trainingRepo.getExpiredTrainings(date);
    }

    public Training getTraining(int id) {
        return trainingRepo.getTraining(id);
    }
    public Training createTraining(Training training) {
        trainingRepo.insert(training);
        return training;
    }
    public void updateTraining(Training training) {
        trainingRepo.update(training);

    }
    public void deleteTraining(Training training) {
        trainingRepo.delete(training);
    };
}
