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
	
	public TrainingPool() {	}

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
	
	@ManyToMany
	List<Goal> goals;
	
	@OneToMany
	List<ExerciseTrainingData> exercisesData;
	
	@OneToMany
	List<EquipmentPoolData> equipmentPoolData;
	
	
	public List<ExerciseTrainingData> getExercisesData() {
		return exercisesData;
	}

	public void setExercisesData(List<ExerciseTrainingData> exercisesData) {
		this.exercisesData = exercisesData;
	}

	public List<Goal> getGoals() {
		return goals;
	}

	public void setGoals(List<Goal> goals) {
		this.goals = goals;
	}

	public List<EquipmentPoolData> getEquipmentPoolData() {
		return equipmentPoolData;
	}

	public void setEquipmentPoolData(List<EquipmentPoolData> equipmentPoolData) {
		this.equipmentPoolData = equipmentPoolData;
	}

	

	

}
