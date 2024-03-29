package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import entities.Team;
import entities.User;

@Dao
public interface TeamDao {

    @Query("SELECT * FROM team WHERE name LIKE :teamName ")
    Team getTeam(String teamName);

    @Query("SELECT * FROM team WHERE publicTeam LIKE :status")
    List<Team> getPublicTeams(boolean status);
    @Insert
    void insert(Team team);

    @Insert
    void insertAll(Team... teams);

    @Delete
    void delete(Team team);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Team team);
}
