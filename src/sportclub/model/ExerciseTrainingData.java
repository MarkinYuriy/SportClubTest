package sportclub.model;

import javax.persistence.*;


@Entity
public class ExerciseTrainingData {
	@Id@GeneratedValue
	  @Column(name="EX_TR_DATA_ID")
	  private int id;
	
	@OneToOne@JoinColumn(name="EXERCISE_ID")
	Exercise exercise;
	
	int numberOfRepeats;
	int duration;
	
	public ExerciseTrainingData() {}
	
	public Exercise getExercise() {
		return exercise;
	}
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	
	
	public int getNumberOfRepeats() {
		return numberOfRepeats;
	}
	public void setNumberOfRepeats(int numberOfRepeats) {
		this.numberOfRepeats = numberOfRepeats;
	}
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
}
