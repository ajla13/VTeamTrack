package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import database.AppDatabase;
import entities.Game;
import entities.Training;
import entities.User;
import repository.GameRepo;

public class GameModel extends AndroidViewModel {

    private GameRepo gameRepo;
    private LiveData<List<Game>> games;


    public GameModel(@NonNull Application application) {
        super(application);
        gameRepo = new GameRepo(application);
        games = gameRepo.getAllGames();
    }

    public LiveData<List<Game>> getGames() {
        if (games == null) {
            games = gameRepo.getAllGames();
        }
        return games;
    }
    public List<Game> getExpiredGames(Date date){
        return gameRepo.getExpiredGames(date);
    }
    public Game getGame(int id) {
        return gameRepo.getGame(id);
    }
    public Game createGame(Game game) {
        gameRepo.insert(game);
        return game;
    }
    public void updateGame(Game game) {
        gameRepo.update(game);

    }
    public void deleteGame(Game game) {
        gameRepo.delete(game);
    };

}
