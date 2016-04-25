package sportclub.model;


import java.util.*;

import javax.persistence.*;

import sportclub.profile.Athlete;
import sportclub.profile.Coach;

@Entity
public class Game extends Event {
	
	private String type;
	private String opponent;
	private String results;
	@Temporal(TemporalType.TIMESTAMP)
	private Date extraTime;
	@OneToOne
	private StartStaff startStaff;
	
	public StartStaff getStartStaff() {
		return startStaff;
	}

	public void setStartStaff(StartStaff startStaff) {
		this.startStaff = startStaff;
	}

	public Game() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public Date getExtraTime() {
		return extraTime;
	}

	public void setExtraTime(Date extraTime) {
		this.extraTime = extraTime;
	}

	
	//@ManyToMany
	//List<GameComposition> compositions;
	
	
	

	

}
