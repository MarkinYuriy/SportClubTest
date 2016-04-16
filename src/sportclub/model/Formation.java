package sportclub.model;


import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties("deleted")
@Entity
public class Formation {
	@Override
	public String toString() {
		return "Formation [name=" + name + ", positions=" + positions + "]";
	}

	@Id
	private String name;
	private String positions;
	
	private boolean deleted;
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
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
