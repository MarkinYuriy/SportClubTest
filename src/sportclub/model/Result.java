package sportclub.model;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import sportclub.profile.Profiler;
@JsonIgnoreProperties("deleted")
@Embeddable
public class Result {
	
	private String name;
	private String description;
	
	public Result() {	}

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
