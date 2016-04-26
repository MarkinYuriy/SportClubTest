package sportclub.profile;

import java.util.Map;
import javax.persistence.*;
import sportclub.model.License;

@Entity
public class AdminManagerClub extends Profiler {

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public AdminManagerClub() {
        super();
    }

    @OneToOne
    License license;

    public void setProperties(Map<String, String> properties) {

        super.setProperties(properties);

    }

}
