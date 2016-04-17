package sportclub.controller;

import java.io.IOException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.SingularAttribute;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/*import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;*/
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import flexjson.JSONDeserializer;
import sportclub.interfaces.ISportclubRepository;
import sportclub.model.*;
import sportclub.profile.*;

/**
 * @author paul
 *
 */
public class SportclubDB implements ISportclubRepository {

    private static final String SPORTCLUB_PROFILE = "sportclub.profile.";

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
            
            if (str.equals(SubProfiler)) {
                exist = true;
                
                break;
            }

        }

        try {
			if (exist) {
			    Query query = em.createQuery("from " + SubProfiler + " p where deleted=false");
			    res = query.getResultList();
			}
			return res;
		} catch (NoResultException e) {
			
		}
        return res;

    }

    @Override
    @Transactional
    public boolean addTeam(Team team) {
        boolean res = false;

        Query q = em.createQuery("from Team team where team.name='"+team.getName()+"'");
        //q.setParameter(1, team.getName());
        try { 
         q.getSingleResult();
       


        
        }catch(Exception e){
            em.persist(team);

            res = true;
        }finally{
            
        }

        return res;
    }

    @SuppressWarnings("unchecked")
	@Override
    @Transactional
    public boolean addProfiler(String json)  {
    	boolean res = false;
    	
    	
    	try {
    		JSONDeserializer<Map<String,String>> des =new JSONDeserializer<>();
    		Map<String,String> properties = des.deserialize(json);
			
			Profiler finded = em.find(Profiler.class, properties.get("codeId"));
			finded.setProperties(properties);

			em.persist(finded);
			res = true;
		} catch (Exception e) {
			
		}
            
        
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
        // TODO Auto-generated method stub
        return false;
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
	public Iterable<String> getAnyRequest(String jpql)
			throws JsonGenerationException, JsonMappingException, IOException {

		boolean flMultiple = hasMultipleArguments(jpql);
		return flMultiple ? runMultipleArgumentsRequest(jpql) : runSingleArgumentRequest(jpql);
	}

	private Iterable<String> runSingleArgumentRequest(String jpql)
			throws JsonGenerationException, JsonMappingException, IOException {

		javax.persistence.Query q = em.createQuery(jpql);
		@SuppressWarnings("unchecked")
		List<Object> objects = q.getResultList();

		return objectsToStringsList(objects);
	}

	private Iterable<String> objectsToStringsList(List<Object> objects)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<String> res = new LinkedList<String>();
		ObjectMapper mapper = new ObjectMapper();
		for (Object obj : objects)
			res.add(mapper.writeValueAsString(obj));
		return res;
	}

	private Iterable<String> runMultipleArgumentsRequest(String jpql)
			throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println(jpql);
		Query q = em.createQuery(jpql);
		@SuppressWarnings("unchecked")
		List<Object[]> objects = q.getResultList();
		return toIterableString(objects);
	}

	private Iterable<String> toIterableString(List<Object[]> objects)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<String> res = new LinkedList<String>();

		for (Object[] args : objects)
			res.add(objectToJson(args));

		return res;
	}

	private boolean hasMultipleArguments(String jpql) {
		String upJpql = jpql.toUpperCase();
		int ind = upJpql.indexOf("FROM");
		if (ind < 0)
			return false;
		return upJpql.substring(0, ind).contains(",");
	}

	private String objectToJson(Object[] objs) throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		if (objs == null || objs.length == 0)
			return null;

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
		List<Club> clubs = new LinkedList<>();
		List<Club> newClubs = new LinkedList<>();
		try {
			Query query = em.createQuery("from Club c where deleted=false");
			clubs = query.getResultList();

			for (Club c : clubs) {
				Club newClub = c;
				newClubs.add(newClub);
			}
		} catch (javax.persistence.NoResultException e) {
			newClubs = null;
		}
		return newClubs;

	}

    public Club getClub(int id) {
    	
    	Club foundedClub = em.find(Club.class, id);
    	Club currentClub = null;
    	
    	if(foundedClub!=null && !foundedClub.isDeleted())
    	{
    		currentClub = foundedClub;
    	}
      return currentClub;

    }

    @SuppressWarnings("unchecked")
    @Override
    //@Transactional
    public Team getTeam(int id) {
    	Team findedTeam = null, currentTeam = null;
    	  	
    		findedTeam = em.find(Team.class, id);
    		if(findedTeam!=null&&!findedTeam.isDeleted())	{
    				currentTeam = teamWithProfilerSet(findedTeam);
    			}
       	 return currentTeam;
    }

    private Team teamWithProfilerSet(Team findedTeam) {
    	 
    	Set<Profiler> prfs = findedTeam.getProfiles();
		Set<Profiler> newPrfsSet = new HashSet<Profiler>();
		Set<ImageBank> photos = findedTeam.getPhotos();
		Set<ImageBank> photosCurr = new HashSet<ImageBank>();
		List<GameTeams> results = findedTeam.getResults();
		List<GameTeams> resultsCurr = new ArrayList<GameTeams>();
		Set<Event> diary = findedTeam.getDiary();
		Set<Event>diaryCurr = new HashSet<Event>();
			
		
			for(Profiler p: prfs){
					Profiler profile = new Profiler(p.getCode());
					newPrfsSet.add(profile);
				}
			for(ImageBank img: photos)
			{
				ImageBank currentImg = new ImageBank(img.getId());
				photosCurr.add(currentImg);
			}
			
			for(Event e: diary ){
				Event eCur = new Event(e.getId());
				diaryCurr.add(eCur);
			}
			for(GameTeams gt: results){
				
				GameTeams gtCur = new GameTeams(gt.getId());
				resultsCurr.add(gtCur);
			}
					
			return new Team(findedTeam.getId(), 
					findedTeam.getName(), 
					findedTeam.getDescription(), 
					photosCurr,results,diaryCurr,
					newPrfsSet);
	}

	@SuppressWarnings("unchecked")
    @Override
    //@Transactional
    public Iterable<Team> getTeams() {
    	List<Team> teams = new LinkedList<>();
    	List<Team> newTeams = new LinkedList<>();
        try {
			Query query = em.createQuery("from Team t where t.deleted=false");
			teams= query.getResultList();
			
			for(Team t: teams){
				Team newTeam = teamWithProfilerSet(t);
				newTeams.add(newTeam);
			}
		} catch (javax.persistence.NoResultException e) {
			newTeams = null;
		}
       return newTeams;
    }

    @Override
    public Profiler getProfile(String SubProfiler, String id) {

        Profiler prf = null;
        
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
            Query query = em.createQuery("select new Profiler(p.id, p.name, p.lastName, p.email, p.position, p.description) from " 
            + SubProfiler + " p where code=" + "'" + id + "'" + "and deleted=false");
            //query.setParameter("code", id);

            try {
                prf = (Profiler) query.getSingleResult();
            } catch (javax.persistence.NoResultException e) {
                // TODO Auto-generated catch block
                e.getMessage();
            }
            System.out.println(prf);
        }
        return prf;
    }

    @Override
    public String signIn(LoginPassword lp) {
        //sportclub.profiler WHERE login='login76952' AND password='password99356';
        String id;
        try {
            Query q = em.createQuery("SELECT p.id FROM Profiler p WHERE p.login='" + lp.getLogin()
                    + "' AND p.password='" + lp.getPassword() + "'");
            //em.createQuery("SELECT p.id "
            //+ "FROM Profiler p WHERE p.login='"+lp.getLogin()+"' AND p.password='"+lp.getPassword()+"'");
            // em.createNativeQuery("SELECT p.id "
            // + "FROM Profiler p WHERE p.login=?1 AND p.password=?2");
            //+ "FROM sportclub.profiler AS p WHERE p.login=?1 AND p.password=?2");
            //q.setParameter(1, lp.getLogin());
            //q.setParameter(2, lp.getPassword());
            id = (String) q.getSingleResult();
            System.out.println(id);
        } catch (javax.persistence.NoResultException e) {
            id = null;
        }
        return id;
    }

    @Override
    @Transactional
    public String registration(LoginPassword lp) {

        String id;
        try {  //createNativeQuery
            Query q = em.createQuery("SELECT p.id FROM Profiler p WHERE p.login='" + lp.getLogin()
                    + "' AND p.password='" + lp.getPassword() + "'");
            //"SELECT p.profilerId "
            //	+ "FROM sportclub.profiler AS p WHERE p.login=?1 AND p.password=?2");
            //q.setParameter(1, lp.getLogin());
            //q.setParameter(2, lp.getPassword());
            id = (String) q.getSingleResult();
            id = null;
            System.out.println(id);
        } catch (javax.persistence.NoResultException e) {
            Profiler profile = selectorSubprofiles(lp.subprofile);

            profile.setLogin(lp.getLogin());
            profile.setPassword(lp.getPassword());
            
            em.persist(profile);

            id = profile.getCode();
            System.out.println(id);
        }
        return id;

    }

    private Profiler selectorSubprofiles(String subprofile) {

        Profiler profile = null;

        switch (subprofile) {
            case "Athlete":
                profile = new Athlete();
                break;
            case "AdminManagerClub":
                profile = new AdminManagerClub();
                break;
            case "AssitPhysicCoach":
                profile = new AssitPhysicCoach();
                break;
            case "AssitTeamCoach":
                profile = new AssitTeamCoach();
                break;
            case "Parent":
                profile = new Parent();
                break;
            case "PhysiologyCoach":
                profile = new PhysiologyCoach();
                break;
            case "Psycholog":
                profile = new Psycholog();
                break;
            case "TeamAdminManager":
                profile = new TeamAdminManager();
                break;
            case "ProffesionalManager":
                profile = new ProffesionalManager();
                break;
            case "TeamCoach":
                profile = new TeamCoach();
                break;
            default: {
                break;
            }

        }
        return profile;
    }

    @Override
    @Transactional
    public Profiler registrationWid(String id, LoginPassword lp) {
        Profiler p = null;
        try {  //createNativeQuery
            Query q = em.createQuery("SELECT p FROM Profiler p WHERE p.id='" + id + "'");

            p = (Profiler) q.getSingleResult();
            p.setLogin(lp.getLogin());
            p.setPassword(lp.getPassword());
            em.persist(p);
        } catch (javax.persistence.NoResultException e) {
            return null;
        }
        return p;
    }

    @Override
    @Transactional
    public String registration(Role[] role, String subProfile) {

        String id;
        try {  //createNativeQuery
//            Query q = em.createQuery("SELECT p FROM Profiler p");
//            Profiler p = (Profiler) q.getSingleResult();
//           
//            Profiler profile = selectorSubprofiles(lp.subprofile);
//
//            profile.setLogin(lp.getLogin());
//            profile.setPassword(lp.getPassword());
//
//            em.persist(profile);
       
        } catch (javax.persistence.NoResultException e) {
       
        }

        return null;
    }

}
