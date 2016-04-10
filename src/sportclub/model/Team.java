package sportclub.model;


import java.util.List;
import java.util.Set;

import javax.persistence.*;

import sportclub.profile.Profiler;



@Entity
public class Team {
	
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", description=" + description + ", photos=" + photos
				+ ", results=" + results + ", diary=" + diary + ", profiles=" + profiles + "]";
	}

	@Id
	@GeneratedValue
	int id;
	String name;
	String description;
	boolean deleted;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<ImageBank> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<ImageBank> photos) {
		this.photos = photos;
	}

	public List<GameTeams> getResults() {
		return results;
	}

	public void setResults(List<GameTeams> results) {
		this.results = results;
	}

	public Set<Event> getDiary() {
		return diary;
	}

	public void setDiary(Set<Event> diary) {
		this.diary = diary;
	}

	@OneToMany
	Set<ImageBank> photos;
	@OneToMany
	List<GameTeams> results;
	
	@ManyToMany(mappedBy="teams")
	Set<Event> diary;
	
	@ManyToMany(mappedBy = "teams")
	Set<Profiler> profiles;
	
	

	
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
		
	public Set<Profiler> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<Profiler> profiles) {
		this.profiles = profiles;
	}

}
