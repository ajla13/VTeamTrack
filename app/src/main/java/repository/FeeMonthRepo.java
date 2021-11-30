package repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ul.lj.si.vteamtrack.PreferenceData;

import java.util.List;

import javax.inject.Inject;

import dao.FeeDao;
import dao.FeeMonthDao;
import database.AppDatabase;
import entities.Fee;
import entities.FeeMonth;

public class FeeMonthRepo {

    private String teamName;
    private FeeMonthDao feeMonthDao;


    @Inject
    public FeeMonthRepo(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        teamName= PreferenceData.getTeam(application.getApplicationContext());
        feeMonthDao = db.feeMonthDao();

    }
    public List<FeeMonth> getAll(){
        return feeMonthDao.getAll(teamName);
    }

    public FeeMonth getFeeMonth(int id){
        return feeMonthDao.getFeeMonth(id);
    }
    public FeeMonth getFeeMonthByMonth(String month){
        return feeMonthDao.getFeeMonthByMonth(month, teamName);
    }

    public void delete(FeeMonth feeMonth){
        feeMonthDao.delete(feeMonth);
    }
    public FeeMonth insert(FeeMonth feeMonth){
       feeMonthDao.insert(feeMonth);
       return feeMonth;
    }
    public FeeMonth update (FeeMonth feeMonth){
        feeMonthDao.update(feeMonth);
        return feeMonth;
    }
}
