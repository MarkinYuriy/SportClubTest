package sportclub.model;

import java.io.Serializable;

import javax.persistence.Embeddable;



@SuppressWarnings("serial")
@Embeddable
public class ExerciseSession implements Serializable{
	

	@Override
	public String toString() {
		return "ExerciseSession [sets=" + sets + ", reps=" + reps + ", duration=" + duration + "]";
	}

	int sets;
	int reps;
	int duration;
	
	public ExerciseSession(){
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + duration;
		result = prime * result + reps;
		result = prime * result + sets;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ExerciseSession)) {
			return false;
		}
		ExerciseSession other = (ExerciseSession) obj;
		if (duration != other.duration) {
			return false;
		}
		if (reps != other.reps) {
			return false;
		}
		if (sets != other.sets) {
			return false;
		}
		return true;
	}

	

	
}
