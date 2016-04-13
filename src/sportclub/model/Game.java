package sportclub.model;


import java.util.*;

import javax.persistence.*;

import sportclub.profile.Athlete;
import sportclub.profile.Coach;

@Entity
public class Game extends Event {
	
	String type;
	String opponent;
	String results;
	@Temporal(TemporalType.TIMESTAMP)
	Date extraTime;
	@OneToOne
	StartStaff startStaff;
	@ManyToMany
	@JoinTable(name="game_athl",
	joinColumns={@JoinColumn(name="game_id", referencedColumnName="id")})
	List<Athlete> athletes;
	@ManyToMany
	@JoinTable(name="game_coach",
	joinColumns={@JoinColumn(name="game_id", referencedColumnName="id")})
	List<Coach> coach;
	
	
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

	public List<Athlete> getAthletes() {
		return athletes;
	}

	public void setAthletes(List<Athlete> athletes) {
		this.athletes = athletes;
	}

	public List<Coach> getCoach() {
		return coach;
	}

	public void setCoach(List<Coach> coach) {
		this.coach = coach;
	}
	
	//@ManyToMany
	//List<GameComposition> compositions;
	
	
	

	

}
