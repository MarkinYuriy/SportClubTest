package sportclub.model;


import java.util.*;
import javax.persistence.*;

import org.hibernate.engine.internal.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import sportclub.profile.Profiler;
@JsonIgnoreProperties("deleted")
@Entity

public class Event {
	
	@Id@GeneratedValue
	private int id;
	
	private String name;
	private String address;
	private String description;
	private String googleMapLink;
	private boolean deleted;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	@ManyToOne
	private Slot slots;
	
	@ManyToMany
	@JoinTable
	  (
		      name="eventProfiler",
		      joinColumns={ @JoinColumn(name="eventId", referencedColumnName="Id") },
		      inverseJoinColumns={ @JoinColumn(name="profileId", referencedColumnName="profilerId") }
		  )
	private Set<Profiler> viewedRights;
	
	@ManyToMany@JoinTable
	  (
		      name="eventTeam",
		      joinColumns={ @JoinColumn(name="eventId", referencedColumnName="Id") },
		      inverseJoinColumns={ @JoinColumn(name="profileId", referencedColumnName="id") }
		  )
	private Set<Team> teams;
	
	/*@OneToOne
	CourtSchedule cs;*/
	
	public Event() {}
	
	public Event(int id) {
		this.id =id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGoogleMapLink() {
		return googleMapLink;
	}
	public void setGoogleMapLink(String googleMapLink) {
		this.googleMapLink = googleMapLink;
	}
	
	

	public Slot getSlots() {
		return slots;
	}

	public void setSlots(Slot slots) {
		this.slots = slots;
	}

	/*public List<Profiler> getViewedRights() {
		return viewedRights;
	}

	public void setViewedRights(List<Profiler> viewedRights) {
		this.viewedRights = viewedRights;
	}*/

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((googleMapLink == null) ? 0 : googleMapLink.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((slots == null) ? 0 : slots.hashCode());
		result = prime * result + ((teams == null) ? 0 : teams.hashCode());
		//result = prime * result + ((viewedRights == null) ? 0 : viewedRights.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (googleMapLink == null) {
			if (other.googleMapLink != null)
				return false;
		} else if (!googleMapLink.equals(other.googleMapLink))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (slots == null) {
			if (other.slots != null)
				return false;
		} else if (!slots.equals(other.slots))
			return false;
		if (teams == null) {
			if (other.teams != null)
				return false;
		} else if (!teams.equals(other.teams))
			return false;
		/*if (viewedRights == null) {
			if (other.viewedRights != null)
				return false;
		} else if (!viewedRights.equals(other.viewedRights))
			return false;*/
		return true;
	}
	
	

}
