package sportclub.model;

import javax.persistence.*;


public class Partition {

	@Id@Column
	int id;
	
	Training training;
	
}
