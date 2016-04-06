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

import sportclub.model.Event;
import sportclub.model.Exercise;
import sportclub.model.ExerciseSession;

import sportclub.model.Court;
import sportclub.model.CourtSchedule;
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

public class SportclubRandomDB implements ISportclubRandomDBRepository {
	String[] subClasses = { "AdminManagerClub", "AssitPhysicCoach", "AssitTeamCoach", "Athlete",
			// "Coach",
			"Parent", "PhysiologyCoach", "ProffesionalManager", "Profiler", "Psycholog", "TeamAdminManager", "TeamCoach"

	};

	private static final int N_ATHLETS = 30;

	private static final int N_SLOTS = 150;
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

		addRandomRoles(5);
		addRandomField(3);
		createFieldScheduleTable();

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
			el.setCode(rd.randomCode());
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

	private void addRandomField(int n) {
		for (int i = 1; i <= n; i++) {
			Court court = new Court();
			court.setName("field" + i);
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
		//event.setId(random.nextInt(1000000));
		event.setAddress("address" + random.nextInt(10000));
		event.setDescription(randomDescription());
		event.setGoogleMapLink("http://" + random.nextInt(10000) + ".htm");
		event.setName("eventname" + random.nextInt(10000));

		Query q = em.createQuery("from Slot");

		List<Slot> slots = q.getResultList();
		Slot slot = slots.get(random.nextInt(slots.size()));

		event.setSlots(slot);

		em.persist(event);
		
		Event eventq = em.find (Event.class,event.getId());
		recordNewLineToClubDiary(eventq);

		return true;

	}

	private void recordNewLineToClubDiary(Event event) {
		Club club = new Club();
		Query q = em.createQuery("select club from Club club where club.name='club1'");
		club = (Club) q.getSingleResult();
		List<Event> diary = club.getDiary();
		if (diary == null) {
			diary = new ArrayList<Event>();
			club.setDiary(diary);
		}
		diary.add(event);

		em.flush();
		club.setDiary(diary);
	}

	@Override
	@Transactional
	public boolean addRandomTraining() {
		//randomSchedule();
		for (int j = 0; j < 18; j++) {
			Training tr = new Training();

			Query q = em.createQuery("from Team team");
			List<Team> teams = q.getResultList();
			int size = teams.size();
			Team team = new Team();
			team = teams.get(random.nextInt(size));
			List<Team> thisTeam = new ArrayList<>();
			thisTeam.add(team);
			tr.setTeams(thisTeam);
			tr.setAddress("address" + random.nextInt(10000));
			tr.setDescription(randomDescription());
			tr.setGoogleMapLink("http://" + random.nextInt(10000) + ".htm");
			tr.setName("eventname" + random.nextInt(10000));

			q = em.createQuery("from TrainingPool tp");
			List<TrainingPool> tps = new ArrayList<TrainingPool>();
			tps = q.getResultList();
			size = tps.size();
			TrainingPool tp = new TrainingPool();
			tp = tps.get(random.nextInt(size));
			tr.setTrainingPool(tp);
			
			Slot slot = new Slot();
			q = em.createQuery("select sl from Slot sl");
			@SuppressWarnings("unchecked")
			List<Slot> slots = q.getResultList();
			
			CourtSchedule fs = null;
			
			while(fs==null){
			slot = slots.get(random.nextInt(slots.size()));
			tr.setSlots(slot);
			fs = selectFreeScheduleRow(slot);
			}
			
			em.persist(tr);
			recordNewLineToClubDiary(tr);
			
			Class c = fs.getClass();
			for(int i=1; i<4; i++){
			
			try {
				java.lang.reflect.Field f = c.getDeclaredField("fieldPart"+i);
				f.setAccessible(true);
				if(f.get(fs)==null) {
					f.set(fs, tr);
					fs.setFieldPartitionType(3);
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
			
		return false;

	}
private CourtSchedule selectFreeScheduleRow(Slot slot) {
	Query q;
	try {
		q = em.createQuery("select fs from FieldSchedule fs join fs.slot sl where sl.startTime= :slotId");
		q.setParameter("slotId", slot.getStartTime());
	} catch (HibernateException e) {
		e.getMessage();
		return null;
	}
	@SuppressWarnings("unchecked")
	List<CourtSchedule> fss = q.getResultList();
	
	for (CourtSchedule fs: fss){
		
	if(isFreeFieldPart(fs)) 
		return fs;
	}
		return null;
	}

private boolean isFreeFieldPart(CourtSchedule fs) {
		
			if(fs.getFieldPartitionType()==0)
			return true;
			if(fs.getFieldPart1()!=null||fs.getFieldPart2()!=null||fs.getFieldPart3()!=null)return true;
					
		return false;
	}

private void createFieldScheduleTable(){
	
	
	Query q = em.createQuery("from Slot");
	@SuppressWarnings("unchecked")
	List<Slot> slots = q.getResultList();
	q = em.createQuery("from Field");
	@SuppressWarnings("unchecked")
	List<Court> courts = q.getResultList();
	for(Court court: courts){
			for (Slot slot: slots){
				CourtSchedule fs = new CourtSchedule ();
				fs.setFieldPartitionType(0);
				fs.setSlot(slot);
				fs.setCourt(court);
				em.persist(fs);
			}		
	}
}
	

	private void randomSchedule() {

		Slot slot = new Slot();
		Court court = new Court();
		Query q = em.createQuery("from Slot");

		List<Slot> slots = q.getResultList();
		q = em.createQuery("from Field");
		List<Court> courts = q.getResultList();
		
		for (int i = 0; i < 20; i++) {
			slot = slots.get(i);
			court = courts.get(random.nextInt(courts.size()));
			CourtSchedule fs = new CourtSchedule();
			fs.setSlot(slot);
			fs.setCourt(court);
			fs.setFieldPartitionType(3);
			em.persist(fs);
		}
	}

	private CourtSchedule createNewSchedule(Court court, Slot slot) {

		
		Query q = em.createQuery(
				"from FieldSchedule fs join fs.field fie join fs.slot sl where fie.id =:fieldId and sl.id=:slotId");
		q.setParameter("fieldId", court.getId());
		q.setParameter("slotId", slot.getStartTime());
		CourtSchedule fs =new CourtSchedule(); 
		fs = (CourtSchedule) q.getSingleResult();
		System.out.println(fs);
		if (fs!=null) {
			
			return fs;
		}
		CourtSchedule fsThis = new CourtSchedule();
		fsThis.setCourt(court);
		fsThis.setSlot(slot);
		fsThis.setFieldPartitionType(2);
		em.persist(fsThis);
		return fsThis;
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
			Map<Exercise, ExerciseSession> etds = new HashMap<Exercise, ExerciseSession>();
			for (Exercise ex : exs) {
				ExerciseSession es = new ExerciseSession(random.nextInt(20), random.nextInt(20), random.nextInt(20));

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
		
		game.setName(rd.randomName());
		game.setType(randomType());
		game.setDescription(randomDescription());

		Slot slot = new Slot();

		
		
		game.setSlots(slot);

		
		game.setOpponent("opponent" + random.nextInt(20));

		em.persist(game);

		recordNewLineToClubDiary(game);

	}
}
