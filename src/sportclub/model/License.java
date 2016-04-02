package sportclub.model;


import java.util.*;

import javax.persistence.*;

@Entity
public class License {
	
	@Id
	@GeneratedValue
	int id;
	String code;
	String description;
	
	public License() {}
	
	public License(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToMany
	List<Role> roles;
	
	
	
	
	

}
