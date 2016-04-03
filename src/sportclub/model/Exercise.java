package sportclub.model;


import java.util.*;

import javax.persistence.*;

@Entity
public class Exercise {
	
	@Id
	@GeneratedValue
	int id;
	
	String name;
	String description;
	
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
