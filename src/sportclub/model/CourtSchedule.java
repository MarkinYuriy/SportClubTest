package sportclub.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class CourtSchedule {
	@Id @GeneratedValue
	int id;
	@ManyToOne(cascade=CascadeType.ALL) 
   	Slot slot;
	@ManyToOne(cascade=CascadeType.ALL)  
	Court court;
	
	int courtPartitionType;
	
	@OneToOne
	Event courtPart1;
	@OneToOne
	Event courtPart2;
	@OneToOne
	Event courtPart3;
	
	public Slot getSlot() {
		return slot;
	}
	public void setSlot(Slot slot) {
		this.slot = slot;
	}
	public Court getCourt() {
		return court;
	}
	public void setCourt(Court court) {
		this.court = court;
	}
	public int getCourtPartitionType() {
		return courtPartitionType;
	}
	public void setCourtPartitionType(int fieldPartitionType) {
		this.courtPartitionType = fieldPartitionType;
	}
	
	
	public Event getCourtPart1() {
		return courtPart1;
	}
	public void setCourtPart1(Event courtPart1) {
		this.courtPart1 = courtPart1;
	}
	public Event getCourtPart2() {
		return courtPart2;
	}
	public void setCourtPart2(Event courtPart2) {
		this.courtPart2 = courtPart2;
	}
	public Event getCourtPart3() {
		return courtPart3;
	}
	public void setCourtPart3(Event courtPart3) {
		this.courtPart3 = courtPart3;
	}
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "CourtSchedule [id=" + id + ", slot=" + slot + ", court=" + court + ", courtPartitionType="
				+ courtPartitionType + ", courtPart1=" + courtPart1 + ", courtPart2=" + courtPart2 + ", courtPart3="
				+ courtPart3 + "]";
	}
}
