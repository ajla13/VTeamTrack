package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ul.lj.si.vteamtrack.typeConverters.Converters;
import com.ul.lj.si.vteamtrack.typeConverters.DateConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.CommentDao;
import dao.GamesDao;
import dao.PostDao;
import dao.TeamDao;
import dao.TrainingDao;
import dao.UserDao;
import entities.Comment;
import entities.Game;
import entities.Post;
import entities.Team;
import entities.Training;
import entities.User;

@Database(entities = {User.class, Game.class, Team.class,Training.class, Post.class, Comment.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class, DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract GamesDao gameDao();
    public abstract TeamDao teamDao();
    public abstract PostDao postDao();
    public abstract CommentDao commentDao();
    public abstract TrainingDao trainingDao();
    private static final String DB_NAME="user_db";
    private static AppDatabase databaseInstance;

    public static ExecutorService executor = Executors.newSingleThreadExecutor();


    public static synchronized AppDatabase getInstance(Context context) {
        if(databaseInstance==null){
            databaseInstance= Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                 DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries()
                   .build();

            }
        return databaseInstance;

    }
    }



