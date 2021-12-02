package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ul.lj.si.vteamtrack.PreferenceData;

import java.util.Date;
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
    String teamName;

    @Inject
    public TrainingRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        trainingDao = db.trainingDao();
        teamName=PreferenceData.getTeam(application.getApplicationContext());
        allTrainings = trainingDao.getAll(teamName);

    }

    public LiveData<List<Training>> getAllTrainings() {
        if (allTrainings == null) {
            AppDatabase.executor.execute(() -> {
                allTrainings = trainingDao.getAll(teamName);
            });
        }
        return allTrainings;
    }

    public List<Training> getExpiredTrainings(Date date){
        return trainingDao.getExpairedTrainings(teamName,date);
    }
    public List<Training> getTeamTrainings(String nameOfTeam){
        return trainingDao.getTeamTrainings(nameOfTeam);
    }

    public Training getTraining(int id) {
        training = trainingDao.getTraining(id);
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
