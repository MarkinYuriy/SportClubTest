package sportclub.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import flexjson.transformer.StringTransformer;

import java.util.logging.Level;
import java.util.logging.Logger;
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

	@RequestMapping({ "/", "home" })
	public String home() {
		return "success";

	}

	@RequestMapping(value = SportclubConstants.REMOVE + "/{id}", method = RequestMethod.POST)
	public @ResponseBody boolean remove(@PathVariable int id) {

		return profiles.removeProfile(id);
	}

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
	}

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
	}

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
	public @ResponseBody String getClubs() throws JsonGenerationException, JsonMappingException, IOException {
		
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
	}

	@RequestMapping(value = SportclubConstants.GET_TEAMS, method = RequestMethod.POST)
	public @ResponseBody String getTeams() throws JsonGenerationException, JsonMappingException, IOException {
		//ObjectMapper mapper = new ObjectMapper();
		Iterable<Team> teams = profiles.getTeams();
		
		return singleObjectToRequest(teams);
	}

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

	@RequestMapping(value = SportclubConstants.GET_ROLES, method = RequestMethod.POST)
	public @ResponseBody String getRoles() {
		String res = "";

		List<sportclub.nodeprocessor.Role> roleSet = new ArrayList<sportclub.nodeprocessor.Role>();
		Iterable<Role> getRoles = profiles.getRoles("");

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

		// System.out.println(json);
		return res;
	}

	@RequestMapping(value = SportclubConstants.ADD_TEAM, method = RequestMethod.POST)
	public @ResponseBody String addTeam(@RequestBody Team team) {
		System.out.println("add team");
		boolean f = profiles.addTeam(team);

		String res = responseToJSONForAdd(f);

		return res;
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

}
