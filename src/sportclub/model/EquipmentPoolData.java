package sportclub.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("deleted")
@Entity
@Table(name ="equipmentPoolData")
public class EquipmentPoolData {
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private boolean deleted;
	
	
	@ManyToOne
	@JoinColumn(name="equipmentPoolId")
	private EquipmentPool equipmentPool;
	
	private int quantity;

	public EquipmentPoolData() {
		super();
	}

	public EquipmentPool getEquipmentPool() {
		return equipmentPool;
	}

	public void setEquipmentPool(EquipmentPool equipmentPool) {
		this.equipmentPool = equipmentPool;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
