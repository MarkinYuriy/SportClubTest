package sportclub.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class CourtSchedule {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ManyToOne(cascade=CascadeType.REFRESH) 
	private Slot slot;
	@ManyToOne(cascade=CascadeType.REFRESH)  
	private Court court;
	
	private int courtPartitionType;//add table CourtPartitionType
	
	@OneToOne
	private Event courtPart1;
	@OneToOne
	private Event courtPart2;
	@OneToOne
	private Event courtPart3;
	
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
