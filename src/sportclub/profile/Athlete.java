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
	private boolean federationPlayer;
	
	public boolean isFedPlayer() {
		return federationPlayer;
	}

	public void setFedPlayer(boolean fedPlayer) {
		this.federationPlayer = fedPlayer;
	}

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
		return "Athlete [number=" + number + ", type=" + type + ", birthday=" + birthday + ", federationPlayer="
				+ federationPlayer + ", games=" + games + "]";
	}

	

}
