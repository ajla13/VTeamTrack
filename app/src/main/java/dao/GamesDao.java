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
import entities.User;


@Dao
public interface GamesDao {

        @Query("SELECT * FROM game WHERE teamName LIKE :teamName")
        LiveData<List<Game>> getAll(String teamName);

        @Query("SELECT * FROM game WHERE id IN (:gameIds)")
        LiveData<List<Game>> loadAllByIds(int[] gameIds);

        @Query("SELECT * FROM game WHERE id LIKE :gameId")
        Game getGame(int gameId);

        @Insert
        void insert(Game game);

        @Insert
        void insertAll(Game... games);

        @Delete
        void delete(Game game);

        @Update(onConflict = OnConflictStrategy.REPLACE)
        void update(Game game);

}
