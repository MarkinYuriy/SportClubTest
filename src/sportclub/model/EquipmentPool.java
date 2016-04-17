package sportclub.model;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

<<<<<<< HEAD
import flexjson.JSON;

=======
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import flexjson.JSON;
@JsonIgnoreProperties("deleted")
>>>>>>> refs/heads/2016-04-14
@Entity

public class EquipmentPool implements Serializable {
	
	private static final long serialVersionUID = -562592345245057817L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="equipmentId")
	private int id;
	
	private String name;
	private String description;
	private boolean deleted;
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	/*@OneToMany(mappedBy="equipmentPool")
	Set<EquipmentPoolData> equipmentPoolData;*/
	/*@Override
	public String toString() {
		
		return "{\"id\""+"\"name\":"+name+",\"description\":"+description+"\""+"]}";
	}*/
	public EquipmentPool() {	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EquipmentPool other = (EquipmentPool) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

}
