package sportclub.model;


import java.util.*;
import javax.persistence.*;

import sportclub.profile.Profiler;

@Entity
public class Event {
	
	@Id
	int id;
	
	String name;
	String address;
	String description;
	String googleMapLink;
	
	public Event() {}
	
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
	@ManyToOne
	Field fields;
	
	public Field getFields() {
		return fields;
	}

	public void setFields(Field fields) {
		this.fields = fields;
	}
	@ManyToOne
	Slot slots;
	
	@ManyToMany
	List<Profiler> viewedRights;
	
	@ManyToMany
	List<Team> teams;

	public Slot getSlots() {
		return slots;
	}

	public void setSlots(Slot slots) {
		this.slots = slots;
	}

	public List<Profiler> getViewedRights() {
		return viewedRights;
	}

	public void setViewedRights(List<Profiler> viewedRights) {
		this.viewedRights = viewedRights;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	

}
