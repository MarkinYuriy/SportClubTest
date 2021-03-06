package sportclub.model;


import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import sportclub.profile.Profiler;


@JsonIgnoreProperties("deleted")
@Entity
public class Team {
	
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	
	private String description;
	private boolean deleted;
	@JsonIgnore
	@OneToMany
	private Set<ImageBank> photos;
	@JsonIgnore
	@OneToMany
	private List<GameTeams> results;
	@JsonIgnore
	@ManyToMany(mappedBy="teams")
	private Set<Event> diary;
	
	//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIgnore
	@ManyToMany(mappedBy="teams")
	 Set<Profiler> profiles;
	@JsonIgnore
	@ManyToOne
	Club club;
	
	
	public Club getClub() {
		return club;
	}

	public Team(int id, String name, String description, Set<Profiler> profiles) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.profiles = profiles;
	}

	public Team(int id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Team() {}
	
	public boolean isDeleted() {
		return deleted;
	}

	public Team(int id, String name, String description, Set<ImageBank> photos, List<GameTeams> results,
			Set<Event> diary, Set<Profiler> profiles) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.photos = photos;
		this.results = results;
		this.diary = diary;
		this.profiles = profiles;
	}

	
	public Team(int id, String name, String description, Set<ImageBank> photos, List<GameTeams> results,
			Set<Profiler> profiles) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.photos = photos;
		this.results = results;
		this.profiles = profiles;
	}

	public Team(int id) {
this.id = id;
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
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", description=" + description + ", photos=" + photos
				+ ", results=" + results + ", diary=" + diary + ", profiles=" + profiles + "]";
	}

	public void setClub(Club club) {
		this.club = club;
	}

	
}
