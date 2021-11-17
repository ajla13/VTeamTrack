package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Answer;


@Dao
public interface AnswerDao {
    @Query("SELECT * FROM answer WHERE surveyId LIKE :surveyId")
    List<Answer> getAll(int surveyId);

    @Query("SELECT * FROM answer WHERE id LIKE :answerId")
    Answer getAnswer(int answerId);

    @Insert
    void insert(Answer answer);

    @Insert
    void insertAll(Answer... answers);

    @Delete
    void delete(Answer answer);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Answer answer);
}
