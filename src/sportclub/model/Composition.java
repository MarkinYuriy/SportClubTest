package sportclub.model;

import javax.persistence.*;

import sportclub.profile.Athlete;

@Entity
public class Composition {

	@Id
	String pos;
	
	@ManyToOne
	Athlete athlete;
	
	
	
}
