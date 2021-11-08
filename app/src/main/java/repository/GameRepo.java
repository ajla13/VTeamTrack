package repository;

import android.app.Application;


import androidx.lifecycle.LiveData;


import com.ul.lj.si.vteamtrack.PreferenceData;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dao.GamesDao;
import database.AppDatabase;
import entities.Game;
import entities.Training;

public class GameRepo {


    private LiveData<List<Game>> allGames;
    private GamesDao gameDao;
    private Game game;
    String teamName;
    @Inject
    public GameRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        gameDao = db.gameDao();
        teamName= PreferenceData.getTeam(application.getApplicationContext());
        allGames = gameDao.getAll(teamName);

    }

    public LiveData<List<Game>> getAllGames() {
        if (allGames == null) {
            AppDatabase.executor.execute(() -> {
                allGames = gameDao.getAll(teamName);
            });
        }
        return allGames;
    }

    public Game getGame(int id) {
        game = gameDao.getGame(id);
        return game;
    }
    public List<Game> getExpiredGames(Date date){
        return gameDao.getExpiredGames(teamName,date);
    }
    public void insert(Game game) {
        AppDatabase.executor.execute(() -> {
            gameDao.insert(game);
        });
    }
    public void delete(Game game) {
        AppDatabase.executor.execute(() -> {
            gameDao.delete(game);
        });
    }
    public void update(Game game) {
        AppDatabase.executor.execute(() -> {
            gameDao.update(game);
        });
    }
}