package sportclub.profile;


import java.util.*;
import javax.persistence.*;

import sportclub.model.ImageBank;
import sportclub.model.Role;
import sportclub.model.Team;

@Entity
public class Profiler {
	
	@Id
	
	long code;
	/*String code; *///Don't set int id as var in this class
	//@Column(nullable=false,unique=true)
	String login; 
	String password;
	String name;
	String lastName;
	String email;
	String position;
	String description;
	
	public Profiler() {}


	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.REFRESH)
	Set<Role> roles;
	
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.REFRESH)
	Set<ImageBank> photos;
	
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.REFRESH)
	Set<Team> teams;

	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public Set<ImageBank> getPhotos() {
		return photos;
	}


	public void setPhotos(Set<ImageBank> photos) {
		this.photos = photos;
	}


	public Set<Team> getTeams() {
		return teams;
	}


	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}


	

	

}
