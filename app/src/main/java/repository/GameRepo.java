package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import dao.GamesDao;
import database.AppDatabase;
import entities.Game;

public class GameRepo {


    private LiveData<List<Game>> allGames;
    private GamesDao gameDao;


    @Inject
    public GameRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        gameDao = db.gameDao();
        allGames = gameDao.getAll();

    }

    public LiveData<List<Game>> getAllGames() {
        if (allGames == null) {
            AppDatabase.executor.execute(() -> {
                allGames = gameDao.getAll();
            });
        }
        return allGames;
    }


    public void insert(Game game) {
        AppDatabase.executor.execute(() -> {
            gameDao.insert(game);
        });
    }
}