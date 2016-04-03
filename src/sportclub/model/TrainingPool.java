package sportclub.model;


import java.util.*;
import javax.persistence.*;

@Entity
public class TrainingPool {
	
	@Id
	@GeneratedValue
	int id;
	
	String type;
	String name;
	String description;
	int level;
	@ManyToMany
	Set<Goal>goals=new HashSet<Goal>();
	
	
	
	public Set<Goal> getGoals() {
		return goals;
	}

	public void setGoals(Set<Goal> goals) {
		this.goals = goals;
	}

	

	public TrainingPool() {	
		equipmentPoolData = new HashMap<EquipmentPool, Integer>();
		exercises = new HashMap<Exercise,ExerciseSession>();}
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
	
	@ElementCollection
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
	}

	@ElementCollection
    @CollectionTable(name="exerciseData")
    @MapKeyJoinColumn(name="exerciseId")
    Map<Exercise, ExerciseSession> exercises;
	public Map<Exercise, ExerciseSession> getExercises() {
		return exercises;
	}

	public void setExercises(Map<Exercise, ExerciseSession> exercises) {
		this.exercises = exercises;
	}
	
	public void setExercisesDataValue(Exercise ep,ExerciseSession es){
		exercises.put(ep, es);
	}
	
	
	
	

	

	

}
