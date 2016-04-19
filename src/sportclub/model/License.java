package sportclub.model;


import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import sportclub.profile.Profiler;
@JsonIgnoreProperties("deleted")
@Entity
public class License {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String code;
	private String description;
	private boolean deleted;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
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
	@JoinTable
	  (
		      name="licenseRole",
		      joinColumns={ @JoinColumn(name="licenseId", referencedColumnName="id") },
		      inverseJoinColumns={ @JoinColumn(name="rolesId", referencedColumnName="idCode") }
		  )
	private Set<Role> roles;
	
	
	
	
	

}
