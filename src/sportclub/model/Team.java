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
	@ManyToOne
	Club club;
	
	
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

	public List<Event> getDiary() {
		return diary;
	}

	public void setDiary(List<Event> diary) {
		this.diary = diary;
	}

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", description=" + description + ", photos=" + photos
				+ ", results=" + results + ", diary=" + diary + ", profiles=" + profiles + ", club=" + club + "]";
	}
	

}
