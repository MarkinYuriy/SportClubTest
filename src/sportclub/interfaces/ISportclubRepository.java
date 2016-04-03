package sportclub.interfaces;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.fasterxml.jackson.core.JsonProcessingException;

import sportclub.profile.*;
import sportclub.model.*;

public interface ISportclubRepository {
boolean addProfile(Profiler profile);


boolean removeProfile(int id);
boolean addTeam(Team team);

boolean addField(Field field);
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

Iterable<Profiler> getProfiles(String subProfiler) throws ReflectiveOperationException;
public Iterable<String> getAnyRequest(String jpql) throws JsonGenerationException, JsonMappingException, IOException;



Iterable<Role> getRoles(String id);

Iterable<Club> getClubs();
Iterable<Club> getClubs(long id);


Iterable<Team> getTeams(int id);

Iterable<Team> getTeams();

Iterable<Profiler> getProfiles(String subProfiler, long id);



boolean addProfiler(Profiler profiler, String subProfiler);



}
