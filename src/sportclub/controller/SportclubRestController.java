package sportclub.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;

import flexjson.JSONSerializer;
import flexjson.transformer.MapTransformer;
import random.context.ISportclubRandomDBRepository;
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
	
	@Autowired 
	ISportclubRandomDBRepository dbrepo;
	
	ObjectMapper om;
	
	@RequestMapping({"/","home"})
	public String home(){
		return "success";

	}
	
	@RequestMapping(value=SportclubConstants.REMOVE+"/{id}", method=RequestMethod.POST)
	public @ResponseBody boolean remove(@PathVariable int id){

		return profiles.removeProfile(id);
	}
	
	
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
	}

	@RequestMapping(value=SportclubConstants.GET_CLUB+"/{id}", method=RequestMethod.POST)
	public @ResponseBody String getClub(@PathVariable int id) throws JsonGenerationException, JsonMappingException, IOException {
			return JSONToClient(profiles.getClub(id));
	}
	
	@RequestMapping(value=SportclubConstants.GET_CLUBS, method=RequestMethod.POST)
	public @ResponseBody String getClubs() throws JsonGenerationException, JsonMappingException, IOException {
			return JSONToClient(profiles.getClubs());
	}
	
	@RequestMapping(value=SportclubConstants.GET_TEAMS, method=RequestMethod.POST)
	public @ResponseBody String getTeams() throws JsonGenerationException, JsonMappingException, IOException {
		Iterable<Team> teams = profiles.getTeams();
		String result = JSONToClient(teams);
		//System.out.println(result);
		return result;
	}
		
	@RequestMapping(value=SportclubConstants.GET_TEAM+"/{id}", method=RequestMethod.POST)
	public @ResponseBody String getTeam(@PathVariable int id) throws JsonGenerationException, JsonMappingException, IOException {
			return JSONToClient(profiles.getTeam(id));
		
	}

	@RequestMapping(value=SportclubConstants.GET_ROLE+"/{id}", method=RequestMethod.POST)
	public @ResponseBody String  getRole(@PathVariable String id) {
		String res ="";
		Iterable<Role> getRoles = null;
		//sportclub.nodeprocessor.node
		String json="";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			json = objectMapper.writeValueAsString((RoleGenerator.node(id)));
			//System.out.println(json);
			if(!json.equals("null")){
				getRoles =  profiles.getRoles(json);
			}else{
				getRoles =  null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		if (getRoles !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			String stri=new Gson().toJson(getRoles);
			stri = stri.replace("\\\"id\\\"","\"id\"");
			stri = stri.replace("\\\"node\\\"","\"node\"");
			stri = stri.replace("\"{","{");
			stri = stri.replace("}\"","}");
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}

		System.out.println(res);
		return res;
	}

	@RequestMapping(value=SportclubConstants.GET_ROLES, method=RequestMethod.POST)
	public @ResponseBody String  getRoles() {
		String res ="";

		List<sportclub.nodeprocessor.Role> roleSet = new ArrayList<sportclub.nodeprocessor.Role>();
		Iterable<Role> getRoles =  profiles.getRoles("");

		if (getRoles !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			String stri=new Gson().toJson(getRoles);
			stri = stri.replace("\\\"id\\\"","\"id\"");
			stri = stri.replace("\\\"node\\\"","\"node\"");
			stri = stri.replace("\"{","{");
			stri = stri.replace("}\"","}");
			res+=stri+"}";

			
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}

		//System.out.println(json);
		return res;
	}

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
		return res;
    }
	

}
