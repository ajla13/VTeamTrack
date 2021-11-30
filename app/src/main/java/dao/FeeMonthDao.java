package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Fee;
import entities.FeeMonth;

@Dao
public interface FeeMonthDao {

    @Query("SELECT * FROM feeMonth WHERE teamName LIKE :teamName")
    List<FeeMonth> getAll(String teamName);

    @Query("SELECT * FROM feeMonth WHERE id LIKE :feeId")
    FeeMonth getFeeMonth(int feeId);

    @Query("SELECT * FROM feeMonth WHERE name LIKE :month AND teamName LIKE :teamName")
    FeeMonth getFeeMonthByMonth(String month, String teamName);

    @Insert
    void insert(FeeMonth feeMonth);

    @Insert
    void insertAll(FeeMonth... feeMonths);

    @Delete
    void delete(FeeMonth feeMonth);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(FeeMonth feeMonth);
}
