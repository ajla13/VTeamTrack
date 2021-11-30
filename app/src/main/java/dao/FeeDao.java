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


@Dao
public interface FeeDao {

    @Query("SELECT * FROM fee WHERE teamName LIKE :teamName")
    LiveData<List<Fee>> getAll(String teamName);

    @Query("SELECT * FROM fee WHERE id LIKE :feeId")
    Fee getFee(int feeId);

    @Query("SELECT * FROM fee WHERE month LIKE :month AND teamName LIKE :teamName")
    List<Fee> getFeeByMonth(String month, String teamName);

    @Query("SELECT * FROM fee WHERE playerId LIKE :playerId AND teamName LIKE :teamName")
    List<Fee> getFeeByPlayer(int playerId, String teamName);

    @Query("SELECT * FROM fee WHERE playerId LIKE :playerId AND teamName LIKE :teamName AND month LIKE:month")
    List<Fee> getMonthlyPlayerFee(int playerId, String teamName,String month);


    @Insert
    void insert(Fee fee);

    @Insert
    void insertAll(Fee... fees);

    @Delete
    void delete(Fee fee);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Fee fee);
}
