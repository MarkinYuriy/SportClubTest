package random.context;

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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import sportclub.model.Club;
import sportclub.model.EquipmentPool;

import sportclub.model.Event;
import sportclub.model.Exercise;
import sportclub.model.ExerciseSession;

import sportclub.model.Field;
import sportclub.model.Game;
import sportclub.model.Goal;
import sportclub.model.Role;
import sportclub.model.Slot;
import sportclub.model.Team;
import sportclub.model.Training;
import sportclub.model.TrainingPool;
import sportclub.nodeprocessor.RoleGenerator;
import sportclub.profile.Athlete;
import sportclub.profile.Profiler;

public class SportclubRandomDB implements ISportclubRandomDBRepository{
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
	Random random = new Random();
	RandomData rd = new RandomData();
	@PersistenceContext(unitName = "springHibernate", type = PersistenceContextType.EXTENDED)
	EntityManager em;
	
	@Override
	@Transactional
	public boolean addRandomProfile() throws JsonProcessingException {
		List<Profiler> profiles = new LinkedList<Profiler>();
		RandomData rd = new RandomData();
		Club club = new Club();
		club.setName("club1");
		club.setLocation("Be'er Sheva");
		club.setDescription("descr1");
		em.persist(club);
		addRandomRoles(5);
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
			for (int i=0;i<(random.nextInt(s)+1);i++){
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
			Map<Exercise, ExerciseSession> etds = new HashMap<Exercise, ExerciseSession>();
			for (Exercise ex : exs) {
				ExerciseSession es = new ExerciseSession(random.nextInt(20),random.nextInt(20),random.nextInt(20));
				
				etds.put(ex, es);
				
				}
			tp.setExercises(etds);

			Query q1 = em.createQuery("from EquipmentPool e");
			List<EquipmentPool> equipments = q1.getResultList();
			
			for (EquipmentPool equipment : equipments) {
				
				tp.setEquipmentPoolDataValue(equipment, random.nextInt(20));
				
			}
			
			Query q2 = em.createQuery("from Goal e");
			List<Goal> gls = q2.getResultList();
			Set<Goal> glsSet = new HashSet<Goal>();
			glsSet.addAll(gls);
			
			tp.setGoals(glsSet);

			em.persist(tp);

		}
		

		return true;

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
}
