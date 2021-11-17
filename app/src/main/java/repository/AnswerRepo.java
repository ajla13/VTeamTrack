package repository;

import android.app.Application;
import android.view.animation.AnimationSet;

import androidx.lifecycle.LiveData;

import com.ul.lj.si.vteamtrack.PreferenceData;

import java.util.List;

import javax.inject.Inject;

import dao.AnswerDao;
import database.AppDatabase;
import entities.Answer;

public class AnswerRepo {
    private List<Answer> allAnswers;
    private AnswerDao answerDao;

    @Inject
    public AnswerRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        answerDao=db.answerDao();
    }

    public List<Answer> getAllAnswers(int surveyId){
        allAnswers=answerDao.getAll(surveyId);
        return allAnswers;
    }
    public Answer getAnswer(int answerId){
        return answerDao.getAnswer(answerId);
    }
    public void delete(Answer answer){
        answerDao.delete(answer);
    }
    public void insert(Answer answer){
        answerDao.insert(answer);
    }
    public void update(Answer answer){
        answerDao.update(answer);
    }
}
