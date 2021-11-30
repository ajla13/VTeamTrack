package viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import entities.Fee;
import entities.Game;
import repository.FeeRepo;

public class FeeModel extends AndroidViewModel {
    private FeeRepo feeRepo;
    private LiveData<List<Fee>> allFees;

    public FeeModel(@NonNull Application application) {
        super(application);
        feeRepo=new FeeRepo(application);
        allFees=feeRepo.getAllFees();
    }
    public LiveData<List<Fee>> getAllFees() {
        if (allFees == null) {
            allFees = feeRepo.getAllFees();
        }
        return allFees;
    }
    public List<Fee> getMonthlyPlayerFee(int id, String month){
        return feeRepo.getMonthlyPlayerFee(id, month);
    }
    public List<Fee> getFeeByMonth(String month){
        return feeRepo.getFeeByMonth(month);
    }
    public List<Fee> getFeeByPlayer(int id){
        return feeRepo.getFeeByPlayer(id);
    }

    public Fee getFee(int id){
        return feeRepo.getFee(id);
    }
    public void delete(Fee fee){
        feeRepo.delete(fee);
    }
    public Fee insert(Fee fee){
        return feeRepo.insert(fee);
    }
    public Fee update(Fee fee){
        return feeRepo.update(fee);
    }
}
