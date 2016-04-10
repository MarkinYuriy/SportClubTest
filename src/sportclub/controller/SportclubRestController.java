package sportclub.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
//import flexjson.JSONSerializer;
import sportclub.interfaces.ISportclubRepository;
import sportclub.model.*;
import sportclub.model.Role;
import sportclub.profile.Profiler;
import sportclub.nodeprocessor.*;



@Controller
@Scope("session")
@RequestMapping("/")
public class SportclubRestController {
	@Autowired
	ISportclubRepository profiles;
	
	@RequestMapping({"/","home"})
	public String home(){
		return "success";

	}
	
	@RequestMapping(value=SportclubConstants.REMOVE+"/{id}", method=RequestMethod.PUT)
	public @ResponseBody boolean remove(@PathVariable int id){

		return profiles.removeProfile(id);
	}
	
	@RequestMapping(value=SportclubConstants.GET_PROFILES+ "/{SubProfiler}", method=RequestMethod.GET)
	public @ResponseBody String getProfiles(@PathVariable String SubProfiler) throws JsonGenerationException, JsonMappingException, IOException, ReflectiveOperationException {
		String res="";
		Iterable<Profiler> listProfiles = profiles.getProfiles(SubProfiler);
		if (listProfiles !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			String stri = new ObjectMapper().writeValueAsString(listProfiles);
			//System.out.println(stri);
			//\"id\"   \\\"id\\\"
			//\"node\"
			//stri = stri.replace("\\\"","\"");
			stri = stri.replace("\\\"id\\\"","\"id\"");
			stri = stri.replace("\\\"node\\\"","\"node\"");
			stri = stri.replace("\"{","{");
			stri = stri.replace("}\"","}");
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}

		return res;
	}
	
	@RequestMapping(value=SportclubConstants.GET_PROFILES+ "/{SubProfiler}"+ "/{id}", method=RequestMethod.GET)
	public @ResponseBody String getProfiles(@PathVariable String SubProfiler, @PathVariable long id) throws JsonGenerationException, JsonMappingException, IOException, ReflectiveOperationException {
		String res="";
		Iterable<Profiler> listProfiles = profiles.getProfiles(SubProfiler,id);
		if (listProfiles !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			String stri = new ObjectMapper().writeValueAsString(listProfiles);

			stri = stri.replace("\\\"id\\\"","\"id\"");
			stri = stri.replace("\\\"node\\\"","\"node\"");
			stri = stri.replace("\"{","{");
			stri = stri.replace("}\"","}");
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}

		return res;
	}

	@RequestMapping(value=SportclubConstants.GET_CLUBS+ "/{id}", method=RequestMethod.GET)
	public @ResponseBody String getClubs(@PathVariable long id) throws JsonGenerationException, JsonMappingException, IOException {
		String res="";
		Iterable<Club> listClubs = profiles.getClubs(id);
		if (listClubs !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			String stri = new ObjectMapper().writeValueAsString(listClubs);
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}

		return res;
	}
	
	@RequestMapping(value=SportclubConstants.GET_CLUBS, method=RequestMethod.GET)
	public @ResponseBody String getClubs() throws JsonGenerationException, JsonMappingException, IOException {
		String res="";
		Iterable<Club> listClubs = profiles.getClubs();
		if (listClubs !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			String stri = new ObjectMapper().writeValueAsString(listClubs);
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}

		return res;
	}
	
	@RequestMapping(value=SportclubConstants.GET_TEAMS, method=RequestMethod.GET)
	//public @ResponseBody Iterable<Team> getTeams() {
	public @ResponseBody String getTeams() throws JsonGenerationException, JsonMappingException, IOException {
		String res="";
		Iterable<Team> listTeams = profiles.getTeams();
		if (listTeams !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			String stri = new ObjectMapper().writeValueAsString(listTeams);
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}

		return res;


		//return profiles.getTeams();
	}
	
	
	
	@RequestMapping(value=SportclubConstants.GET_TEAMS+"/{id}", method=RequestMethod.GET)
	public @ResponseBody String getTeams(@PathVariable int id) throws JsonGenerationException, JsonMappingException, IOException {
		String res="";
		Iterable<Team> listTeams = profiles.getTeams(id);
		if (listTeams !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			String stri = new ObjectMapper().writeValueAsString(listTeams);
			res+=stri+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}

		return res;


		//return profiles.getTeams();
	}

	@RequestMapping(value=SportclubConstants.GET_ROLES+"/{id}", method=RequestMethod.GET)
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

		//System.out.println(json);
		return res;
	}

	@RequestMapping(value=SportclubConstants.GET_ROLES, method=RequestMethod.GET)
	public @ResponseBody String  getRoles() {
		String res ="";

		List<sportclub.nodeprocessor.Role> roleSet = new ArrayList<sportclub.nodeprocessor.Role>();
		Iterable<Role> getRoles =  profiles.getRoles("");

		if (getRoles !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			/*for(Role rol:getRoles){
				Node node = new Gson().fromJson(rol.getId_code(),Node.class);
				String desc=rol.getDescription();
				sportclub.nodeprocessor.Role role = new sportclub.nodeprocessor.Role();
				role.setId(node);
				role.setDescription(desc);
				roleSet.add(role);
			}*/
			String stri=new Gson().toJson(getRoles);
			stri = stri.replace("\\\"id\\\"","\"id\"");
			stri = stri.replace("\\\"node\\\"","\"node\"");
			stri = stri.replace("\"{","{");
			stri = stri.replace("}\"","}");
			res+=stri+"}";

			//Gson gson = new Gson();
			//res+=gson.toJson(roleSet)+"}";
		}else{
			res+="{\"Status\":\"Unsuccess\",\"Data\":\"Undefined\"}";
		}

		//System.out.println(json);
		return res;
	}

	@RequestMapping(value=SportclubConstants.ADD_TEAM, method=RequestMethod.PUT)
	public @ResponseBody void addTeam(@RequestBody Team team){
		profiles.addTeam(team);
		System.out.println(team);

	}
	
	@RequestMapping(value=SportclubConstants.ADD_PROFILE+ "/{SubProfiler}", method=RequestMethod.POST)
	public @ResponseBody void addProfile(@RequestBody Profiler profile,@PathVariable String SubProfiler){
		profiles.addProfiler(profile,SubProfiler);

		
	}
	
	@RequestMapping(value = SportclubConstants.ADD_TEAM, method = RequestMethod.POST)
	@ResponseBody
    public String updateTeam(@RequestBody Team team) {
		String res ="";
    	profiles.addTeam(team);
    	
		Iterable<Team> listTeams = profiles.getTeams(team.getId());
		if (listTeams !=null){
			res+="{\"Status\":\"Success\",\"Data\":";
			String stri="";
			try {
				stri = new ObjectMapper().writeValueAsString(listTeams);
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

	@RequestMapping(value=SportclubConstants.ADD_FIELD, method=RequestMethod.PUT)
	public @ResponseBody void addCourt(@RequestBody Court court){
		profiles.addCourt(court);


	}


	@RequestMapping(value=SportclubConstants.ALL_QUERIES, method=RequestMethod.PUT)
	public @ResponseBody void getAnyRequest(@RequestBody String jpql) throws JsonGenerationException, JsonMappingException, IOException {
System.out.println("query");
		Iterable<Object> it = profiles.getAnyRequest(jpql);
		
		for(Object t: it){
			//String stri = om.writeValueAsString(t);
		//String stri = new ObjectMapper().writeValueAsString(it);
			MapTransformer mt = new MapTransformer( );
			
			JSONSerializer ser = new JSONSerializer();

			String h = ser.exclude("diary").deepSerialize(t);

			System.out.println(h);}

		
		
		
		
	}














	//	@RequestMapping(value=SportclubConstants.GET_PERSONS_BY_NAME+"/{name}", method=RequestMethod.GET)
	//	public @ResponseBody Iterable<Person> getPersonsByName (@PathVariable String name) {
	//		return persons.getPersonsByCity(name);
	//	}
	//	
	//	@RequestMapping(value=SportclubConstants.GET_PERSONS_BY_CITY+"/{city}", method=RequestMethod.GET)
	//	public @ResponseBody Iterable<Person> getPersonsByCity (@PathVariable String city) {
	//		return persons.getPersonsByCity(city);
	//	}


}
