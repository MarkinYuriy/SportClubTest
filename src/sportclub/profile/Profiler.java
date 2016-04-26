package sportclub.profile;


import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import sportclub.model.Club;
import sportclub.model.ImageBank;
import sportclub.model.Role;
import sportclub.model.Team;
@JsonFilter("myFilter")
@Entity
public class Profiler implements Serializable{
	
	public Profiler(String id, String name, String lastName, String email,
			String position, String description) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.position = position;
		this.description = description;
	}


	public String getId() {
		return id;
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 247213587096808684L;
	/*@Id@GeneratedValue
	@Column(name="profilerId")*/
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name="profilerId", columnDefinition ="CHAR(32)" )
    @Id
    private String id;
	public Profiler(String id) {
		
		this.id = id;
	}


	@Override
	public String toString() {
		return "Profiler [id=" + id + ", login=" + login + ", password=" + password + ", name=" + name
				+ ", lastName=" + lastName + ", email=" + email + ", position=" + position + ", description="
				+ description + ", deleted=" + deleted + ", roles=" + roles + ", photos=" + photos 
				+ "]";
	}

	private String login; 
	private String password;
	private String name;
	private String lastName;
	private String email;
	private String position;
	private String description;
	private boolean deleted;
	private boolean federationPlayer;
	
	
	public boolean isFederationPlayer() {
		return federationPlayer;
	}


	public void setFederationPlayer(boolean federationPlayer) {
		this.federationPlayer = federationPlayer;
	}

	@JsonIgnore
	/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idCode")*/
	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.REFRESH)
	private Set<Role> roles;
	
	@JsonIgnore
	@OneToMany
	private Set<ImageBank> photos;
	
	/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")*/
	@JsonIgnore
	@ManyToMany
	private Set<Team> teams;
	
	@JsonIgnore
	@ManyToOne
	private Club club;
	
	public Club getClub() {
		return club;
	}


	public void setClub(Club club) {
		this.club = club;
	}


	public Profiler() {}


	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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


	
	public void setProperties(Map<String, String> properties){
		
				
		login = properties.get("login"); 
		password= properties.get("password"); ;
		name= properties.get("name"); ;
		lastName= properties.get("lastName"); ;
		email= properties.get("email"); ;
		position= properties.get("position"); ;
		description= properties.get("description");
		deleted = Boolean.parseBoolean(properties.get("deleted"));
		federationPlayer =Boolean.parseBoolean(properties.get("federationPlayer"));
		
		
	}


	public void setId(String id) {
		this.id = id;
	}
	

}
