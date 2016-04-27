package sportclub.data;

public class TeamData {
	private int id;
	private String name;
	private String description;
	private String errorMassage;
	
	public String getErrorMassage() {
		return errorMassage;
	}

	public void setErrorMassage(String errorMassage) {
		this.errorMassage = errorMassage;
	}

	public TeamData(String errorMassage) {
		super();
		this.errorMassage = errorMassage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TeamData(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public TeamData(int id) {
		this.id =id;
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
	
	
}
