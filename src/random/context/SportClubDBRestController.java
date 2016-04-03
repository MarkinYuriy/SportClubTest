package random.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import sportclub.controller.SportclubConstants;

@Controller
@Scope("session")
@RequestMapping("/")
public class SportClubDBRestController {
@Autowired ISportclubRandomDBRepository dbrepo;



@RequestMapping(value=SportclubConstants.CREATE_RANDOM_DB, method=RequestMethod.PUT)
public @ResponseBody boolean createRandomDB() throws JsonProcessingException{
	
		dbrepo.addRandomProfile();
		System.out.println("random Profilers added");
		dbrepo.addRandomTrainingPool();
		System.out.println("random TrainingPool added");
		dbrepo.addRandomEvent();
		System.out.println("random Event added");
		dbrepo.addRandomTraining();
		System.out.println("random Training added");
		dbrepo.addRandomGame();
		System.out.println("random game added");
	System.out.println("random DB created");
	return true;
}




}
