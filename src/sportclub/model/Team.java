package sportclub.model;


import java.util.List;
import java.util.Set;

import javax.persistence.*;

import sportclub.profile.Profiler;



@Entity
public class Team {
	
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	private boolean deleted;

	@OneToMany
	private Set<ImageBank> photos;
	
	@OneToMany
	private List<GameTeams> results;
	
	@ManyToMany(mappedBy="teams")
	private Set<Event> diary;
	
	@ManyToMany(mappedBy = "teams")
	private Set<Profiler> profiles;
	
	@ManyToOne
	Club club;
	
	public Team() {}
	
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
	
	
	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", description=" + description + ", deleted=" + deleted
				+ ", photos=" + photos + ", results=" + results + ", diary=" + diary + ", profiles=" + profiles
				+ ", club=" + club + "]";
	}

	
}
