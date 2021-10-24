package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import dao.GamesDao;
import dao.TrainingDao;
import database.AppDatabase;
import entities.Game;
import entities.Training;

public class TrainingRepo {

    private LiveData<List<Training>> allTrainings;
    private TrainingDao trainingDao;
    private Training training;

    @Inject
    public TrainingRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        trainingDao = db.trainingDao();
        allTrainings = trainingDao.getAll();

    }

    public LiveData<List<Training>> getAllTrainings() {
        if (allTrainings == null) {
            AppDatabase.executor.execute(() -> {
                allTrainings = trainingDao.getAll();
            });
        }
        return allTrainings;
    }

    public Training getTraining(int id) {
        AppDatabase.executor.execute(() -> {

            training = trainingDao.getTraining(id);
            while (training==null) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e)  {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });
        while (training==null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e)  {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        return training;
    }

    public void insert(Training training) {
            trainingDao.insert(training);
    }
    public void delete(Training training) {
        AppDatabase.executor.execute(() -> {
            trainingDao.delete(training);
        });
    }
    public void update(Training training) {
        AppDatabase.executor.execute(() -> {
            trainingDao.update(training);
        });
    }
}
