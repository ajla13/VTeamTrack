package entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "feeMonth", foreignKeys = @ForeignKey(entity = Team.class,
        parentColumns = "id",
        childColumns = "teamId",
        onDelete = CASCADE))
public class FeeMonth {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date validationDate;
    private String name;

    private int teamId;

    private String teamName;

    public int getTeamId() {
        return teamId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Date getValidationDate() {
        return validationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setValidationDate(Date validationDate) {
        this.validationDate = validationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FeeMonth(String name, Date validationDate, int teamId, String teamName){
        this.name=name;
        this.validationDate=validationDate;
        this.teamId = teamId;
        this.teamName = teamName;
    }
}
