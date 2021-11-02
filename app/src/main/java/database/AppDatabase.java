package database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ul.lj.si.vteamtrack.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.GamesDao;
import dao.TeamDao;
import dao.TrainingDao;
import dao.UserDao;
import entities.Game;
import entities.Team;
import entities.Training;
import entities.User;

@Database(entities = {User.class, Game.class, Team.class,Training.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract GamesDao gameDao();
    public abstract TeamDao teamDao();
    public abstract TrainingDao trainingDao();
    private static final String DB_NAME="user_db";
    private static AppDatabase databaseInstance;

    public static ExecutorService executor = Executors.newSingleThreadExecutor();


    public static synchronized AppDatabase getInstance(Context context) {
        if(databaseInstance==null){
            databaseInstance= Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                 DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries()
                    .addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                        getInstance(context).userDao().insertAll(User.populateData());
                        getInstance(context).teamDao().insertAll(Team.populateTeam());
                        getInstance(context).trainingDao().insertAll(Training.populateTraining());
                        getInstance(context).gameDao().insertAll(Game.populateGame());

                }}).build(); ;

            }
        return databaseInstance;

    }
    }



