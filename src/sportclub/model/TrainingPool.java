package sportclub.model;


import java.util.*;
import javax.persistence.*;

import org.hibernate.annotations.Cascade;

@Entity
public class TrainingPool {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tpId")
	private int id;
	
	private String type;
	private String name;
	private String description;
	private int level;
	@ManyToMany
	private Set<Goal>goals=new HashSet<Goal>();
	
	
	
	public Set<Goal> getGoals() {
		return goals;
	}

	public void setGoals(Set<Goal> goals) {
		this.goals = goals;
	}

	

	public TrainingPool() {	
		/*equipmentPoolData = new HashMap<EquipmentPool, Integer>();
		exercises = new HashMap<Exercise,ExerciseSession>();*/}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy ="trainingPool", cascade = 
	    {CascadeType.PERSIST, CascadeType.MERGE})
	    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private Set<EquipmentPoolData> equipmentPoolData = new HashSet<EquipmentPoolData>();*/
	/*@ElementCollection
    @CollectionTable(name="equipmentPoolData")
    @MapKeyJoinColumn(name="equipmentId")
    @Column(name="quantity", nullable = false, columnDefinition = "int default 0")
    private Map<EquipmentPool, Integer> equipmentPoolData;
	
	public Map<EquipmentPool, Integer> getEquipmentPoolData() {
		return equipmentPoolData;
	}

	public void setEquipmentPoolData(Map<EquipmentPool, Integer> equipmentPoolData) {
		this.equipmentPoolData = equipmentPoolData;
	}
	
	public void setEquipmentPoolDataValue(EquipmentPool ep, int quantity){
		equipmentPoolData.put(ep, quantity);
	}*/

	/*@ElementCollection
    @CollectionTable(name="exerciseData")
    @MapKeyJoinColumn(name="exerciseId")*/
	
	/*public Set<EquipmentPoolData> getEquipmentPoolData() {
		return equipmentPoolData;
	}

	public void setEquipmentPoolData(Set<EquipmentPoolData> equipmentPoolData) {
		this.equipmentPoolData = equipmentPoolData;
	}*/

	/*public Map<Exercise, ExerciseSession> getExercises() {
		return exercises;
	}

	public void setExercises(Map<Exercise, ExerciseSession> exercises) {
		this.exercises = exercises;
	}
	
	public void setExercisesDataValue(Exercise ep,ExerciseSession es){
		exercises.put(ep, es);
	}*/
	@OneToMany
    private List<ExerciseSession> exercises;
	
	
	
	public List<ExerciseSession> getExercises() {
		return exercises;
	}

	public void setExercises(List<ExerciseSession> exercises) {
		this.exercises = exercises;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainingPool other = (TrainingPool) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
	

	

	

}
