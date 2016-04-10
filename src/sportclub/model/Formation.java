package sportclub.model;


import java.util.*;
import javax.persistence.*;

@Entity
public class Formation {
	@Override
	public String toString() {
		return "Formation [name=" + name + ", positions=" + positions + "]";
	}

	@Id
	String name;
	String positions;
	
	public Formation() {	}
	
	/*public int getFormationId() {
		return formationId;
	}*/
	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	}
