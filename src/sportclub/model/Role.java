package sportclub.model;



import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role {
	
	@Id
	/*@GeneratedValue
	int id;*/
	String id_code;
	String description;
	
	public Role() {
		}

	public String getId_code() {
		return id_code;
	}

	public void setId_code(String id_code) {
		this.id_code = id_code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	

}
