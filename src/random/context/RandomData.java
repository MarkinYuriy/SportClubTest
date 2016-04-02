package random.context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import sportclub.model.ImageBank;
import sportclub.data.RoleData;
import sportclub.data.TeamData;



public class RandomData {
	private Random random = new Random();
	private int nCode = 1000;
	private int nLogin = 1000;
	private int nPassword = 1000;
	private int nName = 1000;
	private int nLastName = 1000;
	private int nEmail=1000;
	private int nPosition=1000;

	private String stringDateMin = "01/01/1930";
	private String stringDateMax = "12/12/2006";
	private int maxNumAuthors=100;
	private int nTeams=15;

	public RandomData() {
		super();
	}

	public RandomData(Random random, int nCode, int nLogin, int nPassword, int nName, int nLastName, int nEmail,
			int nPosition, String stringDateMin, String stringDateMax, int maxNumAuthors, int nTeams) {
		super();
		this.random = random;
		this.nCode = nCode;
		this.nLogin = nLogin;
		this.nPassword = nPassword;
		this.nName = nName;
		this.nLastName = nLastName;
		this.nEmail = nEmail;
		this.nPosition = nPosition;
		this.stringDateMin = stringDateMin;
		this.stringDateMax = stringDateMax;
		this.maxNumAuthors = maxNumAuthors;
		this.nTeams = nTeams;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public int getnCode() {
		return nCode;
	}

	public void setnCode(int nCode) {
		this.nCode = nCode;
	}

	public int getnLogin() {
		return nLogin;
	}

	public void setnLogin(int nLogin) {
		this.nLogin = nLogin;
	}

	public int getnPassword() {
		return nPassword;
	}

	public void setnPassword(int nPassword) {
		this.nPassword = nPassword;
	}

	public int getnName() {
		return nName;
	}

	public void setnName(int nName) {
		this.nName = nName;
	}

	public int getnLastName() {
		return nLastName;
	}

	public void setnLastName(int nLastName) {
		this.nLastName = nLastName;
	}

	public int getnEmail() {
		return nEmail;
	}

	public void setnEmail(int nEmail) {
		this.nEmail = nEmail;
	}

	public int getnPosition() {
		return nPosition;
	}

	public void setnPosition(int nPosition) {
		this.nPosition = nPosition;
	}

	public String getStringDateMin() {
		return stringDateMin;
	}

	public void setStringDateMin(String stringDateMin) {
		this.stringDateMin = stringDateMin;
	}

	public String getStringDateMax() {
		return stringDateMax;
	}

	public void setStringDateMax(String stringDateMax) {
		this.stringDateMax = stringDateMax;
	}

	public int getMaxNumAuthors() {
		return maxNumAuthors;
	}

	public void setMaxNumAuthors(int maxNumAuthors) {
		this.maxNumAuthors = maxNumAuthors;
	}

	public int getnTeams() {
		return nTeams;
	}

	public void setnTeams(int nTeams) {
		this.nTeams = nTeams;
	}

	public long randomCode() {

				return random.nextLong();
	}

	public String randomLogin() {

		return "login" + random.nextInt(nLogin);
	}

	public String randomPassword() {
		return "password" + random.nextInt(nPassword);
	}

	public String randomName() {

		return "name" + random.nextInt(nName);
	}

	public String randomLastName() {
		return "LastName" + random.nextInt(nLastName);
	}

	public String randomEmail() {
		return "email" + random.nextInt(nEmail) + "@smth.com";
	}

	public String randomPosition() {

		return "position" + random.nextInt(nPosition);
	}

	public List<TeamData> teams() {

		List<TeamData> teams = new LinkedList<TeamData>();
		int n = 1 + random.nextInt(maxNumAuthors - 1);
		for (int i = 0; i < n; i++)
			teams.add(new TeamData(randomTeamName(), "description"));

		return teams;
	}

	public String randomTeamName() {

		return "team" + random.nextInt(nTeams);
	}

	public Date randomDate() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		Date dateMin = null;
		Date dateMax = null;
		try {
			dateMin = df.parse(stringDateMin);
			dateMax = df.parse(stringDateMax);
		} catch (ParseException e1) {

		}

		long randomNumberMs = dateMin.getTime()
				+ (long) (Math.random() * ((dateMax.getTime() - dateMin.getTime()) + 1));

		Date date = new Date(randomNumberMs);

		return date;
	}

	public List<RoleData> roles() {
		List<RoleData> roles = new LinkedList<>();
		int n = 1 + random.nextInt(maxNumAuthors - 1);
		for (int i = 0; i < n; i++)
			roles.add(new RoleData(randomRole(), "description"));

		return roles;

	}

	public String randomRole() {

		return "role" + random.nextInt(maxNumAuthors);
	}

	public String randomIDcode(){
		return "code"+random.nextInt(maxNumAuthors);
	}
	
	public ImageBank randomImage() {
		ImageBank img = new ImageBank(randomImageName(), randomLinkToFile(), randomDescription());

		return img;
	}

	public String randomDescription() {
		// TODO Auto-generated method stub
		return "description" + random.nextInt(10000);
	}

	public String randomLinkToFile() {
		// TODO Auto-generated method stub
		return "http://site.org/image" + random.nextInt(10000) + ".jpg";
	}

	public String randomImageName() {
		// TODO Auto-generated method stub
		return "image" + random.nextInt(10000);
	}

}
