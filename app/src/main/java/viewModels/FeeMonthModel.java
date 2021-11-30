package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import entities.FeeMonth;
import repository.FeeMonthRepo;

public class FeeMonthModel extends AndroidViewModel {

    private FeeMonthRepo feeMonthRepo;

    public FeeMonthModel(@NonNull Application application) {
        super(application);
        feeMonthRepo = new FeeMonthRepo(application);
    }

    public List<FeeMonth> getAll(){
        return feeMonthRepo.getAll();
    }
    public FeeMonth getFeeMonth(int id){
        return feeMonthRepo.getFeeMonth(id);
    }
    public FeeMonth getFeeMonthByMonth(String month){
        return feeMonthRepo.getFeeMonthByMonth(month);
    }
    public void delete(FeeMonth feeMonth){
        feeMonthRepo.delete(feeMonth);
    }
    public FeeMonth insert(FeeMonth feeMonth){
        feeMonthRepo.insert(feeMonth);
        return feeMonth;
    }
    public FeeMonth update (FeeMonth feeMonth){
        feeMonthRepo.update(feeMonth);
        return feeMonth;
    }
}
