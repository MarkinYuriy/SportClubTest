package sportclub.model;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

@Entity
public class Field {
	
	@Id
	@GeneratedValue@Column(name="FIELD_ID")
	int id;
	
	String name;
	String type;
	String description;
	
	@ElementCollection
    @CollectionTable(name="PARTITIONS")
    @MapKeyJoinColumn(name="PART_ID")
    @Column(name="IS_USED")
    private Map<Integer, Integer> partitions;

	public Field() {
		partitions = new HashMap<Integer, Integer>();
	}
	public Map<Integer, Integer> getPartitions() {
        return partitions;
    }

    public void setPartitions(Map<Integer, Integer> partitions) {
		this.partitions = partitions;
	}
	public void setPartionsValue(int part, int trainingId){
    	partitions.put(part, trainingId);
    }
	
	public void setOnePartitionField(){
		partitions.put(1, null);
	}
	
	public void setTwoPartitionsField(){
		partitions.put(1, null);
		partitions.put(2, null);
	}
	
	public void setThreePartitionsField(){
		partitions.put(1, null);
		partitions.put(2, null);
		partitions.put(3, null);
	}
    
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
	
	

}
