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
import sportclub.interfaces.ISportclubRepository;
import sportclub.model.*;
import sportclub.profile.*;

/**
 * @author paul 04_18_2016
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
    @Transactional
    public boolean addClub(Club club) {
        try{
        
       Query q = em.createQuery("SELECT c.name "
            + "FROM Club c WHERE c.name=:name");
        q.setParameter("name", club.getName());
       String name = (String) q.getSingleResult();   
        return false;
        } catch (javax.persistence.NoResultException e) {
            em.persist(club);
            return true;
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

	private Profiler selectorSubprofiles(String subprofile) {
		
		Profiler profile =null;
		
		switch(subprofile){
		case "Athlete":  profile = new Athlete(); break;
		case "AdminManagerClub":  profile = new AdminManagerClub(); break;
		case "AssitPhysicCoach": profile = new AssitPhysicCoach(); break;
		case "AssitTeamCoach": profile = new AssitTeamCoach(); break;
		case "Parent":  profile = new Parent(); break;
		case "PhysiologyCoach":  profile = new PhysiologyCoach(); break;
		case "Psycholog": profile = new Psycholog(); break;
		case "TeamAdminManager":  profile = new TeamAdminManager(); break;
		case "ProffesionalManager": profile = new ProffesionalManager(); break;
		case "TeamCoach": profile = new TeamCoach(); break; 
					
		}
		return profile;
	}
	
	@Override
	@Transactional
	public boolean updateTeam(Team team) {
		
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
						return false;
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
					Profiler pr = em.find(Profiler.class, p.getCode());
					if (pr == null)
						return false;
					profilers.add(pr);
				}
				resTeam.setProfiles(profilers);
			}
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updateClub(Club club) {
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
						return false;
					images.add(im);
				}
				cl.setPhotos(images);
			}
			return true;
		}
		return false;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean removeClub(Club club) {
		Club cl = em.find(Club.class, club.getId());
		
		if (cl!=null) {
			if(cl.isDeleted()) return false;
			cl.setDeleted(true);
			Query q = em.createQuery("Select t.id from Team t join t.club c where c.id=?1");
			q.setParameter(1, cl.getId());
			Iterable<Integer> teams = q.getResultList();
			System.out.println(teams.toString());
			for (Integer t : teams){
				Team tm = em.find(Team.class, t);
				tm.setClub(null);
			}
			q = em.createQuery("Select p.code from Profiler p join p.club c where c.id=?1");
			q.setParameter(1, cl.getId());
			List<String> profilers = q.getResultList();
			
			for (String p : profilers){
				Profiler pr = em.find(Profiler.class, p);
				pr.setClub(null);
			}
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean removeTeam(Team team) {
		Team rTeam = em.find(Team.class, team.getId());
		if(rTeam!=null){
			if(rTeam.isDeleted()) {return false;}
			
			rTeam.setClub(null);
			
			Set<Profiler> prs = rTeam.getProfiles();
			for(Profiler p:prs){
				Profiler pr = em.find(Profiler.class, p.getCode());
				Set<Team> teamTmp = pr.getTeams();
				for(Team t:teamTmp){
					if(t.getId()==team.getId())
						prs.remove(t);
				}
				pr.setTeams(teamTmp);
			}
			rTeam.setProfiles(null);
			rTeam.setDeleted(true);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	
	public boolean removeProfiler(String code) {
		
		Profiler pr = em.find(Profiler.class, code);
		if(pr!=null){
			
			if(pr.isDeleted()){return false;}
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
			return true;
		}
		return false;
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
        
        boolean exist = false;
        for (String str : subClasses) {
            if (str.equals(SubProfiler)) {
            	
                exist = true;
                break;
            }
        }

        if (exist) {
           
Query query = em.createQuery("select p from Profiler as p where p.code=:code and p.class=:type and p.deleted = false");
query.setParameter("code", id);
query.setParameter("type", SubProfiler);

            try {
                prf = (Profiler) query.getSingleResult();
            } catch (javax.persistence.NoResultException e) {
                // TODO Auto-generated catch block
               System.out.println(e.getMessage());
            }
            System.out.println(prf);
        }
        return prf;
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
        System.err.println("in the registration");
        String id;
        try {  
            Query q = em.createQuery("SELECT p.id FROM Profiler p WHERE p.login=:login AND p.password=:password");
            
            q.setParameter("login", lp.getLogin());
            q.setParameter("password", lp.getPassword());
            id = (String) q.getSingleResult();
            id = null;
            System.out.println("find!!");
        } catch (javax.persistence.NoResultException e) {
            System.out.println("catch NoResultException");
            System.err.println("lp.getSubprofile()"+lp.getSubprofile());
        	Profiler profile = (Profiler) Class.forName("sportclub.profile."+lp.getSubprofile()).newInstance();
            System.out.println("after for name");    
            profile.setLogin(lp.getLogin());
            profile.setPassword(lp.getPassword());
            
            em.persist(profile);

            id = profile.getCode();
            System.out.println(id);
        }
        return id;

    }

    /*private Profiler selectorSubprofiles(String subprofile) {

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
    }*/

//    @Override
//    @Transactional
//    public Profiler registrationWid(String id, LoginPassword lp) {
//        Profiler p = null;
//        try {  //createNativeQuery
//            Query q = em.createQuery("SELECT p FROM Profiler p WHERE p.id='" + id + "'");
//
//            p = (Profiler) q.getSingleResult();
//            p.setLogin(lp.getLogin());
//            p.setPassword(lp.getPassword());
//            em.persist(p);
//        } catch (javax.persistence.NoResultException e) {
//            return null;
//        }
//        return p;
//    }
//
////    @Override
//    @Transactional
//    public String registration(Role[] role, String subProfile) {
//
//        String id;
//        try {  //createNativeQuery
////            Query q = em.createQuery("SELECT p FROM Profiler p");
////            Profiler p = (Profiler) q.getSingleResult();
////           
////            Profiler profile = selectorSubprofiles(lp.subprofile);
////
////            profile.setLogin(lp.getLogin());
////            profile.setPassword(lp.getPassword());
////
////            em.persist(profile);
//       
//        } catch (javax.persistence.NoResultException e) {
//       
//        }
//
//        return null;
//    }
    /*@Override
	@Transactional
	public boolean updateAthlete(Athlete profiler, String subProfiler) {
		Profiler pr = selectorSubprofiles(subProfiler);
		pr = em.find(Profiler.class, profiler.getCode());
		if(pr!=null){
			if(profiler.getLogin()!=null)
			pr.setLogin(profiler.getLogin());
			if(profiler.getPassword()!=null)
			pr.setPassword(profiler.getPassword());
			if(profiler.getName()!=null)
			pr.setName(profiler.getName());
			if(profiler.getLastName()!=null)
			pr.setLastName(profiler.getLastName());
			if(profiler.getEmail()!=null)
			pr.setEmail(profiler.getEmail());
			if(profiler.getPosition()!=null)
			pr.setPosition(profiler.getPosition());
			if(profiler.getDescription()!=null)
			pr.setDescription(profiler.getDescription());
			
			if (profiler.getPhotos()!=null) {
				Set<ImageBank> images = new HashSet<ImageBank>();
				for (ImageBank ib : profiler.getPhotos()) {
					ImageBank im = em.find(ImageBank.class, ib.getId());
					if (im == null)
						return false;
					images.add(im);
				}
				pr.setPhotos(images);
			}
			
			Club club = em.find(Club.class, profiler.getClub().getId());
			if(club!=null)
				pr.setClub(club);
			
			if(profiler.getTeams()!=null){
				Set<Team> teams = new HashSet<Team>();
				for(Team t:profiler.getTeams()){
					Team tm = em.find(Team.class, t.getId());
					if(tm == null)
						return false;
					teams.add(tm);
				}
				pr.setTeams(teams);
			}
			
			if(profiler.getRoles()!=null){
				Set<Role> roles = new HashSet<Role>();
				for(Role r:profiler.getRoles()){
					Role rl = em.find(Role.class, r.getIdCode());
					if(rl == null)
						return false;
					roles.add(rl);
				}
				pr.setRoles(roles);
			}
			
			return true;
		}
		return false;
	}*/
}
