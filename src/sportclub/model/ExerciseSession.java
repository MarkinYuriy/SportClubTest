package sportclub.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@JsonIgnoreProperties("deleted")
@Entity
public class ExerciseSession implements Serializable {

    public ExerciseSession(Exercise exercise, int sets, int reps, int duration) {
        super();
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.duration = duration;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean deleted;

    @ManyToOne
    private Exercise exercise;

    private int sets;
    private int reps;
    private int duration;

    public ExerciseSession() {
        sets = 0;
        reps = 0;
        duration = 0;
    }

    public ExerciseSession(int sets, int reps, int duration) {
        super();
        this.sets = sets;
        this.reps = reps;
        this.duration = duration;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getId() {
        return id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
