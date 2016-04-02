package sportclub.model;


import javax.persistence.*;

import sportclub.profile.Profiler;

@Embeddable
public class Result {
	
	String name;
	String description;
	
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
