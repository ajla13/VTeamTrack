package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import entities.Survey;
import repository.SurveyRepo;

public class SurveyModel extends AndroidViewModel {
    private SurveyRepo surveyRepo;
    private LiveData<List<Survey>> allSurveys;

    public SurveyModel(@NonNull Application application) {
        super(application);
        surveyRepo = new SurveyRepo(application);
        allSurveys=surveyRepo.getAllSurveys();
    }

    public LiveData<List<Survey>> getAllSurveys() {
        if (allSurveys == null) {
            allSurveys=surveyRepo.getAllSurveys();
        }
        return allSurveys;
    }
    public Survey getSurvey(int id){
        return surveyRepo.getSurvey(id);
    }
    public Survey getSurveyByCI(int id){
        return surveyRepo.getSurveyByCI(id);
    }

    public Survey createSurvey(Survey survey){
        surveyRepo.insert(survey);
        return survey;
    }
    public Survey update(Survey survey){
        surveyRepo.update(survey);
        return survey;
    }
    public void delete(Survey survey){
        surveyRepo.delete(survey);
    }
}
