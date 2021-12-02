package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

import entities.Game;
import entities.Training;
import entities.User;

@Dao
public interface TrainingDao {

    @Query("SELECT * FROM training WHERE teamName LIKE :teamName")
    LiveData<List<Training>> getAll(String teamName);

    @Query("SELECT * FROM training WHERE teamName IN (SELECT name FROM team WHERE publicTeam LIKE :status )")
    List<Training> getAllPublicTrainings(boolean status);

    @Query("SELECT * FROM training WHERE teamName LIKE :teamName AND date <= :date")
    List<Training> getExpairedTrainings(String teamName, Date date);

    @Query("SELECT * FROM training WHERE teamName LIKE :teamName")
    List<Training> getTeamTrainings(String teamName);

    @Query("SELECT * FROM training WHERE id IN (:trainingIds)")
    LiveData<List<Training>> loadAllByIds(int[] trainingIds);

    @Query("SELECT * FROM training WHERE id LIKE :trainingId")
    Training getTraining(int trainingId);

    @Insert
    void insert(Training training);

    @Insert
    void insertAll(Training... trainings);

    @Delete
    void delete(Training training);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Training training);
}
