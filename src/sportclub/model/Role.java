package sportclub.model;



import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRawValue;
@JsonIgnoreProperties("deleted")
@Entity
@Table(name="roles")
public class Role {
	/*@GeneratedValue
	int id;*/
	
	@Id@JsonRawValue
	private String idCode;
	private String description;
	private boolean deleted;
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public Role() {
		}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String id_code) {
		this.idCode = id_code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	

}
