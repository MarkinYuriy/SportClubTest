package random.context;

import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ISportclubRandomDBRepository {

	boolean addRandomProfile() throws JsonProcessingException, ParseException;

	boolean addRandomEvent();

	boolean addRandomTrainingPool();

	boolean addRandomTraining();

	boolean addRandomRoles(int n) throws JsonProcessingException;

	void addRandomGame();
}
