package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import dao.TeamDao;
import dao.UserDao;
import database.AppDatabase;
import entities.Team;
import entities.Training;
import entities.User;

public class TeamRepo {
    public List<User> userList;
    private List<User> allTeamUsers;
    private TeamDao teamDao;
    private Team team;


    @Inject
    public TeamRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        teamDao = db.teamDao();
    }


    public Team getTeam(String teamName) {
        return teamDao.getTeam(teamName);

    }
    public List<Team> getPublicTeams() {
        return teamDao.getPublicTeams(true);
    }
    public void insert(Team team) {
        teamDao.insert(team);

    }
    public void update(Team team){
        teamDao.update(team);
    }
}
