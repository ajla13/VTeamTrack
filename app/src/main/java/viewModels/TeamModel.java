package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import entities.Team;
import entities.Training;
import entities.User;
import repository.TeamRepo;


public class TeamModel extends AndroidViewModel {

    private TeamRepo teamRepo ;
    private List<User> allTeamUsers;

    public TeamModel(@NonNull Application application) {
        super(application);
        teamRepo = new TeamRepo(application);
    }


    public Team getTeam (String teamName){
        return teamRepo.getTeam(teamName);
    }
    public Team createTeam(Team team) {
        teamRepo.insert(team);
        return team;
    }

    public List<Team> getPublicTeams(){
        return teamRepo.getPublicTeams();
    }
    public Team update(Team team){
        teamRepo.update(team);
        return team;
    }

}
