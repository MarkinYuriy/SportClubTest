package sportclub.model;

import javax.persistence.*;

@Entity
public class GameTeams {

    @Id
    @GeneratedValue
    private int id;

    private int grade;
    private String result;
    private String description;

    public GameTeams() {
    }

    public GameTeams(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne
    Game game;

}
