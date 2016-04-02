package sportclub.model;


import javax.persistence.*;

@Entity
public class EquipmentPool {
	
	@Id
	@GeneratedValue
	int id;
	
	String name;
	String description;
	/*int quantity;*/
	
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

	/*public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	*/
	

}
