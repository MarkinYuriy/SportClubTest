package random.context;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ISportclubRandomDBRepository {

	boolean addRandomProfile() throws JsonProcessingException;

	boolean addRandomEvent();

	boolean addRandomTrainingPool();

	boolean addRandomTraining();

	boolean addRandomRoles(int n) throws JsonProcessingException;

	void addRandomGame();
}
