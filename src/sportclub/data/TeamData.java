package sportclub.data;

public class TeamData {
	int id;
	String name;
	String description;
	
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
