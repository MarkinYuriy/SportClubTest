package sportclub.model;


import java.util.*;
import javax.persistence.*;

@Entity
public class Training extends Event {
		
	private String results;
	private int resultsPercent;
	
	public Training() {
		super();
	}

	
	/*public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}*/

	public int getResultsPercent() {
		return resultsPercent;
	}

	public void setResultsPercent(int resultsPercent) {
		this.resultsPercent = resultsPercent;
	}
	
	@ManyToOne
	private TrainingPool trainingPool;
	/*@OneToMany
	List<Result> resultes;*/
	


	public TrainingPool getTrainingPool() {
		return trainingPool;
	}

	public void setTrainingPool(TrainingPool trainingPool) {
		this.trainingPool = trainingPool;
	}

	
	

}
