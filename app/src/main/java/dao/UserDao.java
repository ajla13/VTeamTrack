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
import entities.Training;
import entities.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE teamName LIKE :teamName AND ( userRole LIKE 'player' OR userRole LIKE 'admin') AND registrationConfirmed LIKE :registrationStatus")
    LiveData<List<User>> getPlayers(String teamName,  boolean registrationStatus);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM user")
    List<User> getUsers();

    @Query("SELECT * FROM user WHERE id LIKE :userId")
    User getUser(int userId);

    @Query("SELECT * FROM user WHERE email LIKE :userEmail AND teamName LIKE :teamName")
    User checkUserCred(String userEmail, String teamName);

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    LiveData<List<User>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE firstname LIKE :first AND " +
            "lastname LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Query("SELECT * FROM user WHERE registrationConfirmed LIKE :confirmation AND userRole LIKE :userRole OR userRole LIKE :userRoleSecond AND teamName LIKE :teamName")
    LiveData<List<User>>  usersByregistration(boolean confirmation, String userRole, String userRoleSecond, String teamName);

    @Insert
    void insert(User user);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);
}
