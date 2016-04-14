package sportclub.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.persistence.*;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import random.context.RandomData;

import sportclub.interfaces.ISportclubRepository;
import sportclub.model.*;
import sportclub.nodeprocessor.RoleGenerator;
import sportclub.profile.*;

/**
 * @author paul
 *
 */
public class SportclubDB implements ISportclubRepository {

    String[] subClasses = {
        "AdminManagerClub",
        "AssitPhysicCoach",
        "AssitTeamCoach",
        "Athlete",
        //"Coach",
        "Parent",
        "PhysiologyCoach",
        "ProffesionalManager",
        "Profiler",
        "Psycholog",
        "TeamAdminManager",
        "TeamCoach"

    };

    @PersistenceContext(unitName = "springHibernate", type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    private String data;

    @Override
    public boolean removeProfile(int id) {
        // TODO Auto-generated method stub
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    //@Transactional
    public Iterable<Profiler> getProfiles(String SubProfiler) throws ReflectiveOperationException {

        Iterable<Profiler> res = null;
        //System.out.println("Sub "+SubProfiler);

        boolean exist = false;
        for (String str : subClasses) {
            //System.out.println("str "+ str);
            //System.out.println("subprof "+SubProfiler);
            if (str.equals(SubProfiler)) {
                exist = true;
                //System.out.println(exist);
                break;
            }

        }

        if (exist) {
            Query query = em.createQuery("from " + SubProfiler + " p where deleted=false");
            res = query.getResultList();
        }
        return res;

    }

    @Override
    @Transactional
    public boolean addTeam(Team team) {
        boolean res = false;
        Team finded = em.find(Team.class, team.getId());
        if (finded == null) {

            em.persist(team);

            res = true;
            //System.out.println("true");
        } else {
            finded.setName(team.getName());
            finded.setDescription(team.getDescription());
        }

        return res;
    }

    @Override
    @Transactional
    public boolean addProfiler(Profiler profiler, String subProfiler) {
        boolean res = false;
        Profiler finded = em.find(Profiler.class, profiler.getCode());
        if (finded == null) {

            em.persist(profiler);

            res = true;
            //System.out.println("true");
        } else {
            finded.setName(profiler.getName());
            finded.setDescription(profiler.getDescription());
            finded.setEmail(profiler.getEmail());
            finded.setLastName(profiler.getLastName());
            finded.setLogin(profiler.getLogin());
            finded.setPassword(profiler.getPassword());
            finded.setPhotos(profiler.getPhotos());
            finded.setPosition(profiler.getPosition());
            finded.setRoles(profiler.getRoles());
            finded.setTeams(profiler.getTeams());

        }
        boolean exist = false;
        for (String str : subClasses) {
            if (str.equals(subProfiler)) {
                exist = true;
                break;
            }

        }

        /*	if(exist){
			try {
				Class cl = Class.forName(subProfiler);
				 java.lang.reflect.Field[] fields = cl.getFields();
				 for (java.lang.reflect.Field field:fields){
					 field.getName();
				 }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}*/
        return res;
    }

    @Override
    @Transactional
    public boolean addCourt(Court court) {
        boolean res = false;

        if (em.find(Court.class, court.getId()) == null) {

            em.persist(court);

            res = true;
            System.out.println("true");
        }

        return res;

    }

    @Override
    @Transactional
    public boolean addAthlete(Athlete ath, Team team) {
        boolean res = false;
        Set<Team> teams = new LinkedHashSet<Team>();
        if (em.find(Athlete.class, ath.getCode()) == null) {
            teams.add(team);
            ath.setTeams(teams);
            em.persist(ath);
            res = true;
            System.out.println("true");
        }

        return res;
    }

    @Override
    public boolean addProfile(Profiler profile) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addExercise(Exercise exercise) {
        boolean res = false;

        if (em.find(Exercise.class, exercise.getId()) == null) {
            em.persist(exercise);
            res = true;
            System.out.println("true");
        }

        return res;
    }

    @Override
    public boolean addEquipment(EquipmentPool equipment) {
        boolean res = false;

        if (em.find(EquipmentPool.class, equipment.getId()) == null) {
            em.persist(equipment);
            res = true;
            System.out.println("true");
        }

        return res;
    }

    @Override
    public boolean addTrainingPoolElement(TrainingPool tr) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addGoal(Goal goal) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addClub(Club club) {
          boolean res = false;
        Club finded = em.find(Club.class, club.getId());
        if (finded == null) {

            em.persist(club);

            res = true;
            //System.out.println("true");
        } else {
            finded.setName(club.getName());
            finded.setDescription(club.getDescription());
        }

        return res;
    }

    @Override
    public boolean addEvent(Event event) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addTraining(Training training) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addPrivateTraining(PrivateTraining pt) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addGame(Game game) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Get any request from database
     */
    @Override
    @Transactional
    public Iterable<Object> getAnyRequest(String jpql)
            throws JsonGenerationException, JsonMappingException, IOException {
        System.out.println("getAnyRequest");
        boolean flMultiple = hasMultipleArguments(jpql);
        return flMultiple ? runMultipleArgumentsRequest(jpql) : runSingleArgumentRequest(jpql);
    }

    private Iterable<Object> runSingleArgumentRequest(String jpql)
            throws JsonGenerationException, JsonMappingException, IOException {

        javax.persistence.Query q = em.createQuery(jpql);
        @SuppressWarnings("unchecked")
        List<Object> objects = q.getResultList();

        return objects/*objectsToStringsList(objects)*/;
    }

//	private Iterable<String> objectsToStringsList(List<Object> objects)
//			throws JsonGenerationException, JsonMappingException, IOException {
//		List<String> res = new LinkedList<String>();
//		ObjectMapper mapper = new ObjectMapper();
//		for (Object obj : objects)
//			res.add(mapper.writeValueAsString(obj));
//		return res;
//	}
    private Iterable<Object> runMultipleArgumentsRequest(String jpql)
            throws JsonGenerationException, JsonMappingException, IOException {
        System.out.println(jpql);
        Query q = em.createQuery(jpql);
        @SuppressWarnings("unchecked")
        List<Object[]> objects = q.getResultList();
        return toIterableString(objects);
    }

    private Iterable<Object> toIterableString(List<Object[]> objects)
            throws JsonGenerationException, JsonMappingException, IOException {
        List<Object> res = new LinkedList<Object>();

        for (Object[] args : objects) {
            res.add(args);
        }

        return res;
    }

    private boolean hasMultipleArguments(String jpql) {
        String upJpql = jpql.toUpperCase();
        int ind = upJpql.indexOf("FROM");
        if (ind < 0) {
            return false;
        }
        return upJpql.substring(0, ind).contains(",");
    }

    private String objectToJson(Object[] objs) throws JsonGenerationException, JsonMappingException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        if (objs == null || objs.length == 0) {
            return null;
        }

        return mapper.writeValueAsString(objs);
    }

    @SuppressWarnings("unchecked")
    @Override
    //@Transactional
    public Iterable<Role> getRoles(String id) {
        Query query = null;
        if (id.length() > 0) {
            query = em.createQuery("from Role r where id='" + id + "' and deleted=false");
        } else {
            query = em.createQuery("from Role r");
        }
        return query.getResultList();

    }

    @SuppressWarnings("unchecked")
    @Override
    //@Transactional

    public Iterable<Club> getClubs() {
        Query query = em.createQuery("from Club c where deleted=false");
        return query.getResultList();
    }

    public Club getClub(int id) {
        Query query = em.createQuery("from Club c where id=:id and deleted=false");
        query.setParameter("id", id);

        return (Club) query.getSingleResult();

    }

    @SuppressWarnings("unchecked")
    @Override
    //@Transactional
    public Team getTeam(int id) {
        Query query = em.createQuery("from Team t where id=:id and deleted=false");
        query.setParameter("id", id);
        return (Team) query.getSingleResult();

    }

    @SuppressWarnings("unchecked")
    @Override
    //@Transactional
    public Iterable<Team> getTeams() {
        Query query = em.createQuery("from Team t where deleted=false");
        //("select t.name, t.description, profiles from Team t join t.profiles profiles where t.deleted=false");

        return query.getResultList();

    }

    @Override
    public Profiler getProfile(String SubProfiler, String id) {

        Profiler prf = null;
        /*Class<? extends Profiler> prfClass;
		try {
			prfClass = (Class<? extends Profiler>) Class.forName(SubProfiler);
			prf = prfClass.newInstance();
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        //System.out.println("Sub "+SubProfiler);
        String[] subClasses = {
            "AdminManagerClub",
            "AssitPhysicCoach",
            "AssitTeamCoach",
            "Athlete",
            //"Coach",
            "Parent",
            "PhysiologyCoach",
            "ProffesionalManager",
            "Profiler",
            "Psycholog",
            "TeamAdminManager",
            "TeamCoach"

        };
        boolean exist = false;
        for (String str : subClasses) {
            if (str.equals(SubProfiler)) {
                exist = true;
                break;
            }

        }

        if (exist) {
            //from Athlete a where id='8805712271700122315'
            Query query = em.createQuery("from " + SubProfiler + " p where code=" + "'" + id + "'" + "and deleted=false");
            //query.setParameter("code", id);

            try {
                prf = (Profiler) query.getSingleResult();
            } catch (javax.persistence.NoResultException e) {
                // TODO Auto-generated catch block
                e.getMessage();
            }
            System.out.println(prf.toString());
        }
        return prf;
    }

}
