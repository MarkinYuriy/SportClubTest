package sportclub.model;


import java.util.List;
import java.util.Set;

import javax.persistence.*;

import sportclub.profile.Profiler;

@Entity
public class Team {
	
	@Id
	@GeneratedValue
	int id;
	String name;
	String description;
	
	@OneToMany
	Set<ImageBank> photos;
	@OneToMany
	List<GameTeams> results;
	@ManyToMany(mappedBy="teams")
	List<Event> diary;
	@ManyToMany(mappedBy = "teams")
	List<Profiler> profiles;
	
	public Team() {}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
	public List<Profiler> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profiler> profiles) {
		this.profiles = profiles;
	}

}
