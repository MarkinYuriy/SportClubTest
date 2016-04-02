package sportclub.model;


import java.util.Date;

import javax.persistence.*;

@Entity
public class Slot {
	
	@Id
	@GeneratedValue
	int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date startTime;
	@Temporal(TemporalType.TIMESTAMP)
	Date endTime;
	
	public Slot() {	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	

}
