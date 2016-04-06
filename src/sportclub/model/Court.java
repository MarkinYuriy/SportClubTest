package sportclub.model;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Court {
	
	@Id
	@GeneratedValue@Column(name="fieldId")
	int id;
	
	String name;
	String type;
	String description;
	
	


	public Court() {
		
	}
	
	@ManyToMany 
	Set<CourtSchedule> eventSessions;
    
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
	
	

}
