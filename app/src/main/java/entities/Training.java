package entities;

import android.media.MediaParser;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ul.lj.si.vteamtrack.Converters;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "training")
public class Training {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String date;
    public String time;
    public String location;
    public String teamName;

    @TypeConverters(Converters.class)
    public List<Integer> attendancy;

    public Training(){}
    public Training( String date, String time, String location, String teamName, List<Integer> attendancy){

        this.date=date;
        this.time=time;
        this.location=location;
        this.teamName=teamName;
        this.attendancy=attendancy;
    }

    public static Training[] populateTraining() {
        return new Training[] {
                new Training( "03/11/2021", "14:00", "Hall A", "Coast VT", new ArrayList<Integer>()),
                new Training( "02/11/2021", "14:00", "Hall A", "Coast VT",
                        new ArrayList<Integer>(){{add(0);add(3);add(1);}}),
                new Training( "25/10/2021", "14:00", "Hall A", "Coast VT",
                        new ArrayList<Integer>(){{add(0);add(3);add(1);add(4);}}),
                new Training( "23/10/2021", "14:00", "Hall A", "Coast VT",
                        new ArrayList<Integer>(){{add(0);add(6);add(5); add(3); add(1); add(2);}})
        };
    }

}
