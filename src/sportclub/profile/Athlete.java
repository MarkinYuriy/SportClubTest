package sportclub.profile;

import java.util.*;
import javax.persistence.*;

import sportclub.model.GameAthletes;

@Entity
public class Athlete extends Profiler {

    private int number;
    private String type;
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    public Athlete() {
        super();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<GameAthletes> getGames() {
        return games;
    }

    public void setGames(List<GameAthletes> games) {
        this.games = games;
    }

    public Athlete(int number, String type, Date birthday) {
        super();
        this.number = number;
        this.type = type;
        this.birthday = birthday;
    }

    @OneToMany
    private List<GameAthletes> games;

    @Override
    public String toString() {
        return "Athlete [number=" + number + ", type=" + type + ", birthday=" + birthday + "]" + super.toString();
    }

    @Override
    public void setProperties(Map<String, String> properties) {

        super.setProperties(properties);

        type = properties.get("type");

        if (properties.get("number") != null) {
            number = Integer.parseInt(properties.get("number"));
        }

    }

}
