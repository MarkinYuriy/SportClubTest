package random.context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import sportclub.model.Club;
import sportclub.model.EquipmentPool;
import sportclub.model.EquipmentPoolData;
import sportclub.model.Event;
import sportclub.model.Exercise;
import sportclub.model.ExerciseSession;
import sportclub.model.Formation;
import sportclub.model.Court;
import sportclub.model.CourtSchedule;
import sportclub.model.Game;
import sportclub.model.Goal;
import sportclub.model.Role;
import sportclub.model.Slot;
import sportclub.model.StartStaff;
import sportclub.model.Team;
import sportclub.model.Training;
import sportclub.model.TrainingPool;
import sportclub.nodeprocessor.RoleGenerator;
import sportclub.profile.AssitTeamCoach;
import sportclub.profile.Athlete;
import sportclub.profile.Coach;
import sportclub.profile.Profiler;
import sportclub.profile.TeamCoach;

public class SportclubRandomDB implements ISportclubRandomDBRepository {
	String[] subClasses = { "AdminManagerClub", "AssitPhysicCoach", "AssitTeamCoach", "Athlete",
			// "Coach",
			"Parent", "PhysiologyCoach", "ProffesionalManager", "Profiler", "Psycholog", "TeamAdminManager", "TeamCoach"

	};

	private static final int N_ATHLETS = 30;

	private static final int N_SLOTS = 150;

	private static final String FORMATION_NAME = "4-4-2";
	private static final String POSITIONS_LIST="GK LFB LCB RCB RFB LSM LCM RCM RSM LCF RCF";
	Random random = new Random();
	RandomData rd = new RandomData();
	CourtSchedule fsGlobal;
	@PersistenceContext(unitName = "springHibernate", type = PersistenceContextType.EXTENDED)
	EntityManager em;

	@Override
	@Transactional
	public boolean addRandomProfile() throws JsonProcessingException, ParseException {
		List<Profiler> profiles = new LinkedList<Profiler>();
		RandomData rd = new RandomData();
		Club club = new Club();
		club.setName("club1");
		club.setLocation("Be'er Sheva");
		club.setDescription("descr1");
		em.persist(club);

		createSlotTable();
		createFormation();
		addRandomRoles(5);
		addRandomCourt(3);
		createCourtScheduleTable();

		List<Role> roles = new ArrayList<Role>();
		Query q = em.createQuery("select roles from Role roles");
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
			//el.setCode(rd.randomCode());
			el.setEmail(rd.randomEmail());
			el.setLastName(rd.randomLastName());
			el.setLogin(rd.randomLogin());
			el.setName(rd.randomName());
			el.setPassword(rd.randomPassword());
			el.setPosition(rd.randomPosition());
			for (int i = 0; i < (random.nextInt(s) + 1); i++) {
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

	private void createFormation() {
		Formation formation = new Formation();
		formation.setName(FORMATION_NAME);
		formation.setPositions(POSITIONS_LIST);
		em.persist(formation);
		
	}

	private void addRandomCourt(int n) {
		for (int i = 1; i <= n; i++) {
			Court court = new Court();
			court.setName("court" + i);
			court.setType("type" + i);

			em.persist(court);
		}

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
		
		for (int i = 0; i < 5; i++) {

			Profiler assistCoach = new AssitTeamCoach();
			Set<Team> teamAth = new LinkedHashSet<Team>();
			teamAth.add(team);
			assistCoach.setTeams(teamAth);
			aths.add(assistCoach);
		}
		
		Profiler teamCoach = new TeamCoach();
		Set<Team> teamAth = new LinkedHashSet<Team>();
		teamAth.add(team);
		teamCoach.setTeams(teamAth);
		aths.add(teamCoach);
		
		return aths;

	}
	
	private List<Profiler> addRandomCoaches(Team team){
		
		List<Profiler> aths = new LinkedList<Profiler>();
		for (int i = 0; i < 5; i++) {

			Profiler assistCoach = new AssitTeamCoach();
			Set<Team> teamAth = new LinkedHashSet<Team>();
			teamAth.add(team);
			assistCoach.setTeams(teamAth);
			aths.add(assistCoach);
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
		setEventData(event);
		
		Query q = em.createQuery("from Slot");
		List<Slot> slots = q.getResultList();
		Slot slot = slots.get(random.nextInt(slots.size()));
		event.setSlots(slot);

		em.persist(event);

		Event eventq = em.find(Event.class, event.getId());
		recordNewLineToClubDiary(eventq);

		return true;

	}

	private void recordNewLineToClubDiary(Event event) {
		Club club = new Club();
		Query q = em.createQuery("select club from Club club where club.name='club1'");
		club = (Club) q.getSingleResult();
		Set<Event> diary = club.getDiary();
		if (diary == null) {
			diary = new HashSet<Event>();
			club.setDiary(diary);
		}
		diary.add(event);

		//em.flush();
		club.setDiary(diary);
		em.persist(club);
	}

	@Override
	@Transactional
	public boolean addRandomTraining() {
		for (int j = 0; j < 18; j++) {
			Training tr = new Training();
			setEventData(tr);
			putTeamDataToEvent(tr);
			
			Query q = em.createQuery("from TrainingPool tp");
			List<TrainingPool> tps = new ArrayList<TrainingPool>();
			tps = q.getResultList();
			int size = tps.size();
			TrainingPool tp = new TrainingPool();
			tp = tps.get(random.nextInt(size));
			tr.setTrainingPool(tp);
						
			setEventScheduleAndPersist(tr);

			}

		return false;

	}

	private void setEventScheduleAndPersist(Event event) {
		
		Slot slot = new Slot();
		Query q = em.createQuery("select sl from Slot sl");
		@SuppressWarnings("unchecked")
		List<Slot> slots = q.getResultList();

		CourtSchedule fs = null;

		while (fs == null) {
			slot = slots.get(random.nextInt(slots.size()));
			event.setSlots(slot);
			fs = selectFreeScheduleRow(slot);
		}
		em.persist(event);
		recordNewLineToClubDiary(event);
		int courtQuantity = 0;
		int courtPartitionType = fs.getCourtPartitionType();
		
		if(courtPartitionType==0){
				if(event instanceof Game)courtQuantity=1;
				else courtQuantity=1+random.nextInt(3);
		}
		else {courtQuantity = courtPartitionType;}
		
		Class<?> c = fs.getClass();
		
		
		for (int i = 1; i <= courtQuantity; i++) {

			try {
				java.lang.reflect.Field f = c.getDeclaredField("courtPart" + i);
				f.setAccessible(true);
				if (f.get(fs) == null) {
					f.set(fs, event);
					fs.setCourtPartitionType(courtQuantity);
					System.out.println(fs);
					em.persist(fs);
					
					
					
					break;

				}
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private Team putTeamDataToEvent(Event event) {
		Query q = em.createQuery("from Team team");
		List<Team> teams = q.getResultList();
		int size = teams.size();
		Team team = new Team();
		team = teams.get(random.nextInt(size));
		Set<Team> thisTeam = new HashSet<>();
		thisTeam.add(team);
		event.setTeams(thisTeam);
		return team;
		
	}

	private void setEventData(Event event) {
		event.setAddress("address" + random.nextInt(10000));
		event.setDescription(randomDescription());
		event.setGoogleMapLink("http://" + random.nextInt(10000) + ".htm");
		event.setName("eventname" + random.nextInt(10000));
	}

	private CourtSchedule selectFreeScheduleRow(Slot slot) {
		Query q;
		try {
			q = em.createQuery("select fs from CourtSchedule fs join fs.slot sl where sl.startTime= :slotId");
			q.setParameter("slotId", slot.getStartTime());
		} catch (HibernateException e) {
			e.getMessage();
			return null;
		}
		@SuppressWarnings("unchecked")
		List<CourtSchedule> fss = q.getResultList();

		for (CourtSchedule fs : fss) {

			if (isFreeCourtPart(fs))
				return fs;
		}
		return null;
	}

	private boolean isFreeCourtPart(CourtSchedule fs) {

		if (fs.getCourtPartitionType() == 0)
			return true;
		if (fs.getCourtPart1() != null || fs.getCourtPart2() != null || fs.getCourtPart3() != null)
			return true;

		return false;
	}

	private void createCourtScheduleTable() {

		Query q = em.createQuery("select sl from Slot sl");
		@SuppressWarnings("unchecked")
		List<Slot> slots = q.getResultList();
		q = em.createQuery("from Court");
		@SuppressWarnings("unchecked")
		List<Court> courts = q.getResultList();
		for (Court court : courts) {
			for (Slot slot : slots) {
				CourtSchedule fs = new CourtSchedule();
				fs.setCourtPartitionType(0);
				fs.setSlot(slot);
				fs.setCourt(court);
				em.persist(fs);
			}
		}
	}

	private void createSlotTable() throws ParseException {
		String beginCalendar = "01.04.16 06:00";

		DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm");

		Date startTime = df.parse(beginCalendar);

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startTime);

		for (int i = 0; i < N_SLOTS; i++) {
			int minutes = 30;
			calendar.add(Calendar.MINUTE, minutes);
			Date date = calendar.getTime();
			/*
			 * System.out.println("start: "+df.format(startTime));
			 * System.out.println("end: "+df.format(date));
			 */

			startTime = date;
			calendar.setTime(date);
			Slot slot = new Slot();

			slot.setStartTime(startTime);
			slot.setEndTime(date);

			em.persist(slot);
		}

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
			List<ExerciseSession> etds = new ArrayList<ExerciseSession>();
			for (Exercise ex : exs) {
				ExerciseSession es = new ExerciseSession(ex, random.nextInt(20), random.nextInt(20), random.nextInt(20));
				
				em.persist(es);
				etds.add(es);

			}
			tp.setExercises(etds);

			
			//tp.setEquipmentPoolData(epds);
			q = em.createQuery("from Goal e");
			List<Goal> gls = q.getResultList();
			Set<Goal> glsSet = new HashSet<Goal>();
			glsSet.addAll(gls);

			tp.setGoals(glsSet);

			em.persist(tp);
			tp = em.find(TrainingPool.class, tp.getId());
			q = em.createQuery("from EquipmentPool e");
			List<EquipmentPool> equipments = q.getResultList();
			
			//Set<EquipmentPoolData>epds = new HashSet<>();
			
			for (EquipmentPool equipment : equipments) {
				EquipmentPoolData epd = new EquipmentPoolData();
				epd.setTrainingPool(tp);
				epd.setEquipmentPool(equipment);
				epd.setQuantity(random.nextInt(20));
				em.persist(epd);
				
				//epds.add(epd);

			}
			
			
		}

		return true;

	}

	@Override
	@Transactional
	public void addRandomGame() {

		Game game = new Game();
		Team team = putTeamDataToEvent(game);
		setEventData(game);
		
		game.setOpponent("opponent" + random.nextInt(20));
		setStartStaff(game, team);
		
		setEventScheduleAndPersist(game);
}

	private void setStartStaff(Game game, Team team) {
		
		StartStaff sst = new StartStaff();
		//get all participants of the game
		Query q = em.createQuery("select p from Profiler p join p.teams t where t.id=:tId and p.class='Coach'");
		q.setParameter("tId", team.getId());
		
		//add coaches
		List<Coach> coaches = q.getResultList();
		sst.setCoaches(coaches);
				
				
		q = em.createQuery("select p from Team t join t.profiles p where t.id=:tId and p.class='Athlete'");
		q.setParameter("tId", team.getId());
		//add reserve players
		List<Athlete> athletes = q.getResultList();
		List<Athlete> reserve = new ArrayList<Athlete>();
		for(int i=13;i<18;i++){
			
			reserve.add(athletes.get(i));
			
		}
		sst.setReserve(reserve);
		
		//add main staff to composition
		List<Athlete> mainStaff = new ArrayList<Athlete>();
		for(int i=0; i<12;i++){
			mainStaff.add(athletes.get(i));
		}
		
		Formation formation = em.find(Formation.class, FORMATION_NAME);
		sst.setFormation(formation);
		sst.setCompositionKeysByFormation(formation);
		Map<String, Athlete> map = sst.getComposition();
		int i=0;
		
		for(Map.Entry<String, Athlete> pair: map.entrySet()){
			
			pair.setValue(mainStaff.get(i));
			i++;
		}
				
		em.persist(sst);
		game.setStartStaff(sst);
	}
}
