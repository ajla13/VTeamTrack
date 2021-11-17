package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import entities.Answer;
import repository.AnswerRepo;

public class AnswerModel extends AndroidViewModel {
    private AnswerRepo answerRepo;

    public AnswerModel(@NonNull Application application) {
        super(application);
        answerRepo = new AnswerRepo(application);
    }
    public List<Answer> getAllAnswers(int surveyId){
        return answerRepo.getAllAnswers(surveyId);
    }
    public Answer getAnswer(int id){
        return answerRepo.getAnswer(id);
    }
    public Answer create(Answer answer){
        answerRepo.insert(answer);
        return answer;
    }
    public Answer update(Answer answer){
        answerRepo.update(answer);
        return answer;
    }
    public void delete(Answer answer){
        answerRepo.delete(answer);
    }
}
