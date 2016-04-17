package sportclub.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
<<<<<<< HEAD

import javax.persistence.Query;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
=======
import java.util.Set;

>>>>>>> refs/heads/2016-04-14
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import flexjson.JSONSerializer;
<<<<<<< HEAD
import flexjson.transformer.MapTransformer;
import random.context.ISportclubRandomDBRepository;
=======
import flexjson.transformer.StringTransformer;

import java.util.logging.Level;
import java.util.logging.Logger;
>>>>>>> refs/heads/2016-04-14
//import flexjson.JSONSerializer;
import sportclub.interfaces.ISportclubRepository;
import sportclub.model.*;
import sportclub.model.Role;
import sportclub.profile.Athlete;
import sportclub.profile.Profiler;
import sportclub.nodeprocessor.*;

@Controller
@Scope("session")
@RequestMapping("/")
public class SportclubRestController {
	@Autowired
	ISportclubRepository profiles;
<<<<<<< HEAD
	
	@Autowired 
	ISportclubRandomDBRepository dbrepo;
	
	ObjectMapper om;
	
	@RequestMapping({"/","home"})
	public String home(){
=======

	@RequestMapping({ "/", "home" })
	public String home() {
>>>>>>> refs/heads/2016-04-14
		return "success";

	}
<<<<<<< HEAD
	
	@RequestMapping(value=SportclubConstants.REMOVE+"/{id}", method=RequestMethod.POST)
	public @ResponseBody boolean remove(@PathVariable int id){
=======

	@RequestMapping(value = SportclubConstants.REMOVE + "/{id}", method = RequestMethod.POST)
	public @ResponseBody boolean remove(@PathVariable int id) {
>>>>>>> refs/heads/2016-04-14

		return profiles.removeProfile(id);
	}
<<<<<<< HEAD
	
	
	@RequestMapping(value=SportclubConstants.SIGN_IN, method=RequestMethod.POST)
	public @ResponseBody String signIn(@RequestBody LoginPassword lp){
		System.out.println(lp.toString());
		String uid = profiles.signIn(lp);	

		String res="";
		if (uid !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
	
			String stri = uid+"";
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Authorization error\"}";
		}
	//System.out.println(res);	

		return res;
	}
	
	@RequestMapping(value=SportclubConstants.REGISTRATION, method=RequestMethod.POST)
	public @ResponseBody String registration(@RequestBody LoginPassword lp){
		System.out.println(lp.toString());
		String uid = profiles.registration(lp);	

		String res="";
		if (uid !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
	
			String stri = uid+"";
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"User exist\"}";
		}
	//System.out.println(res);	

		return res;
	}
	
	@RequestMapping(value=SportclubConstants.CREATE_RANDOM_DB, method=RequestMethod.PUT)
	public @ResponseBody boolean createRandomDB() throws JsonProcessingException{
		
			try {
				dbrepo.addRandomProfile();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			System.out.println("random Profilers added");
			dbrepo.addRandomTrainingPool();
			System.out.println("random TrainingPool added");
			dbrepo.addRandomEvent();
			System.out.println("random Event added");
			dbrepo.addRandomTraining();
			System.out.println("random Training added");
			dbrepo.addRandomGame();
			System.out.println("random game added");
		System.out.println("random DB created");
		return true;
	}
	
	@RequestMapping(value=SportclubConstants.GET_PROFILES+"/{SubProfiler}", method=RequestMethod.POST)
	public @ResponseBody String getProfiles(@PathVariable String SubProfiler) throws JsonGenerationException, JsonMappingException, IOException, ReflectiveOperationException {
		System.out.println("GET_PROFILES");	
		String h = JSONToClient(profiles.getProfiles(SubProfiler));
		System.out.println(h);
		return h;
	}
	
	@RequestMapping(value=SportclubConstants.GET_PROFILE+"/{SubProfiler}"+"/{id}", method=RequestMethod.POST)
	public @ResponseBody String getProfile(@PathVariable String SubProfiler, @PathVariable String id) throws JsonGenerationException, JsonMappingException, IOException, ReflectiveOperationException {
		
		return JSONToClient(profiles.getProfile(SubProfiler,id));
=======

	@RequestMapping(value = SportclubConstants.SIGN_IN, method = RequestMethod.POST)
	public @ResponseBody String signIn(@RequestBody LoginPassword lp) {
		System.out.println(lp.toString());
		String uid = profiles.signIn(lp);

		String res = "";
		if (uid != null) {
			res += "{\"Status\":\"Success\",\"Data\":";

			String stri = "\"" + uid + "\"";
			res += stri + "}";
		} else {
			res += "{\"Status\":\"Unsuccess\",\"Data\":\"Authorization error\"}";
		}
		// System.out.println(res);

		return res;
>>>>>>> refs/heads/2016-04-14
	}

<<<<<<< HEAD
	@RequestMapping(value=SportclubConstants.GET_CLUB+"/{id}", method=RequestMethod.POST)
	public @ResponseBody String getClub(@PathVariable int id) throws JsonGenerationException, JsonMappingException, IOException {
			return JSONToClient(profiles.getClub(id));
=======
	@RequestMapping(value = SportclubConstants.REGISTRATION, method = RequestMethod.POST)
	public @ResponseBody String registration(@RequestBody LoginPassword lp) {
		System.out.println(lp.toString());
		String uid = profiles.registration(lp);

		String res = "";
		if (uid != null) {
			res += "{\"Status\":\"Success\",\"Data\":";

			String stri = "\"" + uid + "\"";
			res += stri + "}";
		} else {
			res += "{\"Status\":\"Unsuccess\",\"Data\":\"User exist\"}";
		}
		// System.out.println(res);

		return res;
>>>>>>> refs/heads/2016-04-14
	}
<<<<<<< HEAD
	
	@RequestMapping(value=SportclubConstants.GET_CLUBS, method=RequestMethod.POST)
=======

	@RequestMapping(value = SportclubConstants.REGISTRATION + "/{id}", method = RequestMethod.POST)
	public @ResponseBody String registrationWid(@PathVariable String id, @RequestBody LoginPassword lp) {
		Profiler p = profiles.registrationWid(id, lp);

		String res = "";
		if (p != null) {
			res += "{\"Status\":\"Success\",\"Data\":{";

			String stri = "";
			try {
				stri = new ObjectMapper().writeValueAsString(p);
				stri = stri.replace("\\\"id\\\"", "\"id\"");
				stri = stri.replace("\\\"node\\\"", "\"node\"");
				stri = stri.replace("\"{", "{");
				stri = stri.replace("}\"", "}");
			} catch (JsonProcessingException ex) {
				Logger.getLogger(SportclubRestController.class.getName()).log(Level.SEVERE, null, ex);
			}

			res += stri + "}";
		} else {
			res += "{\"Status\":\"Unsuccess\",\"Data\":\"id no correct\"}";
		}
		// System.out.println(res);

		return res;
	}

	@RequestMapping(value = SportclubConstants.GET_PROFILES + "/{SubProfiler}", method = RequestMethod.POST)
	public @ResponseBody String getProfiles(@PathVariable String SubProfiler)
			throws JsonParseException, JsonParseException, IOException, ReflectiveOperationException {
		
		String h = JSONToClient(profiles.getProfiles(SubProfiler));
		
		return h;
	}

	@RequestMapping(value = SportclubConstants.GET_PROFILE + "/{SubProfiler}" + "/{id}", method = RequestMethod.POST)
	public @ResponseBody String getProfile(@PathVariable String SubProfiler, @PathVariable String id)
			throws com.fasterxml.jackson.core.JsonGenerationException, com.fasterxml.jackson.databind.JsonMappingException, IOException, ReflectiveOperationException {

		return singleObjectToRequest(profiles.getProfile(SubProfiler, id));
	}

	@RequestMapping(value = SportclubConstants.GET_CLUB + "/{id}", method = RequestMethod.POST)
	public @ResponseBody String getClub(@PathVariable int id)
			throws com.fasterxml.jackson.core.JsonGenerationException, com.fasterxml.jackson.databind.JsonMappingException, IOException {
		
		JSONSerializer ser = new JSONSerializer();
		Object obj =	profiles.getClub(id);
			String res = "";
			if (obj != null) {
				RequestSuccess rs = new RequestSuccess();
				rs.setData(obj);
				res = ser.exclude("*.class").exclude("data.deleted")
						.exclude("data.diary")
						.include("status")
						.serialize(rs);
			} else {
				RequestUnsuccess urs = new RequestUnsuccess();
				urs.setData("Record/es doesn't exist");
				res = ser.serialize(urs);
			}
			
			
			System.out.println(res);
			
			return res;
	}

	@RequestMapping(value = SportclubConstants.GET_CLUBS, method = RequestMethod.POST)
>>>>>>> refs/heads/2016-04-14
	public @ResponseBody String getClubs() throws JsonGenerationException, JsonMappingException, IOException {
<<<<<<< HEAD
			return JSONToClient(profiles.getClubs());
=======
		
		JSONSerializer ser = new JSONSerializer();
	Object obj =	profiles.getClubs();
		String res = "";
		if (obj != null) {
			RequestSuccess rs = new RequestSuccess();
			rs.setData(obj);
			res = ser.exclude("*.class").exclude("data.deleted")
					.exclude("data.diary")
					.include("status")
					.serialize(rs);
		} else {
			RequestUnsuccess urs = new RequestUnsuccess();
			urs.setData("Record/es doesn't exist");
			res = ser.serialize(urs);
		}
		
		
		System.out.println(res);
		
		return res;
>>>>>>> refs/heads/2016-04-14
	}
<<<<<<< HEAD
	
	@RequestMapping(value=SportclubConstants.GET_TEAMS, method=RequestMethod.POST)
=======

	@RequestMapping(value = SportclubConstants.GET_TEAMS, method = RequestMethod.POST)
>>>>>>> refs/heads/2016-04-14
	public @ResponseBody String getTeams() throws JsonGenerationException, JsonMappingException, IOException {
<<<<<<< HEAD
		Iterable<Team> teams = profiles.getTeams();
		String result = JSONToClient(teams);
		//System.out.println(result);
		return result;
	}
		
	@RequestMapping(value=SportclubConstants.GET_TEAM+"/{id}", method=RequestMethod.POST)
	public @ResponseBody String getTeam(@PathVariable int id) throws JsonGenerationException, JsonMappingException, IOException {
			return JSONToClient(profiles.getTeam(id));
		
=======
		//ObjectMapper mapper = new ObjectMapper();
		Iterable<Team> teams = profiles.getTeams();
		
		return singleObjectToRequest(teams);
>>>>>>> refs/heads/2016-04-14
	}

<<<<<<< HEAD
	@RequestMapping(value=SportclubConstants.GET_ROLE+"/{id}", method=RequestMethod.POST)
	public @ResponseBody String  getRole(@PathVariable String id) {
		String res ="";
=======
	@RequestMapping(value = SportclubConstants.GET_TEAM + "/{id}", method = RequestMethod.POST)
	public @ResponseBody String getTeam(@PathVariable int id)
			throws JsonGenerationException, JsonMappingException, IOException {

		return singleObjectToRequest(profiles.getTeam(id));
	}

	private String singleObjectToRequest(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
	
		objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS , false);
		objectMapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, false);
		String res = "";
		if (obj != null) {
			RequestSuccess rs = new RequestSuccess();
			rs.setData(obj);
			res = objectMapper.writeValueAsString(rs);
		} else {
			RequestUnsuccess urs = new RequestUnsuccess();
			urs.setData("Record/es doesn't exist");
			res = objectMapper.writeValueAsString(urs);
		}
		return res;
	}

	@RequestMapping(value = SportclubConstants.GET_ROLE + "/{id}", method = RequestMethod.POST)
	public @ResponseBody String getRole(@PathVariable String id) {
		String res = "";
>>>>>>> refs/heads/2016-04-14
		Iterable<Role> getRoles = null;
		// sportclub.nodeprocessor.node
		String json = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			json = objectMapper.writeValueAsString((RoleGenerator.node(id)));
			// System.out.println(json);
			if (!json.equals("null")) {
				getRoles = profiles.getRoles(json);
			} else {
				getRoles = null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		if (getRoles != null) {
			res += "{\"Status\":\"Success\",\"Data\":";
			String stri = new Gson().toJson(getRoles);
			stri = stri.replace("\\\"id\\\"", "\"id\"");
			stri = stri.replace("\\\"node\\\"", "\"node\"");
			stri = stri.replace("\"{", "{");
			stri = stri.replace("}\"", "}");
			res += stri + "}";
		} else {
			res += "{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}

		System.out.println(res);
		return res;
	}

<<<<<<< HEAD
	@RequestMapping(value=SportclubConstants.GET_ROLES, method=RequestMethod.POST)
	public @ResponseBody String  getRoles() {
		String res ="";
=======
	@RequestMapping(value = SportclubConstants.GET_ROLES, method = RequestMethod.POST)
	public @ResponseBody String getRoles() {
		String res = "";
>>>>>>> refs/heads/2016-04-14

		List<sportclub.nodeprocessor.Role> roleSet = new ArrayList<sportclub.nodeprocessor.Role>();
		Iterable<Role> getRoles = profiles.getRoles("");

<<<<<<< HEAD
		if (getRoles !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			String stri=new Gson().toJson(getRoles);
			stri = stri.replace("\\\"id\\\"","\"id\"");
			stri = stri.replace("\\\"node\\\"","\"node\"");
			stri = stri.replace("\"{","{");
			stri = stri.replace("}\"","}");
			res+=stri+"}";
=======
		if (getRoles != null) {
			res += "{\"Status\":\"Success\",\"Data\":";
			String stri = new Gson().toJson(getRoles);
			stri = stri.replace("\\\"id\\\"", "\"id\"");
			stri = stri.replace("\\\"node\\\"", "\"node\"");
			stri = stri.replace("\"{", "{");
			stri = stri.replace("}\"", "}");
			res += stri + "}";
>>>>>>> refs/heads/2016-04-14

<<<<<<< HEAD
			
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
=======
		} else {
			res += "{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
>>>>>>> refs/heads/2016-04-14
		}

		// System.out.println(json);
		return res;
	}

<<<<<<< HEAD
	@RequestMapping(value=SportclubConstants.ADD_TEAM, method=RequestMethod.POST)
	public @ResponseBody String addTeam(@RequestBody Team team){
		System.out.println("add team");
		boolean f = profiles.addTeam(team);
		
		String res=responseToJSONForAdd(f);
		
		return res;
	}
		
	private String responseToJSONForAdd(boolean f) {
		String res="";
		if (f){
			res+="{\"Status\":\"Success\",\"Data\":\"record added\"}";
			}
		else res+="{\"Status\":\"Unsuccess\",\"Data\":\"data error or record not found\"}";
		return res;
	}

	@RequestMapping(value=SportclubConstants.ADD_PROFILE+ "/{SubProfiler}", method=RequestMethod.POST)
	public @ResponseBody String addProfile(@RequestBody Profiler profile,@PathVariable String SubProfiler){
		
		System.out.println("add profile");
		boolean f = profiles.addProfiler(profile,SubProfiler);
		
		String res=responseToJSONForAdd(f);
		
		return res;
		
	}
	
//	@RequestMapping(value = SportclubConstants.ADD_TEAM, method = RequestMethod.POST)
//	@ResponseBody
//    public String updateTeam(@RequestBody Team team) {
//		String res ="";
//    	profiles.addTeam(team);
//    	
//		Team teamCur = profiles.getTeam(team.getId());
//		if (teamCur !=null){
//			res+="{\"Status\":\"Success\",\"Data\":";
//			String stri="";
//			try {
//				stri = new ObjectMapper().writeValueAsString(teamCur);
//			} catch (JsonProcessingException e) {
//				// TODO Auto-generated catch block
//				// e.printStackTrace();
//			}
//			res+=stri+"}";
//		}else{
//			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
//		}
//		//System.out.println(res);
//		return res;
//    }

	@RequestMapping(value=SportclubConstants.ADD_FIELD, method=RequestMethod.POST)
	public @ResponseBody void addCourt(@RequestBody Court court){
		profiles.addCourt(court);
	}

	@RequestMapping(value=SportclubConstants.ALL_QUERIES, method=RequestMethod.POST)
	public @ResponseBody void getAnyRequest(@RequestBody String jpql) throws JsonGenerationException, JsonMappingException, IOException {
System.out.println("query");
		Iterable<Object> it = profiles.getAnyRequest(jpql);
		
		for(Object t: it){
					
			JSONSerializer ser = new JSONSerializer();

			String h = ser.exclude("diary").deepSerialize(t);

			System.out.println(h);}

	}

	private String JSONToClient(Object objects) {
		String res="";
		if (objects !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			JSONSerializer ser = new JSONSerializer();
			String stri = ser.deepSerialize(objects);
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}
		return res;
	}
	
	@RequestMapping(value = SportclubConstants.UPDATE_CLUB, method = RequestMethod.POST)
	@ResponseBody String updateClub(@RequestBody Club club){
		String res="";
		Club resClub = null;
		if (profiles.updateClub(club)){
			resClub = profiles.getClub(club.getId());
		}
		res = JSONToClient(resClub);
		return res;
	}
	
	@RequestMapping(value = SportclubConstants.UPDATE_PROFILER, method = RequestMethod.POST)
	@ResponseBody String updateProfiler(@RequestBody Athlete profiler){
		String res="";
		String subProfiler = profiler.getClass().getSimpleName();
		System.out.println("Prof "+profiler.getCode());
		System.out.println("Sub "+subProfiler);
		Profiler resProfiler = null;
		if (profiles.updateAthlete(profiler, subProfiler)){
			resProfiler = profiles.getProfile(subProfiler, profiler.getCode());
		}
		res = JSONToClient(resProfiler);
		return res;
	}
	
	@RequestMapping(value = SportclubConstants.UPDATE_TEAM, method = RequestMethod.POST)
	@ResponseBody
    public String updateTeam(@RequestBody Team team) {
		String res ="";
		Team resTeam = null;
		if (profiles.updateTeam(team)){
			resTeam = profiles.getTeam(team.getId());
		}
		res = JSONToClient(resTeam);
		//System.out.println(res);
		return res;
    }
	
	@RequestMapping(value = SportclubConstants.REMOVE_CLUB, method = RequestMethod.POST)
	@ResponseBody String removeClub(@RequestBody Club club){
		String res="";
		//Club resClub = profiles.getClub(club.getId());
		if (profiles.removeClub(club)){
			res+="{\"Status\":\"Removing Success}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}
		return res;
	}
	
	@RequestMapping(value = SportclubConstants.REMOVE_PROFILER+"{/subProfiler}", method = RequestMethod.POST)
	@ResponseBody String removeProfiler(@RequestBody Profiler profiler, @PathVariable String subProfiler){
		String res="";
		Profiler resProfiler = profiles.getProfile(subProfiler, profiler.getCode());
		if (profiles.removeProfiler(profiler, subProfiler)){
			res+="{\"Status\":\" Removing Success\",\"Data\":";
			String stri="";
			try {
				stri = new ObjectMapper().writeValueAsString(resProfiler);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}
		return res;
	}
	
	@RequestMapping(value = SportclubConstants.REMOVE_TEAM, method = RequestMethod.POST)
	@ResponseBody
    public String removeTeam(@RequestBody Team team) {
		String res ="";
		Team resTeam = profiles.getTeam(team.getId());
		if (profiles.updateTeam(team)){
			res+="{\"Status\":\"Removing Success\",\"Data\":";
			String stri="";
			try {
				stri = new ObjectMapper().writeValueAsString(resTeam);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}
		//System.out.println(res);
=======
	@RequestMapping(value = SportclubConstants.ADD_TEAM, method = RequestMethod.POST)
	public @ResponseBody String addTeam(@RequestBody Team team) {
		System.out.println("add team");
		boolean f = profiles.addTeam(team);

		String res = responseToJSONForAdd(f);

>>>>>>> refs/heads/2016-04-14
		return res;
<<<<<<< HEAD
    }
	
=======
	}

	private String responseToJSONForAdd(boolean f) {
		String res = "";
		if (f) {
			res += "{\"Status\":\"Success\",\"Data\":\"record added\"}";
		} else
			res += "{\"Status\":\"Unsuccess\",\"Data\":\"data error or record not found\"}";
		return res;
	}

	@RequestMapping(value = SportclubConstants.ADD_PROFILE + "/{SubProfiler}", method = RequestMethod.POST)
	public @ResponseBody String addProfile(@RequestBody Athlete profile, @PathVariable String SubProfiler) {

		System.out.println(profile.toString()+" "+SubProfiler);
		/*boolean f = profiles.addProfiler(profile, SubProfiler);

		String res = responseToJSONForAdd(f);*/

		return "answer";

	}

	// @RequestMapping(value = SportclubConstants.ADD_TEAM, method =
	// RequestMethod.POST)
	// @ResponseBody
	// public String updateTeam(@RequestBody Team team) {
	// String res ="";
	// profiles.addTeam(team);
	//
	// Team teamCur = profiles.getTeam(team.getId());
	// if (teamCur !=null){
	// res+="{\"Status\":\"Success\",\"Data\":";
	// String stri="";
	// try {
	// stri = new ObjectMapper().writeValueAsString(teamCur);
	// } catch (JsonProcessingException e) {
	// // TODO Auto-generated catch block
	// // e.printStackTrace();
	// }
	// res+=stri+"}";
	// }else{
	// res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
	// }
	// //System.out.println(res);
	// return res;
	// }

	@RequestMapping(value = SportclubConstants.ADD_FIELD, method = RequestMethod.POST)
	public @ResponseBody void addCourt(@RequestBody Court court) {
		profiles.addCourt(court);
	}

	@RequestMapping(value = SportclubConstants.ALL_QUERIES, method = RequestMethod.POST)
	public @ResponseBody void getAnyRequest(@RequestBody String jpql)
			throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("query");
		Iterable<String> it = profiles.getAnyRequest(jpql);

		for (String t : it) {

			/*JSONSerializer ser = new JSONSerializer();

			String h = ser.exclude("diary").deepSerialize(t);*/

			System.out.println(t);
		}

	}

	private String JSONToClient(Object objects) {
		String res = "";
		if (objects != null) {
			res += "{\"Status\":\"Success\",\"Data\":";
			JSONSerializer ser = new JSONSerializer();
			String stri = ser.deepSerialize(objects);
			res += stri + "}";
		} else {
			res += "{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}
		return res;
	}
>>>>>>> refs/heads/2016-04-14

}
