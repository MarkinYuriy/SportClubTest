package sportclub.profile;


import java.util.Map;

import javax.persistence.*;

import sportclub.model.License;
@Entity
public class AdminManagerClub extends Profiler {
	
	
	
	public AdminManagerClub() {
		super();
	}

	@OneToOne
	License code;

	
	
	
	public void setProperties(Map<String, String> properties){
		
		super.setProperties(properties);
		
	}
	

}
