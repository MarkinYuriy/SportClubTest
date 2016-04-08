package sportclub.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.*;

import sportclub.profile.*;

@Entity
public class StartStaff {
	@Id@GeneratedValue
	int staffId;
	
	
	@ManyToOne
	Formation formation;
	
	@OneToMany
	@JoinTable
	  (
	      name="reserveProfile",
	      joinColumns={ @JoinColumn(name="staffId", referencedColumnName="staffId") },
	      inverseJoinColumns={ @JoinColumn(name="profilerId", referencedColumnName="profilerId", unique=true) }
	  )
	 List<Athlete> reserve;
	
	@OneToMany@JoinTable(name="staffCoach",
			joinColumns={ @JoinColumn(name="staffId", referencedColumnName="staffId") },
		      inverseJoinColumns={ @JoinColumn(name="profilerId", referencedColumnName="profilerId", unique=true)})
	List<Coach> coaches;
	
	public List<Athlete> getReserve() {
		return reserve;
	}

	public void setReserve(List<Athlete> reserve) {
		this.reserve = reserve;
	}

	@OneToMany
	private Map<String, Athlete> composition = new HashMap<String, Athlete>();
	
	public StartStaff() {
		
	}

	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		this.formation = formation;
	}

	
	

		public List<Coach> getCoaches() {
			return coaches;
		}
	
		public void setCoaches(List<Coach> coaches) {
			this.coaches = coaches;
		}

	public int getStaffId() {
		return staffId;
	}

	
	public Map<String, Athlete> getComposition() {
		return composition;
	}
	
	public void setCompositionKeysByFormation(Formation formation){
		
		String [] sch = formation.getPositions().split(" ");
		for(String s: sch){
		composition.put(s,null);
		}
	}

	public void setComposition(Map<String, Athlete> composition) {
		this.composition = composition;
	}
	
	
}
