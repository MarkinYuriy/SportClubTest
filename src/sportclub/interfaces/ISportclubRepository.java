package sportclub.interfaces;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import sportclub.profile.*;
import sportclub.controller.LoginPassword;
import sportclub.data.ProfileData;
import sportclub.data.TeamData;
import sportclub.model.*;

public interface ISportclubRepository {
ProfileData removeProfiler(String id);
TeamData addTeam(Team team);

boolean addCourt(Court court);
boolean addExercise(Exercise exercise);
boolean addEquipment(EquipmentPool equipment);
boolean addTrainingPoolElement(TrainingPool tr);
boolean addGoal(Goal goal);
Club addClub(Club club);
boolean addEvent(Event event);
boolean addTraining(Training training);
boolean addPrivateTraining(PrivateTraining pt);
boolean addGame(Game game);

boolean addAthlete(Athlete ath,Team team);

public Iterable<String> getAnyRequest(String jpql) throws JsonGenerationException, JsonMappingException, IOException;


Iterable<Profiler> getProfiles(String subProfiler);
Profiler getProfile(String subProfiler, String id);

Iterable<Role> getRoles(String id);

Iterable<Club> getClubs();
Club getClub(int id);

Iterable<Team> getTeams();

Team getTeam(int id);

String signIn(LoginPassword lp);


String registration(LoginPassword lp) throws InstantiationException, IllegalAccessException, ClassNotFoundException;
//String registration(Role[] role, String subProfile);

//Profiler registrationWid(String id, LoginPassword lp);


ProfileData updateProfiler(Map<String,String> properties);
Team updateTeam(Team team);
Club updateClub(Club club);
//boolean updateAthlete(Athlete profiler, String subProfiler);
Club removeClub(Club club);
Team removeTeam(Team team);


}
