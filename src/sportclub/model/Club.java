package sportclub.model;


import java.util.*;
import javax.persistence.*;

@Entity
public class Club {
	
	@Id
	@GeneratedValue
	int id;
	
	String name;
	String location;
	String description;
	
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

	public List<Event> getDiary() {
		return diary;
	}

	public void setDiary(List<Event> diary) {
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
	List<Event> diary;
	
	@OneToMany
	List<ImageBank> photos;

}
