package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import entities.Game;
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


    public Game createGame(Game game) {
        gameRepo.insert(game);
        return game;
    }
}
