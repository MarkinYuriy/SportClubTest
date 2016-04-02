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

	private static final int N_ATHLETS = 30;
	@PersistenceContext(unitName = "springHibernate", type = PersistenceContextType.EXTENDED)
	EntityManager em;
	Random random = new Random();
	RandomData rd = new RandomData();
	String data;

	@Override
	@Transactional
	public boolean addRandomProfile() {
		List<Profiler> profiles = new LinkedList<Profiler>();
		RandomData rd = new RandomData();
		Club club = new Club();
		club.setName("club1");
		club.setLocation("Be'er Sheva");
		club.setDescription("descr1");
		em.persist(club);

		for (int i = 1; i < 3; i++) {
			Field field = new Field();
			field.setName("field" + i);
			field.setType("type" + i);

			em.persist(field);
		}
		List<Role> roles = new ArrayList<Role>();
		Query q = em.createQuery("from Role roles");
		roles = q.getResultList();
		int s = roles.size();

		// creating the table of random teams
		List<Team> teams = new LinkedList<Team>();
		for (int i = 0; i < 13; i++) {

			Team team = new Team();
			team.setName(rd.randomName());
			team.setDescription(rd.randomDescription());

			em.persist(team);
			teams.add(team);
		}

		// adding random athletes in a team
		List<Profiler> aths = new LinkedList<Profiler>();

		for (Team team : teams) {
			aths = addRandomAthletes(team);
			profiles.addAll(aths);
		}

		// persisting all profiles
		for (Profiler el : profiles) {

			el.setDescription(rd.randomDescription());
			el.setCode(rd.randomCode());
			el.setEmail(rd.randomEmail());
			el.setLastName(rd.randomLastName());
			el.setLogin(rd.randomLogin());
			el.setName(rd.randomName());
			el.setPassword(rd.randomPassword());
			el.setPosition(rd.randomPosition());
			for (int i=0;i<(random.nextInt(s)+1)/2;i++){
				Role role = new Role();
				role = roles.get(random.nextInt(s));
				Set<Role> thisRole = new HashSet<Role>();
				thisRole.add(role);
				el.setRoles(thisRole);
			}
			/*
			 * ImageBank image = randomImage(); em.persist(image);
			 */// el.setImage(image);

			em.persist(el);
		}
		randomExercise();
		randomEquipmentPool();
		randomGoals();

		return true;
	}

	private void randomGoals() {
		for (int i = 0; i < 5; i++) {

			Goal gl = new Goal();
			gl.setName("goal" + rd.randomImageName());
			gl.setDescription(randomDescription());
			em.persist(gl);

		}
	}

	private void randomEquipmentPool() {
		for (int i = 0; i < 10; i++) {
			EquipmentPool eqp = new EquipmentPool();
			eqp.setName("equipment" + rd.randomImageName());
			// eqp.setQuantity(random.nextInt(30));
			eqp.setDescription(randomDescription());
			em.persist(eqp);
		}

	}

	private void randomExercise() {
		for (int i = 0; i < 5; i++) {
			Exercise exercise = new Exercise();

			exercise.setName("exercise" + rd.randomName());

			em.persist(exercise);

		}

	}

	private List<Profiler> addRandomAthletes(Team team) {
		List<Profiler> aths = new LinkedList<Profiler>();
		for (int i = 0; i < N_ATHLETS; i++) {

			Profiler ath = new Athlete(1 + random.nextInt(30), randomType(), randomBirthday());
			Set<Team> teamAth = new LinkedHashSet<Team>();
			teamAth.add(team);
			ath.setTeams(teamAth);
			aths.add(ath);
		}

		return aths;

	}

	private Date randomBirthday() {
		// TODO Auto-generated method stub
		return rd.randomDate();
	}

	private String randomType() {
		return "type" + random.nextInt(20);
	}
	/*
	 * private ImageBank randomImage() { ImageBank img = new
	 * ImageBank(randomImageName(), randomLinkToFile(), randomDescription());
	 * 
	 * return img; }
	 */

	private String randomDescription() {
		// TODO Auto-generated method stub
		return "description" + random.nextInt(10000);
	}

	/*
	 * private String randomLinkToFile() { // TODO Auto-generated method stub
	 * return "http://site.org/image" + random.nextInt(10000) + ".jpg"; }
	 * 
	 * private String randomImageName() { // TODO Auto-generated method stub
	 * return "image" + random.nextInt(10000); }
	 */
	@Override
	@Transactional
	public boolean addRandomEvent() {

		Event event = new Event();
		event.setId(random.nextInt(1000000));
		event.setAddress("address" + random.nextInt(10000));
		event.setDescription(randomDescription());
		event.setGoogleMapLink("http://" + random.nextInt(10000) + ".htm");
		event.setName("eventname" + random.nextInt(10000));

		Date startTime = rd.randomDate();
		Date endTime = new Date(startTime.getTime() + 120 * 60 * 1000);

		Slot slot = new Slot();

		slot.setStartTime(startTime);
		slot.setEndTime(endTime);
		em.persist(slot);
		event.setSlots(slot);

		em.persist(event);

		recordNewLineToClubDiary(event);

		return true;

	}

	private void recordNewLineToClubDiary(Event event){
		Club club = new Club();
		Query q = em.createQuery("from Club club where club.name='club1'");
		club = (Club) q.getSingleResult();
		List<Event> diary = club.getDiary();
		if(diary==null){
			diary=new ArrayList<Event>();
			club.setDiary(diary);
		}
		diary.add(event);

		em.flush();
		club.setDiary(diary);
	}

	@Override
	@Transactional
	public boolean addRandomTraining() {

		for (int i = 0; i < 20; i++) {
			Training tr = new Training();

			Query q = em.createQuery("from Team team");
			List<Team> teams = q.getResultList();
			int size = teams.size();
			Team team = new Team();
			team = teams.get(random.nextInt(size));
			List<Team> thisTeam = new ArrayList<>();
			thisTeam.add(team);

			tr.setTeams(thisTeam);

			tr.setId(random.nextInt(10000000));
			tr.setAddress("address" + random.nextInt(10000));
			tr.setDescription(randomDescription());
			tr.setGoogleMapLink("http://" + random.nextInt(10000) + ".htm");
			tr.setName("eventname" + random.nextInt(10000));

			Date startTime = rd.randomDate();
			Date endTime = new Date(startTime.getTime() + 120 * 60 * 1000);

			Slot slot = new Slot();

			slot.setStartTime(startTime);
			slot.setEndTime(endTime);
			em.persist(slot);
			tr.setSlots(slot);

			q = em.createQuery("from TrainingPool tp");
			List<TrainingPool> tps = new ArrayList<TrainingPool>();
			tps = q.getResultList();
			size = tps.size();
			TrainingPool tp = new TrainingPool();
			tp = tps.get(random.nextInt(size));
			tr.setTrainingPool(tp);

			q = em.createQuery("from Field f");
			List<Field> fields = q.getResultList();
			size = fields.size();
			Field field = new Field();
			field = fields.get(random.nextInt(size));

			Map<Integer, Integer> partitions = new HashMap<Integer, Integer>();
			partitions = field.getPartitions();
			/* \\select 1-2-3 parts in the field and create partitions */
			if (partitions.size() == 0) {

				field.setThreePartitionsField();// select 3-parted field
				field.setPartionsValue(1, tr.getId());// select needed part
				em.persist(field);

			} else if (partitions.containsValue(null)) {
				for (Map.Entry<Integer, Integer> pair : partitions.entrySet()) {

					if (pair.getValue() != null) {
						field.setPartionsValue(pair.getKey(), tr.getId());
						em.persist(field);
						break;
					}
				}

			}

			tr.setFields(field);

			em.persist(tr);
			recordNewLineToClubDiary(tr);
		}
		return false;

	}

	@Override
	@Transactional
	public boolean addRandomRoles(int n) throws JsonProcessingException {
		RoleGenerator.JSONRoleGenerator(n, em);

		return false;
	}

	@Override
	@Transactional
	public boolean addRandomTrainingPool() {

		for (int j = 0; j < 10; j++) {

			TrainingPool tp = new TrainingPool();
			tp.setName(rd.randomName());
			tp.setLevel(random.nextInt(6));
			tp.setType(randomType());
			tp.setDescription(randomDescription());

			Query q = em.createQuery("from Exercise e");
			List<Exercise> exs = q.getResultList();
			List<ExerciseTrainingData> etds = new ArrayList<ExerciseTrainingData>();
			for (Exercise ex : exs) {
				ExerciseTrainingData etd = new ExerciseTrainingData();
				etd.setExercise(ex);
				etd.setNumberOfRepeats(random.nextInt(20));
				etd.setDuration(random.nextInt(30));
				etds.add(etd);
				em.persist(etd);

			}
			tp.setExercisesData(etds);

			Query q1 = em.createQuery("from EquipmentPool e");
			List<EquipmentPool> equipments = q1.getResultList();
			List<EquipmentPoolData> epds = new ArrayList<EquipmentPoolData>();

			for (EquipmentPool equipment : equipments) {
				EquipmentPoolData epd = new EquipmentPoolData();
				epd.setEquipmentPool(equipment);

				// int quantity = equipment.getQuantity();

				epd.setQuantityForTraining(random.nextInt(20));
				epds.add(epd);
				em.persist(epd);

			}
			tp.setEquipmentPoolData(epds);
			Query q2 = em.createQuery("from Goal e");
			List<Goal> gls = q2.getResultList();

			tp.setGoals(gls);

			em.persist(tp);

		}


		return true;

	}

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
			Query query = em.createQuery("from "+SubProfiler+" p");
			res = query.getResultList();
		}
		return res;

	}



	@Override
	@Transactional
	public boolean addTeam(Team team) {
		boolean res = false;
		Team finded = em.find(Team.class, team.getId());
		if (finded== null) {

			em.persist(team);

			res = true;
			//System.out.println("true");
		}else{
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
		if (finded== null) {

			em.persist(profiler);

			res = true;
			//System.out.println("true");
		}else{
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
		boolean exist=false;
		for(String str:subClasses){
			if(str.equals(subProfiler)) {
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
	public boolean addField(Field field) {
		boolean res = false;

		if (em.find(Field.class, field.getId()) == null) {

			em.persist(field);

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

	@Override
	@Transactional
	public void addRandomGame() {

		Game game = new Game();
		Query q = em.createQuery("from Team");

		List<Team> teams = q.getResultList();
		List<Team> thisTeam = new ArrayList<>();
		int size = teams.size();
		Team team = teams.get(random.nextInt(size));
		thisTeam.add(team);
		game.setTeams(thisTeam);
		game.setId(random.nextInt(100000));
		game.setName(rd.randomName());
		game.setType(randomType());
		game.setDescription(randomDescription());

		Date startTime = rd.randomDate();
		Date endTime = new Date(startTime.getTime() + 120 * 60 * 1000);

		Slot slot = new Slot();

		slot.setStartTime(startTime);
		slot.setEndTime(endTime);
		em.persist(slot);
		game.setSlots(slot);

		/*
		 * q = em.createQuery("from Field f"); List<Field> fields =
		 * q.getResultList(); size = fields.size(); Field field = new Field();
		 * field = fields.get(random.nextInt(size));
		 * 
		 * Map<Integer, Integer> partitions = new HashMap<Integer, Integer>();
		 * partitions = field.getPartitions();
		 * 
		 * 
		 * game.setFields(field);
		 */
		game.setOpponent("opponent" + random.nextInt(20));

		em.persist(game);

		recordNewLineToClubDiary(game);

	}

	/**
	 * Get any request from database
	 */
	@Override
	//@Transactional
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
		Query query=null;
		if(id.length()>0){
			query = em.createQuery("from Role r where id='"+id+"'");
		}else{
			query = em.createQuery("from Role r");
		}
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	//@Transactional

	public Iterable<Club> getClubs() {
		Query query = em.createQuery("from Club c");
		
		
		return query.getResultList();

	}
	
	public Iterable<Club> getClubs(long id) {
		Query query = em.createQuery("from Club c where id='"+id+"'");
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	//@Transactional
	public Iterable<Team> getTeams(int id) {
		Query query = em.createQuery("from Team t where id='"+id+"'");
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	//@Transactional
	public Iterable<Team> getTeams() {
		Query query = em.createQuery("from Team t");
		return query.getResultList();

	}

	@Override
	public Iterable<Profiler> getProfiles(String SubProfiler, long id) {

		Iterable<Profiler> res=null;
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
			Query query = em.createQuery("from "+SubProfiler+" p where id='"+id+"'");
			res = query.getResultList();
		}
		return res;
	}

}
