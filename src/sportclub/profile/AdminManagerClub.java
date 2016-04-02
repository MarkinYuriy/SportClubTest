package sportclub.profile;


import javax.persistence.*;

import sportclub.model.License;
@Entity
public class AdminManagerClub extends Profiler {
	
	
	
	public AdminManagerClub() {
		super();
	}

	@OneToOne
	License code;
	
	
	
	

}
