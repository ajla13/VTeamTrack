package dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import entities.Game;

@Dao
public interface GamesDao {

        @Query("SELECT * FROM game")
        LiveData<List<Game>> getAll();

        @Query("SELECT * FROM game WHERE id IN (:gameIds)")
        LiveData<List<Game>> loadAllByIds(int[] gameIds);

        @Insert
        void insert(Game game);

        @Delete
        void delete(Game game);


}
