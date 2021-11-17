package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ul.lj.si.vteamtrack.PreferenceData;

import java.util.List;

import javax.inject.Inject;

import dao.FeeDao;
import database.AppDatabase;
import entities.Fee;
import entities.Post;

public class FeeRepo {

    private String teamName;
    private FeeDao feeDao;
    private LiveData<List<Fee>> allFees;


    @Inject
    public FeeRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        teamName= PreferenceData.getTeam(application.getApplicationContext());
        feeDao = db.feeDao();
        allFees=feeDao.getAll(teamName);


    }

    public LiveData<List<Fee>> getAllFees() {
        if (allFees == null) {
            AppDatabase.executor.execute(() -> {
                allFees = feeDao.getAll(teamName);
            });
        }
        return allFees;
    }

    public List<Fee> getFeeByMonth(String month){
        return feeDao.getFeeByMonth(month, teamName);
    }

    public Fee getFee(int id){
        return feeDao.getFee(id);
    }

    public void delete(Fee fee){
        feeDao.delete(fee);
    }
    public Fee insert(Fee fee){
        feeDao.insert(fee);
        return fee;
    }
    public Fee update (Fee fee){
        feeDao.update(fee);
        return fee;
    }
}
