package sportclub.interfaces;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.fasterxml.jackson.core.JsonProcessingException;

import sportclub.profile.*;
import sportclub.controller.LoginPassword;
import sportclub.model.*;

public interface ISportclubRepository {
boolean addProfile(Profiler profile);


boolean removeProfile(int id);
boolean addTeam(Team team);

boolean addCourt(Court court);
boolean addExercise(Exercise exercise);
boolean addEquipment(EquipmentPool equipment);
boolean addTrainingPoolElement(TrainingPool tr);
boolean addGoal(Goal goal);
boolean addClub(Club club);
boolean addEvent(Event event);
boolean addTraining(Training training);
boolean addPrivateTraining(PrivateTraining pt);
boolean addGame(Game game);

boolean addAthlete(Athlete ath,Team team);

public Iterable<Object> getAnyRequest(String jpql) throws JsonGenerationException, JsonMappingException, IOException;


Iterable<Profiler> getProfiles(String subProfiler) throws ReflectiveOperationException;
Profiler getProfile(String subProfiler, String id);

Iterable<Role> getRoles(String id);

Iterable<Club> getClubs();
Club getClub(int id);

Iterable<Team> getTeams();

Team getTeam(int id);





boolean addProfiler(Profiler profiler, String subProfiler);


String signIn(LoginPassword lp);


String registration(LoginPassword lp);

boolean updateTeam(Team team);
boolean updateClub(Club club);
boolean updateAthlete(Athlete profiler, String subProfiler);
boolean removeClub(Club club);
boolean removeTeam(Team team);
boolean removeProfiler(Profiler profiler, String subProfiler);

}
