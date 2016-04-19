package sportclub.model;


import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties("deleted")
@Entity
public class Exercise {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	private String description;
	private boolean deleted;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return String.format("\"id\":%s, \"name\":\"%s\", \"description\":\"%s\"", id, name, description);
	}
	
	public Exercise() {	}

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

	

	
	
	

}
