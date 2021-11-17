package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ul.lj.si.vteamtrack.PreferenceData;

import java.util.List;

import javax.inject.Inject;

import dao.SurveyDao;
import database.AppDatabase;
import entities.Post;
import entities.Survey;

public class SurveyRepo {

    private String teamName;
    private SurveyDao surveyDao;
    private LiveData<List<Survey>> allSurveys;
    @Inject
    public SurveyRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        teamName= PreferenceData.getTeam(application.getApplicationContext());
        surveyDao = db.surveyDao();
        allSurveys = surveyDao.getAll(teamName);

    }

    public LiveData<List<Survey>> getAllSurveys() {
        if (allSurveys == null) {
            AppDatabase.executor.execute(() -> {
                allSurveys = surveyDao.getAll(teamName);
            });
        }
        return allSurveys;
    }
    public Survey getSurvey(int id){
        return surveyDao.getSurvey(id);
    }
    public Survey getSurveyByCI(int id){
        return surveyDao.getSurveyByCI(id);
    }
    public void insert(Survey survey){
        surveyDao.insert(survey);
    }
    public void update(Survey survey){
        surveyDao.update(survey);
    }
    public void delete(Survey survey){
        surveyDao.delete(survey);
    }
}
