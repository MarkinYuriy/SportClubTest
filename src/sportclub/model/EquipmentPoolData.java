package sportclub.model;

import javax.persistence.*;

@Entity
public class EquipmentPoolData {
	
	
	@Id@GeneratedValue
	  @Column(name="EQ_POOL_DATA_ID")
	  private int id;
	
	@OneToOne@JoinColumn(name="EQUIPMENT_ID")
	EquipmentPool equipmentPool;
	
	int quantityForTraining;

	public EquipmentPool getEquipmentPool() {
		return equipmentPool;
	}

	public void setEquipmentPool(EquipmentPool equipmentPool) {
		this.equipmentPool = equipmentPool;
	}

	public int getQuantityForTraining() {
		return quantityForTraining;
	}

	public void setQuantityForTraining(int quantityForTraining) {
		this.quantityForTraining = quantityForTraining;
	}
	
}
