package sportclub.profile;


import javax.persistence.*;

import sportclub.model.License;
@Entity
public class AdminManagerClub extends Profiler {
	
	
	
	public AdminManagerClub() {
		super();
	}

	@OneToOne
<<<<<<< HEAD
	License codeLicense;

	public License getCodeLicense() {
		return codeLicense;
	}

	public void setCodeLicense(License codeLicense) {
		this.codeLicense = codeLicense;
	}

=======
	License code;

	
	
>>>>>>> refs/heads/2016-04-14
	
	
	

}
