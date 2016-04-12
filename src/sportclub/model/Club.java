package sportclub.model;


import java.util.*;
import javax.persistence.*;

@Entity
public class Club {
	
	@Id
	@GeneratedValue
	@Column(name="clubId")
	private int id;
	
	private String name;
	private String location;
	private String description;
	private boolean deleted;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Club() {}

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public Set<Event> getDiary() {
		return diary;
	}

	public void setDiary(Set<Event> diary) {
		this.diary = diary;
	}

	public List<ImageBank> getPhotos() {
		return photos;
	}

	public void setPhotos(List<ImageBank> photos) {
		this.photos = photos;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany
	@JoinTable
	  (
	      name="clubEvent",
	      joinColumns={ @JoinColumn(name="clubId", referencedColumnName="clubId") },
	      inverseJoinColumns={ @JoinColumn(name="eventId", referencedColumnName="id") }
	  )
	private Set<Event> diary;
	
	@OneToMany
	private List<ImageBank> photos;

}
