package sportclub.interfaces;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

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

<<<<<<< HEAD
public Iterable<Object> getAnyRequest(String jpql) throws JsonGenerationException, JsonMappingException, IOException;
=======
public Iterable<String> getAnyRequest(String jpql) throws JsonGenerationException, JsonMappingException, IOException;
>>>>>>> refs/heads/2016-04-14


Iterable<Profiler> getProfiles(String subProfiler) throws ReflectiveOperationException;
Profiler getProfile(String subProfiler, String id);

Iterable<Role> getRoles(String id);

Iterable<Club> getClubs();
Club getClub(int id);

Iterable<Team> getTeams();

Team getTeam(int id);
<<<<<<< HEAD


=======
>>>>>>> refs/heads/2016-04-14





<<<<<<< HEAD
String signIn(LoginPassword lp);


String registration(LoginPassword lp);

boolean updateTeam(Team team);
boolean updateClub(Club club);
boolean updateAthlete(Athlete profiler, String subProfiler);
boolean removeClub(Club club);
boolean removeTeam(Team team);
boolean removeProfiler(Profiler profiler, String subProfiler);
=======
boolean addProfiler(Profiler profiler, String subProfiler) throws ClassNotFoundException, InstantiationException, IllegalAccessException;


String signIn(LoginPassword lp);


String registration(LoginPassword lp);
String registration(Role[] role, String subProfile);

Profiler registrationWid(String id, LoginPassword lp);
>>>>>>> refs/heads/2016-04-14

}
