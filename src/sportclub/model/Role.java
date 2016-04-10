package sportclub.model;



import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role {
	
	@Id
	/*@GeneratedValue
	int id;*/
	private String idCode;
	private String description;
	
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
