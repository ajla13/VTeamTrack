package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Game;
import entities.Training;

@Dao
public interface TrainingDao {

    @Query("SELECT * FROM training")
    LiveData<List<Training>> getAll();

    @Query("SELECT * FROM training WHERE id IN (:trainingIds)")
    LiveData<List<Training>> loadAllByIds(int[] trainingIds);

    @Query("SELECT * FROM training WHERE id LIKE :trainingId")
    Training getTraining(int trainingId);

    @Insert
    void insert(Training training);

    @Delete
    void delete(Training training);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Training training);
}
