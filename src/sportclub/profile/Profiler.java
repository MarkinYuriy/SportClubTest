package sportclub.profile;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

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

import sportclub.model.ImageBank;
import sportclub.model.Role;
import sportclub.model.Team;
import sportclub.model.Club;

@Entity
public class Profiler implements Serializable {

    public String getCode() {
        return code;
    }

    /**
     *
     */
    private static final long serialVersionUID = 247213587096808684L;
    /*@Id@GeneratedValue
	@Column(name="profilerId")*/
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "profilerId", columnDefinition = "CHAR(32)")
    @Id
    private String code;

    @Override
    public String toString() {
        return "Profiler [code=" + code + ", login=" + login + ", password=" + password + ", name=" + name
                + ", lastName=" + lastName + ", email=" + email + ", position=" + position + ", description="
                + description + ", deleted=" + deleted + ", roles=" + roles + ", photos=" + photos + ", teams=" + teams
                + "]";
    }

    @Column(nullable = false,unique = true)
    private String login;
    @Column(nullable = false)
    private String password;
    private String name;
    private String lastName;
    private String email;
    private String position;
    private String description;
    private boolean deleted;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<ImageBank> photos;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Set<Team> teams;

    @ManyToOne
    private Club club;

    public Profiler() {
    }

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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

}
