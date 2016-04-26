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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import flexjson.JSONDeserializer;
import java.util.logging.Level;
import java.util.logging.Logger;

import sportclub.data.ProfileData;
import sportclub.data.TeamData;
import sportclub.interfaces.ISportclubRepository;
import sportclub.model.*;
import sportclub.profile.*;

/**
 * @author paul 04_26_2016
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

     @SuppressWarnings("unchecked")
    @Override
    //@Transactional
    public Iterable<Profiler> getProfiles(String subProfiler) {
    	 Iterable<Profiler> it = null;
        try {
			    Query query = em.createQuery("select p from Profiler p where deleted=false and p.class=:subprofiler");
			    query.setParameter("subprofiler", subProfiler);
			    it = query.getResultList();
			   
			    return !it.iterator().hasNext()?null: it;
		} catch (NoResultException e) {System.out.println("point2");
			return null;
		}
      }

    @Override
    @Transactional
    public TeamData addTeam(Team team) {
        

        Query q = em.createQuery("select team from Team team where team.name=:teamName");
        q.setParameter("teamName", team.getName());
        try { 
         q.getSingleResult();
         }catch(Exception e){
            em.persist(team);
            TeamData td = new TeamData(team.getId());
            return td;
        }
      return null;
    }

   
	@Override
    @Transactional
    public ProfileData updateProfiler(Map<String,String> properties)  {
		ProfileData pd;
		String idOut = properties.get("id");
			Profiler finded = em.find(Profiler.class, idOut);
			if(finded!=null){
			finded.setProperties(properties);
			 pd = new ProfileData(finded.getId());
			return pd;
			}
            
        return null;
    }

    @Override
    @Transactional
    public boolean addCourt(Court court) {
        boolean res = false;

        if (em.find(Court.class, court.getId()) == null) {
            em.persist(court);
            res = true;
            }
        return res;
    }

    @Override
    @Transactional
    public boolean addAthlete(Athlete ath, Team team) {
        boolean res = false;
        Set<Team> teams = new LinkedHashSet<Team>();
        if (em.find(Athlete.class, ath.getId()) == null) {
            teams.add(team);
           // ath.setTeams(teams);
            em.persist(ath);
            res = true;
            System.out.println("true");
        }

        return res;
    }

    @Override
    public boolean addExercise(Exercise exercise) {
        boolean res = false;

        if (em.find(Exercise.class, exercise.getId()) == null) {
            em.persist(exercise);
            res = true;
            
        }

        return res;
    }

    @Override
    public boolean addEquipment(EquipmentPool equipment) {
        boolean res = false;

        if (em.find(EquipmentPool.class, equipment.getId()) == null) {
            em.persist(equipment);
            res = true;
           }
        return res;
    }

    @Override
    public boolean addTrainingPoolElement(TrainingPool tr) {
        
        return false;
    }

    @Override
    public boolean addGoal(Goal goal) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    @Transactional
    public Club addClub(Club club) {
        try{
        
       Query q = em.createQuery("SELECT c.name "
            + "FROM Club c WHERE c.name=:name");
        q.setParameter("name", club.getName());
       @SuppressWarnings("unused")
	String name = (String) q.getSingleResult();   
        return null;
        } catch (javax.persistence.NoResultException e) {
            em.persist(club);
            
            Club res = new Club(club.getId());
            return res;
       }
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
		
		try {
			Query query = em.createQuery("select c from Club c where deleted=false");
			clubs = query.getResultList();

			
		} catch (javax.persistence.NoResultException e) {
			return null;
		}
		return clubs;

	}

	@Override
	@Transactional
	public Team updateTeam(Team team) {
		
		Team resTeam = em.find(Team.class, team.getId());
		if(resTeam!=null){
			if(team.getName()!=null)
			resTeam.setName(team.getName());
			if(team.getDescription()!=null)
			resTeam.setDescription(team.getDescription());
			
			Set<ImageBank> images = new HashSet<ImageBank>();
			if (team.getPhotos()!=null) {
				for (ImageBank ib : team.getPhotos()) {
					ImageBank im = em.find(ImageBank.class, ib.getId());
					if (im == null)
						return null;
					images.add(im);
				}
				resTeam.setPhotos(images);
			}
			if(team.getClub()!=null){
			
			Club club = em.find(Club.class, team.getClub().getId());
			if(club!=null)
				resTeam.setClub(club);
			}
			if (team.getProfiles()!=null) {
				Set<Profiler> profilers = new HashSet<Profiler>();
				for (Profiler p : team.getProfiles()) {
					Profiler pr = em.find(Profiler.class, p.getId());
					if (pr == null)
						return null;
					profilers.add(pr);
				}
				resTeam.setProfiles(profilers);
			}
			
			Team res = new Team(resTeam.getId());
			return res;
		}
		return null;
	}

	@Override
	@Transactional
	public Club updateClub(Club club) {
		Club cl = em.find(Club.class, club.getId());
		if(cl!=null){
			if(club.getName()!=null)
			cl.setName(club.getName());
			if(club.getLocation()!=null)
			cl.setLocation(club.getLocation());
			if(club.getDescription()!=null)
			cl.setDescription(club.getDescription());
			
			if (club.getPhotos()!=null) {
				List<ImageBank> images = new LinkedList<ImageBank>();
				for (ImageBank ib : club.getPhotos()) {
					ImageBank im = em.find(ImageBank.class, ib.getId());
					if (im == null)
						return null;
					images.add(im);
				}
				cl.setPhotos(images);
			}
			Club res = new Club(cl.getId());
			return res;
		}
		return null;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Club removeClub(Club club) {
		Club cl = em.find(Club.class, club.getId());
		
		if (cl!=null) {
			if(cl.isDeleted()) return null;
			cl.setDeleted(true);
			Query q = em.createQuery("Select t.id from Team t join t.club c where c.id=?1");
			q.setParameter(1, cl.getId());
			Iterable<Integer> teams = q.getResultList();
			System.out.println(teams.toString());
			for (Integer t : teams){
				Team tm = em.find(Team.class, t);
				tm.setClub(null);
			}
			q = em.createQuery("Select p.id from Profiler p join p.club c where c.id=?1");
			q.setParameter(1, cl.getId());
			List<String> profilers = q.getResultList();
			
			for (String p : profilers){
				Profiler pr = em.find(Profiler.class, p);
				pr.setClub(null);
			}
			Club res = new Club(cl.getId());
			return res;
		}
		return null;
	}

	@Override
	@Transactional
	public Team removeTeam(Team team) {
		Team rTeam = em.find(Team.class, team.getId());
		if(rTeam!=null){
			if(rTeam.isDeleted()) {return null;}
			
			rTeam.setClub(null);
			
			Set<Profiler> prs = rTeam.getProfiles();
			for(Profiler p:prs){
				Profiler pr = em.find(Profiler.class, p.getId());
				Set<Team> teamTmp = pr.getTeams();
				for(Team t:teamTmp){
					if(t.getId()==team.getId())
						prs.remove(t);
				}
				pr.setTeams(teamTmp);
			}
			rTeam.setProfiles(null);
			rTeam.setDeleted(true);
			Team res = new Team(rTeam.getId());
			return res;
		}
		return null;
	}

	@Override
	@Transactional
	
	public ProfileData removeProfiler(String id) {
		
		Profiler pr = em.find(Profiler.class, id);
		if(pr!=null){
			
			if(pr.isDeleted()){return null;}
			pr.setDeleted(true);
			pr.setClub(null);
			
			/*Set<Team> teams = pr.getTeams();
			for(Team t:teams){
				Team tm = em.find(Team.class, t.getId());
				Set<Profiler> prs = tm.getProfiles();
				for(Profiler p:prs){
					if(p.getCode().equals(pr.getCode()))
						prs.remove(p);
				}
				tm.setProfiles(prs);
			}
			pr.setTeams(null);*/
			ProfileData pd = new ProfileData(id);
			return pd;
		}
		return null;
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

    
    @Override
    //@Transactional
    public Team getTeam(int id) {
    	Team findedTeam = null, currentTeam = null;
    	  	
    		findedTeam = em.find(Team.class, id);
    		if(findedTeam!=null&&!findedTeam.isDeleted())	{
    				currentTeam = findedTeam;
    			}
       	 return currentTeam;
    }

    

	@SuppressWarnings("unchecked")
    @Override
    //@Transactional
    public Iterable<Team> getTeams() {
    	List<Team> teams = new LinkedList<>();
    	
        try {
			Query query = em.createQuery("select t from Team t where t.deleted=false");
			teams= query.getResultList();
			
			
		} catch (javax.persistence.NoResultException e) {
			teams = null;
		}
       return teams;
    }

    @Override
    public Profiler getProfile(String subProfiler, String id) {

        Profiler prf = null;
         
        Query query = em.createQuery("select p from Profiler as p where p.id=:id and p.class=:type and p.deleted = false");
        query.setParameter("id", id);
        query.setParameter("type", subProfiler);

            try {
                prf = (Profiler) query.getSingleResult();
                return prf;
            } catch (javax.persistence.NoResultException e) {
                // TODO Auto-generated catch block
               return null;
            }
           }

    @Override
    public String signIn(LoginPassword lp) {
        
        String id;
        try {
            Query q = em.createQuery("SELECT p.id "
            + "FROM Profiler p WHERE p.login=:login AND p.password=:password");
            
            q.setParameter("login", lp.getLogin());
            q.setParameter("password", lp.getPassword());
            id = (String) q.getSingleResult();
            System.out.println(id);
        } catch (javax.persistence.NoResultException e) {
            id = null;
        }
        return id;
    }

    @Override
    @Transactional
    public String registration(LoginPassword lp) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        
        String id;
        try {  
            Query q = em.createQuery("SELECT p.id FROM Profiler p WHERE p.login=:login AND p.password=:password");
            
            q.setParameter("login", lp.getLogin());
            q.setParameter("password", lp.getPassword());
            q.getSingleResult();
            id=null;
            
        } catch (javax.persistence.NoResultException e) {
            
        	Profiler profile = (Profiler) Class.forName("sportclub.profile."+lp.getSubprofile()).newInstance();
            
            profile.setLogin(lp.getLogin());
            profile.setPassword(lp.getPassword());
            
            em.persist(profile);

            id = profile.getId();
            System.out.println(id);
        }
        return id;

    }

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Team> getTeamByClub(int clubId) {
		Query q = em.createQuery("Select t From Team t join t.club c Where t.deleted=false and c.id=?1");
		q.setParameter(1, clubId);
		List<Team> teams = new LinkedList<Team>();
		teams =	q.getResultList();
		for(Team t:teams){
			t.setDiary(null);
			t.setResults(null);
		Set<Profiler> prof = t.getProfiles();
		Set<Profiler> resProf = new HashSet<Profiler>();
		for(Profiler p:prof){
			Profiler pr = new Profiler(p.getId());
//			pr.setFederationPlayer(p.isFederationPlayer());
//			pr.setDeleted(p.isDeleted());
			resProf.add(pr);
		}
		t.setProfiles(resProf);
		}
		return teams;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Profiler> getProfilerByTeam(int teamId) {
		Query q = em.createQuery("Select p From Profiler p join p.teams t Where p.deleted=false and t.deleted=false and t.id=?1");
		q.setParameter(1, teamId);
		List<Profiler> profiles = new LinkedList<Profiler>();
		profiles = q.getResultList();
		for(Profiler p:profiles){
			Set<Role> resRol = new HashSet<Role>(), roles = new HashSet<Role>();
			Set<Team> resTeam = new HashSet<Team>(), teams = new HashSet<Team>();
			roles = p.getRoles();
			teams = p.getTeams();
			if(roles.size()>0){
			for(Role r:roles){
				Role tmpR = new Role();
				tmpR.setIdCode(r.getIdCode());
				resRol.add(tmpR);
			}
			p.setRoles(resRol);
			}
			if(teams.size()>0){
			for(Team t:teams){
				Team tmpT = new Team(t.getId());
				resTeam.add(tmpT);
			}
			p.setTeams(resTeam);
			}
			
			
		}
		return profiles;
	}

   }
