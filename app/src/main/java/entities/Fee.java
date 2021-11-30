package entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "fee", foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "playerId",
        onDelete = CASCADE),
        @ForeignKey(entity = FeeMonth.class,
        parentColumns = "id",
        childColumns = "feeMonthId",
        onDelete = CASCADE)})
public class Fee {

    @PrimaryKey(autoGenerate = true)
    private int id;


    private int playerId;

    private int feeMonthId;

    private String amount;

    private String month;

    private String teamName;

    private boolean payed;

    public int getFeeMonthId() {
        return feeMonthId;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isPayed() {
        return payed;
    }

    public int getId() {
        return id;
    }


    public String getMonth() {
        return month;
    }


    public int getPlayerId() {
        return playerId;
    }

    public String getAmount() {
        return amount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setFeeMonthId(int feeMonthId) {
        this.feeMonthId = feeMonthId;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    public Fee(String month, int playerId, boolean payed, String teamName, String amount, int feeMonthId) {
        this.playerId = playerId;
        this.amount = amount;
        this.payed = payed;
        this.month = month;
        this.teamName = teamName;
        this.feeMonthId = feeMonthId;
    }

    public static Fee[] populateFee() throws ParseException {

        return new Fee[]{
                new Fee("Jan", 1, false, "Coast VT", "10",1),
                new Fee("Feb", 1, false, "Coast VT", "10",1),
                new Fee("Mar", 1, false, "Coast VT", "10",1),
                new Fee("Apr", 1, false, "Coast VT", "10",1),
        };
    }
}
