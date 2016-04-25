package sportclub.data;

public class RoleData {
	String id_code;
	String description;
	public RoleData(String id_code, String description) {
		super();
		this.id_code = id_code;
		this.description = description;
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
