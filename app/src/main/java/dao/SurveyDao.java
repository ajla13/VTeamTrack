package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Survey;

@Dao
public interface SurveyDao {
    @Query("SELECT * FROM survey WHERE teamName LIKE :teamName")
    LiveData<List<Survey>> getAll(String teamName);

    @Query("SELECT * FROM survey WHERE id LIKE :surveyId")
    Survey getSurvey(int surveyId);

    @Query("SELECT * FROM survey WHERE customIdentifier LIKE :surveyId")
    Survey getSurveyByCI(int surveyId);

    @Insert
    void insert(Survey survey);

    @Insert
    void insertAll(Survey... surveys);

    @Delete
    void delete(Survey survey);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Survey survey);

}
