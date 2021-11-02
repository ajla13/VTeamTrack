package entities;

import android.provider.ContactsContract;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.RenameTable;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

@Entity(tableName = "user")
public class User {



    @PrimaryKey(autoGenerate = true)
    public int id;

    public String firstName;

    public String lastName;
    public String teamName;
    public String email;

    public String password;
    public String userRole;
    public String phoneNumber;

    public String dateOfBirth;

    public boolean registrationConfirmed;

    public User(){}
    public User( String firstName, String lastName, String dateOfBirth, String teamName, String email, String password, String userRole, String phoneNumber, boolean registrationConfirmed) {


        this.firstName = firstName;
        this.lastName = lastName;
        this.teamName = teamName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
        this.registrationConfirmed = registrationConfirmed;

    }
    public static User[] populateData() {
        return new User[] {
                new User("Jane", "Doe", "25/11/1998", "Coast VT", "jane.doe@mail.com", "janedoe", "player", "00386725643", true),
                new User("John", "Doe", "25/11/1998", "Coast VT","john.doe@mail.com", "johndoe", "player", "00386526781", true ),
                new User("Emily", "Hart", "25/11/1998","Coast VT", "emily.hart@mail.com", "emilyhart", "player", "00389782826", true ),
                new User("Leona", "Martin", "25/11/1998","Coast VT","leona.martin@mail.com", "leonamartin", "player", "00386543123", true ),
                new User("Marin", "Samson","25/11/1998","Coast VT", "marin.samson@mail.com", "marinsamson", "player", "00389724563", true ),
                new User("Kendall", "Murphy","25/11/1998", "Coast VT","kendall.murphy@mail.com", "kendallmurphy", "admin", "00389825634", true ),
                new User("Samuel", "Philips","25/11/1998", "Coast VT","samuel.philips@mail.com", "samuelphilips", "admin", "00389176254", true ),
                new User("Daniel", "Fanton", "25/11/1998","Coast VT","daniel.fanton@mail.com", "danielfanton", "player", "00389165245", false ),
                new User("Mirel", "Jackobs","25/11/1998","Coast VT", "mirel.jackbos@mail.com", "mireljackobs", "player", "00389172564", false ),
                new User("Dana", "North","25/11/1998","Coast VT","dana.north@mail.com", "dananorth", "player", "00389726561", false ),
                new User("Elsa", "Clarity","25/10/1977","Coast VT", "elsa.clarity@mail.com", "elsaclarity", "trainer", "00387123678", true ),
                new User("Mark", "Hampton","25/11/1965","Leading VT", "mark.hampton@mail.com", "markhampton", "trainer", "00387762543", true )

        };
    }
}
