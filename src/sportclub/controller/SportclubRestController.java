package sportclub.controller;
/**
 * @author paul 04_25_2016
 *
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.parser.ParseException;
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
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.gson.Gson;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import sportclub.data.EventData;
import sportclub.data.MessageData;
import sportclub.data.ProfileData;
import sportclub.data.TeamData;
import sportclub.interfaces.ISportclubRepository;
import sportclub.model.*;
import sportclub.model.Role;
import sportclub.profile.*;

import sportclub.nodeprocessor.*;

@Controller
@Scope("session")
@RequestMapping("/")
public class SportclubRestController {
	@Autowired
	ISportclubRepository profiles;
	
	@RequestMapping({ "/", "home" })
	public String home() {
		return "success";

	}

	@RequestMapping(value = SportclubConstants.SIGN_IN, method = RequestMethod.POST)
	public @ResponseBody String signIn(@RequestBody String json) {
		RequestSuccess rs = new RequestSuccess();
        LoginPassword lp;
            try {
                lp = new ObjectMapper().readValue(json, LoginPassword.class);
            } catch (IOException ex) {
            	rs.setData("incorrected JSON format");
            	rs.setStatus("unsuccess");
            	
                return ObjectToJson(rs);
            }
            String result = getResponse(profiles.signIn(lp),"profiler with this id exists" );
		return result;
	}

	@RequestMapping(value = SportclubConstants.REGISTRATION, method = RequestMethod.POST)
	public @ResponseBody String registration(@RequestBody String json){
		
        LoginPassword lp;
        String uid = null; 
        try{  
            lp = new ObjectMapper().readValue(json, LoginPassword.class);
          	uid = profiles.registration(lp);
          } catch
		(InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
        	  RequestSuccess rs = new RequestSuccess();
        	  rs.setData("incorrected JSON format");
        	  rs.setStatus("unsuccess");
            return ObjectToJson(rs);}
		
        System.out.println(uid);
		String result = getResponse(uid,"profiler with such password and login exists" );
		return result;
	}



	@RequestMapping(value = SportclubConstants.GET_PROFILES + "/{subProfiler}", method = RequestMethod.GET)
	public @ResponseBody String getProfiles(@PathVariable String subProfiler) {
		
		String result = getResponse(profiles.getProfiles(subProfiler),"recordes doesn't exist" );
		
		return result;
			
	}
	
	@RequestMapping(value = SportclubConstants.GET_PROFILE_BY_ID + "/{id}", method = RequestMethod.GET)
	public @ResponseBody String getProfilerById(@PathVariable String id) {
		
		String result = getResponse(profiles.getProfilerById(id),"recordes doesn't exist" );
		
		return result;
			
	}

	@RequestMapping(value = SportclubConstants.GET_PROFILE + "/{SubProfiler}" + "/{id}", method = RequestMethod.GET)
	public @ResponseBody String getProfile(@PathVariable String SubProfiler, @PathVariable String id) {

		String result = getResponse(profiles.getProfile(SubProfiler, id),"recordes doesn't exist" );
	return result;
	}

	@RequestMapping(value = SportclubConstants.GET_CLUB + "/{id}", method = RequestMethod.GET)
	public @ResponseBody String getClub(@PathVariable int id)
			 {
		Object obj = profiles.getClub(id);
		
		String result = getResponse(obj,"record doesn't exist");
		
		return result;
	}

	@RequestMapping(value = SportclubConstants.GET_CLUBS, method = RequestMethod.GET)
	public @ResponseBody String getClubs() {
		String result = getResponse(profiles.getClubs(),"record doesn't exist");
		return result;
	}

	@RequestMapping(value = SportclubConstants.GET_TEAMS+"/{clubId}", method = RequestMethod.GET)
	public @ResponseBody String getTeams(@PathVariable int clubId) {
		System.out.println(clubId);
		String result = getResponse(profiles.getTeams(clubId),"record doesn't exist");
 
		return result;
	}
	
	@RequestMapping(value = SportclubConstants.GET_TEAMS, method = RequestMethod.GET)
	public @ResponseBody String getTeams() {
		
		RequestSuccess rs = new RequestSuccess();
  	  rs.setData("input club id!");
  	  rs.setStatus("unsuccess");
      return ObjectToJson(rs);
	}
	
	@RequestMapping(value = SportclubConstants.GET_TEAM_STUFF+"/{teamId}", method = RequestMethod.GET)
	public @ResponseBody String getStuff(@PathVariable int teamId) {
		if (teamId==0){
			RequestSuccess rs = new RequestSuccess();
      	  rs.setData("empty id");
      	  rs.setStatus("unsuccess");
          return ObjectToJson(rs);
			}
		String result = getResponse(profiles.getTeamStuff(teamId,""),"record doesn't exist");
 
		return result;
	}
	@RequestMapping(value = SportclubConstants.GET_TEAM_STUFF+"/{teamId}" +"/{subprofiler}", method = RequestMethod.GET)
	public @ResponseBody String getStuff(@PathVariable int teamId, @PathVariable String subprofiler) {
		if (teamId==0){
			RequestSuccess rs = new RequestSuccess();
      	  rs.setData("empty id");
      	  rs.setStatus("unsuccess");
          return ObjectToJson(rs);
			}
		String result = getResponse(profiles.getTeamStuff(teamId,subprofiler),"record doesn't exist");
 
		return result;
	}
	

	@RequestMapping(value = SportclubConstants.GET_TEAM + "/{teamId}", method = RequestMethod.GET)
	public @ResponseBody String getTeam(@PathVariable int teamId){
		String result = getResponse(profiles.getTeam(teamId),"record doesn't exist");
		return result;
	}

	

	@RequestMapping(value = SportclubConstants.GET_ROLE + "/{id}", method = RequestMethod.GET)
	public @ResponseBody String getRole(@PathVariable String id) {
		String res = "";
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
			res += "{\"status\":\"success\",\"data\":";
			String stri = new Gson().toJson(getRoles);
			stri = stri.replace("\\\"id\\\"", "\"id\"");
			stri = stri.replace("\\\"node\\\"", "\"node\"");
			stri = stri.replace("\"{", "{");
			stri = stri.replace("}\"", "}");
			res += stri + "}";
		} else {
			res += "{\"status\":\"unsuccess\",\"data\":\"undefined\"}";
		}

		System.out.println(res);
		return res;
	}

	@RequestMapping(value = SportclubConstants.GET_ROLES, method = RequestMethod.GET)
	public @ResponseBody String getRoles() {
		String res = "";

		List<sportclub.nodeprocessor.Role> roleSet = new ArrayList<sportclub.nodeprocessor.Role>();
		Iterable<Role> getRoles = profiles.getRoles("");

		if (getRoles != null) {
			res += "{\"status\":\"success\",\"data\":";
			String stri = new Gson().toJson(getRoles);
			stri = stri.replace("\\\"id\\\"", "\"id\"");
			stri = stri.replace("\\\"node\\\"", "\"node\"");
			stri = stri.replace("\"{", "{");
			stri = stri.replace("}\"", "}");
			res += stri + "}";

		} else {
			res += "{\"status\":\"unsuccess\",\"data\":\"undefined\"}";
		}

		// System.out.println(json);
		return res;
	}
@RequestMapping(value = SportclubConstants.UPDATE_CLUB, method = RequestMethod.POST)
	@ResponseBody String updateClub(@RequestBody String json) {
	
		Club club = null;
		try {
			club = new ObjectMapper().readValue(json, Club.class);
		} catch (IOException e) {
			RequestSuccess rs = new RequestSuccess();
			rs.setStatus("unsuccess");
			rs.setData("incorrected JSON format");
		return ObjectToJson(rs);
		}
		
	 String res = getResponse(profiles.updateClub(club), "club with id "+club.getId()+" doesn't exist");
		
		
		return res;
	}
	
@RequestMapping(value = SportclubConstants.ADD_CLUB, method = RequestMethod.POST)
	@ResponseBody String addClub(@RequestBody String json) {
	Club club=null;
	try {
		club = new ObjectMapper().readValue(json, Club.class);
	} catch (IOException e) {
		RequestSuccess rs = new RequestSuccess();
		rs.setStatus("unsuccess");
		rs.setData("incorrected JSON format");
	return ObjectToJson(rs);
	}
	
	String res = getResponse(profiles.addClub(club), "club with name "+club.getName()+"already exists");

	return res;
	}
	
	
	@RequestMapping(value = SportclubConstants.UPDATE_TEAM, method = RequestMethod.POST)
	@ResponseBody
    public String updateTeam(@RequestBody String json)  {
		Team team = null;
		try {
			team = new ObjectMapper().readValue(json, Team.class);
		} catch (IOException e) {
			RequestSuccess rs = new RequestSuccess();
			rs.setStatus("unsuccess");
			rs.setData("incorrected JSON format");
		return ObjectToJson(rs);
		}
		
	 String res = getResponse(profiles.updateTeam(team), "team with id "+team.getId()+" doesn't exist");
				
		return res;
		
    }
	
	@RequestMapping(value = SportclubConstants.REMOVE_CLUB, method = RequestMethod.POST)
	@ResponseBody String removeClub(@RequestBody String json) {
		Club club = null;
		
		try {
			club = new ObjectMapper().readValue(json, Club.class);
		} catch (IOException e) {
			RequestSuccess rs = new RequestSuccess();
			rs.setStatus("unsuccess");
			rs.setData("incorrected JSON format");
		return ObjectToJson(rs);
		}
		
	 String res = getResponse(profiles.removeClub(club), "club with id "+club.getId()+" doesn't exist");
		
		
		return res;
		
		}
	
	@RequestMapping(value = SportclubConstants.REMOVE_PROFILER, method = RequestMethod.POST)
	@ResponseBody String removeProfiler(@RequestBody String json) {
		Profiler prf = null;
		String id="";
		try {
			prf = new ObjectMapper().readValue(json, Profiler.class);
			id = prf.getId();
		} catch (IOException e) {
			RequestSuccess rs = new RequestSuccess();
			rs.setStatus("unsuccess");
			rs.setData("incorrected JSON format");
		return ObjectToJson(rs);
		}
		
		String res = getResponse(profiles.removeProfiler(id), "profiler with id "+id+" doesn't exist");
		return res;
	}
	
	@RequestMapping(value = SportclubConstants.REMOVE_TEAM, method = RequestMethod.POST)
	@ResponseBody
    public String removeTeam(@RequestBody String json) {
		Team team = null;
		try {
			team = new ObjectMapper().readValue(json, Team.class);
		} catch (IOException e) {
			RequestSuccess rs = new RequestSuccess();
			rs.setStatus("unsuccess");
			rs.setData("incorrected JSON format");
		return ObjectToJson(rs);
		}
		
	 String res = getResponse(profiles.removeTeam(team), "team with id "+team.getId()+" doesn't exist");
				
		return res;
	}
	@RequestMapping(value = SportclubConstants.ADD_TEAM, method = RequestMethod.POST)
	public @ResponseBody String addTeam(@RequestBody String json) {
		Map<String,Object> mapJ = new HashMap<String,Object>();
		RequestSuccess rs = new RequestSuccess();
		
		try {
			
			mapJ = new ObjectMapper().readValue(json, new TypeReference<Map<String,String>>(){});
			if(!mapJ.containsKey("clubId")&&mapJ.get("clubId")==null){
				rs.setStatus("unsuccess");
				rs.setData("club id is absent");
			return ObjectToJson(rs);
			}
		} catch (IOException e) {
			
			rs.setStatus("unsuccess");
			rs.setData("incorrected JSON format");
		return ObjectToJson(rs);
		}
		TeamData td = profiles.addTeam(mapJ);
		String error = td.getErrorMassage();
		if(error!=null){
			td=null;
		}
		String res = getResponse(td, error);
		
		return res;

	}

	@RequestMapping(value = SportclubConstants.UPDATE_PROFILER, method = RequestMethod.POST)
	public @ResponseBody String updateProfiler(@RequestBody String json) {
		JSONDeserializer<Map<String,String>> des =new JSONDeserializer<>();
		Map<String,String> properties = des.deserialize(json);
		if(!properties.containsKey("id")){
			
			RequestSuccess rs = new RequestSuccess();
			rs.setData("id is indefinite");
			rs.setStatus("unsuccess");
			return ObjectToJson(rs);
		};
		
		ProfileData pd = profiles.updateProfiler(properties);
		String res = getResponse(pd, "profile with id "+properties.get("id")+" doesn't exist");
		
		return res;

	}

	@RequestMapping(value = SportclubConstants.ADD_FIELD, method = RequestMethod.POST)
	public @ResponseBody String addCourt(@RequestBody String json) {
		
		Court c = null;
		try {
			c = new ObjectMapper().readValue(json, Court.class);
		} catch (IOException e) {
			RequestSuccess rs = new RequestSuccess();
			rs.setStatus("unsuccess");
			rs.setData("incorrected JSON format");
		return ObjectToJson(rs);
		}
		
		String res = getResponse(profiles.addCourt(c), "court with name "+c.getName()+" already exists");
		
		return res;
	}

	@RequestMapping(value = SportclubConstants.ALL_QUERIES, method = RequestMethod.GET)
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

	private String getResponse(Object obj, String errInfo) {
		RequestSuccess rs = new RequestSuccess();
		if (obj != null) {
			rs.setStatus("success");
			rs.setData(obj);
		} else {
			rs.setStatus("unsuccess");
			rs.setData(errInfo);
		}
		String res = ObjectToJson(rs);
		return res;
	}
	
	private String ObjectToJson(Object rs) {
		
		
		
		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_EMPTY);
		om.setSerializationInclusion(Include.NON_NULL);
		om.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS , false);
		
		SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter.serializeAllExcept("deleted");
	    FilterProvider filters = new SimpleFilterProvider().addFilter("myFilter", theFilter);
		String res="";
		try {
			res = om.writer(filters).writeValueAsString(rs);
		} catch (JsonProcessingException e) {
			return "incorrected JSON format";
		}
		return res;
	}
	
	@RequestMapping(value = SportclubConstants.GET_TEAM_BY_CLUB+"/{clubId}", method = RequestMethod.GET)
	public @ResponseBody String getTeamByClub(@PathVariable String clubId){
		int clId = Integer.parseInt(clubId);
		Object obj = profiles.getTeamByClub(clId);
		String res = getResponse(obj, "record doesn't exist");
		return res;
	}
	
	@RequestMapping(value = SportclubConstants.GET_PROFILE_BY_TEAM+"/{teamId}", method = RequestMethod.GET)
	public @ResponseBody String getProfileByTeam(@PathVariable String teamId){
		int tmId = Integer.parseInt(teamId);
		Object obj = profiles.getProfilerByTeam(tmId);
		String res = getResponse(obj, "record doesn't exist");
		return res;
	}
	
	/** Events Methods
	 * 
	 */
	
	@RequestMapping(value = SportclubConstants.GET_EVENT+ "/{clubId}", method = RequestMethod.GET)
	public @ResponseBody String getEvent(@PathVariable int clubId){
		
		
		String result = getResponse(profiles.getEvents(clubId),"record doesn't exist");
 
		return result;
		
			}
	
	
	@RequestMapping(value = SportclubConstants.ADD_EVENT, method = RequestMethod.POST)
	public @ResponseBody String addEvent(@RequestBody String json){
		
		
		return jsonResponseToRequestEvent(json,true);
		
	}
	
	@RequestMapping(value = SportclubConstants.GET_SLOTS,method = RequestMethod.POST)
	public @ResponseBody String getSlots (@RequestBody String json){
		Map<String,Object> mapJ = new HashMap<String,Object>();
		RequestSuccess rs = new RequestSuccess();

		try {
			mapJ = parseJSONToMap(json);
		} catch (IOException e) {
			rs.setStatus("unsuccess");
			rs.setData("incorrected JSON format");
		return ObjectToJson(rs);
		}
		
		
		Object obj = profiles.getSlots(mapJ);
		String res = getResponse(obj, "slots doesn't exist");
			
		
		return res;
		
		
	}
	
	@RequestMapping(value = SportclubConstants.ADD_SLOTS,method = RequestMethod.POST)
	public @ResponseBody String addSlots (@RequestBody String json){
		
		
		return "";
	}
	
	
	
	private String jsonResponseToRequestEvent (String json, boolean hasClub){
		Map<String,Object> mapJ = new HashMap<String,Object>();
		RequestSuccess rs = new RequestSuccess();

		try {
			mapJ = parseJSONToMap(json);
		} catch (IOException e) {
			rs.setStatus("unsuccess");
			rs.setData("incorrected JSON format");
		return ObjectToJson(rs);
		}
		if(hasClub){
			if(!mapJ.containsKey("clubId")&&mapJ.get("clubId")==null){
			rs.setStatus("unsuccess");
			rs.setData("club id is absent");
		return ObjectToJson(rs);
		};
		}
		
		MessageData md = profiles.addEvent(mapJ);
		String error = md.getErrorMessage();
		if(error!=null){
			md=null;
		}
		String res = getResponse(md, error);
		
		
		return res;
	}

	private Map<String, Object> parseJSONToMap(String json) throws JsonParseException, JsonMappingException, IOException {
		Map<String,Object> mapJ = new HashMap<String,Object>();
		mapJ = new ObjectMapper().readValue(json, new TypeReference<Map<String,String>>(){});
		return mapJ;
	}
}
