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

		Iterable<Profiler> res=null;
		//System.out.println("Sub "+SubProfiler);
	
		boolean exist=false;
		for(String str:subClasses){
			//System.out.println("str "+ str);
			//System.out.println("subprof "+SubProfiler);
			if(str.equals(SubProfiler)) {
				exist = true;
				//System.out.println(exist);
				break;
			}

		}

		if(exist){
			Query query = em.createQuery("from "+SubProfiler+" p where deleted=false");
			res = query.getResultList();
		}
		return res;

	}



	@Override
	@Transactional
	public boolean addTeam(Team team) {
		boolean res = false;
		
		Query q = em.createQuery("from Team as team where team.name=?1");
		q.setParameter(1, team.getName());
		Team finded = (Team) q.getSingleResult();
		if (finded== null) {

			em.persist(team);

			res = true;
			
		}else{
			res=false;
		}

		return res;
	}

	

	@Override
	@Transactional
	public boolean addProfiler(Profiler profiler, String subProfiler) {
		boolean res = false;
		Profiler finded = em.find(Profiler.class, profiler.getCode());
				
		if (finded== null) {
			em.persist(profiler);
			res = true;
			
		}else{
			res=false;
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

		for (Object[] args : objects)
			res.add(args);

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
		Query query=null;
		if(id.length()>0){
			query = em.createQuery("from Role r where id='"+id+"' and deleted=false");
		}else{
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
		Query query = em.createQuery("select t.name, t.description, profiles from Team t join t.profiles profiles where t.deleted=false");
		
		
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
		boolean exist=false;
		for(String str:subClasses){
			if(str.equals(SubProfiler)) {
				exist = true;
				break;
			}

		}

		if(exist){
			//from Athlete a where id='8805712271700122315'
			Query query = em.createQuery("from "+SubProfiler+" p where code="+"'"+id+"'"+"and deleted=false");
			//query.setParameter("code", id);
			
			try {
				prf =(Profiler) query.getSingleResult();
			} catch (javax.persistence.NoResultException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			System.out.println(prf.toString());
		}
		return prf;
	}

	@Override
	public String signIn(LoginPassword lp) {
		//sportclub.profiler WHERE login='login76952' AND password='password99356';
		String id;
		try {
			Query q = em.createNativeQuery("SELECT p.profilerId "
					+ "FROM sportclub.profiler AS p WHERE p.login=?1 AND p.password=?2");
			q.setParameter(1, lp.getLogin());
			q.setParameter(2, lp.getPassword());
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
		try {
			Query q = em.createNativeQuery("SELECT p.profilerId "
					+ "FROM sportclub.profiler AS p WHERE p.login=?1 AND p.password=?2");
			q.setParameter(1, lp.getLogin());
			q.setParameter(2, lp.getPassword());
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
			
			Club club = em.find(Club.class, team.getClub().getId());
			if(club!=null)
				resTeam.setClub(club);
			
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

	@Override
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
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean removeClub(Club club) {
		Club cl = em.find(Club.class, club.getId());
		
		if (cl!=null) {
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
	public boolean removeTeam(Team team) {
		Team rTeam = em.find(Team.class, team.getId());
		if(rTeam!=null){
			rTeam.setDeleted(true);
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
			return true;
		}
		return false;
	}

	@Override
	public boolean removeProfiler(Profiler profiler, String subProfiler) {
		Profiler pr = selectorSubprofiles(subProfiler);
		pr = em.find(Profiler.class, profiler.getCode());
		if(pr!=null){
			pr.setDeleted(true);
			pr.setClub(null);
			
			Set<Team> teams = pr.getTeams();
			for(Team t:teams){
				Team tm = em.find(Team.class, t.getId());
				Set<Profiler> prs = tm.getProfiles();
				for(Profiler p:prs){
					if(p.getCode().equals(profiler.getCode()))
						prs.remove(p);
				}
				tm.setProfiles(prs);
			}
			pr.setTeams(null);
			return true;
		}
		return false;
	}

}
