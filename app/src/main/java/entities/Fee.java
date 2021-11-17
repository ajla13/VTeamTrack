package entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Entity(tableName = "fee", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "playerId",
        onDelete = CASCADE))
public class Fee {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date validationDate;
    private Date payedOnDate;

    private int playerId;

    private String amount;

    private String month;

    private String teamName;

    private boolean payed;

    public String getTeamName() {
        return teamName;
    }

    public boolean isPayed() {
        return payed;
    }

    public int getId() {
        return id;
    }

    public Date getPayedOnDate() {
        return payedOnDate;
    }

    public String getMonth() {
        return month;
    }


    public Date getValidationDate() {
        return validationDate;
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

    public void setPayedOnDate(Date payedOnDate) {
        this.payedOnDate = payedOnDate;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }

    public Fee(String month, int playerId, boolean payed, String teamName, String amount, Date validationDate, Date payedOnDate) {
        this.playerId = playerId;
        this.amount = amount;
        this.validationDate = validationDate;
        this.payedOnDate = payedOnDate;
        this.payed = payed;
        this.month = month;
        this.teamName = teamName;
    }

    public static Fee[] populateFee() throws ParseException {

        return new Fee[]{
                new Fee("january", 1, false, "Coast VT", "10", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), new SimpleDateFormat("hh:mm").parse("15:00")),
                new Fee("february", 1, false, "Coast VT", "10", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), new SimpleDateFormat("hh:mm").parse("15:00")),
                new Fee("march", 1, false, "Coast VT", "10", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), new SimpleDateFormat("hh:mm").parse("15:00")),
                new Fee("april", 1, false, "Coast VT", "10", new SimpleDateFormat("dd/MM/yyyy").parse("27/04/2021"), new SimpleDateFormat("hh:mm").parse("15:00")),
        };
    }
}
