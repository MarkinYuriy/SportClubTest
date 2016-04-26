package sportclub.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import sportclub.profile.*;

@Entity
public class StartStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int staffId;
    private boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @ManyToOne
    private Formation formation;

    @OneToMany
    @JoinTable(
            name = "reserveProfile",
            joinColumns = {
                @JoinColumn(name = "staffId", referencedColumnName = "staffId")},
            inverseJoinColumns = {
                @JoinColumn(name = "profilerId", referencedColumnName = "profilerId", unique = true)}
    )
    private List<Athlete> reserve;

    @OneToMany
    @JoinTable(name = "staffCoach",
            joinColumns = {
                @JoinColumn(name = "staffId", referencedColumnName = "staffId")},
            inverseJoinColumns = {
                @JoinColumn(name = "profilerId", referencedColumnName = "profilerId", unique = true)})
    private List<Coach> coaches;

    public List<Athlete> getReserve() {
        return reserve;
    }

    public void setReserve(List<Athlete> reserve) {
        this.reserve = reserve;
    }

    @OneToMany
    private Map<String, Athlete> composition = new HashMap<String, Athlete>();

    public StartStaff() {

    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public List<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(List<Coach> coaches) {
        this.coaches = coaches;
    }

    public int getStaffId() {
        return staffId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + staffId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        StartStaff other = (StartStaff) obj;
        if (staffId != other.staffId) {
            return false;
        }
        return true;
    }

    public Map<String, Athlete> getComposition() {
        return composition;
    }

    public void setCompositionKeysByFormation(Formation formation) {

        String[] sch = formation.getPositions().split(" ");
        for (String s : sch) {
            composition.put(s, null);
        }
    }

    public void setComposition(Map<String, Athlete> composition) {
        this.composition = composition;
    }

}
