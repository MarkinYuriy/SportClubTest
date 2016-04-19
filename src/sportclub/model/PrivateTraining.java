package sportclub.model;


import javax.persistence.*;

import sportclub.profile.Profiler;

@Entity
public class PrivateTraining extends Training {

	public PrivateTraining() {
		super();
	}
	
	@OneToOne
	Profiler athlete;
	@OneToOne
	Profiler coach;
	
	

}
